package com.example.security.repository;

import com.example.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

//기본 crud를 JpaRepository가 들고있다
//repository 어노테이션 안해도 된다 JpaRepository상속했기때문에
public interface UserRepository extends JpaRepository<User, Long> {

    // select * from user where username=?
    // jpa 쿼리 메서드
    public User findByUsername(String username);

//    public User findByEmail();
}
