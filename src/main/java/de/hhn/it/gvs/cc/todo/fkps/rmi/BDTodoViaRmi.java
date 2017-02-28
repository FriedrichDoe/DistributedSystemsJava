package de.hhn.it.gvs.cc.todo.fkps.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hhn.it.gvs.cc.basics.Token;
import de.hhn.it.gvs.cc.exceptions.IllegalParameterException;
import de.hhn.it.gvs.cc.exceptions.InvalidTokenException;
import de.hhn.it.gvs.cc.exceptions.ServiceNotAvailableException;
import de.hhn.it.gvs.cc.todo.fkps.bd.BDTodoListsService;
import de.hhn.it.gvs.cc.todo.fkps.bd.TodoList;
import de.hhn.it.gvs.cc.todo.fkps.bd.TodoListItem;

public class BDTodoViaRmi implements BDTodoListsService {

	private static final Logger logger = LoggerFactory.getLogger(BDTodoViaRmi.class);

	private String host = "localhost";
	private int port = 1099;
	private RmiTodo service;

	private void connectToService() throws ServiceNotAvailableException {

		logger.info("get access to registry on host {} with port {}", host, port);

		try {
			Registry registry = LocateRegistry.getRegistry(host, port);
			service = (RmiTodo) registry.lookup(RmiTodo.SERVICE_KEY);
		} catch (RemoteException | NotBoundException e) {
			String errorMessage = "cannot connect to service on host / port " + host + " / " + port;
			throw new ServiceNotAvailableException(errorMessage, e);
		}
	}

	public BDTodoViaRmi(String hostname, int portNumber) {
		this.host = hostname;
		this.port = portNumber;
	}

	@Override
	public Token createTodoList(Token userToken, String todoListName)
			throws ServiceNotAvailableException, InvalidTokenException, IllegalParameterException {
		logger.info("create todo list via rmi call");
		connectToService();
		try {
			logger.info("create todo list via rmi done.");
			return service.createTodoList(userToken, todoListName);
		} catch (RemoteException e) {
			logger.info("create todo list via rmi failed.");
			throw new ServiceNotAvailableException(e);
		}
	}

	@Override
	public TodoListItem getItemFromTodoList(Token userToken, Token itemToken)
			throws ServiceNotAvailableException, InvalidTokenException, IllegalArgumentException {
		logger.info("getItemFromTodoList via rmi call");
		connectToService();
		try {
			return service.getItemFromTodoList(userToken, itemToken);
		} catch (RemoteException e) {
			throw new ServiceNotAvailableException(e);
		}
	}

	@Override
	public Token addTodoListItem(Token userToken, Token listToken, String todoListItemName)
			throws ServiceNotAvailableException, IllegalArgumentException, InvalidTokenException {
		logger.info("addTodoListItem via rmi call");
		connectToService();
		try {
			return service.addTodoListItem(userToken, listToken, todoListItemName);
		} catch (RemoteException e) {
			throw new ServiceNotAvailableException(e);
		}
	}

	@Override
	public void updateTodoListItem(Token userToken, String newTodoListItemName, Token itemToken)
			throws ServiceNotAvailableException, InvalidTokenException, IllegalParameterException {
		logger.info("updateTodoListItem via rmi call");
		connectToService();
		try {
			service.updateTodoListItem(userToken, newTodoListItemName, itemToken);
		} catch (RemoteException e) {
			throw new ServiceNotAvailableException(e);
		}
	}

	@Override
	public void removeTodoListItem(Token userToken, Token itemToken)
			throws ServiceNotAvailableException, InvalidTokenException, IllegalParameterException {
		logger.info("removeTodoListItem via rmi call");
		connectToService();
		try {
			service.removeTodoListItem(userToken, itemToken);
		} catch (RemoteException e) {
			throw new ServiceNotAvailableException(e);
		}
	}

	@Override
	public void removeTodoList(Token userToken, Token listToken)
			throws ServiceNotAvailableException, InvalidTokenException, IllegalParameterException {
		logger.info("removeTodoList via rmi call");
		connectToService();
		try {
			service.removeTodoList(userToken, listToken);
		} catch (RemoteException e) {
			throw new ServiceNotAvailableException(e);
		}
	}

	@Override
	public List<TodoList> getAllTodoLists(Token userToken)
			throws ServiceNotAvailableException, InvalidTokenException, IllegalParameterException {
		logger.info("getAllTodoLists via rmi call");
		connectToService();
		try {
			return service.getAllTodoLists(userToken);
		} catch (RemoteException e) {
			throw new ServiceNotAvailableException(e);
		}

	}

	@Override
	public List<TodoListItem> getAllItemsFromAList(Token userToken, Token listToken)
			throws ServiceNotAvailableException, InvalidTokenException, IllegalParameterException {
		logger.info("getAllItemsFromAList via rmi call");
		connectToService();
		try {
			return service.getAllItemsFromAList(userToken, listToken);
		} catch (RemoteException e) {
			throw new ServiceNotAvailableException(e);
		}
	}

	@Override
	public Properties getInfo() {
		// TODO Auto-generated method stub
		return null;
	}

}
