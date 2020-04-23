package com.testinium.sample.service;

import com.testinium.sample.model.User;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Component
public class UserService {

    @Autowired
    RestTemplateBuilder builder;


    @Value( "${baseURL}" )
    String baseURL;


    public User saveUser(User user){
        User returnUser = builder.build().postForObject(baseURL + "/rest/user/add",user, User.class);
        return returnUser;
    }

    public List<User> getUserList(){
       List<User> userList = builder.build().getForObject(baseURL + "/rest/user/list", List.class);
       return userList;
    }

    public User findByUser(Long userId){
        User user = builder.build().getForObject(baseURL + "/rest/user/" + userId, User.class);
        return user;
    }

    public List<User> kullaniciListesi(){
        List<User> userList = builder.build().getForObject(baseURL + "/kullanici",  List.class);
        return userList;
    }

}
