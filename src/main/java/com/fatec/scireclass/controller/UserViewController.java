package com.fatec.scireclass.controller;

import com.fatec.scireclass.model.UserView;
import com.fatec.scireclass.service.UserViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userview")
public class UserViewController {
    @Autowired
    private UserViewService userViewService;

    @PostMapping
    public ResponseEntity<UserView> saveUserView(@RequestBody UserView userView) {
        return new  ResponseEntity<>(userViewService.save(userView), HttpStatus.OK);
    }
}
