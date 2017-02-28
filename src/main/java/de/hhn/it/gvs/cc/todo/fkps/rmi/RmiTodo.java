package de.hhn.it.gvs.cc.todo.fkps.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import de.hhn.it.gvs.cc.basics.Token;
import de.hhn.it.gvs.cc.exceptions.IllegalParameterException;
import de.hhn.it.gvs.cc.exceptions.InvalidTokenException;
import de.hhn.it.gvs.cc.exceptions.ServiceNotAvailableException;
import de.hhn.it.gvs.cc.todo.fkps.bd.TodoList;
import de.hhn.it.gvs.cc.todo.fkps.bd.TodoListItem;

/*
 * Interface of the todo lists service
 * 
 * @author Friedrich
 * @version 2016-11-13
 */
public interface RmiTodo extends Remote {

	/**
	 * rmi service variable
	 */
	String SERVICE_KEY = "todooooo";

	/**
	 * creates a new todo list.
	 *
	 * @param userToken
	 * @param todoListName
	 * @throws RemoteException
	 *             the service not available exception
	 * @throws InvalidTokenException
	 *             the invalid token exception
	 * @throws IllegalParameterException
	 *             for invalid parameters
	 * @throws ServiceNotAvailableException
	 */
	public Token createTodoList(Token userToken, String todoListName)
			throws RemoteException, InvalidTokenException, IllegalParameterException, ServiceNotAvailableException;

	/**
	 * get the todo list item depending on a given token.
	 * 
	 * @param userToken
	 * @param itemToken
	 * @throws RemoteException
	 *             the service not available exception
	 * @throws ServiceNotAvailableException
	 * @throws InvalidTokenExceptio6n
	 *             the invalid token exception
	 * @throws IllegalParameterException
	 *             for invalid parameters
	 */
	public TodoListItem getItemFromTodoList(Token userToken, Token itemToken)
			throws RemoteException, InvalidTokenException, IllegalArgumentException, ServiceNotAvailableException;

	/**
	 * adds a new item to a todo list.
	 *
	 * @param userToken
	 * @param listToken
	 * @param itemToken
	 * @param todoListItemName
	 * @throws RemoteException
	 *             the service not available exception
	 * @throws InvalidTokenException
	 *             the invalid token exception
	 * @throws ServiceNotAvailableException
	 * @throws IllegalParameterException
	 *             for invalid parameters
	 */
	public Token addTodoListItem(Token userToken, Token listToken, String todoListItemName)
			throws RemoteException, IllegalArgumentException, InvalidTokenException, ServiceNotAvailableException;

	/**
	 * updates a item in a todo list.
	 *
	 * @param userToken
	 * @param itemToken
	 * @param oldTodoListItemName
	 * @param newTodoListItemName
	 * @throws RemoteException
	 *             the service not available exception
	 * @throws InvalidTokenException
	 *             the invalid token exception
	 * @throws IllegalParameterException
	 *             for invalid parameters
	 * @throws ServiceNotAvailableException
	 */
	public void updateTodoListItem(Token userToken, String newTodoListItemName, Token itemToken)
			throws RemoteException, InvalidTokenException, IllegalParameterException, ServiceNotAvailableException;

	/**
	 * removes an item from a todo list.
	 *
	 * @param userToken
	 * @param listToken
	 * @param itemToken
	 * @param todoListItemName
	 * @throws RemoteException
	 *             the service not available exception
	 * @throws InvalidTokenException
	 *             the invalid token exception
	 * @throws IllegalParameterException
	 *             for invalid parameters
	 * @throws ServiceNotAvailableException
	 */
	public void removeTodoListItem(Token userToken, Token itemToken)
			throws RemoteException, InvalidTokenException, IllegalParameterException, ServiceNotAvailableException;

	/**
	 * removes a todo list.
	 *
	 * @param userToken
	 * @param listToken
	 * @throws RemoteException
	 *             the service not available exception
	 * @throws InvalidTokenException
	 *             the invalid token exception
	 * @throws IllegalParameterException
	 *             for invalid parameters
	 * @throws ServiceNotAvailableException
	 */
	public void removeTodoList(Token userToken, Token listToken)
			throws RemoteException, InvalidTokenException, IllegalParameterException, ServiceNotAvailableException;

	/**
	 * gets all todo lists.
	 *
	 * @param userToken
	 * @throws RemoteException
	 *             the service not available exception
	 * @throws InvalidTokenException
	 *             the invalid token exception
	 * @throws IllegalParameterException
	 *             for invalid parameters
	 * @throws ServiceNotAvailableException
	 */
	public List<TodoList> getAllTodoLists(Token userToken)
			throws RemoteException, InvalidTokenException, IllegalParameterException, ServiceNotAvailableException;

	/**
	 * gets all items from a list.
	 * 
	 * @param userToken
	 * @param listToken
	 * @throws RemoteException
	 *             the service not available exception
	 * @throws InvalidTokenException
	 *             the invalid token exception
	 * @throws IllegalParameterException
	 *             for invalid parameters
	 * @throws ServiceNotAvailableException
	 */
	public List<TodoListItem> getAllItemsFromAList(Token userToken, Token listToken)
			throws RemoteException, InvalidTokenException, IllegalParameterException, ServiceNotAvailableException;
}
