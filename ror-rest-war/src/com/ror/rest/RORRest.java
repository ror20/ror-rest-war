package com.ror.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.ror.model.User;

@Path("rest")
public class RORRest {

	private static String DATABASE_NAME = "ror";
	private static String COLLECTION_NAME = "users";
	private static MongoDatabase mongoDatabase = null;
	private static MongoCollection<Document> mongoCollection = null;

	@Autowired
	MongoClient client;

	@GET
	@Path("/sample")
	@Produces(MediaType.APPLICATION_JSON)
	public User sampleRest() {
		return new User(574384, "Suda");
	}

	@GET
	@Path("/mongo")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser() {
		mongoDatabase = client.getDatabase(DATABASE_NAME);
		mongoCollection = mongoDatabase.getCollection(COLLECTION_NAME);
		Gson gson = new Gson();
		/*
		 * String userJSON = gson.toJson(new User(574377, "pavithra")); Document
		 * document = new Document(); String key = "userPavi"; document.append(key,
		 * userJSON); mongoCollection.insertOne(document);
		 */
		FindIterable<Document> findIterable = mongoCollection.find();
		String key = "userPavi";
		User user = null;
		for (Document document1 : findIterable) {
			if (document1.containsKey(key)) {
				user = gson.fromJson((String) document1.get(key), User.class);
			}
		}
		System.out.println(user);
		return user;
	}

}
