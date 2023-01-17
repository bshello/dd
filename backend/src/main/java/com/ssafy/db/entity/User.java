package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.netty.handler.codec.socks.SocksAuthRequest;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * 유저 모델 정의.
 */
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
@Builder
public class User {
    @JsonIgnore
    @Id
    @Column(name = "user_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long userNo;

    @Column(name = "email", nullable = false, length = 30, unique = true)
    String email;

    @JsonIgnore
    @Column(name = "password", length = 30)
    String password;

    @Column(name = "gender")
    Boolean gender;

    @Column(name = "nickname")
    String nickname;

    @Column(name = "age")
    Integer age;

    @OneToMany(mappedBy = "user")
    private List<Interest> interests;

}
