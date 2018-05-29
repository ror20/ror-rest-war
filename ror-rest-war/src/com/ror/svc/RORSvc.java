package com.ror.svc;

import java.util.List;

import com.ror.model.MessageDetails;
import com.ror.model.RORMessages;
import com.ror.model.RORUser;
import com.ror.vo.RORResponseVO;

public interface RORSvc {
	
	public RORResponseVO storeUser(RORUser user);
	
	public RORUser fetchUser(String userId);

	public RORResponseVO updateUser(RORUser user);
	
	public RORResponseVO deleteUser(String userId);

	public List<RORUser> fetchAlluser();

	public boolean checkUserExist(String userId);

	public boolean checkPasswordMatch(String idAndPassword);

	public List<MessageDetails> sentMessage(String id);

	public List<MessageDetails> receivedMessage(String id);

	public RORResponseVO draftMessage(MessageDetails messageDetails);

	public RORMessages messageComepleteDetails(String id);

}
