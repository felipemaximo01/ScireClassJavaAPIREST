package com.fatec.scireclass.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

import com.fatec.scireclass.model.Certificado;
import com.fatec.scireclass.model.Matricula;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;


public class CertificadoServiceImpl implements CertificadoService {

    private Certificado pdf =new Certificado();
    private String caminhoImagem="src/main/resources/img/logo_aprendex.png";

    private final String caminho="src/main/java/com/fatec/scireclass/certificado";
    public CertificadoServiceImpl(Matricula matricula){

        String caminhoc=caminho+"/Certificado"+matricula.getNumeroMatricula()+".pdf";
        pdf.setCaminho(caminhoc);
        pdf.setNome("Certificado"+matricula.getNumeroMatricula()+".pdf");
        pdf.setMatricula(matricula);
        pdf.setCertificado(new Document());
        try{
            PdfWriter.getInstance(pdf.getCertificado(),new FileOutputStream(pdf.getCaminho()));

            pdf.getCertificado().open();
            Image logo = Image.getInstance(caminhoImagem);
            logo.setAlignment(Element.ALIGN_CENTER);
            pdf.getCertificado().add(new Paragraph(" "));
            pdf.getCertificado().add(new Paragraph(" "));
            pdf.getCertificado().add(logo);
            pdf.getCertificado().add(new Paragraph(" "));
            pdf.getCertificado().add(new Paragraph(" "));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
    @Override
    public void gerarCabecalho() {

        Paragraph paragrafoTitulo=new Paragraph();
        paragrafoTitulo.setAlignment(Element.ALIGN_CENTER);
        paragrafoTitulo.add(new Chunk("CERTIFICADO "+pdf.getMatricula().getCurso().getNome(),new Font(Font.HELVETICA,24)));
        pdf.getCertificado().add(paragrafoTitulo);
        pdf.getCertificado().add(new Paragraph(" "));
        pdf.getCertificado().add(new Paragraph(" "));
        pdf.getCertificado().add(new Paragraph(" "));
    }

    @Override
    public void gerarCorpo() {

        Paragraph paragrafoCorpo=new Paragraph();
        paragrafoCorpo.setAlignment(Element.ALIGN_JUSTIFIED);
        paragrafoCorpo.add(new Chunk("Certificamos que "+pdf.getMatricula().getAluno().getNome()+", portador(a)  do documento CPF "+pdf.getMatricula().getAluno().getCpf()+", concluiu com aproveitamento o curso acima identificado, no período de "+pdf.getMatricula().getDataInicio().format(DateTimeFormatter.ofPattern("dd/MM/yy"))+" à "+pdf.getMatricula().getDataFim().format(DateTimeFormatter.ofPattern("dd/MM/yy"))+".",new Font(Font.HELVETICA,16)));
        pdf.getCertificado().add(paragrafoCorpo);
        pdf.getCertificado().add(new Paragraph(" "));

    }

    @Override
    public void gerarRodape() {

        Paragraph paragrafoRodape=new Paragraph();
        paragrafoRodape.setAlignment(Element.ALIGN_JUSTIFIED);
        paragrafoRodape.add(new Chunk("Diadema, " + pdf.getMatricula().getDataFim(), new Font(Font.HELVETICA,16)));
        Paragraph paragrafoassinatura=new Paragraph();
        paragrafoassinatura.setAlignment(Element.ALIGN_CENTER);
        paragrafoassinatura.add(new Chunk("___________________________", new Font(Font.HELVETICA,16)));
        pdf.getCertificado().add(new Paragraph(" "));
        pdf.getCertificado().add(new Paragraph(" "));
        pdf.getCertificado().add(new Paragraph(" "));
        pdf.getCertificado().add(new Paragraph(" "));
        pdf.getCertificado().add(paragrafoRodape);
        pdf.getCertificado().add(new Paragraph(" "));
        pdf.getCertificado().add(new Paragraph(" "));
        pdf.getCertificado().add(new Paragraph(" "));
        pdf.getCertificado().add(new Paragraph(" "));
        pdf.getCertificado().add(new Paragraph(" "));
        pdf.getCertificado().add(paragrafoassinatura);
        pdf.getCertificado().add(new Paragraph(" "));


    }

    @Override
    public Certificado imprimir() {
        gerarCabecalho();
        gerarCorpo();
        gerarRodape();
        if(this.pdf.getCertificado()!=null && this.pdf.getCertificado().isOpen()){
            pdf.getCertificado().close();
            return pdf;
        }
        return null;



    }
}
