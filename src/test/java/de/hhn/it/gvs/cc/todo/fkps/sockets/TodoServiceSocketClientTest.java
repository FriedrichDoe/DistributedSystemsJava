package de.hhn.it.gvs.cc.todo.fkps.sockets;

import de.hhn.it.gvs.cc.basics.Token;
import de.hhn.it.gvs.cc.exceptions.IllegalParameterException;
import de.hhn.it.gvs.cc.exceptions.InvalidTokenException;
import de.hhn.it.gvs.cc.exceptions.ServiceNotAvailableException;
import de.hhn.it.gvs.cc.todo.fkps.bd.BDTodoListsService;

public class TodoServiceSocketClientTest {

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(TodoServiceSocketClientTest.class);

	public static void main(String[] args)
			throws ServiceNotAvailableException, InvalidTokenException, IllegalParameterException {

		logger.info("client start");
		Token userToken = new Token("fk ps");

		logger.info("created token with: " + userToken.getToken());
		BDTodoListsService service = new BDTodoViaSockets("localhost", 1099);

		Token listToken = service.createTodoList(userToken, "neue liste");
		logger.info("create todoList done. listToken: " + listToken.getToken());

		Token itemToken = service.addTodoListItem(userToken, listToken, "einkaufen");
		logger.info("create todolistitem done. itemtoken: " + itemToken.getToken());

		logger.info("now we must see rethrown exception :) ");

		Token itemToken2 = service.addTodoListItem(userToken, null, "lernen");
		logger.info("create todolistitem done. itemtoken: " + itemToken2.getToken());
	}
}
