package com.fatec.scireclass.service.implementation;

import com.fatec.scireclass.model.UserView;
import com.fatec.scireclass.repository.UserViewRepository;
import com.fatec.scireclass.service.UserViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserViewServiceImpl implements UserViewService {
    @Autowired
    private UserViewRepository userViewsRepository;

    @Override
    public UserView save(UserView userViews) {
        if(userViews.getData() != null && userViews.getCurso() != null && userViews.getUsuario() != null)
            return userViewsRepository.save(userViews);
        return null;
    }
}
