package de.hhn.it.gvs.cc.todo.fkps.nonjunit;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hhn.it.gvs.cc.todo.fkps.bd.TodoListsService;
import de.hhn.it.gvs.cc.todo.fkps.rmi.RmiTodo;
import de.hhn.it.gvs.cc.todo.fkps.rmi.RmiTodoService;

public class LaunchTodoOnlyTestServer {

	private static final Logger logger = LoggerFactory.getLogger(LaunchTodoOnlyTestServer.class);

	public static void main(String[] args) throws RemoteException, InterruptedException {
		Registry registry = LocateRegistry.createRegistry(1099);
		logger.info("registry done");
		TodoListsService service = new TodoListsService();
		logger.info("service creation done");
		RmiTodoService rmiTodoService = new RmiTodoService(service);
		RmiTodo proxy = (RmiTodo) UnicastRemoteObject.exportObject(rmiTodoService, 0);
		registry.rebind(RmiTodo.SERVICE_KEY, proxy);
		logger.info("Proxy Service Ready! " + proxy);
		Thread.sleep(500);
	}
}
