package com.example.cryptotradingsystem.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;

@Entity
@Data
@Table(name = "t_transaction_history")
public class TransactionPO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    private UserPO userPO;
    
    private String symbol;
    private String type; 
    private Double price;
    private Double amount; 
    private LocalDateTime timestamp;

    
}
