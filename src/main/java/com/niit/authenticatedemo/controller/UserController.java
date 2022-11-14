package com.niit.authenticatedemo.controller;

import com.niit.authenticatedemo.domain.User;
import com.niit.authenticatedemo.exception.UserNotFoundException;
import com.niit.authenticatedemo.services.ServiceTokenGenerator;
import com.niit.authenticatedemo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
//@RequestMapping("/userdata/v1/")
public class UserController {
    private UserService userServices;
    private ServiceTokenGenerator securityTokenGenerator;

    @Autowired
    public UserController(UserService userServices,ServiceTokenGenerator securityTokenGenerator ) {
        this.userServices = userServices;
        this.securityTokenGenerator=securityTokenGenerator;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user)throws UserNotFoundException{
        Map<String,String> map=null;
        try{
            User user1=userServices.findByUsernameAndPassword(user.getUsername(), user.getPassword());
            if(user1.getUsername().equals(user.getUsername())){
                map=securityTokenGenerator.generateToken(user);
            }
            return new ResponseEntity<>(map,HttpStatus.OK);
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException();
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Try after sometime",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        User createdUser = userServices.addUser(user);
        return new ResponseEntity(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/userdata/v1/fetchusers")
    public ResponseEntity<?> getAllUsers(){
        List<User> userList=userServices.getAllUsers();
        return new ResponseEntity<>(userList,HttpStatus.OK);
    }
    @DeleteMapping("/userdata/v1/{userid}")
    public ResponseEntity<?> deleteUser(@PathVariable int userid) throws UserNotFoundException {
        ResponseEntity responseEntity = null;
        try{
            userServices.deleteUser(userid);
            responseEntity = new ResponseEntity<>("successfully deleted one record",HttpStatus.OK);
        }catch (UserNotFoundException e){
            throw new UserNotFoundException();
        }catch (Exception e){
            responseEntity=new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}