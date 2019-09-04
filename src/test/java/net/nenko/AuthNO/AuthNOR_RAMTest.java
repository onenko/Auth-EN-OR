package net.nenko.AuthNO;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AuthNOR_RAMTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void test() {
		AuthNOR auth = new AuthNOR_RAM();
		auth.init();

		String token1 = auth.login("user1", "pass1");
		assertNotNull(token1);

		assertNull(auth.login("user2", "pass1"));
		assertNotNull(auth.login("user2", "pass2"));

		long permissions1 = auth.permissions(token1);
		assertEquals(AuthNOR_RAM.PERM1, permissions1);

		assertEquals(1, auth.checkPermission(token1, AuthNOR_RAM.PERM1));
		assertEquals(0, auth.checkPermission(token1, AuthNOR_RAM.PERM2));
		assertEquals(-2, auth.checkPermission("BAD TOKEN", AuthNOR_RAM.PERM1));

		assertNull(auth.checkPermission(token1, AuthNOR_RAM.PERM1, "NOT AUTHORIZED", "WRONG_TOKEN", "EXPIRED TOKEN"));
		assertEquals("NOT AUTHORIZED", auth.checkPermission(token1, AuthNOR_RAM.PERM2, "NOT AUTHORIZED", "WRONG_TOKEN", "EXPIRED TOKEN"));
		assertEquals("WRONG_TOKEN", auth.checkPermission("BAD TOKEN", AuthNOR_RAM.PERM1, "NOT AUTHORIZED", "WRONG_TOKEN", "EXPIRED TOKEN"));

		String token4 = auth.login("user34", "pass34");
		assertEquals(AuthNOR_RAM.PERM3 | AuthNOR_RAM.PERM4, auth.permissions(token4));
	}

}
