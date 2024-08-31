package com.example.cryptotradingsystem.model;

import java.util.List;

import javax.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "t_users")
public class UserPO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private Double balance;

}
