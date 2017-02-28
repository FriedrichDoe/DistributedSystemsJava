package de.hhn.it.gvs.cc.todo.fkps.bd;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import de.hhn.it.gvs.cc.basics.ObjectTokenFactory;
import de.hhn.it.gvs.cc.basics.Token;
import de.hhn.it.gvs.cc.exceptions.IllegalParameterException;
import de.hhn.it.gvs.cc.exceptions.InvalidTokenException;
import de.hhn.it.gvs.cc.exceptions.ServiceNotAvailableException;

public class TodoListsService implements BDTodoListsService {

	private static final Logger logger = Logger.getLogger(TodoListsService.class.toString());

	/**
	 * list for the todoList objects
	 */
	private List<TodoList> todoLists;

	/**
	 * list for the todoListItem objects
	 */
	private List<TodoListItem> todoListItems;

	/**
	 * ObjectTokenFactory for creating the tokens
	 */
	private ObjectTokenFactory tokenObj;

	// constructor
	public TodoListsService() {
		todoLists = new ArrayList<TodoList>();
		todoListItems = new ArrayList<TodoListItem>();

		tokenObj = new ObjectTokenFactory(null);
	}

	@Override
	public Token createTodoList(Token userToken, String todoListName)
			throws ServiceNotAvailableException, InvalidTokenException, IllegalParameterException {
		// TODO logging creator? time?

		Token newToken = tokenObj.createToken("todoList" + todoListName);

		if (checkToken(newToken)) {
			TodoList newList = new TodoList();
			newList.setTodoListToken(newToken);
			newList.setCreator(userToken);
			newList.setCreatingTime(Instant.now());
			newList.setTodoListName(todoListName);
			todoLists.add(newList);
			return newToken;
		} else {
			throw new InvalidTokenException("ungültiger Token");
		}
	}

	@Override
	public TodoListItem getItemFromTodoList(Token userToken, Token itemToken)
			throws ServiceNotAvailableException, InvalidTokenException, IllegalArgumentException {

		// TODO logging -> usertoken

		if (!checkToken(userToken)) {
			throw new InvalidTokenException("invalid user token");
		} else if (!checkToken(itemToken)) {
			throw new InvalidTokenException("invalid item token");
		}

		// JAVA 8 DAM YOU WHY THIS CODE DONT WORK??
		// todoListItems.forEach(item -> {
		// if (item.todoListItemToken.equals(itemToken))
		// return item;
		// });

		for (TodoListItem todoItem : todoListItems) {
			if (todoItem.getTodoListItemToken().equals(itemToken))
				return todoItem;
		}

		return null;
	}

	@Override
	public Token addTodoListItem(Token userToken, Token listToken, String todoListItemName)
			throws ServiceNotAvailableException, InvalidTokenException, IllegalArgumentException {

		// TODO logging

		if (!checkToken(userToken))
			throw new InvalidTokenException("invalid user token");
		if (!checkToken(listToken))
			throw new InvalidTokenException("invalid list token");

		// new token for todo item
		Token newTodoItemToken = tokenObj.createToken("todoListItem-" + todoListItemName);

		// new todo item obj
		TodoListItem newTodoItem = new TodoListItem(userToken);
		newTodoItem.setTodoListItemToken(newTodoItemToken);
		newTodoItem.setTodoItem(todoListItemName);

		// add the todo item
		todoListItems.add(newTodoItem);

		// add the todo item into given list
		todoLists.forEach(list -> {
			if (list.getTodoListToken().equals(listToken)) {
				list.addTodoItem(newTodoItem);
			}
		});

		return newTodoItemToken;
	}

	@Override
	public void updateTodoListItem(Token userToken, String newTodoListItemName, Token itemToken)
			throws ServiceNotAvailableException, InvalidTokenException, IllegalParameterException {

		// TODO logging

		if (!checkToken(userToken))
			throw new InvalidTokenException("invalid user token");
		if (!checkToken(itemToken))
			throw new InvalidTokenException("invalid item token");

		todoListItems.forEach(todoItem -> {
			if (todoItem.getTodoListItemToken().equals(itemToken))
				todoItem.setTodoItem(newTodoListItemName);
		});

	}

	@Override
	public void removeTodoListItem(Token userToken, Token itemToken)
			throws ServiceNotAvailableException, InvalidTokenException, IllegalParameterException {

		// TODO logging

		// proof if parameter ok else throw exception
		if (!checkToken(userToken))
			throw new InvalidTokenException("invalid user token");
		if (!checkToken(itemToken))
			throw new InvalidTokenException("invalid item token");

		// remove todo item from todo list obj and from todoListItems
		// todoListItems.forEach(todoItem -> {
		// if (todoItem.getTodoListItemToken().equals(itemToken)) {
		// todoLists.forEach(todoList -> {
		// if (todoList.getTodoList().contains(todoItem))
		// todoList.removeTodoItem(todoItem);
		// });
		// todoListItems.remove(todoItem);
		// }
		// });

		for (TodoListItem todoItem : todoListItems) {
			if (todoItem.getTodoListItemToken().equals(itemToken)) {

				for (TodoList todoList : todoLists) {
					if (todoList.getTodoList().contains(todoItem))
						todoList.removeTodoItem(todoItem);
				}

				todoListItems.remove(todoItem);
				return;
			}
		}

	}

	@Override
	public void removeTodoList(Token userToken, Token listToken)
			throws ServiceNotAvailableException, InvalidTokenException, IllegalParameterException {

		// TODO logging

		// proof if parameter ok else throw exception
		if (!checkToken(userToken))
			throw new InvalidTokenException("invalid user token");
		if (!checkToken(listToken))
			throw new InvalidTokenException("invalid list token");

		// remove list from saved data
		for (TodoList todoList : todoLists) {
			if (todoList.getTodoListToken().equals(listToken) && todoList.getCreator().equals(userToken)) {
				todoLists.remove(todoList);
				return;
			}
		}

		// AND AGAIN LAMBDA EXPRESSION FAIL
		// todoLists.forEach(todoList -> {
		// if (todoList.getTodoListToken().equals(listToken) &&
		// todoList.getCreator().equals(userToken)) {
		// System.out.println("first: " + todoLists.size());
		// todoLists.remove(todoList);
		// System.out.println("second: " + todoLists.size());
		// return;
		// }
		// });
	}

	@Override
	public List<TodoList> getAllTodoLists(Token userToken)
			throws ServiceNotAvailableException, InvalidTokenException, IllegalParameterException {
		// TODO logging

		// proof if parameter ok else throw exception
		if (!checkToken(userToken))
			throw new InvalidTokenException("invalid user token");

		return todoLists;
	}

	@Override
	public List<TodoListItem> getAllItemsFromAList(Token userToken, Token listToken)
			throws ServiceNotAvailableException, InvalidTokenException, IllegalParameterException {
		// TODO logging
		// TODO impl return the items depends on the todolist given from
		// parameter

		// proof if parameter ok else throw exception
		if (!checkToken(userToken))
			throw new InvalidTokenException("invalid user token");
		if (!checkToken(listToken))
			throw new InvalidTokenException("invalid list token");

		return todoListItems;
	}

	/**
	 * checks if token is valid.
	 * 
	 * @param token
	 * @return boolean true -> valid token, false -> invalid token
	 */
	private boolean checkToken(Token token) {
		if (token == null)
			return false;

		if (token.getToken() == null)
			return false;

		return true;
	}

	@Override
	public Properties getInfo() {
		// TODO Auto-generated method stub
		return null;
	}
}
