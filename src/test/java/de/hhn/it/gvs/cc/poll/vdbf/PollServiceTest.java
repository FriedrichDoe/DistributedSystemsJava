package de.hhn.it.gvs.cc.poll.vdbf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.security.InvalidParameterException;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import de.hhn.it.gvs.cc.basics.Token;
import de.hhn.it.gvs.cc.exceptions.InvalidTokenException;
import de.hhn.it.gvs.cc.exceptions.ServiceNotAvailableException;
import de.hhn.it.gvs.cc.poll.vdbf.bd.Poll;
import de.hhn.it.gvs.cc.poll.vdbf.bd.PollImp;
import de.hhn.it.gvs.cc.poll.vdbf.bd.Question;
import de.hhn.it.gvs.cc.usermanagement.crvr.bd.User;

/**
 * Tests for Answer
 * 
 * @version 2016-11-13
 *
 */

public class PollServiceTest {

	static PollImp pollImp;
	static Poll poll;
	static User user;
	static List<Question> questions;

	@BeforeClass
	public static void setUp() throws Exception {
		pollImp = PollImp.getInstance();
		poll = new Poll("poll", questions);
		user = new User("john.doe@email.de", "passwordxyz", "john");
	}

	@AfterClass
	public static void tearDown() throws Exception {
		pollImp.allPolls.clear();
		pollImp = null;
		poll = null;
		user = null;
	}

	@Test(expected = InvalidParameterException.class)
	public void checkTokenTest() throws ServiceNotAvailableException, InvalidTokenException {
		Token userToken = new Token("12345");
		assertTrue(pollImp.checkToken(userToken));
		Token userToken1 = null;
		assertTrue(pollImp.checkToken(user.getUserToken()));
		assertFalse(pollImp.checkToken(userToken1));
	}

	@Test
	public void createPollTest() throws ServiceNotAvailableException, InvalidTokenException {
		assertEquals(pollImp.listAllPolls(user.getUserToken()).size(), 2);
		pollImp.createPoll(user.getUserToken(), "poll");
		assertEquals(pollImp.listAllPolls(user.getUserToken()).size(), 3);
	}

	@Test
	public void startPollTest() throws ServiceNotAvailableException, InvalidTokenException {
		pollImp.startPoll(user.getUserToken(), poll.getPollToken());
		assertFalse(poll.getMembers().isEmpty());
		assertEquals(poll.getMembers().get(0), user.getUserToken());
	}

	@Test
	public void checkStatusTest() {

	}

	@Test
	public void closePollTest() throws ServiceNotAvailableException, InvalidTokenException {
		pollImp.startPoll(user.getUserToken(), poll.getPollToken());
		pollImp.closePoll(user.getUserToken(), poll.getPollToken());
		assertTrue(poll.getMembers().isEmpty());
	}

	@Test
	public void publishResultTest() {

	}

	@Test
	public void participatePollTest() {

	}

	@Test
	public void getPollTest() throws ServiceNotAvailableException, InvalidTokenException {
		assertEquals(pollImp.getPoll(poll.getPollToken()), poll);
	}

	/**
	 * Test for list all Session of a user
	 */
	@Test
	public void ListAllPollsTest() throws Exception {
		assertEquals(1, pollImp.listAllPolls(user.getUserToken()).size());
		pollImp.createPoll(user.getUserToken(), "thePoll");
		assertEquals(poll, pollImp.listAllPolls(user.getUserToken()).get(0));
		assertEquals(2, pollImp.listAllPolls(user.getUserToken()).size());
		assertEquals("thePoll", pollImp.listAllPolls(user.getUserToken()).get(1).getPollName());
	}
}
