package com.corruptionfinder.blockchain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlockchainApplication {
	public static void main(String[] args) {
		System.setProperty("spring.mongodb.uri",
				"mongodb://Piyush_Pareek_207:PPiyush%402007@ac-omg5rua-shard-00-00.lem3u4g.mongodb.net:27017,ac-omg5rua-shard-00-01.lem3u4g.mongodb.net:27017,ac-omg5rua-shard-00-02.lem3u4g.mongodb.net:27017/CorruptionCatcher?ssl=true&replicaSet=atlas-m4bfzm-shard-0&authSource=admin&appName=Piyush-Pareek-207");
		SpringApplication.run(BlockchainApplication.class, args);
	}

}
