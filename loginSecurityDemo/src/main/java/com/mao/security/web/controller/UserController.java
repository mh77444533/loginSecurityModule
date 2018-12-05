package com.mao.security.web.controller;

import com.mao.security.web.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/me")
    public Object getCurrentUser(@AuthenticationPrincipal UserDetails user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return user;
    }

    @GetMapping("/jwt")
    public Object getCurrentUserJwt(Authentication user, HttpServletRequest request) throws UnsupportedEncodingException {
        String token = request.getParameter("access_token");

        JwtParser jwtParser = Jwts.parser().setSigningKey(("xushihui").getBytes("UTF-8")); //这里是签名的秘钥

        Claims claims = jwtParser.parseClaimsJws(token).getBody();

        String company = (String)claims.get("company");

        return claims;
    }


    @PostMapping("/listPost")
    public List<User> getInfoPost (){
        return getUserList();
    }

    @GetMapping("/list")
    public List<User> getInfolist (){
       return getUserList();
    }


    @GetMapping("/info/{id:\\d+}")
    public User getInfo (@PathVariable String id){
        User user =new User();
        user.setId(id);
        user.setName("tom");
        user.setUsername("tom");
        return user;
    }


    public List<User> getUserList(){
        List<User> users = new ArrayList<User>();
        User user =new User();
        user.setId("1111");
        user.setName("茅华");
        user.setUsername("maohua");
        users.add(user);
        User user2 =new User();
        user2.setId("222");
        user2.setName("茅家心");
        user2.setUsername("maojiaxin");
        users.add(user2);
        User user3 =new User();
        user3.setId("333");
        user3.setName("徐世慧");
        user3.setUsername("徐世慧");
        users.add(user3);
        return users;
    }


}
