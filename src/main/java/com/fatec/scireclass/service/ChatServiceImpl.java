package com.fatec.scireclass.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.scireclass.model.Chat;
import com.fatec.scireclass.model.Mensagem;
import com.fatec.scireclass.model.Usuario;
import com.fatec.scireclass.repository.ChatRepository;
import com.fatec.scireclass.repository.MensagemRepository;
import com.fatec.scireclass.repository.UsuarioRepository;
import com.fatec.scireclass.service.exceptions.UsuarioNotFoundException;

@Service
public class ChatServiceImpl implements ChatService{

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private MensagemRepository mensagemRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private static String MENSAGEMHELLO = "Olá professor eu gostaria de me matricular no seu curso !";

    @Override
    public Chat saveChat(Chat chat) {
        return chatRepository.save(chat);
    }

    @Override
    public Chat getChat(String id) {
        return chatRepository.findById(id).get();
    }

    @Override
    public void deleteChat(String id){
        chatRepository.deleteById(id);
    }

    @Override
    public Chat createChat(String alunoID, String professorID) {
        Usuario aluno = usuarioRepository.findById(alunoID).get();
        if(aluno == null)
            throw new UsuarioNotFoundException("O usuário com ID: " + alunoID + " não foi encontrado");
        Usuario professor = usuarioRepository.findById(professorID).get();
        if(professor == null)
            throw new UsuarioNotFoundException("O usuário com ID: " + professorID + " não foi encontrado");

        Chat chat = new Chat();

        chat.setAluno(aluno);
        chat.setProfessor(professor);
        
        chat = chatRepository.save(chat);

        Mensagem mensagemHello = new Mensagem();

        mensagemHello.setChat(chat);
        mensagemHello.setUsuario(aluno);
        mensagemHello.setMensagens(MENSAGEMHELLO);
        mensagemHello.setInstante(Instant.now());

        mensagemHello = mensagemRepository.save(mensagemHello);

        chat.getMensagens().add(mensagemHello);

        return chatRepository.save(chat);
    }
    
}
