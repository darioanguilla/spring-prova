package com.example.users;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService service;
    
    @GetMapping("/users")
    public List<User> list(){
        return service.listAll();
    }

    @GetMapping("/users/{user_id}")
    public ResponseEntity<User> get(@PathVariable Integer user_id){

       try{
           User user = service.get(user_id);
           return new ResponseEntity<User>(user, HttpStatus.OK);
       } catch (NoSuchElementException e) {
           return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
       }

    }

    @PostMapping("/users")
    public void add(@RequestBody User user){
        service.save(user);
    }

    @PutMapping("/users/{user_id}")
    public ResponseEntity<User> update(@RequestBody User user, @PathVariable Integer user_id){
        try{
            User user_old = service.get(user_id);
            user.setUser_id(user_id);
            service.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/users/{user_id}")
    public ResponseEntity<?> delete(@PathVariable Integer user_id){
        try {

            service.delete(user_id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
