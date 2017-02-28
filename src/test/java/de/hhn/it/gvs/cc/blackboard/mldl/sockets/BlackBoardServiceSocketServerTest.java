package de.hhn.it.gvs.cc.blackboard.mldl.sockets;

import de.hhn.it.gvs.cc.basics.distribute.sockets.SimpleDelegatingServer;
import de.hhn.it.gvs.cc.blackboard.mldl.bd.BDBlackboardService;
import de.hhn.it.gvs.cc.blackboard.mldl.bd.BlackBoardService;

/**
 */
public class BlackBoardServiceSocketServerTest {

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory
			.getLogger(BlackBoardServiceSocketServerTest.class);

	public static void main(String[] args) throws Exception {

		BDBlackboardService service = new BlackBoardService();
		SimpleDelegatingServer delegatingServer = new SimpleDelegatingServer(1099, service,
				BlackBoardServiceServeOneClient.class);
		delegatingServer.foreverAcceptAndDelegate();
		logger.info("ready to go ...");
	}

}
