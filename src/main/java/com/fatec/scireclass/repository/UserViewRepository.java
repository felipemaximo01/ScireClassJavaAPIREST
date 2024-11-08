package com.fatec.scireclass.repository;

import com.fatec.scireclass.model.UserView;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserViewRepository extends MongoRepository<UserView, String> {
    List<UserView> findUserViewByUsuario(String usuario);
}
