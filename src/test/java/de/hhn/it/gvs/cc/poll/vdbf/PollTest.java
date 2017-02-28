package de.hhn.it.gvs.cc.poll.vdbf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import de.hhn.it.gvs.cc.poll.vdbf.bd.Poll;
import de.hhn.it.gvs.cc.poll.vdbf.bd.Question;
import de.hhn.it.gvs.cc.usermanagement.crvr.bd.User;

/**
 * Tests for Answer
 * 
 * @version 2016-11-13
 *
 */

public class PollTest {

	/**
	 * TeamSession object for Testing
	 */
	static Poll poll;

	/**
	 * User object for Testing
	 */
	static User user;

	static List<Question> questions;

	static List<Question> questions1;

	/**
	 * Sets variables before running class
	 * 
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUp() throws Exception {
		poll = new Poll("poll", questions);
		user = new User("test@email.de", "password", "user");
	}

	/**
	 * "Resets" variables after class
	 * 
	 * @throws Exception
	 */
	@AfterClass
	public static void tearDown() throws Exception {
		poll = null;
	}

	/**
	 * Test of name getter and setter
	 */
	@Test
	public void testName() {
		poll.setPollName("poll");
		assertEquals(poll.getPollName(), "poll");
		assertNotEquals(poll.getPollName(), "thePoll");
	}

	/**
	 * Test of members methods (getMembers(), addMember(), removeMember())
	 * 
	 * @throws Exception
	 */
	@Test
	public void testMembers() throws Exception {
		assertTrue(poll.getMembers().isEmpty());
		poll.addMember(user.getUserToken());
		assertEquals(poll.getMembers().size(), 1);
		assertEquals(poll.getMembers().get(0), user.getUserToken());
		poll.removeMember(user.getUserToken());
		assertTrue(poll.getMembers().isEmpty());
	}

	/**
	 * Test of name getter and setter
	 */
	@Test
	public void testSetQuestion() {
		poll.setQuestion(questions);
		assertEquals(poll.getQuestion(), questions);
		// assertNotEquals(poll.getQuestion(), questions1);
	}
}
