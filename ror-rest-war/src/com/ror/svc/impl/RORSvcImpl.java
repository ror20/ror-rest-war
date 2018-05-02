package com.ror.svc.impl;

import java.util.List;

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

}
