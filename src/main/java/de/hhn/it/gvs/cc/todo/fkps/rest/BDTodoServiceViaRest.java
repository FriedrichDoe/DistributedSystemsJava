package de.hhn.it.gvs.cc.todo.fkps.rest;

import java.util.List;
import java.util.Properties;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import de.hhn.it.gvs.cc.basics.Token;
import de.hhn.it.gvs.cc.exceptions.IllegalParameterException;
import de.hhn.it.gvs.cc.exceptions.InvalidTokenException;
import de.hhn.it.gvs.cc.exceptions.ServiceNotAvailableException;
import de.hhn.it.gvs.cc.todo.fkps.bd.BDTodoListsService;
import de.hhn.it.gvs.cc.todo.fkps.bd.TodoList;
import de.hhn.it.gvs.cc.todo.fkps.bd.TodoListItem;

public class BDTodoServiceViaRest implements BDTodoListsService {

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(BDTodoServiceViaRest.class);

	private String baseUrl;
	private final RestTemplate template;

	public BDTodoServiceViaRest(final String baseUrl) {
		this.baseUrl = baseUrl;
		template = new RestTemplate();
	}

	private HttpHeaders getHttpHeadersWithUserToken(final Token userToken) {
		// Transport the userToken string in the header
		HttpHeaders headers = new HttpHeaders();
		headers.add("Token", userToken.getToken());
		return headers;
	}

	/**
	 * Maps HTTP status codes to application-specific exceptions
	 *
	 * @param e
	 *            original rest call exception
	 * @throws InvalidTokenException
	 *             if rest client error exception has status code BAD_REQUEST
	 * @throws IllegalParameterException
	 *             if rest client error exception has status code NOT_FOUND
	 * @throws ServiceNotAvailableException
	 *             if rest client error exception has status code
	 *             SERVICE_UNAVAILABLE
	 */
	private void rethrowException(final HttpClientErrorException e)
			throws InvalidTokenException, IllegalParameterException, ServiceNotAvailableException {
		logger.warn("REST call failed with " + e.getStatusCode());
		HttpStatus statusCode = e.getStatusCode();
		String reason = e.getResponseBodyAsString();
		switch (statusCode) {
		case BAD_REQUEST:
			throw new InvalidTokenException("something went wrong. Reason: " + reason);
		case NOT_FOUND:
			throw new IllegalParameterException("Hm. One parameter is not ok. Reason: " + reason);
		case SERVICE_UNAVAILABLE:
			throw new ServiceNotAvailableException("Remote service not available. Reason: " + reason);
		default:
			throw new ServiceNotAvailableException(
					"Request failed with status code " + statusCode + ". Reason: " + reason);
		}
	}

	@Override
	public Properties getInfo() {
		return null;
	}

	@Override
	public Token createTodoList(Token userToken, String todoListName)
			throws ServiceNotAvailableException, InvalidTokenException, IllegalParameterException {
		final String uri = "todolists";
		// Create the HttpRequest
		HttpHeaders headers = getHttpHeadersWithUserToken(userToken);

		// put String parameter into body of request and add headers
		HttpEntity requestEntity = new HttpEntity<String>(todoListName, headers);

		try {
			Token token = template.postForObject(baseUrl + uri, requestEntity, Token.class);
			return token;
		} catch (HttpClientErrorException ex) {
			rethrowException(ex);
		}

		// unreachable because of rethrowException ...
		throw new ServiceNotAvailableException(">>>>>>>>> WTF: This should be unreachable code!");
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
		final String uri = "todolists/" + listToken.getToken() + "/todoitems/";
		logger.info("post item url: " + baseUrl + uri);
		// Create the HttpRequest
		HttpHeaders headers = getHttpHeadersWithUserToken(userToken);

		// put String parameter into body of request and add headers
		HttpEntity requestEntity = new HttpEntity<String>(todoListItemName, headers);

		try {
			Token token = template.postForObject(baseUrl + uri, requestEntity, Token.class);
			return token;
		} catch (HttpClientErrorException ex) {
			rethrowException(ex);
		}

		// unreachable because of rethrowException ...
		throw new ServiceNotAvailableException(">>>>>>>>> WTF: This should be unreachable code!");
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
		final String uri = "todolists";

		// Create the HttpRequest
		HttpHeaders headers = getHttpHeadersWithUserToken(userToken);
		HttpEntity requestEntity = new HttpEntity<Void>(null, headers);

		// Create an object describing the expected Type in the response
		ParameterizedTypeReference<List<TodoList>> responseType = new ParameterizedTypeReference<List<TodoList>>() {
		};

		try {
			ResponseEntity<List<TodoList>> responseEntity = template.exchange(baseUrl + uri, HttpMethod.GET,
					requestEntity, responseType);
			List<TodoList> todolists = responseEntity.getBody();
			return todolists;
		} catch (HttpClientErrorException ex) {
			rethrowException(ex);
		}

		// unreachable because of rethrowException ...
		throw new ServiceNotAvailableException(">>>>>>>>> WTF: This should beunreachable code!");
	}

	@Override
	public List<TodoListItem> getAllItemsFromAList(Token userToken, Token listToken)
			throws ServiceNotAvailableException, InvalidTokenException, IllegalParameterException {
		// TODO Auto-generated method stub
		return null;
	}

}
