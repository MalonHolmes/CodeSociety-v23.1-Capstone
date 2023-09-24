package com.cloudboundstudioz.capstoneprojectserver.domain.user.controllers;

import com.cloudboundstudioz.capstoneprojectserver.domain.user.models.UserModel;
import com.cloudboundstudioz.capstoneprojectserver.domain.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // POST requests
    @PostMapping
    public ResponseEntity<UserModel> create(@RequestBody UserModel userModel) {
        userModel = userService.create(userModel);
        return new ResponseEntity<>(userModel, HttpStatus.CREATED);
    }

    // GET requests

    @GetMapping
    public ResponseEntity<List<UserModel>> getAll(){
        List<UserModel> userModelEntities = userService.getAll();
        return new ResponseEntity<>(userModelEntities, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserModel> getById(@PathVariable("id") Long id) {
        UserModel userModel = userService.getById(id);
        return new ResponseEntity<>(userModel, HttpStatus.OK);
    }

    // TODO - getByEmail() and getByUsername()??
    // Dont need them because it's all included in the id

    // PUT requests
    @PutMapping("{id}")
    public ResponseEntity<UserModel> update(@PathVariable("id") Long id, @RequestBody UserModel userModelDetail){
        userModelDetail = userService.updateUser(id, userModelDetail);
        return new ResponseEntity<>(userModelDetail, HttpStatus.ACCEPTED);
    }

    // DELETE requests
    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable("id") Long id){
        userService.deleteUser(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
