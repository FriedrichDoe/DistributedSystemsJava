package de.hhn.it.gvs.cc.poll.vdbf.bd;

import de.hhn.it.gvs.cc.basics.Token;
import de.hhn.it.gvs.cc.exceptions.InvalidTokenException;
import de.hhn.it.gvs.cc.exceptions.ServiceNotAvailableException;

/**
 * Interface of the poll service
 * 
 * @version 2016-09-29
 */
public interface BDPollService {

	/**
	 * Check, if the user token valid is.
	 *
	 * @param userToken
	 *            the user token
	 * @throws ServiceNotAvailableException
	 *             the service not available exception
	 * @throws InvalidTokenException
	 *             the invalid token exception
	 */
	public boolean checkToken(Token userToken) throws ServiceNotAvailableException, InvalidTokenException;

	/**
	 * A user creates and owns a poll.
	 *
	 * @param userToken
	 *            the user token
	 * @param poll
	 *            the poll
	 * @throws ServiceNotAvailableException
	 *             the service not available exception
	 * @throws InvalidTokenException
	 *             the invalid token exception
	 */
	public void createPoll(Token userToken, String pollName) throws ServiceNotAvailableException, InvalidTokenException;

	/**
	 * The owner of the poll starts the poll.
	 *
	 * @param userToken
	 *            the user token
	 * @param pollToken
	 *            the poll token
	 * @throws ServiceNotAvailableException
	 *             the service not available exception
	 * @throws InvalidTokenException
	 *             the invalid token exception
	 */
	public void startPoll(Token userToken, Token pollToken) throws ServiceNotAvailableException, InvalidTokenException;

	/**
	 * Getter for the owner of the poll to check the status of the poll.
	 *
	 * @param userToken
	 *            the user token
	 * @param status
	 *            the status
	 * @return the check status
	 * @throws ServiceNotAvailableException
	 *             the service not available exception
	 * @throws InvalidTokenException
	 *             the invalid token exception
	 */
	public void checkStatus(Token userToken, String status) throws ServiceNotAvailableException, InvalidTokenException;

	/**
	 * The owner of the poll closes the poll.
	 *
	 * @param userToken
	 *            the user token
	 * @param poll
	 *            the poll
	 * @throws ServiceNotAvailableException
	 *             the service not available exception
	 * @throws InvalidTokenException
	 *             the invalid token exception
	 */
	public void closePoll(Token userToken, Token pollToken) throws ServiceNotAvailableException, InvalidTokenException;

	/**
	 * The owner of the poll publishes the results.
	 *
	 * @param userToken
	 *            the user token
	 * @param pollToken
	 *            the poll token
	 * @throws ServiceNotAvailableException
	 *             the service not available exception
	 * @throws InvalidTokenException
	 *             the invalid token exception
	 */
	public void resultPublish(Token userToken, Token pollToken)
			throws ServiceNotAvailableException, InvalidTokenException;

	/**
	 * Every user can participate to a poll.
	 *
	 * @param userToken
	 *            the user token
	 * @param pollToken
	 *            the poll name
	 * @throws ServiceNotAvailableException
	 *             the service not available exception
	 * @throws InvalidTokenException
	 *             the invalid token exception
	 */
	public void participatePoll(Token userToken, String pollName)
			throws ServiceNotAvailableException, InvalidTokenException;

	/**
	 * Return all the polls that we have already created.
	 *
	 * @param userToken
	 *            the user token
	 * @param pollToken
	 *            the poll token
	 * @return the poll
	 */
	public Poll getPoll(Token pollToken);
}