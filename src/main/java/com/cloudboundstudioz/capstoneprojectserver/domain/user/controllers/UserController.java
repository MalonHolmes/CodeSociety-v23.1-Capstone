package com.cloudboundstudioz.capstoneprojectserver.domain.user.controllers;

import com.cloudboundstudioz.capstoneprojectserver.domain.user.models.User;
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
    public ResponseEntity<User> create(@RequestBody User user) {
        user = userService.create(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    // GET requests

    @GetMapping
    public ResponseEntity<List<User>> getAll(){
        List<User> users = userService.getAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getById(@PathVariable("id") Long id) {
        User user = userService.getById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // TODO - getByEmail() and getByUsername()??
    // Dont need them because it's all included in the id

    // PUT requests
    @PutMapping("{id}")
    public ResponseEntity<User> update(@PathVariable("id") Long id, @RequestBody User userDetail){
        userDetail = userService.updateUser(id, userDetail);
        return new ResponseEntity<>(userDetail, HttpStatus.ACCEPTED);
    }

    // DELETE requests
    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable("id") Long id){
        userService.deleteUser(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
