package com.kugelschlag.SpringPwdValidator;

import com.kugelschlag.SpringPwdValidator.controllers.UserController;
import com.kugelschlag.SpringPwdValidator.models.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,

        properties = {
                "validation.fubar.allowed=false",
                "validation.length.min=5",
                "validation.length.max=15",
                "validation.repeat.max=4",
                "validation.digit.min=2",
                "validation.lowercase.min=2",
                "default.endpoint.msg=Charter Co. Password Validator Here! Use login page!"
        })


public class SpringPwdValidatorApplicationTests {

    @Autowired
    private UserController controller;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Value("${default.endpoint.msg}")
    private String defaultMsg;

    @Test
    public void testUserCreation() {
        User user = new User("Alice", "1testfubar");
        assertNotNull(user);

    }

    @Test
    public void testControllerExists() throws Exception {
        assertNotNull(controller);


    }

    //Integration tests
    @Test
    public void testShouldReturnDefaultMessage() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/", String.class)).contains(defaultMsg);
    }


    @Test
    public void testShouldReturnValidatedUser() throws Exception {
        String validPW = "1test1";
        HttpEntity<User> request = new HttpEntity<User>(new User("bar", validPW));
        ResponseEntity<User> response = restTemplate.exchange("http://localhost:" + port + "/v1/users", HttpMethod.POST, request, User.class);
        HttpStatus status = response.getStatusCode();
        assertThat(status.is2xxSuccessful());

        System.out.println("***********  Valid User: " + response.getBody().toString());

    }

    @Test
    public void testShouldFailPWMinLength() throws Exception {
        String invalidPW = "11aa";
        HttpEntity<User> request = new HttpEntity<User>(new User("bar", invalidPW));
        ResponseEntity<String[]> response = restTemplate.exchange("http://localhost:" + port + "/v1/users", HttpMethod.POST, request, String[].class);
        HttpStatus status = response.getStatusCode();

        assertThat(status.is4xxClientError());
        List<String> stringList = new ArrayList<String>(Arrays.asList(response.getBody()));
        stringList.forEach(item -> System.out.println("************ Expected Error String: Min Length " + item));
    }


    @Test
    public void testShouldFailPWMaxLength() throws Exception {
        String invalidPW = "11acbsfsfgsfdgsdfgdfg";

        HttpEntity<User> request = new HttpEntity<User>(new User("bar", invalidPW));
        ResponseEntity<String[]> response = restTemplate.exchange("http://localhost:" + port + "/v1/users", HttpMethod.POST, request, String[].class);
        HttpStatus status = response.getStatusCode();

        assertThat(status.is4xxClientError());

        List<String> stringList = new ArrayList<String>(Arrays.asList(response.getBody()));
        stringList.forEach(item -> System.out.println("************ Expected Error String: Max Length " + item));

    }


    @Test
    public void testShouldFailPWMinLowerCase() throws Exception {
        String invalidPW = "111111";

        HttpEntity<User> request = new HttpEntity<User>(new User("bar", invalidPW));
        ResponseEntity<String[]> response = restTemplate.exchange("http://localhost:" + port + "/v1/users", HttpMethod.POST, request, String[].class);
        HttpStatus status = response.getStatusCode();

        assertThat(status.is4xxClientError());

        List<String> stringList = new ArrayList<String>(Arrays.asList(response.getBody()));
        stringList.forEach(item -> System.out.println("************ Expected Error String: Min Lowercase " + item));

    }

    @Test
    public void testShouldFailPWMaxRepeat() throws Exception {
        String invalidPW = "11aaaa";

        HttpEntity<User> request = new HttpEntity<User>(new User("bar", invalidPW));
        ResponseEntity<String[]> response = restTemplate.exchange("http://localhost:" + port + "/v1/users", HttpMethod.POST, request, String[].class);
        HttpStatus status = response.getStatusCode();

        assertThat(status.is4xxClientError());

        List<String> stringList = new ArrayList<String>(Arrays.asList(response.getBody()));
        stringList.forEach(item -> System.out.println("************ Expected Error String: Max Repeat " + item));

    }

    @Test
    public void testShouldFailPWMinDigits() throws Exception {
        String invalidPW = "aabbccdd";

        HttpEntity<User> request = new HttpEntity<User>(new User("bar", invalidPW));
        ResponseEntity<String[]> response = restTemplate.exchange("http://localhost:" + port + "/v1/users", HttpMethod.POST, request, String[].class);
        HttpStatus status = response.getStatusCode();

        assertThat(status.is4xxClientError());

        List<String> stringList = new ArrayList<String>(Arrays.asList(response.getBody()));
        stringList.forEach(item -> System.out.println("************ Expected Error String: Min Digits " + item));

    }
}