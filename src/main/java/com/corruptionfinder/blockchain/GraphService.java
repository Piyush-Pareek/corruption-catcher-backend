package com.corruptionfinder.blockchain;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class GraphService {

    private final GraphRepository graphRepository;

    public GraphService(GraphRepository graphRepository) {
        this.graphRepository = graphRepository;
    }

    public void recordTransfer(String senderName, String receiverName) {
        // 1. Find the sender in the database, or create them if they are new
        FinancialEntity sender = graphRepository.findById(senderName)
                .orElse(new FinancialEntity(senderName));

        // 2. Find the receiver in the database, or create them if they are new
        FinancialEntity receiver = graphRepository.findById(receiverName)
                .orElse(new FinancialEntity(receiverName));

        // 3. Draw the forensic link (the spiderweb string)
        sender.sendMoneyTo(receiver);

        // 4. Save both entities back to your Neo4j AuraDB cloud
        graphRepository.save(sender);
        graphRepository.save(receiver);
    }
    // This pulls the entire forensic web from Neo4j
    public List<FinancialEntity> getCorruptionWeb() {
        return graphRepository.findAll();
    }
}