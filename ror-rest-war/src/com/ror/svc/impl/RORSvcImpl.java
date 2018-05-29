package com.ror.svc.impl;

import static com.ror.constants.RORConstants.SYMBOL_AND;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ror.dao.RORDAO;
import com.ror.model.MessageDetails;
import com.ror.model.RORMessages;
import com.ror.model.RORUser;
import com.ror.svc.RORSvc;
import com.ror.vo.RORResponseVO;

public class RORSvcImpl implements RORSvc {

	@Autowired
	private RORDAO rorDAO;

	@Override
	public RORResponseVO storeUser(RORUser user) {
		return rorDAO.storeUser(user);
	}

	@Override
	public RORUser fetchUser(String userId) {
		RORUser user = rorDAO.fetchUser(userId);
		if (user == null) {
			return new RORUser(null, null, null, null);
		}
		return user;
	}

	@Override
	public RORResponseVO updateUser(RORUser user) {
		return rorDAO.updateUser(user);
	}

	@Override
	public RORResponseVO deleteUser(String userId) {
		return rorDAO.deleteUser(userId);
	}

	@Override
	public List<RORUser> fetchAlluser() {
		return rorDAO.fetchAllUser();
	}

	@Override
	public boolean checkUserExist(String userId) {
		return rorDAO.checkUserExist(userId);
	}

	@Override
	public boolean checkPasswordMatch(String idAndPassword) {
		String credentials[] = idAndPassword.split(SYMBOL_AND);
		if (credentials.length == 2) {
			String id = credentials[0];
			String password = credentials[1];
			return rorDAO.checkPasswordMatch(id, password);
		} else {
			return false;
		}
	}

	@Override
	public List<MessageDetails> sentMessage(String id) {
		return rorDAO.sentMessage(id);
	}

	@Override
	public List<MessageDetails> receivedMessage(String id) {
		return rorDAO.receivedMessage(id);
	}

	@Override
	public RORResponseVO draftMessage(MessageDetails messageDetails) {
		messageDetails.setMessageSentTime(new Date().toString());
		return rorDAO.draftMessage(messageDetails);
	}

	@Override
	public RORMessages messageComepleteDetails(String id) {
		 List<MessageDetails> sent = rorDAO.sentMessage(id);
		 List<MessageDetails> received = rorDAO.receivedMessage(id);
		 RORMessages messages = new RORMessages(id, sent, received);
		return messages;
	}

}
