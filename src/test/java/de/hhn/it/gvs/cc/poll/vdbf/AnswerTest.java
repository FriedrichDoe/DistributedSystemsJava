package de.hhn.it.gvs.cc.poll.vdbf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import de.hhn.it.gvs.cc.poll.vdbf.bd.Answer;

/**
 * Tests for Answer
 * 
 * @version 2016-11-13
 *
 */

public class AnswerTest {

	/**
	 * TeamSession object for Testing
	 */
	static Answer answer;

	/**
	 * Sets variables before running class
	 * 
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUp() throws Exception {
		answer = new Answer("The answer");
	}

	/**
	 * "Resets" variables after class
	 * 
	 * @throws Exception
	 */
	@AfterClass
	public static void tearDown() throws Exception {
		answer = null;
	}

	/**
	 * Test of name getter and setter
	 */
	@Test
	public void testName() {
		answer.setAnswer("the answer");
		assertEquals(answer.getAnswer(), "the answer");
		assertNotEquals(answer.getAnswer(), "theanswer");
	}

}
