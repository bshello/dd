package com.example.security.controller;

import com.example.security.auth.PrincipalDetails;
import com.example.security.model.User;
import com.example.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping({ "", "/" })
    public String index() {
        return "index";
    }
    @GetMapping("/user")
    public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        System.out.println("principalDetails:" + principalDetails.getUser());
        return "user";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(User user) {
        System.out.println(user);
        user.setRole("ROLE_USER");
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        userRepository.save(user); //이상태로는 비밀번호가 암호화가 안돼서 시큐리티 로그인이 안됨
        return "redirect:/loginForm";
    }

    @GetMapping("test/login")
    public @ResponseBody String testLogin( //일반로그인시 UserDetails
            Authentication authentication,
            @AuthenticationPrincipal PrincipalDetails userDetails) {
        System.out.println("/test/login============");
        PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
        System.out.println("authentication:" + principalDetails.getUser());  //2가지 방식으로 유저정보 가져오기
        System.out.println("userDetails:" + userDetails.getUser().getEmail());
        return "세션정보 확인";
    }

    @GetMapping("test/oauth/login")
    public @ResponseBody String testOauthLogin( //소셜로그인시 oauth2user
            Authentication authentication,
            @AuthenticationPrincipal OAuth2User oAuth2) {
        System.out.println("/test/login============");
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        System.out.println("authentication:" + oAuth2User.getAttributes());  //2가지 방식으로 유저정보 가져오기
        System.out.println("oauth2User:" + oAuth2.getAttributes());
        return "oauth 세션정보 확인";
    }
}
