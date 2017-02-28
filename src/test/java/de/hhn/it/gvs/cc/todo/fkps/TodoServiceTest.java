package de.hhn.it.gvs.cc.todo.fkps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import de.hhn.it.gvs.cc.basics.ObjectTokenFactory;
import de.hhn.it.gvs.cc.basics.Token;
import de.hhn.it.gvs.cc.exceptions.IllegalParameterException;
import de.hhn.it.gvs.cc.exceptions.InvalidTokenException;
import de.hhn.it.gvs.cc.exceptions.ServiceNotAvailableException;
import de.hhn.it.gvs.cc.todo.fkps.bd.TodoListItem;
import de.hhn.it.gvs.cc.todo.fkps.bd.TodoListsService;

public class TodoServiceTest {

	private TodoListsService todoService;
	private ObjectTokenFactory tokenObj;
	private Token userToken;

	private final String todoListName = "Freizeit";
	private final String todoItemName1 = "einkaufen";
	private final String todoItemName2 = "lernen";
	private final String todoItemUpdateName = "Power Nap";

	@Before
	public void setUp() throws Exception {
		todoService = new TodoListsService();
		tokenObj = new ObjectTokenFactory(null);
		userToken = tokenObj.createToken(this);
	}

	@Test
	public void createTodoListTest() {
		try {
			Token todoListToken = todoService.createTodoList(userToken, todoListName);

			assertFalse(todoService.getAllTodoLists(userToken).isEmpty());
			assertNotNull(todoListToken);

			todoService.getAllTodoLists(userToken).forEach(list -> {
				if (list.getTodoListToken().equals(todoListToken))
					assertEquals(list.getTodoListName(), todoListName);
			});

		} catch (ServiceNotAvailableException | InvalidTokenException | IllegalParameterException e) {
			System.out.println("createTodoListTest ERROR");
			e.printStackTrace();
		}
	}

	@Test
	public void addAndGetItemsTest() {
		try {
			Token todoListToken = todoService.createTodoList(userToken, todoListName);

			Token itemToken1 = todoService.addTodoListItem(userToken, todoListToken, todoItemName1);
			Token itemToken2 = todoService.addTodoListItem(userToken, todoListToken, todoItemName2);

			assertNotNull(todoService.getAllTodoLists(userToken));
			assertNotNull(todoService.getItemFromTodoList(userToken, itemToken1));
			assertNotNull(todoService.getItemFromTodoList(userToken, itemToken2));

		} catch (ServiceNotAvailableException | InvalidTokenException | IllegalParameterException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void updateTodoListItemTest() {
		try {
			Token todoListToken = todoService.createTodoList(userToken, todoListName);

			Token itemToken1 = todoService.addTodoListItem(userToken, todoListToken, todoItemName1);
			Token itemToken2 = todoService.addTodoListItem(userToken, todoListToken, todoItemName2);

			todoService.updateTodoListItem(userToken, todoItemUpdateName, itemToken1);
			todoService.updateTodoListItem(userToken, todoItemUpdateName, itemToken2);

			TodoListItem item1 = todoService.getItemFromTodoList(userToken, itemToken1);
			TodoListItem item2 = todoService.getItemFromTodoList(userToken, itemToken2);

			assertNotNull(todoService.getAllTodoLists(userToken));
			assertNotNull(item1);
			assertNotNull(item2);
			assertNotEquals(item1.getTodoItem(), todoItemName1);
			assertEquals(item2.getTodoItem(), todoItemUpdateName);

		} catch (ServiceNotAvailableException | InvalidTokenException | IllegalParameterException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void removeTodoListItemTest() {
		try {
			Token todoListToken = todoService.createTodoList(userToken, todoListName);

			Token itemToken1 = todoService.addTodoListItem(userToken, todoListToken, todoItemName1);
			Token itemToken2 = todoService.addTodoListItem(userToken, todoListToken, todoItemName2);

			todoService.removeTodoList(userToken, todoListToken);
			todoService.removeTodoListItem(userToken, itemToken1);
			todoService.removeTodoListItem(userToken, itemToken2);

			assertEquals(todoService.getAllTodoLists(userToken).size(), 0);
			assertNull(todoService.getItemFromTodoList(userToken, itemToken1));
			assertNull(todoService.getItemFromTodoList(userToken, itemToken2));

		} catch (ServiceNotAvailableException | InvalidTokenException | IllegalParameterException e) {
			e.printStackTrace();
		}
	}

}
