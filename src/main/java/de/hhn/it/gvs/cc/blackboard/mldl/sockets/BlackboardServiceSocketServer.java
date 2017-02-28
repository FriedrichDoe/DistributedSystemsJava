package de.hhn.it.gvs.cc.blackboard.mldl.sockets;

import java.util.Properties;

import de.hhn.it.gvs.cc.basics.distribute.sockets.SimpleDelegatingServer;
import de.hhn.it.gvs.cc.blackboard.mldl.bd.BDBlackboardService;
import de.hhn.it.gvs.cc.blackboard.mldl.bd.BlackBoardService;
import de.hhn.it.gvs.cc.forum.wnck.UserManagementFacade;

/**
 */
public class BlackboardServiceSocketServer {

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory
			.getLogger(BlackboardServiceSocketServer.class);

	public static void main(String[] args) throws Exception {
		Properties properties = new Properties();
		UserManagementFacade facade = null;
		BDBlackboardService service = new BlackBoardService();
		SimpleDelegatingServer delegatingServer = new SimpleDelegatingServer(1099, service,
				BlackBoardServiceServeOneClient.class);
		delegatingServer.foreverAcceptAndDelegate();
		logger.info("ready to go ...");
	}
}
