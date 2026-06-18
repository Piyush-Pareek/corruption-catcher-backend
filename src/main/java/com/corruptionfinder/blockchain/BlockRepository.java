package com.corruptionfinder.blockchain;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface BlockRepository extends MongoRepository<Block, String> {
        @Query("{'transactions.projectId': ?0}")
        List<Block> findBlockbyprojectId(String transactionId);

        @Query("{transactions.reciever: ?0}")
        List<Block> findBlockbyReceiver(String receiver);
}