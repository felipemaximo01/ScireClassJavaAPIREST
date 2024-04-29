package com.fatec.scireclass.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.scireclass.model.Chat;
import com.fatec.scireclass.model.Mensagem;
import com.fatec.scireclass.model.Usuario;
import com.fatec.scireclass.model.dto.ChatDTO;
import com.fatec.scireclass.model.enums.Perfil;
import com.fatec.scireclass.model.mapper.ChatMapper;
import com.fatec.scireclass.repository.ChatRepository;
import com.fatec.scireclass.repository.MensagemRepository;
import com.fatec.scireclass.repository.UsuarioRepository;
import com.fatec.scireclass.service.exceptions.UsuarioNotFoundException;
import com.fatec.scireclass.service.exceptions.UsuarioUnauthorizedException;

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
        return chatRepository.findChatById(id);
    }

    @Override
    public void deleteChat(String id){
        chatRepository.deleteById(id);
    }

    @Override
    public Chat createChat(String alunoID, String professorID) {
        Usuario aluno = usuarioRepository.findUsuarioById(alunoID);
        if(aluno == null)
            throw new UsuarioNotFoundException("O usuário com ID: " + alunoID + " não foi encontrado");
        if(aluno.getPerfil() != Perfil.ALUNO)
            throw new UsuarioUnauthorizedException("O usuário enviado não é um aluno");
        Usuario professor = usuarioRepository.findUsuarioById(professorID);
        if(professor == null)
            throw new UsuarioNotFoundException("O usuário com ID: " + professorID + " não foi encontrado");
        if(professor.getPerfil() != Perfil.PROFESSOR)
            throw new UsuarioUnauthorizedException("O usuário enviado não é um professor");

        List<Chat> chats = chatRepository.findByAlunoAndProfessor(aluno, professor);

        Chat chat = new Chat();

        if(chats != null && chats.size() > 0){
             chat = chatRepository.findById(chats.get(0).getId()).get();
        }else{
            chat.setAluno(aluno);
            chat.setProfessor(professor);
            
            chat = chatRepository.save(chat);
        }
        Mensagem mensagemHello = new Mensagem();
    
        mensagemHello.setChat(chat);
        mensagemHello.setUsuario(aluno);
        mensagemHello.setMensagens(MENSAGEMHELLO);
        mensagemHello.setInstante(Instant.now());
    
        mensagemHello = mensagemRepository.save(mensagemHello);
    
        chat.getMensagens().add(mensagemHello);
    
        return chatRepository.save(chat);
    }

    @Override
    public List<ChatDTO> getChats(String usuarioID) {
        Usuario usuario = usuarioRepository.findUsuarioById(usuarioID);
        if(usuario == null)
            throw new UsuarioNotFoundException("O usuário com ID: " +usuarioID+" não foi encontrado");
        List<Chat> chats = chatRepository.findByAlunoOrProfessor(usuario);
        List<ChatDTO> chatsDTO = new ArrayList<>();
        for (Chat chat : chats) {
            chatsDTO.add(ChatMapper.ChatToChatDTO(chat));
        }
        return chatsDTO;
    }
    
}
