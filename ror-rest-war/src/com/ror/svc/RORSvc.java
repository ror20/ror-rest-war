package com.ror.svc;

import java.util.List;

import com.ror.model.RORUser;
import com.ror.vo.RORResponseVO;

public interface RORSvc {
	
	public RORResponseVO storeUser(RORUser user);
	
	public RORUser fetchUser(String userId);

	public RORResponseVO updateUser(RORUser user);
	
	public RORResponseVO deleteUser(String userId);

	public List<RORUser> fetchAlluser();

}
