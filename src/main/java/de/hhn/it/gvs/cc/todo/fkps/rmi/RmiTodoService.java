package de.hhn.it.gvs.cc.todo.fkps.rmi;

import java.rmi.RemoteException;
import java.util.List;

import de.hhn.it.gvs.cc.basics.Token;
import de.hhn.it.gvs.cc.exceptions.IllegalParameterException;
import de.hhn.it.gvs.cc.exceptions.InvalidTokenException;
import de.hhn.it.gvs.cc.exceptions.ServiceNotAvailableException;
import de.hhn.it.gvs.cc.todo.fkps.bd.TodoList;
import de.hhn.it.gvs.cc.todo.fkps.bd.TodoListItem;
import de.hhn.it.gvs.cc.todo.fkps.bd.TodoListsService;

public class RmiTodoService implements RmiTodo {

	/**
	 * rmi obj.
	 */
	private TodoListsService service;

	// constructor
	public RmiTodoService(final TodoListsService todoService) {
		this.service = todoService;
	}

	@Override
	public Token createTodoList(Token userToken, String todoListName)
			throws RemoteException, InvalidTokenException, IllegalParameterException, ServiceNotAvailableException {
		return service.createTodoList(userToken, todoListName);
	}

	@Override
	public TodoListItem getItemFromTodoList(Token userToken, Token itemToken)
			throws RemoteException, InvalidTokenException, IllegalArgumentException, ServiceNotAvailableException {
		return service.getItemFromTodoList(userToken, itemToken);
	}

	@Override
	public Token addTodoListItem(Token userToken, Token listToken, String todoListItemName)
			throws RemoteException, IllegalArgumentException, InvalidTokenException, ServiceNotAvailableException {
		return service.addTodoListItem(userToken, listToken, todoListItemName);
	}

	@Override
	public void updateTodoListItem(Token userToken, String newTodoListItemName, Token itemToken)
			throws RemoteException, InvalidTokenException, IllegalParameterException, ServiceNotAvailableException {
		service.updateTodoListItem(userToken, newTodoListItemName, itemToken);
	}

	@Override
	public void removeTodoListItem(Token userToken, Token itemToken)
			throws RemoteException, InvalidTokenException, IllegalParameterException, ServiceNotAvailableException {
		service.removeTodoListItem(userToken, itemToken);
	}

	@Override
	public void removeTodoList(Token userToken, Token listToken)
			throws RemoteException, InvalidTokenException, IllegalParameterException, ServiceNotAvailableException {
		service.removeTodoList(userToken, listToken);
	}

	@Override
	public List<TodoList> getAllTodoLists(Token userToken)
			throws RemoteException, InvalidTokenException, IllegalParameterException, ServiceNotAvailableException {
		return service.getAllTodoLists(userToken);
	}

	@Override
	public List<TodoListItem> getAllItemsFromAList(Token userToken, Token listToken)
			throws RemoteException, InvalidTokenException, IllegalParameterException, ServiceNotAvailableException {
		return service.getAllItemsFromAList(userToken, listToken);
	}

}
