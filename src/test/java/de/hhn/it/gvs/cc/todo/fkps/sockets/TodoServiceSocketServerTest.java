package de.hhn.it.gvs.cc.todo.fkps.sockets;

import de.hhn.it.gvs.cc.basics.distribute.sockets.SimpleDelegatingServer;
import de.hhn.it.gvs.cc.todo.fkps.bd.BDTodoListsService;
import de.hhn.it.gvs.cc.todo.fkps.bd.TodoListsService;

public class TodoServiceSocketServerTest {

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(TodoServiceSocketServerTest.class);

	public static void main(String[] args) throws Exception {
		BDTodoListsService service = new TodoListsService();
		SimpleDelegatingServer delegatingServer = new SimpleDelegatingServer(1099, service,
				TodoServiceServeOneClient.class);
		delegatingServer.foreverAcceptAndDelegate();
		logger.info("ready to go ...");
	}

}
