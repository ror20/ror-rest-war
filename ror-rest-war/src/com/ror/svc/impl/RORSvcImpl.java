package com.ror.svc.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.ror.dao.RORDAO;
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
		return rorDAO.fetchUser(userId);
	}

}
