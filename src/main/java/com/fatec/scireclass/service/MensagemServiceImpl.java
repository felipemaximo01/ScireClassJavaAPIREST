package com.fatec.scireclass.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.scireclass.model.Chat;
import com.fatec.scireclass.model.Mensagem;
import com.fatec.scireclass.model.Usuario;
import com.fatec.scireclass.model.dto.MensagemDTO;
import com.fatec.scireclass.model.mapper.MensagemMapper;
import com.fatec.scireclass.repository.ChatRepository;
import com.fatec.scireclass.repository.MensagemRepository;
import com.fatec.scireclass.repository.UsuarioRepository;
import com.fatec.scireclass.service.exceptions.ChatNotFoundException;
import com.fatec.scireclass.service.exceptions.UsuarioNotFoundException;
import com.fatec.scireclass.service.exceptions.UsuarioUnauthorizedException;

@Service
public class MensagemServiceImpl implements MensagemService {

    @Autowired
    private MensagemRepository mensagemRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Mensagem saveMensagem(Mensagem mensagem) {
        return mensagemRepository.save(mensagem);
    }

    @Override
    public Mensagem getMensagem(String id) {
        return mensagemRepository.findMensagemById(id);
    }

    @Override
    public void deleteMensagem(String id){
        mensagemRepository.deleteById(id);
    }

    @Override
    public Mensagem sendMensagem(MensagemDTO mensagemDTO, String chatID, String usuarioID) {
        Chat chat = chatRepository.findChatById(chatID);
        if(chat == null)
            throw new ChatNotFoundException("o chat com o id: " + chatID + " não foi encontrado");
        Usuario usuario = usuarioRepository.findUsuarioById(usuarioID);
        if(usuario == null)
            throw new UsuarioNotFoundException("O usuário com ID: " + usuarioID + " não foi encontrado");
        if(usuario.getId() != chat.getAluno().getId() && usuario.getId() != chat.getProfessor().getId())
            throw new UsuarioUnauthorizedException("O usuário com ID: " + usuarioID + " não tem permissão para usar esse chat");
        
        Mensagem mensagem = new Mensagem();

        mensagem.setChat(chat);
        mensagem.setUsuario(usuario);
        mensagem.setInstante(Instant.now());
        mensagem.setMensagens(mensagemDTO.getMensagens());

        mensagem = mensagemRepository.save(mensagem);

        chat.getMensagens().add(mensagem);
        
        chatRepository.save(chat);

        return mensagem;
    }

    @Override
    public List<MensagemDTO> getMensagens(String chatID) {
        Chat chat = chatRepository.findChatById(chatID);
        if(chat == null)
            throw new ChatNotFoundException("O chat com ID: " + chatID+ " não foi encontrado");
        List<Mensagem> mensagens = mensagemRepository.findByChat(chat);
        List<MensagemDTO> mensagensDTO = new ArrayList<>();
        for (Mensagem mensagem : mensagens) {
            mensagensDTO.add(MensagemMapper.mensagemToMensagemDTO(mensagem));
        }
        return mensagensDTO;
    }
    
}
