package com.corruptionfinder.blockchain;

import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface GraphRepository extends Neo4jRepository<FinancialEntity, String> {
   
}