package com.kugelschlag.SpringPwdValidator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = {"/", "/v1"})
public class DefaultController {


    @Value("${default.endpoint.msg}")
    private String defaultMsg;


    @GetMapping("")
    public ResponseEntity defaultCatch() {
        //Will get auto-converted to JSON by Jackson on return
        HashMap map = new HashMap<>();
        map.put("msg", defaultMsg);
        return new ResponseEntity<Map>(map, HttpStatus.OK);
    }
}