package de.hhn.it.gvs.cc.todo.fkps.bd;

import java.time.Instant;

import de.hhn.it.gvs.cc.basics.Token;

public class TodoListItem {

	/**
	 * token for this obj.
	 */
	public Token todoListItemToken;

	/**
	 * token for the user which creates this obj.
	 */
	public Token creator;

	/**
	 * the time of creation.
	 */
	public Instant creatingTime;

	/**
	 * the item in the todo list.
	 */
	public String todoItem;

	public TodoListItem(Token creator) {
		this.creator = creator;
		creatingTime = Instant.now();
	}

	public Token getTodoListItemToken() {
		return todoListItemToken;
	}

	public void setTodoListItemToken(Token todoListItemToken) {
		this.todoListItemToken = todoListItemToken;
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

	public String getTodoItem() {
		return todoItem;
	}

	public void setTodoItem(String todoItem) {
		this.todoItem = todoItem;
	}

}
