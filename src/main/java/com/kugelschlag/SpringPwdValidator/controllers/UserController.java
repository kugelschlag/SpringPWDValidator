package com.kugelschlag.SpringPwdValidator.controllers;

import com.kugelschlag.SpringPwdValidator.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 *
 * UserController is just a very simple REST controller used to access testing of a custom password validator
 * It maps the URI to Post a User object that has been tagged for validation
 * On validation success, it will return a 200 code and a ResponseEntity with the User object
 * On validation failure, it will return a 400 code with a string array of messages describing the validation errors
 *
 */
@RestController
@RequestMapping(value = {"/v1/user", "/v1/users"})
public class UserController {


    /**
     * Http Post insert a new user
     * Valid applies the check to all fields in the User object tagged for validation
     *
     * @return ResponseEntity
     */
    @PostMapping("")
    public ResponseEntity<User> addAUser(@RequestBody @Valid User user) {

        //Just to make the response customizable and readable, depending on load, this may be optionally verbose
        HashMap map = new HashMap<>();

            map.put("msg", "User validation successful ");
            map.put("number_user_validated", 1);
            map.put("user", user);

            //HTTP 200 OK
            return new ResponseEntity<User>(user, HttpStatus.OK);

    }

    //Error handler
    //Customize the validation failure message
    //This is the interceptor that handles the 400 code/Bad Request code if validation fails

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<String> handleValidationExceptions(MethodArgumentNotValidException ex) {

        return ex.getBindingResult()
                .getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList()) ;
    }

}
