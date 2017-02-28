package de.hhn.it.gvs.cc.blackboard.mldl.sockets;

import de.hhn.it.gvs.cc.basics.Token;
import de.hhn.it.gvs.cc.blackboard.mldl.bd.BDBlackboardService;
import de.hhn.it.gvs.cc.exceptions.IllegalParameterException;
import de.hhn.it.gvs.cc.exceptions.InvalidTokenException;
import de.hhn.it.gvs.cc.exceptions.ServiceNotAvailableException;

/**
 */
public class BlackBoardServiceSocketClientTest {

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory
			.getLogger(BlackBoardServiceSocketClientTest.class);

	public static void main(String[] args)
			throws ServiceNotAvailableException, InvalidTokenException, IllegalParameterException {

		logger.info("client start");
		Token userToken = new Token("fk 1000 ps");

		logger.info("created token with: " + userToken.getToken());
		BDBlackboardService service = new BDBlackboardServiceViaSockets("localhost", 1099);

		Token blackboardToken = service.createBlackboard(userToken, "myBlackboard", "description");
		logger.info("Blackboard has been created. BlackboardToken: " + blackboardToken.getToken());
	}
}
