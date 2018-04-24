package com.ror.dao.impl;

import static com.ror.constants.RORConstants.COLLECTION_NAME;
import static com.ror.constants.RORConstants.DATABASE_NAME;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.ror.dao.RORDAO;
import com.ror.exception.RORException;
import com.ror.model.RORUser;
import com.ror.utils.RORUtils;
import com.ror.vo.RORResponseVO;

public class RORDAOImpl implements RORDAO {

	private static MongoDatabase mongoDatabase = null;

	private static MongoCollection<Document> mongoCollection = null;

	@Autowired
	private MongoClient mongoClient;

	@Override
	public RORResponseVO storeUser(RORUser user) {
		RORResponseVO responseVO = null;
		try {

			if (fetchUser(user.getUserId()) != null) {
				throw new RORException("User already Exists!!");
			} else {
				mongoDatabase = mongoClient.getDatabase(DATABASE_NAME);
				mongoCollection = mongoDatabase.getCollection(COLLECTION_NAME);
				String userJSON = RORUtils.convertToJson(user);
				Document document = new Document();
				String key = user.getUserId();
				document.append(key, userJSON);
				mongoCollection.insertOne(document);
				responseVO = new RORResponseVO("200 Ok", "User Details stored successfully!");
			}
		} catch (RORException e) {
			responseVO = new RORResponseVO("400 Bad Request", e.toString());
		} catch (Exception e) {
			responseVO = new RORResponseVO("404 Bad Request", "Error occured. Failed to store user details!");
		}

		return responseVO;
	}

	@Override
	public RORUser fetchUser(String userId) {
		mongoDatabase = mongoClient.getDatabase(DATABASE_NAME);
		mongoCollection = mongoDatabase.getCollection(COLLECTION_NAME);
		FindIterable<Document> findIterable = mongoCollection.find();
		RORUser user = null;
		for (Document document1 : findIterable) {
			if (document1.containsKey(userId)) {
				user = (RORUser) RORUtils.convertToPOJO(document1.get(userId), RORUser.class);
				System.out.println("User Found:" + user);
			}
		}
		if (user == null) {
			System.out.println("User not found.");
		}
		return user;
	}

}
