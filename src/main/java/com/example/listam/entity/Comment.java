package com.example.listam.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "comments")
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(name = "comment")
    private String comment;


    @ManyToOne
    private Item item;
}
