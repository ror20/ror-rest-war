package com.ror.dao.impl;

import static com.ror.constants.RORConstants.COLLECTION_NAME;
import static com.ror.constants.RORConstants.DATABASE_NAME;
import static com.ror.constants.RORConstants.DOCUMENT_ID;
import static com.ror.constants.RORConstants.DOCUMENT_ID_VALUE;
import static com.ror.constants.RORConstants.ROR_REC_MESSAGE_LIST;
import static com.ror.constants.RORConstants.ROR_SENT_MESSAGE_LIST;
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
import com.ror.model.MessageDetails;
import com.ror.model.RORUser;
import com.ror.vo.RORResponseVO;

public class RORDAOImpl implements RORDAO {

	public static final String ROR_MESSAGE_RECEIVED_DOC = "ROR_Message_Received_DOC";

	public static final String ROR_MESSAGE_SENT_DOC = "ROR_Message_Sent_DOC";

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
		while (itr.hasNext()) {
			rorUserList.add((RORUser) convertToPOJO(itr.next(), RORUser.class));
		}
		return rorUserList;
	}

	private void setMongoParameters() {
		mongoDatabase = mongoClient.getDatabase(DATABASE_NAME);
		mongoCollection = mongoDatabase.getCollection(COLLECTION_NAME);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean checkUserExist(String userId) {
		Map<String, String> userMap = null;
		setMongoParameters();
		FindIterable<Document> findIterable = mongoCollection.find();
		for (Document document : findIterable) {
			if (document.containsKey(USERS_DOCUMENT)) {
				userMap = (Map<String, String>) convertToPOJO(document.get(USERS_DOCUMENT), Map.class);
				break;
			}
		}
		if (userMap.containsKey(userId)) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean checkPasswordMatch(String id, String password) {
		Map<String, String> userMap = null;
		setMongoParameters();
		FindIterable<Document> findIterable = mongoCollection.find();
		for (Document document : findIterable) {
			if (document.containsKey(USERS_DOCUMENT)) {
				userMap = (Map<String, String>) convertToPOJO(document.get(USERS_DOCUMENT), Map.class);
				break;
			}
		}
		if (userMap.containsKey(id)) {
			RORUser user = (RORUser) convertToPOJO(userMap.get(id), RORUser.class);
			if (user != null) {
				if (password.equals(user.getPassword())) {
					return true;
				}
			} else {
				return false;
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MessageDetails> sentMessage(String id) {
		Map<String, String> userSentMessageMap = null;
		List<MessageDetails> sentMessageDetailsList = null;
		setMongoParameters();
		FindIterable<Document> findIterable = mongoCollection.find();
		for (Document document : findIterable) {
			if (document.containsKey(ROR_SENT_MESSAGE_LIST)) {
				System.out.println("Inside ROR Sent message list document");
				userSentMessageMap = (Map<String, String>) convertToPOJO(document.get(ROR_SENT_MESSAGE_LIST),
						Map.class);
				break;
			}
		}
		if (userSentMessageMap != null) {
			String sentMessageDetailsValue = userSentMessageMap.get(id);
			if (sentMessageDetailsValue != null && !sentMessageDetailsValue.isEmpty()) {
				sentMessageDetailsList = (List<MessageDetails>) convertToPOJO(sentMessageDetailsValue, List.class);
				if (sentMessageDetailsList != null && !sentMessageDetailsList.isEmpty()) {
					System.out.println("Returning sent message details list");
					return sentMessageDetailsList;
				} else {
					System.out.println("Sent Message is null or empty after converting to POJO");
				}
			} else {
				System.out.println("Sent Message is null or empty");
			}
		} else {
			System.out.println("userSentMessage map is null");
		}
		return sentMessageDetailsList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MessageDetails> receivedMessage(String id) {
		Map<String, String> userRecMessageMap = null;
		List<MessageDetails> recMessageDetailsList = null;
		setMongoParameters();
		FindIterable<Document> findIterable = mongoCollection.find();
		for (Document document : findIterable) {
			if (document.containsKey(ROR_REC_MESSAGE_LIST)) {
				System.out.println("Inside ROR Received message list document");
				userRecMessageMap = (Map<String, String>) convertToPOJO(document.get(ROR_REC_MESSAGE_LIST), Map.class);
				break;
			}
		}
		if (userRecMessageMap != null) {
			String recMessageDetailsValue = userRecMessageMap.get(id);
			if (recMessageDetailsValue != null && !recMessageDetailsValue.isEmpty()) {
				recMessageDetailsList = (List<MessageDetails>) convertToPOJO(recMessageDetailsValue, List.class);
				if (recMessageDetailsList != null && !recMessageDetailsList.isEmpty()) {
					System.out.println("Returning received message details list");
					return recMessageDetailsList;
				} else {
					System.out.println("Sent Message is null or empty after converting to POJO");
				}
			} else {
				System.out.println("Sent Message is null or empty");
			}
		} else {
			System.out.println("userRecMessage map is null");
		}
		return recMessageDetailsList;
	}

	@Override
	public RORResponseVO draftMessage(MessageDetails messageDetails) {
		RORResponseVO responseVO = null;
		Map<String, String> userSentMessageMap = null;
		Map<String, String> userRecMessageMap = null;
		Document sendDocument = null;
		Document recDocument = null;
		setMongoParameters();
		FindIterable<Document> findIterable = mongoCollection.find();
		boolean sendFlag = sendMessageFunctionality(messageDetails, userSentMessageMap, sendDocument, findIterable);
		boolean receiveFlag = receiveMessageFunctionality(messageDetails, userRecMessageMap, recDocument, findIterable);
		if(sendFlag && receiveFlag) {
			responseVO =new RORResponseVO("200 OK", "Message Deleivered Successfully");
		}else {
			responseVO =new RORResponseVO("400 Bad Request", "Message Failed to Deleiver");
		}

		return responseVO;
	}

	@SuppressWarnings("unchecked")
	public boolean receiveMessageFunctionality(MessageDetails messageDetails, Map<String, String> userRecMessageMap,
			Document recDocument, FindIterable<Document> findIterable) {
		for (Document document : findIterable) {
			System.out.println(document);
			if (document.containsKey(ROR_REC_MESSAGE_LIST)) {
				recDocument = document;
				userRecMessageMap = (Map<String, String>) convertToPOJO(document.get(ROR_REC_MESSAGE_LIST), Map.class);
				break;
			}
		}
		if (userRecMessageMap != null) {
			System.out.println("Fetched user received message map from the document");
			String recMessageMapValue = userRecMessageMap.get(messageDetails.getToUserId());
			if (recMessageMapValue != null && !recMessageMapValue.isEmpty()) {
				List<MessageDetails> recMessageList = (List<MessageDetails>) convertToPOJO(recMessageMapValue,
						List.class);
				System.out.println("Message received list is obtained");
				if (recMessageList != null) {
					recMessageList.add(messageDetails);
					userRecMessageMap.put(messageDetails.getToUserId(), convertToJson(recMessageList));
					mongoCollection.deleteOne(Filters.eq(DOCUMENT_ID, ROR_MESSAGE_RECEIVED_DOC));
					recDocument.put(ROR_REC_MESSAGE_LIST, convertToJson(userRecMessageMap));
					mongoCollection.insertOne(recDocument);
					System.out.println("Received Document Message inserted");
					return true;
				} else {
					System.out.println("Message list received is null");
					recMessageList = new ArrayList<MessageDetails>();
					recMessageList.add(messageDetails);
					userRecMessageMap.put(messageDetails.getToUserId(), convertToJson(recMessageList));
					mongoCollection.deleteOne(Filters.eq(DOCUMENT_ID, ROR_MESSAGE_RECEIVED_DOC));
					recDocument.put(ROR_REC_MESSAGE_LIST, convertToJson(userRecMessageMap));
					mongoCollection.insertOne(recDocument);
					System.out.println("Received Document Message inserted");
					return true;
				}

			} else {
				List<MessageDetails> recMessageList = new ArrayList<MessageDetails>();
				recMessageList.add(messageDetails);
				userRecMessageMap.put(messageDetails.getToUserId(), convertToJson(recMessageList));
				mongoCollection.deleteOne(Filters.eq(DOCUMENT_ID, ROR_MESSAGE_RECEIVED_DOC));
				recDocument.put(ROR_REC_MESSAGE_LIST, convertToJson(userRecMessageMap));
				mongoCollection.insertOne(recDocument);
				System.out.println("Received Document Message inserted");
				return true;
			}
		} else {
			System.out.println("UserReceiveMessageMap is null");
			return false;
		}

	}

	@SuppressWarnings("unchecked")
	public boolean sendMessageFunctionality(MessageDetails messageDetails, Map<String, String> userSentMessageMap,
			Document sendDocument, FindIterable<Document> findIterable) {
		for (Document document : findIterable) {
			System.out.println(document);
			if (document.containsKey(ROR_SENT_MESSAGE_LIST)) {
				sendDocument = document;
				userSentMessageMap = (Map<String, String>) convertToPOJO(document.get(ROR_SENT_MESSAGE_LIST),
						Map.class);
				break;
			}
		}
		if (userSentMessageMap != null) {
			System.out.println("Fetched user sent message map from the document");
			String sentMessageMapValue = userSentMessageMap.get(messageDetails.getFromUserId());
			if (sentMessageMapValue != null && !sentMessageMapValue.isEmpty()) {
				List<MessageDetails> sentMessageList = (List<MessageDetails>) convertToPOJO(sentMessageMapValue,
						List.class);
				System.out.println("Message sent list is obtained");
				if (sentMessageList != null) {
					sentMessageList.add(messageDetails);
					userSentMessageMap.put(messageDetails.getFromUserId(), convertToJson(sentMessageList));
					mongoCollection.deleteOne(Filters.eq(DOCUMENT_ID, ROR_MESSAGE_SENT_DOC));
					sendDocument.put(ROR_SENT_MESSAGE_LIST, convertToJson(userSentMessageMap));
					mongoCollection.insertOne(sendDocument);
					System.out.println("Send Document Message inserted");
					return true;
				} else {
					System.out.println("message list sent is null");
					sentMessageList = new ArrayList<MessageDetails>();
					sentMessageList.add(messageDetails);
					userSentMessageMap.put(messageDetails.getFromUserId(), convertToJson(sentMessageList));
					mongoCollection.deleteOne(Filters.eq(DOCUMENT_ID, ROR_MESSAGE_SENT_DOC));
					sendDocument.put(ROR_SENT_MESSAGE_LIST, convertToJson(userSentMessageMap));
					mongoCollection.insertOne(sendDocument);
					System.out.println("Send Document Message inserted");
					return true;
				}

			} else {
				List<MessageDetails> sentMessageList = new ArrayList<MessageDetails>();
				sentMessageList.add(messageDetails);
				userSentMessageMap.put(messageDetails.getFromUserId(), convertToJson(sentMessageList));
				mongoCollection.deleteOne(Filters.eq(DOCUMENT_ID, ROR_MESSAGE_SENT_DOC));
				sendDocument.put(ROR_SENT_MESSAGE_LIST, convertToJson(userSentMessageMap));
				mongoCollection.insertOne(sendDocument);
				System.out.println("Send Document Message inserted for new User");
				return true;
			}
		} else {
			System.out.println("UserSentMessageMap is null");
			return false;
		}
	}

}
