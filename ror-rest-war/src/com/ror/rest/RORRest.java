package com.ror.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
