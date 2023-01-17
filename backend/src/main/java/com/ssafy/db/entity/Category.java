package com.ssafy.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "category")
public class Category {

    @Id
    @Column(name = "category_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long categoryNo;

    @Column(name = "category_name", nullable = false, length = 20, unique = true)
    String categoryName;

    @OneToMany(mappedBy = "category")
    private List<Interest> interests;
}
