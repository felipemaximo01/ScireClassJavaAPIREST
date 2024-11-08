package com.fatec.scireclass.service;

import com.fatec.scireclass.model.UserView;
import org.springframework.stereotype.Service;

@Service
public interface UserViewService {
    public UserView save(UserView userViews);
}
