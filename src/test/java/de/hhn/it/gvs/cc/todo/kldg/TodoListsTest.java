package de.hhn.it.gvs.cc.todo.kldg;

import org.junit.Assert;
import org.junit.Test;

import de.hhn.it.gvs.cc.basics.Token;
import de.hhn.it.gvs.cc.exceptions.IllegalParameterException;
import de.hhn.it.gvs.cc.exceptions.InvalidTokenException;
import de.hhn.it.gvs.cc.exceptions.ServiceNotAvailableException;
import de.hhn.it.gvs.cc.todo.fkps.bd.TodoListsService;

/**
 */
public class TodoListsTest {

	TodoListsService service = new TodoListsService();
	Token userToken = new Token("12345");

	@Test
	public void createNewTodoListTest()
			throws IllegalParameterException, InvalidTokenException, ServiceNotAvailableException {
		Token todoList = service.createTodoList(userToken, "TestList");
		Assert.assertEquals(service.getAllTodoLists(todoList).size(), 1);
		Assert.assertEquals(service.getAllTodoLists(userToken).get(0).getTodoListName(), "TestList");
	}

	@Test
	public void addItemTest() throws IllegalParameterException, InvalidTokenException, ServiceNotAvailableException {
		Token todoList = service.createTodoList(userToken, "Test");
		service.addTodoListItem(userToken, todoList, "testItem");
		Assert.assertEquals(service.getAllTodoLists(userToken).get(0).getTodoList().get(0).todoItem, "testItem");
	}
}
