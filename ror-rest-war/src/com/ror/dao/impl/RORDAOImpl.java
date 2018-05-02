package com.ror.dao.impl;

import static com.ror.constants.RORConstants.COLLECTION_NAME;
import static com.ror.constants.RORConstants.DATABASE_NAME;
import static com.ror.constants.RORConstants.DOCUMENT_ID;
import static com.ror.constants.RORConstants.DOCUMENT_ID_VALUE;
import static com.ror.constants.RORConstants.USERS_DOCUMENT;
import static com.ror.utils.RORUtils.convertToJson;
import static com.ror.utils.RORUtils.convertToPOJO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.ror.dao.RORDAO;
import com.ror.exception.RORException;
import com.ror.model.RORUser;
import com.ror.vo.RORResponseVO;

public class RORDAOImpl implements RORDAO {

	private static MongoDatabase mongoDatabase = null;

	private static MongoCollection<Document> mongoCollection = null;

	@Autowired
	private MongoClient mongoClient;

	@SuppressWarnings("unchecked")
	@Override
	public RORResponseVO storeUser(RORUser user) {
		RORResponseVO responseVO = null;
		Document document1 = null;
		Map<String, String> userMap = null;
		try {
			System.out.println("Inside store user mathod.");
			if (fetchUser(user.getUserId()) != null) {
				throw new RORException("User already Exists!!");
			} else {
				setMongoParameters();
				FindIterable<Document> findIterable = mongoCollection.find();
				for (Document document : findIterable) {
					if (document.containsKey(USERS_DOCUMENT)) {
						document1 = document;
						userMap = (Map<String, String>) convertToPOJO(document.get(USERS_DOCUMENT), Map.class);
						break;
					}
				}
				userMap.put(user.getUserId(), convertToJson(user));
				mongoCollection.deleteOne(Filters.eq(DOCUMENT_ID, DOCUMENT_ID_VALUE));
				document1.put(USERS_DOCUMENT, convertToJson(userMap));
				mongoCollection.insertOne(document1);
				responseVO = new RORResponseVO("200 Ok", "User Details stored successfully!");
			}
		} catch (RORException e) {
			responseVO = new RORResponseVO("400 Bad Request", e.toString());
		} catch (Exception e) {
			responseVO = new RORResponseVO("404 Bad Request", "Error occured. Failed to store user details!");
		}
		return responseVO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public RORUser fetchUser(String userId) {
		RORUser user = null;
		Map<String, String> userMap = null;
		setMongoParameters();
		FindIterable<Document> findIterable = mongoCollection.find();
		for (Document document : findIterable) {
			if (document.containsKey(USERS_DOCUMENT)) {
				userMap = (Map<String, String>) convertToPOJO(document.get(USERS_DOCUMENT), Map.class);
				break;
			}
		}
		System.out.println("The user map fetched:" + userMap);
		if (userMap.containsKey(userId)) {
			String tempUser = userMap.get(userId);

			if (!(tempUser == null || tempUser.isEmpty())) {
				user = (RORUser) convertToPOJO(tempUser, RORUser.class);
			}
		}
		return user;
	}
	@SuppressWarnings("unchecked")
	@Override
	public RORResponseVO updateUser(RORUser user) {
		RORResponseVO responseVO = null;
		Document document1 = null;
		Map<String, String> userMap = null;
		try {
			if (fetchUser(user.getUserId()) == null) {
				throw new RORException("Update Failed. User Doesn't exsist.");
			} else {
				setMongoParameters();
				FindIterable<Document> findIterable = mongoCollection.find();
				for (Document document : findIterable) {
					if (document.containsKey(USERS_DOCUMENT)) {
						document1 = document;
						userMap = (Map<String, String>) convertToPOJO(document.get(USERS_DOCUMENT), Map.class);
						break;
					}
				}
				userMap.remove(user.getUserId());
				userMap.put(user.getUserId(), convertToJson(user));
				mongoCollection.deleteOne(Filters.eq(DOCUMENT_ID, DOCUMENT_ID_VALUE));
				document1.put(USERS_DOCUMENT, convertToJson(userMap));
				mongoCollection.insertOne(document1);
				responseVO = new RORResponseVO("200 Ok", "User Details updated successfully!");

			}
		} catch (RORException e) {
			responseVO = new RORResponseVO("404 Bad Request", e.toString());
		}
		return responseVO;
	}
	@SuppressWarnings("unchecked")
	@Override
	public RORResponseVO deleteUser(String userId) {
		RORResponseVO responseVO = null;
		Document document1 = null;
		Map<String, String> userMap = null;
		try {
			if (fetchUser(userId) == null) {
				throw new RORException("Delete Failed. User Doesn't exsist.");
			}
			setMongoParameters();
			FindIterable<Document> findIterable = mongoCollection.find();
			for (Document document : findIterable) {
				if (document.containsKey(USERS_DOCUMENT)) {
					document1 = document;
					userMap = (Map<String, String>) convertToPOJO(document.get(USERS_DOCUMENT), Map.class);
					break;
				}
			}
			userMap.remove(userId);
			mongoCollection.deleteOne(Filters.eq(DOCUMENT_ID, DOCUMENT_ID_VALUE));
			document1.put(USERS_DOCUMENT, convertToJson(userMap));
			mongoCollection.insertOne(document1);
			responseVO = new RORResponseVO("200 Ok", "User Details Deleted successfully!");
		} catch (RORException e) {
			responseVO = new RORResponseVO("400 Bad Request", e.toString());
		} catch (Exception e) {
			responseVO = new RORResponseVO("404 Bad Request", "Error occured. Failed to delete user details!");
		}

		return responseVO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RORUser> fetchAllUser() {
		List<RORUser> rorUserList = new ArrayList<>();
		Map<String, String> userMap = null;
		setMongoParameters();
		FindIterable<Document> findIterable = mongoCollection.find();
		for (Document document : findIterable) {
			if (document.containsKey(USERS_DOCUMENT)) {
				userMap = (Map<String, String>) convertToPOJO(document.get(USERS_DOCUMENT), Map.class);
				break;
			}
		}
		Collection<String> rorValues = userMap.values();
		Iterator<String> itr = rorValues.iterator();
		while(itr.hasNext()) {
			rorUserList.add((RORUser) convertToPOJO(itr.next(), RORUser.class));
		}
		return rorUserList;
	}

	private void setMongoParameters() {
		mongoDatabase = mongoClient.getDatabase(DATABASE_NAME);
		mongoCollection = mongoDatabase.getCollection(COLLECTION_NAME);
	}

}
