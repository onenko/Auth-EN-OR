package net.nenko.AuthNO;

public class AuthNOR_RAM extends AuthNOR_Base implements AuthNOR {

	public static final long PERM1 = 0x01L;
	public static final long PERM2 = 0x02L;
	public static final long PERM3 = 0x04L;
	public static final long PERM4 = 0x08L;

	@Override
	public void init() {
		// TODO Auto-generated method stub
		super.init();
		this.addUser("user1", "pass1", PERM1);
		this.addUser("user2", "pass2", PERM2);
		this.addUser("user34", "pass34", PERM3 | PERM4);
	}

}
