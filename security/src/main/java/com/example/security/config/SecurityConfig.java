package com.example.security.config;

import com.example.security.oauth.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;

//구글 로그인 이후 후처리 필요
//1. 코드받기(인증), 2.액세스토큰(권한)
//3.사용자프로필정보 조회
//4.정보 토대로 가입 or 로그인
@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록된다
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public PrincipalOauth2UserService principalOauth2UserService;


    @Bean //해당 매서드의 리턴되는 오브젝트를 ioC로 등록해준다.
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated() //user주소로 들어가려면 인증돼있어야한다
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/loginForm")
                .loginProcessingUrl("/login") //login 주소 호출시 시큐리티가 대신 로그인
                .defaultSuccessUrl("/")
                .and()
                .oauth2Login()
                .loginPage("/loginForm")
                .userInfoEndpoint()
                .userService(principalOauth2UserService);

    }
}
