package de.hhn.it.gvs.cc.blackboard.mldl;

import org.junit.BeforeClass;
import org.junit.Test;

import de.hhn.it.gvs.cc.blackboard.mldl.bd.BlackBoard;
import de.hhn.it.gvs.cc.blackboard.mldl.bd.UserManagementBDFacadeMock;

public class BlackBoardTest {

	static BlackBoard blackBoard;
	private static UserManagementBDFacadeMock user;

	@BeforeClass
	public static void setUp() throws Exception {
		// blackBoard = new
		// BlackBoard(user.generateUserToken(),"FirstBlackboard", "desc");
	}

	@Test
	public void testGetName() {
		// blackBoard.getName();
	}
}
