package com.ror.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ror.exception.RORException;
import com.ror.model.MessageDetails;
import com.ror.model.RORMessages;
import com.ror.model.RORUser;
import com.ror.svc.RORSvc;
import com.ror.vo.RORResponseVO;

@Controller
@RequestMapping("rest")
public class RORRest {

	@Autowired
	private RORSvc rorSvc;

	/**
	 * Fetch user rest Service
	 * 
	 * @param userId
	 * @return RORuser
	 */
	@RequestMapping(value = "/fetchUser/{id}", method = RequestMethod.GET)
	public @ResponseBody RORUser fetchUser(@PathVariable("id") String userId) {
		return rorSvc.fetchUser(userId);
	}

	/**
	 * Store user rest service
	 * 
	 * @param user
	 * @return responseVO
	 */
	@RequestMapping(value = "/storeUser", consumes = "application/json", method = RequestMethod.POST)
	public @ResponseBody RORResponseVO storeUser(@RequestBody RORUser user) {
		return rorSvc.storeUser(user);
	}

	/**
	 * Update user rest service
	 * 
	 * @param user
	 * @return responseVO
	 */
	@RequestMapping(value = "/updateUser", consumes = "application/json", method = RequestMethod.PUT)
	public @ResponseBody RORResponseVO updateUser(@RequestBody RORUser user) {
		return rorSvc.updateUser(user);
	}

	/**
	 * Delete user rest service
	 * 
	 * @param user
	 * @return responseVO
	 */
	@RequestMapping(value = "/deleteUser/{id}", method = RequestMethod.DELETE)
	public @ResponseBody RORResponseVO deleteUser(@PathVariable("id") String userId) {
		return rorSvc.deleteUser(userId);
	}

	/**
	 * Fetches all the users from the DB rest service
	 * 
	 * @return
	 */
	@RequestMapping(value = "/fetchAllUsers", method = RequestMethod.GET)
	public @ResponseBody List<RORUser> fetchAllusers() {
		return rorSvc.fetchAlluser();
	}

	/**
	 * Checks if the userId is present or not
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/checkUserExist/{id}", method = RequestMethod.GET)
	public @ResponseBody boolean checkUserExist(@PathVariable("id") String userId) {
		return rorSvc.checkUserExist(userId);
	}

	/**
	 * Id and Password match check
	 * 
	 * @param idAndPassword
	 * @return
	 */
	@RequestMapping(value = "/checkPasswordMatch/{idpassword}", method = RequestMethod.GET)
	public @ResponseBody boolean checkPasswordMatch(@PathVariable("idpassword") String idAndPassword) {
		return rorSvc.checkPasswordMatch(idAndPassword);
	}

	/**
	 * Rest Service to fetch the messages sent by a user
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/messages/sent/{id}", method = RequestMethod.GET)
	public @ResponseBody List<MessageDetails> sentMessage(@PathVariable("id") String id) {
		return rorSvc.sentMessage(id);
	}

	/**
	 * Rest Service to fetch the messages received by a user
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/messages/received/{id}", method = RequestMethod.GET)
	public @ResponseBody List<MessageDetails> receivedMessage(@PathVariable("id") String id) {
		return rorSvc.receivedMessage(id);
	}

	/**
	 * Rest Service to send a message to a user
	 * 
	 * @param messageDetails
	 * @return
	 */
	@RequestMapping(value = "/messages/draft", method = RequestMethod.POST)
	public @ResponseBody RORResponseVO draftMessage(@RequestBody MessageDetails messageDetails) {
		System.out.println("Incoming request:"+messageDetails);
		return rorSvc.draftMessage(messageDetails);
	}

	/**
	 * Rest Service to fetch the complete messaging details of a user
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/messages/details/{id}", method = RequestMethod.GET)
	public @ResponseBody RORMessages messageComepleteDetails(@PathVariable("id") String id) {
		return rorSvc.messageComepleteDetails(id);
	}
	
	/**Rest Service to fetch the conversation between two users
	 * @param u1andu2
	 * @return
	 */
	@RequestMapping(value = "/messages/convo/{u1andu2}", method = RequestMethod.GET)
	public @ResponseBody List<MessageDetails> fetchConversation(@PathVariable("u1andu2") String u1andu2) throws RORException {
		return rorSvc.fetchConversation(u1andu2);
	}
	
	/**Rest Service to search users based on the user name
	 * @param userName
	 * @return
	 * @throws RORException
	 */
	@RequestMapping(value = "/searchUser/{userName}", method = RequestMethod.GET)
	public @ResponseBody List<RORUser> searchUser(@PathVariable("userName") String userName) throws RORException {
		return rorSvc.searchUser(userName);
	}
}
