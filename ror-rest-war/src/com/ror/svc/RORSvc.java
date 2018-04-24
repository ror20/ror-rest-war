package com.ror.svc;

import com.ror.model.RORUser;
import com.ror.vo.RORResponseVO;

public interface RORSvc {
	
	public RORResponseVO storeUser(RORUser user);
	
	public RORUser fetchUser(String userId);

}
