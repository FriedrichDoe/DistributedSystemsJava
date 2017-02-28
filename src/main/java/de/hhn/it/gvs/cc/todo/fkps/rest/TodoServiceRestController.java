package de.hhn.it.gvs.cc.todo.fkps.rest;

import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.hhn.it.gvs.cc.basics.Token;
import de.hhn.it.gvs.cc.exceptions.IllegalParameterException;
import de.hhn.it.gvs.cc.exceptions.InvalidTokenException;
import de.hhn.it.gvs.cc.exceptions.ServiceNotAvailableException;
import de.hhn.it.gvs.cc.forum.bd.BDForumService;
import de.hhn.it.gvs.cc.todo.fkps.bd.BDTodoListsService;
import de.hhn.it.gvs.cc.todo.fkps.bd.TodoList;
import de.hhn.it.gvs.cc.todo.fkps.bd.TodoListItem;
import de.hhn.it.gvs.cc.todo.fkps.bd.TodoListsService;
import de.hhn.it.gvs.cc.todo.fkps.mock.TodoServicePopulator;

/**
 * 
 * @author Friedrich. RestController
 *
 */
@RestController
@RequestMapping("/todoservice")
public class TodoServiceRestController {

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(TodoServiceRestController.class);

	/**
	 * service object.
	 */
	BDTodoListsService service;

	/**
	 * constructor
	 */
	public TodoServiceRestController() {
		service = new TodoListsService();
		TodoServicePopulator.populate(service);
		logger.info("restController ready");
	}

	/**
	 * Creates a new todo list.
	 *
	 * @see BDTodoListsService#createTodoList
	 * 
	 *      http://localhost:8080/todoservice/todolists
	 * 
	 * 
	 */
	@RequestMapping(value = "todolists", method = RequestMethod.POST)
	public Token createTodoList(@RequestHeader("Token") String userTokenString, @RequestBody String todoTitle)
			throws IllegalParameterException, InvalidTokenException, ServiceNotAvailableException {
		logger.info("createTodoList post request");
		Token userToken = new Token(userTokenString);

		return service.createTodoList(userToken, todoTitle);
	}

	/**
	 * Creates a new todo item.
	 *
	 * @see BDTodoListsService#addTodoListItem
	 */
	@RequestMapping(value = "todolists/{id}/todoitems", method = RequestMethod.POST)
	public Token createTodoItem(@RequestHeader("Token") String userTokenString, @PathVariable String id,
			@RequestBody String todoItem)
			throws IllegalParameterException, InvalidTokenException, ServiceNotAvailableException {
		logger.info("createTodoItem post request");

		Token userToken = new Token(userTokenString);
		Token todoListToken = new Token(id);

		return service.addTodoListItem(userToken, todoListToken, todoItem);
	}

	/**
	 * Gets a list of TodoList objects.
	 *
	 * @see BDForumService#getAllTodoLists
	 */
	@RequestMapping(value = "todolists", method = RequestMethod.GET)
	@ResponseBody
	public HttpEntity<List<TodoList>> getTodos(@RequestHeader("Token") String userTokenString)
			throws IllegalParameterException, InvalidTokenException, ServiceNotAvailableException {
		logger.info("getTodos get request");
		Token userToken = new Token(userTokenString);
		List<TodoList> todos = service.getAllTodoLists(userToken);
		return new ResponseEntity<>(todos, HttpStatus.OK);
	}

	// /**
	// * Gets a list of TodoListItem objects.
	// *
	// * @see BDForumService#getAllItemsFromAList
	// */
	// @RequestMapping(value = "todolists/{id}/todoitems", method =
	// RequestMethod.GET)
	// @ResponseBody
	// public HttpEntity<List<TodoListItem>>
	// getTodolistItems(@RequestHeader("Token") String userTokenString,
	// @PathVariable String id)
	// throws IllegalParameterException, InvalidTokenException,
	// ServiceNotAvailableException {
	// logger.info("getTodolistItems get request");
	// Token userToken = new Token(userTokenString);
	// Token listToken = new Token(id);
	// List<TodoListItem> todoItems = service.getAllItemsFromAList(userToken,
	// listToken);
	// return new ResponseEntity<>(todoItems, HttpStatus.OK);
	// }

	/**
	 * Gets a single TodoListItem object.
	 *
	 * @see BDForumService#getAllItemsFromAList
	 */
	/**
	 * @param userTokenString
	 * @param id
	 * @return
	 * @throws IllegalParameterException
	 * @throws InvalidTokenException
	 * @throws ServiceNotAvailableException
	 */
	@RequestMapping(value = "todoitems/{id}", method = RequestMethod.GET)
	@ResponseBody
	public TodoListItem getTodolistItem(@RequestHeader("Token") String userTokenString, @PathVariable String id)
			throws IllegalParameterException, InvalidTokenException, ServiceNotAvailableException {
		logger.info("getTodolistItem get request");
		Token userToken = new Token(userTokenString);
		Token todoItemToken = new Token(id);
		return service.getItemFromTodoList(userToken, todoItemToken);
	}

	/**
	 * deletes a todo list object.
	 * 
	 * @see BDForumService#removeTodoList
	 */
	@RequestMapping(value = "todolists/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public HttpStatus deleteTodoList(@RequestHeader("Token") String userTokenString, @PathVariable String id)
			throws IllegalParameterException, InvalidTokenException, ServiceNotAvailableException {
		logger.info("deleteTodoList delete request");
		Token userToken = new Token(userTokenString);
		Token todoListToken = new Token(id);
		service.removeTodoList(userToken, todoListToken);
		return HttpStatus.OK;
	}

	/**
	 * deletes a todo list item object.
	 * 
	 * @see BDForumService#removeTodoListItem
	 */
	@RequestMapping(value = "todoitems/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public HttpStatus deleteTodoListItem(@RequestHeader("Token") String userTokenString, @PathVariable String id)
			throws IllegalParameterException, InvalidTokenException, ServiceNotAvailableException {
		logger.info("deleteTodoListItem delete request");
		Token userToken = new Token(userTokenString);
		Token todoItemToken = new Token(id);
		service.removeTodoListItem(userToken, todoItemToken);
		return HttpStatus.OK;
	}

	/**
	 * updates a todo list item object.
	 * 
	 * @see BDForumService#updateTodoListItem
	 */
	@RequestMapping(value = "todoitems/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public HttpStatus updateTodoListItem(@RequestHeader("Token") String userTokenString, @PathVariable String id,
			@RequestBody String todoItem)
			throws IllegalParameterException, InvalidTokenException, ServiceNotAvailableException {
		logger.info("updateTodoListItem put request");
		Token userToken = new Token(userTokenString);
		Token todoItemToken = new Token(id);
		service.updateTodoListItem(userToken, todoItem, todoItemToken);
		return HttpStatus.OK;
	}
}
