package com.ror.dao.impl;

import static com.ror.constants.RORConstants.COLLECTION_NAME;
import static com.ror.constants.RORConstants.DATABASE_NAME;
import static com.ror.constants.RORConstants.DOCUMENT_ID;
import static com.ror.constants.RORConstants.DOCUMENT_ID_VALUE;
import static com.ror.constants.RORConstants.USERS_DOCUMENT;

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
import static com.ror.utils.RORUtils.convertToJson;
import static com.ror.utils.RORUtils.convertToPOJO;
import com.ror.vo.RORResponseVO;

public class RORDAOImpl implements RORDAO {

	private static MongoDatabase mongoDatabase = null;

	private static MongoCollection<Document> mongoCollection = null;

	@Autowired
	private MongoClient mongoClient;

	@Override
	public RORResponseVO storeUser(RORUser user) {
		RORResponseVO responseVO = null;
		Document document1 = null;
		List<Map> userList = null;
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
						userList = (List<Map>) convertToPOJO(document.get(USERS_DOCUMENT), List.class);
						break;
					}
				}
				addUserToList(user, userList);
				mongoCollection.deleteOne(Filters.eq(DOCUMENT_ID, DOCUMENT_ID_VALUE));
				document1.put(USERS_DOCUMENT, convertToJson(userList));
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

	@Override
	public RORUser fetchUser(String userId) {
		RORUser user = null;
		List<Map> userList = null;
		setMongoParameters();
		FindIterable<Document> findIterable = mongoCollection.find();
		for (Document document : findIterable) {
			if (document.containsKey(USERS_DOCUMENT)) {
				userList = (List<Map>) convertToPOJO(document.get(USERS_DOCUMENT), List.class);
				break;
			}
		}
		if (userList != null) {
			Map<String, RORUser> userMap = userList.get(0);
			if (userMap.containsKey(userId)) {
				Object userStringFormat = userMap.get(userId);
				user = convertToRORUser(userStringFormat);
			}
			if (user == null) {
				System.out.println("User not found.");
			}
		}
		return user;
	}

	@Override
	public RORResponseVO updateUser(RORUser user) {
		RORResponseVO responseVO = null;
		Document document1 = null;
		List<Map> userList = null;
		try {
			if (fetchUser(user.getUserId()) == null) {
				throw new RORException("Update Failed. User Doesn't exsist.");
			} else {
				setMongoParameters();
				FindIterable<Document> findIterable = mongoCollection.find();
				for (Document document : findIterable) {
					if (document.containsKey(USERS_DOCUMENT)) {
						document1 = document;
						userList = (List<Map>) convertToPOJO(document.get(USERS_DOCUMENT), List.class);
						break;
					}
				}
				addUserToList(user, userList);
				mongoCollection.deleteOne(Filters.eq(DOCUMENT_ID, DOCUMENT_ID_VALUE));
				document1.put(USERS_DOCUMENT, convertToJson(userList));
				mongoCollection.insertOne(document1);
				responseVO = new RORResponseVO("200 Ok", "User Details updated successfully!");

			}
		} catch (RORException e) {
			responseVO = new RORResponseVO("404 Bad Request", e.toString());
		}
		return responseVO;
	}

	@Override
	public RORResponseVO deleteUser(String userId) {
		RORResponseVO responseVO = null;
		Document document1 = null;
		List<Map> userList = null;
		boolean userFound = false;

		try {
			if (fetchUser(userId) == null) {
				throw new RORException("Delete Failed. User Doesn't exsist.");
			}
			setMongoParameters();
			FindIterable<Document> findIterable = mongoCollection.find();
			for (Document document : findIterable) {
				if (document.containsKey(USERS_DOCUMENT)) {
					document1 = document;
					userList = (List<Map>) convertToPOJO(document.get(USERS_DOCUMENT), List.class);
					break;
				}
			}
			removeUserMapFromList(userId, userList);
			mongoCollection.deleteOne(Filters.eq(DOCUMENT_ID, DOCUMENT_ID_VALUE));
			document1.put(USERS_DOCUMENT, convertToJson(userList));
			mongoCollection.insertOne(document1);
			responseVO = new RORResponseVO("200 Ok", "User Details Deleted successfully!");

		} catch (RORException e) {
			responseVO = new RORResponseVO("400 Bad Request", e.toString());
		} catch (Exception e) {
			responseVO = new RORResponseVO("404 Bad Request", "Error occured. Failed to delete user details!");
		}

		return responseVO;
	}

	private void removeUserMapFromList(String userId, List<Map> userList) {
		Map<String, RORUser> userMap = userList.get(0);
		System.out.println("User Map Before Removing");
		System.out.println(userMap);
		userMap.remove(userId);
		System.out.println("User Map After Removing");
		System.out.println(userMap);
		userList.remove(0);
		userList.add(userMap);
	}

	public RORResponseVO storeUser1(RORUser user) {
		RORResponseVO responseVO = null;
		try {
			System.out.println(user);
			if (fetchUser(user.getUserId()) != null) {
				throw new RORException("User already Exists!!");
			} else {
				setMongoParameters();
				String userJSON = convertToJson(user);
				Document document = new Document();
				String key = user.getUserId();
				document.append(key, userJSON);
				document.put(DOCUMENT_ID, "5addccb971c36b4a9889a4eb");
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

	public RORResponseVO updateUser1(RORUser user) {
		RORResponseVO responseVO = null;

		try {
			if (fetchUser(user.getUserId()) == null) {
				throw new RORException("User does not exist!");
			} else {
				setMongoParameters();
				String userJSON = convertToJson(user);
				Document document = new Document();
				String key = user.getUserId();
				document.append(key, userJSON);
				mongoCollection.insertOne(document);
				responseVO = new RORResponseVO("200 Ok", "User Details updated successfully!");
			}
		} catch (RORException e) {
			responseVO = new RORResponseVO("400 Bad Request", e.toString());
		} catch (Exception e) {
			responseVO = new RORResponseVO("404 Bad Request", "Error occured. Failed to update user details!");
		}

		return responseVO;
	}

	public RORUser fetchUser1(String userId) {
		setMongoParameters();
		FindIterable<Document> findIterable = mongoCollection.find();
		RORUser user = null;
		for (Document document1 : findIterable) {
			if (document1.containsKey(userId)) {
				user = (RORUser) convertToPOJO(document1.get(userId), RORUser.class);
				System.out.println("User Found:" + user);
			}
		}
		if (user == null) {
			System.out.println("User not found.");
		}
		return user;
	}

	public RORResponseVO deleteUser1(String userId) {
		RORResponseVO responseVO = null;
		boolean userFound = false;

		try {
			setMongoParameters();
			FindIterable<Document> findIterable = mongoCollection.find();
			for (Document document1 : findIterable) {
				if (document1.containsKey(userId)) {
					mongoCollection.deleteOne(document1);
					userFound = true;
					System.out.println("User Found:" + userId);
					responseVO = new RORResponseVO("200 Ok", "User Details deleted successfully!");
				}
			}
			if (!userFound) {
				throw new RORException("User does not exist!");
			}
		} catch (RORException e) {
			responseVO = new RORResponseVO("400 Bad Request", e.toString());
		} catch (Exception e) {
			responseVO = new RORResponseVO("404 Bad Request", "Error occured. Failed to delete user details!");
		}

		return responseVO;
	}

	private void setMongoParameters() {
		mongoDatabase = mongoClient.getDatabase(DATABASE_NAME);
		mongoCollection = mongoDatabase.getCollection(COLLECTION_NAME);
	}

	private void addUserToList(RORUser user, List<Map> userList) {
		Map<String, RORUser> userMap = userList.get(0);
		userMap.put(user.getUserId(), user);
		userList.remove(0);
		userList.add(userMap);
	}

	private RORUser convertToRORUser(Object rorUser) {
		String userStringFormat = rorUser.toString();
		System.out.println("User String Format: " + userStringFormat);
		RORUser tempUser = (RORUser) convertToPOJO((String)userStringFormat, RORUser.class);
		return tempUser;
	}
}
