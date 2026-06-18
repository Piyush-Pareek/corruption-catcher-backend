package com.corruptionfinder.blockchain;

import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import com.google.gson.*;
import java.io.*;
import org.springframework.beans.factory.annotation.Autowired;
@Service 
public class Blockchain{
    
    @Autowired
   BlockRepository blockRepository ;

    public void intializeChain(){
        
        if(blockRepository.count()>0){
            System.out.println("Loading blockchain ...");
        }else{
            System.out.println("No existing blockchain found. Creating new blockchain...");
            blockRepository.save(createGenesisBlock());
        }
    }
    private Block createGenesisBlock(){
        return new Block(0,System.currentTimeMillis(),new ArrayList<TransactionDetails>(),"0","",0);
    }
    public Block getLatestBlock(){
         List<Block>chain = blockRepository.findAll();

        return chain.get(chain.size()-1);
    }
    public void addBlock(Block newBlock){
        newBlock.previousHash = getLatestBlock().hash;
        newBlock.hash = newBlock.calculateHash();
        newBlock.mineBlock(ThreadLocalRandom.current().nextInt(4,8));
        blockRepository.save(newBlock);                 
    }
    public boolean isChainValid(){
         List<Block>chain = blockRepository.findAll();
        for(int i=1;i<chain.size();i++){
            Block currBlock = chain.get(i);
            Block previousBlock = chain.get(i-1);
            if (currBlock.getPreviousHash() == null) {
                return false;
            }
            if(!currBlock.hash.equals( currBlock.calculateHash())){
                return false;
            }
            if(!currBlock.previousHash.equals(previousBlock.hash)){
                return false;
            }
        }
        return true;
    }
  
    public List<Block> getLedger(){
        return blockRepository.findAll();
    }
    public double getbalance(String adress) {
        double balance = 0;
        List<Block> chain = blockRepository.findAll();
        for (Block block : chain) {
            for(TransactionDetails transaction: block.transactions){
                if(transaction.sender.equals(adress)){
                    balance-=transaction.amount;
                }
                else if(transaction.reciever.equals(adress)){
                    balance+=transaction.amount;
                }
            }
        }
        System.out.println("Scan Complete: The current balance for '" + adress + "' is: ₹" + balance);
        return balance;
    }
    public List<TransactionDetails> auditProject (String projectId){
        System.out.println("Auditing project: " + projectId);
        List<Block> blocks = blockRepository.findBlockbyprojectId(projectId);
        List<TransactionDetails> transactions = new ArrayList<>();
        for(Block block:blocks){
            for(TransactionDetails transaction:block.getTransactions()){
                if(projectId.equals(transaction.getProjectId())){
                    transactions.add(transaction);
                }
            }
        }
        return transactions;

    }
    
    public List<TransactionDetails> auditEntity(String receiver) {
        System.out.println("Auditing project: " + receiver);
        List<Block> blocks = blockRepository.findBlockbyReceiver(receiver);
        List<TransactionDetails> transactions = new ArrayList<>();
        for (Block block : blocks) {
            for (TransactionDetails transaction : block.getTransactions()) {
                if (receiver.equals(transaction.getReciever())) {
                    transactions.add(transaction);
                }
            }
        }
        return transactions;

    }
}