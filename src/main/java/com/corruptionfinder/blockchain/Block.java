package com.corruptionfinder.blockchain;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "blocks")
public class Block{
    
    int index;
     long timestamp;
     List<TransactionDetails> transactions;
     String previousHash;
    @Id
     String hash;
     int nonce;

     
    String  calculateHash(){
        String BlockHash = (index) + Long.toString(timestamp) + transactions.toString() + previousHash + Integer.toString(nonce);
        return SHA256.applySHA256(BlockHash);
    }
    public void mineBlock(int hardness){
        String target = "0".repeat(hardness);
        while(!hash.substring(0,hardness).equals(target)){
            nonce++;
            hash = calculateHash();
        }
        System.out.println("Block Mined :"+hash);
    }
}