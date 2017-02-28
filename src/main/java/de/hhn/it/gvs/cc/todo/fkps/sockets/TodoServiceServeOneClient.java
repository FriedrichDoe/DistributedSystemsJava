package de.hhn.it.gvs.cc.todo.fkps.sockets;

import java.io.IOException;
import java.net.Socket;

import de.hhn.it.gvs.cc.basics.BDCCService;
import de.hhn.it.gvs.cc.basics.Token;
import de.hhn.it.gvs.cc.basics.distribute.sockets.AbstractServeOneClient;
import de.hhn.it.gvs.cc.basics.distribute.sockets.Request;
import de.hhn.it.gvs.cc.basics.distribute.sockets.Response;
import de.hhn.it.gvs.cc.exceptions.IllegalParameterException;
import de.hhn.it.gvs.cc.todo.fkps.bd.BDTodoListsService;

/**
 * 
 * @author Friedrich
 *
 */
public class TodoServiceServeOneClient extends AbstractServeOneClient {

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(TodoServiceServeOneClient.class);

	/**
	 * service object
	 */
	BDTodoListsService todoService;

	/**
	 * constructor
	 * 
	 * @param socket
	 * @param service
	 * @throws IOException
	 * @throws IllegalParameterException
	 */
	public TodoServiceServeOneClient(Socket socket, BDCCService service) throws IOException, IllegalParameterException {
		super(socket, service);
		todoService = (BDTodoListsService) service;
	}

	@Override
	public void run() {
		super.run();

		try {
			Request request = (Request) in.readObject();
			Response response = null;
			String methodToCall = request.getMethodToCall();
			switch (methodToCall) {
			case BDTodoViaSockets.CREATETODO:
				response = createTodo(request);
				break;
			case BDTodoViaSockets.ADDTODOITEM:
				response = addTodoItem(request);
				break;

			default:
			}
			out.writeObject(response);
		} catch (IOException pE) {
			pE.printStackTrace();
		} catch (ClassNotFoundException pE) {
			pE.printStackTrace();
		}
	}

	private Response createTodo(Request pRequest) {
		logger.debug("createTodo method entry");
		try {
			Token token = todoService.createTodoList((Token) pRequest.getParameter(BDTodoViaSockets.USERTOKEN),
					(String) pRequest.getParameter(BDTodoViaSockets.TITLE));
			logger.info("createTodo done. token: " + token.getToken());
			return new Response(pRequest, token);
		} catch (Exception ex) {
			return new Response(pRequest, ex);
		}
	}

	private Response addTodoItem(Request pRequest) {
		logger.debug("addTodoItem method entry");

		try {
			Token token = todoService.addTodoListItem((Token) pRequest.getParameter(BDTodoViaSockets.USERTOKEN),
					(Token) pRequest.getParameter(BDTodoViaSockets.TODOLISTTOKEN),
					(String) pRequest.getParameter(BDTodoViaSockets.TODONAME));
			logger.info("addTodoItem done. token: " + token.getToken());
			return new Response(pRequest, token);
		} catch (Exception ex) {
			return new Response(pRequest, ex);
		}
	}

}
