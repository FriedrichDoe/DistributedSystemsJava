package de.hhn.it.gvs.cc.poll.vdbf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import de.hhn.it.gvs.cc.poll.vdbf.bd.Answer;
import de.hhn.it.gvs.cc.poll.vdbf.bd.Question;

/**
 * Tests for Answer
 * 
 * @version 2016-11-13
 *
 */

public class QuestionTest {

	/**
	 * TeamSession object for Testing
	 */
	static Question question;

	static List<Answer> answers;

	static List<Answer> answers1;

	/**
	 * Sets variables before running class
	 * 
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUp() throws Exception {
		question = new Question("The answer", answers);
	}

	/**
	 * "Resets" variables after class
	 * 
	 * @throws Exception
	 */
	@AfterClass
	public static void tearDown() throws Exception {
		question = null;
	}

	/**
	 * Test of name getter and setter
	 */
	@Test
	public void testName() {
		question.setQuestion("the question");
		assertEquals(question.getQuestion(), "the question");
		assertNotEquals(question.getQuestion(), "thequestion");
	}

	/**
	 * Test of name getter and setter
	 */
	@Test
	public void testSetAnswer() {
		question.setAnswer(answers);
		assertEquals(question.getAnswer(), answers);
		// assertNotEquals(question.getAnswer(), answers1);
	}
}
