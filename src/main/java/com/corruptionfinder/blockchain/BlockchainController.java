package com.corruptionfinder.blockchain;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.annotation.PostConstruct;

@RestController  
@RequestMapping("/api/blockchain")
@CrossOrigin(origins = "*")
public class BlockchainController{
    @Autowired
    private Blockchain fundtracker;
    @Autowired
    private  GraphService graphService;
    @Bean
    public CommandLineRunner initializeBlockchainNetwork() {
        return args -> {
            try {
                System.out.println("Connecting to MongoDB Atlas and initializing chain...");
                fundtracker.intializeChain();
                System.out.println("Blockchain network connected successfully!");
            } catch (Exception e) {
                System.err.println("WARNING: Database connection failed. Tomcat server is still running for frontend.");
                System.err.println("Error details: " + e.getMessage());
            }
        };
    }

    @GetMapping("/ledger")
    public List<Block> viewledger(){
        return fundtracker.getLedger();
    }
    
    @GetMapping("/audit/receiver/{receiver}")
    public List<TransactionDetails> auditreceiver(@PathVariable String receiver) {
        ArrayList<TransactionDetails> result = new ArrayList<>();
        for (Block block : fundtracker.blockRepository.findAll()) {
            for (TransactionDetails transaction : block.transactions) {
                // Manually checking the receiver just like we did for the sender
                
                if (transaction.reciever.equalsIgnoreCase(receiver)) {
                    result.add(transaction);
                }
            }
        }
        return result;
    }
    
    @GetMapping("/audit/sender/{sender}")
    public List<TransactionDetails> auditsender(@PathVariable String sender) {
        ArrayList<TransactionDetails> result = new ArrayList<>();
        for (Block block : fundtracker.blockRepository.findAll()) {
            for (TransactionDetails transaction : block.transactions) {
                if (transaction.sender.equalsIgnoreCase(sender)) {
                    result.add(transaction);
                }
            }
            
        }
        return result;
    }

@GetMapping("/audit/project/{projectId}")
public List<TransactionDetails> auditProjectId(@PathVariable String projectId){
    return fundtracker.auditProject(projectId);
}
@GetMapping("/audit/amount/{amount}")
public List<TransactionDetails> auditamount(@PathVariable double amount){
    ArrayList<TransactionDetails> result = new ArrayList<>();
    for (Block block : fundtracker.blockRepository.findAll()) {
        for (TransactionDetails transaction : block.transactions) {
            if (transaction.amount == amount) {
                result.add(transaction);
            }
        }

    }
    return result;
}

@GetMapping("/audit/mode/{mode}")
public List<TransactionDetails> auditmode(@PathVariable("mode") String mode){
    ArrayList<TransactionDetails> result = new ArrayList<>();
    for (Block block : fundtracker.blockRepository.findAll()) {
        for (TransactionDetails transaction : block.transactions) {
            if (transaction.transactionType.equalsIgnoreCase(mode)) {
                result.add(transaction);
            }
        }

    }
    return result;
}

@GetMapping("/audit/flagged")
public List<TransactionDetails> auditflagged() {
    ArrayList<TransactionDetails> result = new ArrayList<>();
    for (Block block : fundtracker.blockRepository.findAll()) {
        for (TransactionDetails transaction : block.transactions) {
            if (transaction.isFlagged== true) {
                result.add(transaction);
            }
        }

    }
    return result;
}

// @PostMapping("/mineblock")
// public Block mineBlock(@RequestBody TransactionDetails transaction){
//     if(transaction == null){
//         throw new NullPointerException("Transaction details cannot be null");
//     }
//     if(transaction.getAmount()>5000000||"Cash".equalsIgnoreCase(transaction.getTransactionType())){
//         transaction.setIsFlagged(true);
//     }else{
//         transaction.setIsFlagged(false);
//     }
//     List<TransactionDetails>transactions = new ArrayList<>
// ();
// transactions.add(transaction);
// Block newBlock = new Block(fundtracker.blockRepository.findAll().size(),System.currentTimeMillis(),transactions,fundtracker.getLatestBlock().getHash(),"",0);
// fundtracker.addBlock(newBlock);
// System.out.println("New block mined with transaction: "+transaction);

// return newBlock;
// }
@GetMapping("/graph")
public List<FinancialEntity> viewGraph() {
    System.out.println("--- FORENSIC GRAPH REQUESTED ---");
    return graphService.getCorruptionWeb();
}

@GetMapping("/sync-graph")
public String syncHistoricalDataToGraph() {
    System.out.println("--- INITIATING MONGODB TO NEO4J SYNC ---");
    int count = 0;

    // Loop through every single block in MongoDB
    for (Block block : fundtracker.blockRepository.findAll()) {
        // Loop through every transaction inside that block
        for (TransactionDetails transaction : block.transactions) {
            // Send the old data to the new Neo4j Graph!
            graphService.recordTransfer(transaction.getSender(), transaction.getReciever());
            count++;
        }
    }

    System.out.println(" Sync Complete: " + count + " transactions mapped.");
    return "Successfully synced " + count + " historical transactions into the Corruption Web!";
}
@PostMapping("/mineblock")
public Block mineBlock(@RequestBody TransactionDetails transaction) {
    try {
        System.out.println("--- INCOMING MINING REQUEST ---");

        if (transaction == null) {
            throw new NullPointerException("Transaction details cannot be null");
        }

        System.out.println("Amount Received: " + transaction.getAmount());
        System.out.println("Type Received: " + transaction.getTransactionType());

        if (transaction.getAmount() > 5000000 || "Cash".equalsIgnoreCase(transaction.getTransactionType())) {
            transaction.setIsFlagged(true);
        } else {
            transaction.setIsFlagged(false);
        }

        List<TransactionDetails> transactions = new ArrayList<>();
        transactions.add(transaction);

        // Safety check for the previous block hash
        String prevHash = "0";
        if (fundtracker.getLatestBlock() != null) {
            prevHash = fundtracker.getLatestBlock().getHash();
        }

        Block newBlock = new Block(fundtracker.blockRepository.findAll().size(), System.currentTimeMillis(),
                transactions, prevHash, "", 0);

        // 1. THIS SAVES TO MONGODB
        fundtracker.addBlock(newBlock);

        // 2. ---> ADD THIS LINE: THIS SAVES TO NEO4J <---
        graphService.recordTransfer(transaction.getSender(), transaction.getReciever());

        System.out.println("✅ New block successfully mined to Cloud and mapped in Neo4j!");
        return newBlock;

    } catch (Exception e) {
        // THIS WILL CATCH THE INVISIBLE BUG
        System.err.println("❌ MINING CRASHED: " + e.getMessage());
        e.printStackTrace();
        throw new RuntimeException("Mining failed: " + e.getMessage());
    }
}

}