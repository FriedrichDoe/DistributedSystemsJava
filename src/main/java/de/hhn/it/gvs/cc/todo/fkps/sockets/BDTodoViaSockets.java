package de.hhn.it.gvs.cc.todo.fkps.sockets;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Properties;

import de.hhn.it.gvs.cc.basics.Token;
import de.hhn.it.gvs.cc.basics.distribute.sockets.Request;
import de.hhn.it.gvs.cc.basics.distribute.sockets.Response;
import de.hhn.it.gvs.cc.exceptions.IllegalParameterException;
import de.hhn.it.gvs.cc.exceptions.InvalidTokenException;
import de.hhn.it.gvs.cc.exceptions.ServiceNotAvailableException;
import de.hhn.it.gvs.cc.todo.fkps.bd.BDTodoListsService;
import de.hhn.it.gvs.cc.todo.fkps.bd.TodoList;
import de.hhn.it.gvs.cc.todo.fkps.bd.TodoListItem;

public class BDTodoViaSockets implements BDTodoListsService {

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(BDTodoViaSockets.class);

	protected static final String CREATETODO = "todoservice.createtodo";
	protected static final String ADDTODOITEM = "todoservice.addtodoitem";
	protected static final String USERTOKEN = "usertoken";
	protected static final String TITLE = "title";
	protected static final String GETTODOS = "gettodos";
	protected static final String TODOLISTTOKEN = "todolisttoken";
	protected static final String TODONAME = "todolistname";

	/**
	 * hostname string
	 */
	private String hostname;
	/**
	 * port integer
	 */
	private int portNumber;
	/**
	 * socket object
	 */
	private Socket socket;
	/**
	 * ObjectOutputStream object
	 */
	private ObjectOutputStream out;
	/**
	 * ObjectInputStream object
	 */
	private ObjectInputStream in;

	/**
	 * constructor
	 * 
	 * @param oHostname
	 * @param oPort
	 */
	BDTodoViaSockets(final String oHostname, final int oPort) {
		this.hostname = oHostname;
		this.portNumber = oPort;
	}

	/**
	 * socket connect method
	 * 
	 * @throws ServiceNotAvailableException
	 */
	private void connectToService() throws ServiceNotAvailableException {
		try {
			socket = new Socket(hostname, portNumber);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream((socket.getInputStream()));
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new ServiceNotAvailableException(
					"Cannot connect to host " + hostname + " with port " + portNumber + ".", ex);
		}
	}

	/**
	 * socket disconnect method
	 */
	private void disconnectFromService() {
		try {
			if (out != null) {
				out.close();
				out = null;
			}
			if (in != null) {
				in.close();
				in = null;
			}

			if (socket != null) {
				socket.close();
				socket = null;
			}
		} catch (IOException ex) {
			// Problems disconnecting should not terminate the interaction. So
			// we only log the problem.
			logger.warn("Problems disconnecting from service: " + ex.getMessage());
		}
	}

	private Response sendAndGetResponse(final Request request) throws ServiceNotAvailableException {
		// send request and wait for the response
		try {
			connectToService();
			logger.debug("sending request " + request);
			out.writeObject(request);
			logger.debug("wait for response ...");
			Response response = (Response) in.readObject();
			logger.debug("Got response " + response);
			disconnectFromService();
			return response;
		} catch (Exception ex) {
			throw new ServiceNotAvailableException("Communication problem: " + ex.getMessage(), ex);
		}
	}

	private void rethrowException(final Response response)
			throws InvalidTokenException, IllegalParameterException, ServiceNotAvailableException {
		Exception exceptionFromRemote = response.getExceptionObject();

		logger.info("rethrowException call");

		// check all acceptable exception types and rethrow
		if (exceptionFromRemote instanceof InvalidTokenException) {
			throw (InvalidTokenException) exceptionFromRemote;
		}

		if (exceptionFromRemote instanceof IllegalParameterException) {
			throw (IllegalParameterException) exceptionFromRemote;
		}

		if (exceptionFromRemote instanceof ServiceNotAvailableException) {
			throw (ServiceNotAvailableException) exceptionFromRemote;
		}

		// if we reach this part, than the exception is an exception we do not
		// expect!
		logger.error("WTF - Unknown exception object in response: " + exceptionFromRemote);
		exceptionFromRemote.printStackTrace();
		throw new ServiceNotAvailableException("Unknown exception received in response object.", exceptionFromRemote);
	}

	@Override
	public Token createTodoList(Token userToken, String todoListName)
			throws ServiceNotAvailableException, InvalidTokenException, IllegalParameterException {
		// build request
		Request request = new Request(CREATETODO).addParameter(USERTOKEN, userToken).addParameter(TITLE, todoListName);

		// Communicate with the service
		Response response = sendAndGetResponse(request);

		// handle the bad case
		if (response.isException()) {
			rethrowException(response);
		}

		return (Token) response.getReturnObject();
	}

	@Override
	public TodoListItem getItemFromTodoList(Token userToken, Token itemToken)
			throws ServiceNotAvailableException, InvalidTokenException, IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Token addTodoListItem(Token userToken, Token listToken, String todoListItemName)
			throws ServiceNotAvailableException, IllegalArgumentException, InvalidTokenException,
			IllegalParameterException {
		// build request
		Request request = new Request(ADDTODOITEM).addParameter(USERTOKEN, userToken)
				.addParameter(TODOLISTTOKEN, listToken).addParameter(TODONAME, todoListItemName);

		// Communicate with the service
		Response response = sendAndGetResponse(request);

		// handle the bad case
		if (response.isException()) {
			rethrowException(response);
		}

		return (Token) response.getReturnObject();
	}

	@Override
	public void updateTodoListItem(Token userToken, String newTodoListItemName, Token itemToken)
			throws ServiceNotAvailableException, InvalidTokenException, IllegalParameterException {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeTodoListItem(Token userToken, Token itemToken)
			throws ServiceNotAvailableException, InvalidTokenException, IllegalParameterException {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeTodoList(Token userToken, Token listToken)
			throws ServiceNotAvailableException, InvalidTokenException, IllegalParameterException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<TodoList> getAllTodoLists(Token userToken)
			throws ServiceNotAvailableException, InvalidTokenException, IllegalParameterException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TodoListItem> getAllItemsFromAList(Token userToken, Token listToken)
			throws ServiceNotAvailableException, InvalidTokenException, IllegalParameterException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Properties getInfo() {
		// TODO Auto-generated method stub
		return null;
	}

}
