package com.location1.TVSME_location;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TvsmeLocationApplication {

	@Value("${spring.data.mongodb.database}")
	private String databaseName;

	public static void main(String[] args) {
		SpringApplication.run(TvsmeLocationApplication.class, args);
	}

	@Bean
	CommandLineRunner runner() {
		return args -> {
			try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
				MongoDatabase database = mongoClient.getDatabase(databaseName);
				MongoCollection<Document> collection = database.getCollection("dealerCollection");

				System.out.println("Database Name: " + databaseName);
				System.out.println("Collection Name: dealerCollection");

				// Fetch and print all documents in the collection
				for (Document doc : collection.find()) {
					System.out.println(doc.toJson());
				}

				System.out.println("Custom Message: MongoDB connection details retrieved successfully.");
			}
		};
	}
}
