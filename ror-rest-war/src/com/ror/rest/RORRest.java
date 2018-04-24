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
import com.ror.model.RORUser;
import com.ror.model.User;
import com.ror.svc.RORSvc;
import com.ror.vo.RORResponseVO;

@Controller
@RequestMapping("rest")
public class RORRest {

	private static String DATABASE_NAME = "ror";
	private static String COLLECTION_NAME = "users";
	private static MongoDatabase mongoDatabase = null;
	private static MongoCollection<Document> mongoCollection = null;
	
	Gson gson = new Gson();

	@Autowired
	private MongoClient mongoClient;
	
	@Autowired
	private RORSvc rorSvc;
	
	@RequestMapping(value = "/fetchUser",  method = RequestMethod.GET)
	public @ResponseBody String fetchUser() {
		mongoDatabase = mongoClient.getDatabase(DATABASE_NAME);
		mongoCollection = mongoDatabase.getCollection(COLLECTION_NAME);
		FindIterable<Document> findIterable = mongoCollection.find();
		String key = "574384";
		RORUser user = null;
		for (Document document1 : findIterable) {
			if (document1.containsKey(key)) {
				user = gson.fromJson((String) document1.get(key), RORUser.class);
			}
		}
		System.out.println(user);
		return gson.toJson(user);
	}
	
	/**Store user rest service
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/storeUser", consumes = "application/json", method = RequestMethod.POST)
	public @ResponseBody String storeUser(@RequestBody RORUser user) {
		RORResponseVO responseVO = rorSvc.storeUser(user);
		System.out.println(responseVO);
		return gson.toJson(responseVO);
	}
	
	

}
