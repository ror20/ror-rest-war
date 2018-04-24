package com.ror.rest;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.ror.model.User;

@Controller
@RequestMapping("rest")
public class RORRest {

	private static String DATABASE_NAME = "ror";
	private static String COLLECTION_NAME = "users";
	private static MongoDatabase mongoDatabase = null;
	private static MongoCollection<Document> mongoCollection = null;
	Gson gson = new Gson();

	@Autowired
	MongoClient mongoClient;

	@RequestMapping(value = "/sample", method = RequestMethod.GET)
	public @ResponseBody String sampleRest() {
		return gson.toJson(new User(574384, "Suda"));
	}

	@RequestMapping(value = "/mongo", method = RequestMethod.GET)
	public @ResponseBody String getUser() {
		mongoDatabase = mongoClient.getDatabase(DATABASE_NAME);
		mongoCollection = mongoDatabase.getCollection(COLLECTION_NAME);
		FindIterable<Document> findIterable = mongoCollection.find();
		String key = "userPavi";
		User user = null;
		for (Document document1 : findIterable) {
			if (document1.containsKey(key)) {
				user = gson.fromJson((String) document1.get(key), User.class);
			}
		}
		System.out.println(user);
		return gson.toJson(user);
	}

	@RequestMapping(value = "/store", consumes = "application/json", method = RequestMethod.POST)
	public String storeUser(@RequestBody User user1) {
		mongoDatabase = mongoClient.getDatabase(DATABASE_NAME);
		mongoCollection = mongoDatabase.getCollection(COLLECTION_NAME);
		FindIterable<Document> findIterable = mongoCollection.find();
		String key = "userPavi";
		User user = null;
		for (Document document1 : findIterable) {
			if (document1.containsKey(key)) {
				user = gson.fromJson((String) document1.get(key), User.class);
			}
		}
		System.out.println(user);
		return gson.toJson(user);
	}

}
