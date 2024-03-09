package com.fatec.scireclass.service;

import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.fatec.scireclass.model.Certificado;
import com.fatec.scireclass.model.Matricula;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class CertificadoServicelImpl implements CertificadoService {

    private Certificado certificado = new Certificado();

    private static final String CAMINHOIMAGEM = "src/main/resources/img/logo_aprendex.png";
    private static final String CAMINHO = "src/main/resouces/certificados";

    public CertificadoServicelImpl(Matricula matricula){

        String caminhoc=CAMINHO+"/Certificado"+matricula.getNumeroMatricula()+".pdf";
        certificado.setCaminho(caminhoc);
        certificado.setNome("Certificado"+matricula.getNumeroMatricula()+".pdf");
        certificado.setMatricula(matricula);
        certificado.setDocument(new Document());
        try{
            PdfWriter.getInstance(certificado.getDocument(),new FileOutputStream(certificado.getCaminho()));

            certificado.getDocument().open();
            Image logo = Image.getInstance(CAMINHOIMAGEM);
            logo.setAlignment(Element.ALIGN_CENTER);
            certificado.getDocument().add(new Paragraph(" "));
            certificado.getDocument().add(new Paragraph(" "));
            certificado.getDocument().add(logo);
            certificado.getDocument().add(new Paragraph(" "));
            certificado.getDocument().add(new Paragraph(" "));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void gerarCabecalho() {
        Paragraph paragrafoTitulo = new Paragraph();
        paragrafoTitulo.setAlignment(Element.ALIGN_CENTER);
        paragrafoTitulo.add(new Chunk("CERTIFICADO "+certificado.getMatricula().getCurso().getNome(),new Font(Font.HELVETICA,24)));
        certificado.getDocument().add(paragrafoTitulo);
        certificado.getDocument().add(new Paragraph(" "));
        certificado.getDocument().add(new Paragraph(" "));
        certificado.getDocument().add(new Paragraph(" "));
    }
    @Override
    public void gerarCorpo() {
        Paragraph paragrafoCorpo = new Paragraph();
        paragrafoCorpo.setAlignment(Element.ALIGN_JUSTIFIED);
        paragrafoCorpo.add(new Chunk("Certificamos que "+certificado.getMatricula().getAluno().getNome()+" "+certificado.getMatricula().getAluno().getSobrenome()+", portador(a)  do documento CPF "+certificado.getMatricula().getAluno().getCpf()+", concluiu com aproveitamento o curso acima identificado, com duração de "+certificado.getMatricula().getCurso().getDuracao()+" horas, no período de "+certificado.getMatricula().getDataInicio()+" à "+certificado.getMatricula().getDataFim()+".",new Font(Font.HELVETICA,16)));
        certificado.getDocument().add(paragrafoCorpo);
        certificado.getDocument().add(new Paragraph(" "));
    }
    @Override
    public void gerarRodape() {
        Paragraph paragrafoRodape=new Paragraph();
        paragrafoRodape.setAlignment(Element.ALIGN_JUSTIFIED);
        paragrafoRodape.add(new Chunk("Diadema, " + certificado.getMatricula().getDataFim(), new Font(Font.HELVETICA,16)));
        Paragraph paragrafoassinatura=new Paragraph();
        paragrafoassinatura.setAlignment(Element.ALIGN_CENTER);
        paragrafoassinatura.add(new Chunk("___________________________", new Font(Font.HELVETICA,16)));
        certificado.getDocument().add(new Paragraph(" "));
        certificado.getDocument().add(new Paragraph(" "));
        certificado.getDocument().add(new Paragraph(" "));
        certificado.getDocument().add(new Paragraph(" "));
        certificado.getDocument().add(paragrafoRodape);
        certificado.getDocument().add(new Paragraph(" "));
        certificado.getDocument().add(new Paragraph(" "));
        certificado.getDocument().add(new Paragraph(" "));
        certificado.getDocument().add(new Paragraph(" "));
        certificado.getDocument().add(new Paragraph(" "));
        certificado.getDocument().add(paragrafoassinatura);
        certificado.getDocument().add(new Paragraph(" "));
    }
    @Override
    public Certificado imprimir() {
        gerarCabecalho();
        gerarCorpo();
        gerarRodape();
        if(this.certificado.getDocument()!=null && this.certificado.getDocument().isOpen()){
            certificado.getDocument().close();
            return certificado;
        }
        return null;
    }

}
