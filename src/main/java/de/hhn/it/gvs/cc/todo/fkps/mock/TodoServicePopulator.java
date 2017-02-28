package de.hhn.it.gvs.cc.todo.fkps.mock;

import de.hhn.it.gvs.cc.basics.Token;
import de.hhn.it.gvs.cc.exceptions.IllegalParameterException;
import de.hhn.it.gvs.cc.exceptions.InvalidTokenException;
import de.hhn.it.gvs.cc.exceptions.ServiceNotAvailableException;
import de.hhn.it.gvs.cc.todo.fkps.bd.BDTodoListsService;
import de.hhn.it.gvs.cc.todo.fkps.bd.UserManagementFacadeMock;

public class TodoServicePopulator {

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(TodoServicePopulator.class);

	private static UserManagementFacadeMock umFacade = new UserManagementFacadeMock();

	private static final String TODOLISTNAME1 = "gvs todo list";
	private static final String TODOLISTTASK11 = "bisschen schlafen";
	private static final String TODOLISTTASK12 = "lernen";
	private static final String TODOLISTTASK13 = "bestehen";

	private static final String TODOLISTNAME2 = "pp todo list";
	private static final String TODOTASK21 = "ausschlafen";
	private static final String TODOTASK22 = "bestehen";

	public static void populate(BDTodoListsService service) {
		Token userToken = umFacade.getAlwaysTrueToken();

		try {
			Token todoListToken1 = service.createTodoList(userToken, TODOLISTNAME1);
			Token todotaskToken11 = service.addTodoListItem(userToken, todoListToken1, TODOLISTTASK11);
			Token todotaskToken12 = service.addTodoListItem(userToken, todoListToken1, TODOLISTTASK12);
			Token todotaskToken13 = service.addTodoListItem(userToken, todoListToken1, TODOLISTTASK13);

			Token todoListToken2 = service.createTodoList(userToken, TODOLISTNAME2);
			Token todotaskToken21 = service.addTodoListItem(userToken, todoListToken2, TODOTASK21);
			Token todotaskToken22 = service.addTodoListItem(userToken, todoListToken2, TODOTASK22);

			logger.info("test data creation for REST is done.");

		} catch (InvalidTokenException pE) {
			pE.printStackTrace();
		} catch (IllegalParameterException pE) {
			pE.printStackTrace();
		} catch (ServiceNotAvailableException pE) {
			pE.printStackTrace();
		}

	}
}
