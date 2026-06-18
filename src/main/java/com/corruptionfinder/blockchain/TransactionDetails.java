package com.corruptionfinder.blockchain;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDetails {
    public String transactionId; // Changed from Transaction_id
    public String sender;
    public String reciever;
    public double amount;
    public String projectId; // Changed from ProjectId
    public Long timestamp; // Changed from TimeStamp
    public String transactionType; // Changed from TransactionType
    public String metadata;
    public Boolean isFlagged;
    public String toString(){
        return transactionId+sender+reciever+amount+projectId+timestamp+transactionType+metadata+isFlagged;
    }
    
}
