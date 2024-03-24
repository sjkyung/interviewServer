package com.bisise.interviewserver.domain.user;

import jakarta.persistence.*;

//USER Entity 구성
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

}
