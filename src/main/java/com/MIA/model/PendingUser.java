package com.MIA.model;


import javax.persistence.*;
//TODO: ADD PENDING USER METHOD TO PROJECT
@Entity
@Table(name = "pending_user")
public class PendingUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
