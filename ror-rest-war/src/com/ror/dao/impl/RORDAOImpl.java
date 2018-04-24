package com.ror.dao.impl;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.ror.dao.RORDAO;
import com.ror.model.RORUser;
import com.ror.vo.RORResponseVO;

public class RORDAOImpl implements RORDAO {
	private static String DATABASE_NAME = "ror";
	private static String COLLECTION_NAME = "users";
	private static MongoDatabase mongoDatabase = null;
	private static MongoCollection<Document> mongoCollection = null;
	RORResponseVO responseVO = null;

	Gson gson = new Gson();

	@Autowired
	private MongoClient mongoClient;

	@Override
	public RORResponseVO storeUser(RORUser user) {
		try {
			mongoDatabase = mongoClient.getDatabase(DATABASE_NAME);
			mongoCollection = mongoDatabase.getCollection(COLLECTION_NAME);
			String userJSON = gson.toJson(user);
			Document document = new Document();
			String key = user.getUserId();
			document.append(key, userJSON);
			mongoCollection.insertOne(document);
			responseVO = new RORResponseVO("200 Ok", "User Details stored successfully!");
		} catch (Exception e) {
			responseVO = new RORResponseVO("404 Bad Request", "Failed to store user details!");
		}

		return responseVO;
	}

}
