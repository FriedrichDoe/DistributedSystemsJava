package de.hhn.it.gvs.cc.todo.fkps.bd;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import de.hhn.it.gvs.cc.basics.Token;
import de.hhn.it.gvs.cc.basics.TokenizedObject;

public class TodoList extends TokenizedObject implements Serializable {

	/**
	 * token for this obj.
	 */
	public Token todoListToken;

	/**
	 * token for the user which creates this obj.
	 */
	public Token creator;

	/**
	 * the time of creation.
	 */
	public Instant creatingTime;

	/**
	 * the list for the todo items.
	 */
	public List<TodoListItem> todoList;

	/**
	 * the name for the todo list.
	 */
	public String todoListName;

	/**
	 * 
	 */
	private static final long serialVersionUID = 4711L;

	public TodoList() {
		super(null, null);
		todoList = new ArrayList<TodoListItem>();
	}

	public TodoList(final Token userToken, final Token token, final String title) {
		super(userToken, token);
		this.todoListName = title;
		todoList = new ArrayList<TodoListItem>();
	}

	public Token getTodoListToken() {
		return todoListToken;
	}

	public void setTodoListToken(Token todoListToken) {
		this.todoListToken = todoListToken;
	}

	public Token getCreator() {
		return creator;
	}

	public void setCreator(Token creator) {
		this.creator = creator;
	}

	public Instant getCreatingTime() {
		return creatingTime;
	}

	public void setCreatingTime(Instant creatingTime) {
		this.creatingTime = creatingTime;
	}

	public List<TodoListItem> getTodoList() {
		return todoList;
	}

	public void setTodoList(List<TodoListItem> todoList) {
		this.todoList = todoList;
	}

	public String getTodoListName() {
		return todoListName;
	}

	public void setTodoListName(String todoListName) {
		this.todoListName = todoListName;
	}

	public void addTodoItem(TodoListItem newItem) {
		todoList.add(newItem);
	}

	public void removeTodoItem(TodoListItem todoItem) {
		if (todoList.contains(todoItem))
			todoList.remove(todoItem);
	}

	public void replaceTodoItem(TodoListItem todoItem) {
		if (todoList.contains(todoItem))
			todoList.remove(todoItem);

		todoList.add(todoItem);
	}

}