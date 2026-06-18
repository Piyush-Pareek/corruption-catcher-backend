package com.corruptionfinder.blockchain;

public class Searching {
    public void SeachbyId(Blockchain chain, String transactionId){
        for(Block block :chain.blockRepository.findAll() ){
            for (TransactionDetails transaction : block.transactions) {
                if (transaction.transactionId.equals(transactionId)) {

                    
                    System.out.println("ALERT: Found transaction with ID " + transactionId);
                }
                }
            }
        }

    public void SearchbyType(Blockchain chain, String type){
        for(Block block :chain.blockRepository.findAll() ){
            for (TransactionDetails transaction : block.transactions) {
                if (transaction.transactionType.equals(type)) {

                    
                    System.out.println("ALERT: Found " + type + " - ID: " + transaction.transactionId);
                }
                }
            }
        }
        public void SearchbyAmount(Blockchain chain, double amount){
            for(Block block :chain.blockRepository.findAll() ){
                for (TransactionDetails transaction : block.transactions) {
                    if (transaction.amount == amount) { 
    
                        
                        System.out.println("ALERT: Found " + amount + " - ID: " + transaction.transactionId);
                    }
                    }
                
            }
        }
        public void SearchbyProjectId(Blockchain chain, String projectId){
            for (Block block : chain.blockRepository.findAll()) {
                for (TransactionDetails transaction : block.transactions) {
                    if (transaction.transactionId .equals(projectId)) {

                        System.out.println("ALERT: Found " + projectId + " - ID: " + transaction.transactionId);
                    }
                }
            }
        }
        public void SearchbySender(Blockchain chain, String sender){
            for (Block block : chain.blockRepository.findAll()) {
                for (TransactionDetails transaction : block.transactions) {
                    if (transaction.sender.equals(sender)) {

                        System.out.println("ALERT: Found " + sender + " - ID: " + transaction.transactionId);
                    }
                }
            }  
        }
        public void SearchbyReceiver(Blockchain chain, String reciever){
            for (Block block : chain.blockRepository.findAll()) {
                for (TransactionDetails transaction : block.transactions) {
                    if (transaction.reciever.equals(reciever)) {

                        System.out.println("ALERT: Found " + reciever + " - ID: " + transaction.transactionId);
                    }
                }
            }
        }
        public void SearchbyTime(Blockchain chain, long startTime, long endTime){
            
                for(Block block :chain.blockRepository.findAll()){
                    for (TransactionDetails transaction : block.transactions) {
                        if (transaction.timestamp>=startTime&& transaction.timestamp<=endTime) {
        
                            System.out.println("ALERT: Found transaction between " + startTime + " and " + endTime + " - ID: " + transaction.transactionId);   
                        }
                        }
                }
        }
        public void SearchbyFlag(Blockchain chain, boolean isFlagged){
            for (Block block : chain.blockRepository.findAll()) {
                for (TransactionDetails transaction : block.transactions) {
                    if (transaction.isFlagged == isFlagged) {

                        System.out.println("ALERT: Found transaction with flagged status " + isFlagged + " - ID: " + transaction.transactionId);
                    }
                }
            }
        }
    }
        