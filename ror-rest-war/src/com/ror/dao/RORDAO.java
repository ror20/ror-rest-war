package com.ror.dao;

import java.util.List;

import com.ror.model.RORUser;
import com.ror.vo.RORResponseVO;

public interface RORDAO {

	public RORResponseVO storeUser(RORUser user);

	public RORUser fetchUser(String userId);

	public RORResponseVO updateUser(RORUser user);

	public RORResponseVO deleteUser(String userId);

	public List<RORUser> fetchAllUser();

	public boolean checkUserExist(String userId);

	public boolean checkPasswordMatch(String id, String password);
}
