package de.hhn.it.gvs.cc.todo.fkps.bd;

import java.util.List;

import de.hhn.it.gvs.cc.basics.BDCCService;
import de.hhn.it.gvs.cc.basics.Token;
import de.hhn.it.gvs.cc.exceptions.IllegalParameterException;
import de.hhn.it.gvs.cc.exceptions.InvalidTokenException;
import de.hhn.it.gvs.cc.exceptions.ServiceNotAvailableException;

/*
 * Interface of the todo lists service
 * 
 * @version 2016-09-29
 */
public interface BDTodoListsService extends BDCCService {

	/**
	 * creates a new todo list.
	 *
	 * @param userToken
	 * @param todoListName
	 * @throws ServiceNotAvailableException
	 *             the service not available exception
	 * @throws InvalidTokenException
	 *             the invalid token exception
	 * @throws IllegalParameterException
	 *             for invalid parameters
	 */
	public Token createTodoList(Token userToken, String todoListName)
			throws ServiceNotAvailableException, InvalidTokenException, IllegalParameterException;

	/**
	 * get the todo list item depending on a given token.
	 * 
	 * @param userToken
	 * @param itemToken
	 * @throws ServiceNotAvailableException
	 *             the service not available exception
	 * @throws InvalidTokenExceptio6n
	 *             the invalid token exception
	 * @throws IllegalParameterException
	 *             for invalid parameters
	 */
	public TodoListItem getItemFromTodoList(Token userToken, Token itemToken)
			throws ServiceNotAvailableException, InvalidTokenException, IllegalArgumentException;

	/**
	 * adds a new item to a todo list.
	 *
	 * @param userToken
	 * @param listToken
	 * @param itemToken
	 * @param todoListItemName
	 * @throws ServiceNotAvailableException
	 *             the service not available exception
	 * @throws InvalidTokenException
	 *             the invalid token exception
	 * @throws IllegalParameterException
	 *             for invalid parameters
	 */
	public Token addTodoListItem(Token userToken, Token listToken, String todoListItemName)
			throws ServiceNotAvailableException, IllegalArgumentException, InvalidTokenException,
			IllegalParameterException;

	/**
	 * updates a item in a todo list.
	 *
	 * @param userToken
	 * @param itemToken
	 * @param oldTodoListItemName
	 * @param newTodoListItemName
	 * @throws ServiceNotAvailableException
	 *             the service not available exception
	 * @throws InvalidTokenException
	 *             the invalid token exception
	 * @throws IllegalParameterException
	 *             for invalid parameters
	 */
	public void updateTodoListItem(Token userToken, String newTodoListItemName, Token itemToken)
			throws ServiceNotAvailableException, InvalidTokenException, IllegalParameterException;

	/**
	 * removes an item from a todo list.
	 *
	 * @param userToken
	 * @param listToken
	 * @param itemToken
	 * @param todoListItemName
	 * @throws ServiceNotAvailableException
	 *             the service not available exception
	 * @throws InvalidTokenException
	 *             the invalid token exception
	 * @throws IllegalParameterException
	 *             for invalid parameters
	 */
	public void removeTodoListItem(Token userToken, Token itemToken)
			throws ServiceNotAvailableException, InvalidTokenException, IllegalParameterException;

	/**
	 * removes a todo list.
	 *
	 * @param userToken
	 * @param listToken
	 * @throws ServiceNotAvailableException
	 *             the service not available exception
	 * @throws InvalidTokenException
	 *             the invalid token exception
	 * @throws IllegalParameterException
	 *             for invalid parameters
	 */
	public void removeTodoList(Token userToken, Token listToken)
			throws ServiceNotAvailableException, InvalidTokenException, IllegalParameterException;

	/**
	 * gets all todo lists.
	 *
	 * @param userToken
	 * @throws ServiceNotAvailableException
	 *             the service not available exception
	 * @throws InvalidTokenException
	 *             the invalid token exception
	 * @throws IllegalParameterException
	 *             for invalid parameters
	 */
	public List<TodoList> getAllTodoLists(Token userToken)
			throws ServiceNotAvailableException, InvalidTokenException, IllegalParameterException;

	/**
	 * gets all items from a list.
	 * 
	 * @param userToken
	 * @param listToken
	 * @throws ServiceNotAvailableException
	 *             the service not available exception
	 * @throws InvalidTokenException
	 *             the invalid token exception
	 * @throws IllegalParameterException
	 *             for invalid parameters
	 */
	public List<TodoListItem> getAllItemsFromAList(Token userToken, Token listToken)
			throws ServiceNotAvailableException, InvalidTokenException, IllegalParameterException;
}
