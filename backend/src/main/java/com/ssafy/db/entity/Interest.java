package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@ToString(exclude = {"user", "category"})
@Table(name = "interest")
public class Interest {

    @Id
    @Column(name = "interest_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long interestNo;

    @Column(name = "interest_name", nullable = false, length = 20, unique = true)
    String interestName;

    @ManyToOne
    @JoinColumn(name = "user_no")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_no")
    private Category category;


}
