package com.ssafy.db.dto;
import com.ssafy.db.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private String email;

    private String password;

    private Boolean gender;

    private String nickname;

    private Integer age;




    public User toEntity() {
        User user = User.builder()
                .email(email)
                .password(password)
                .gender(gender)
                .nickname(nickname)
                .age(age)
                .build();
        return user;
    }
}