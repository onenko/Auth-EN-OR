package net.nenko.AuthNO;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Base actions for AuthNOR implementation subclasses
 * @author conenko
 *
 */
public abstract class AuthNOR_Base implements AuthNOR {

	private Map<String, LoginData> users;
	private long expirationMs = 24 * 3600 * 1000;	// 1 day

	//public String token;

	@Override
	public void init() {
		users = new ConcurrentHashMap<>();
	}

	@Override
	public String login(String user, String pass) {
		for(Map.Entry<String, LoginData> row: users.entrySet()) {
			if(row.getValue().user.equals(user) && row.getValue().pass.equals(pass)) {
				return row.getKey();
			}
		}
		return null;
	}

	@Override
	public long permissions(String token) {
		LoginData data = users.get(token);
		if(data == null) {
			return 0L;
		}
		if(data.ms + expirationMs < System.currentTimeMillis()) {
			return -1L;
		}
		return data.permissions;
	}

	protected void addUser(String user, String pass, long permissions) {
		LoginData loginData = new LoginData();
		loginData.user = user;
		loginData.pass = pass;
		loginData.permissions = permissions;
		loginData.ms = System.currentTimeMillis();
		String uid = UUID.randomUUID().toString();
		users.put(uid, loginData);
	}

	/**
	 * represent one row in users dictionary
	 */
	private static class LoginData {
		public String user;
		public String pass;
		public long permissions;
		public long ms;
	}

}
