package de.hhn.it.gvs.cc.todo.fkps.nonjunit;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hhn.it.gvs.cc.basics.Token;
import de.hhn.it.gvs.cc.exceptions.IllegalParameterException;
import de.hhn.it.gvs.cc.exceptions.InvalidTokenException;
import de.hhn.it.gvs.cc.exceptions.ServiceNotAvailableException;
import de.hhn.it.gvs.cc.todo.fkps.bd.BDTodoListsService;
import de.hhn.it.gvs.cc.todo.fkps.bd.UserManagementFacadeMock;
import de.hhn.it.gvs.cc.todo.fkps.rmi.BDTodoViaRmi;

public class LaunchRmiOnlyTestClient {
	private static final Logger logger = LoggerFactory.getLogger(LaunchRmiOnlyTestClient.class);

	public static void main(String[] args) throws IllegalParameterException, InvalidTokenException,
			ServiceNotAvailableException, UnsupportedEncodingException, NoSuchAlgorithmException {

		logger.info("client main started.");
		UserManagementFacadeMock umFacade = new UserManagementFacadeMock();
		Token userToken = umFacade.createGoodToken();
		BDTodoListsService service = new BDTodoViaRmi("localhost", 1099);

		logger.info("usertoken: " + userToken.toString());
		Token listToken = service.createTodoList(userToken, "neue Todo Liste");
		logger.info("todo list created with token: " + listToken.toString());

		Token itemTk1 = service.addTodoListItem(userToken, listToken, "einkaufen");
		logger.info("first todoItem created with token:" + itemTk1.toString());
		Token itemTk2 = service.addTodoListItem(userToken, listToken, "lernen");
		logger.info("second todoItem created with token:" + itemTk2.toString());

	}
}
