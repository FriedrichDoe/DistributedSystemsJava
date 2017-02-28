package de.hhn.it.gvs.cc.todo.fkps.rest;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.hhn.it.gvs.cc.basics.Token;
import de.hhn.it.gvs.cc.exceptions.IllegalParameterException;
import de.hhn.it.gvs.cc.exceptions.InvalidTokenException;
import de.hhn.it.gvs.cc.exceptions.ServiceNotAvailableException;
import de.hhn.it.gvs.cc.forum.mock.UserManagementFacadeMock;
import de.hhn.it.gvs.cc.todo.fkps.bd.BDTodoListsService;
import de.hhn.it.gvs.cc.todo.fkps.bd.TodoList;

public class TodoServiceRestClient {

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(TodoServiceRestClient.class);

	public static void main(String[] args)
			throws IllegalParameterException, InvalidTokenException, ServiceNotAvailableException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules();

		UserManagementFacadeMock umFacade = new UserManagementFacadeMock();
		Token userToken = umFacade.getAlwaysTrueToken();
		logger.info("User Token = " + userToken);
		Token badUserToken = umFacade.createBadToken();

		BDTodoListsService service = new BDTodoServiceViaRest("http://localhost:8080/todoservice/");

		Token newTodolistToken = service.createTodoList(userToken, "neue test todo liste");
		logger.info("create todo list success: " + newTodolistToken);

		Token newTodoitemToken = service.addTodoListItem(userToken, newTodolistToken, "neuer test task");
		logger.info("create todo item success: " + newTodoitemToken);

		List<TodoList> todos = service.getAllTodoLists(badUserToken);

	}

}
