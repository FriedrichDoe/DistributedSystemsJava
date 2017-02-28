package de.hhn.it.gvs.cc.blackboard.mldl.nonJunit;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hhn.it.gvs.cc.basics.Token;
import de.hhn.it.gvs.cc.blackboard.mldl.bd.BDBlackboardService;
import de.hhn.it.gvs.cc.blackboard.mldl.bd.UserManagementBDFacadeMock;
import de.hhn.it.gvs.cc.blackboard.mldl.rmi.BDBlackboardServiceViaRmi;
import de.hhn.it.gvs.cc.exceptions.IllegalParameterException;
import de.hhn.it.gvs.cc.exceptions.InvalidTokenException;
import de.hhn.it.gvs.cc.exceptions.ServiceNotAvailableException;

/**
 */
public class LaunchBlackboardOnlyTestClient {

	private static final Logger logger = LoggerFactory.getLogger(LaunchBlackboardOnlyTestClient.class);

	public static void main(String[] args) throws IllegalParameterException, InvalidTokenException,

			ServiceNotAvailableException, UnsupportedEncodingException, NoSuchAlgorithmException {
		logger.info("Client has been initiated");
		UserManagementBDFacadeMock usermng = new UserManagementBDFacadeMock();
		Token userToken = usermng.generateUserToken();
		BDBlackboardService service = new BDBlackboardServiceViaRmi("localhost", 1099);

		logger.info("Our usertoken: " + userToken.toString());
		Token listofToken = service.createBlackboard(userToken, "NeuesBlackboard", "desc");
		logger.info("Blackboard has been created with token: " + listofToken.toString());

		Token post1 = service.postToBlackboard(userToken, service.getBlackboardToken("Neues Blackboard"), "Content", 2,
				4, "Author");
		logger.info("Post created with token:" + post1.toString());

	}

}
