package de.hhn.it.gvs.cc.poll.vdbf.bd;

import java.util.ArrayList;
import java.util.List;

import de.hhn.it.gvs.cc.basics.Token;
import de.hhn.it.gvs.cc.poll.vdbf.bd.Answer;

/**
 * The Class Question.
 */
public class Question {

	/** The question. */
	private String question;
	
	/** The question token. */
	private Token questionToken;
	
	/** The list of answers of a question. */
	List<Answer> answer = new ArrayList <Answer>();
	
	/**
	 * Instantiates a new question.
	 *
	 * @param question the question
	 */
	public Question(String question, List<Answer> answer){
		this.question = question;
		this.answer = answer;
		this.questionToken = new Token(question);
	}
	
	/**
 	 * getter for unique answer token.
 	 *
 	 * @return pollToken
 	 */
	  public Token getQuestionToken(){
	     return this.questionToken;
	      }
	  
	  public void setQuestionToken(Token questionToken) {
		  this.questionToken = questionToken;
	  }
	  
	  /**
  	 * Gets the question.
  	 *
  	 * @return the question
  	 */
  	public String getQuestion() {
		  return this.question;
	  }
	  
	  /**
  	 * Sets the question.
  	 *
  	 * @param question the new question
  	 */
  	public void setQuestion(String question){
		  this.question = question;
	  }
  	
  	/**
	 * Gets the answer.
	 *
	 * @return the answer
	 */
	public List<Answer> getAnswer() {
		return answer;
	}
	
	
	
	/**
	 * Sets the answer.
	 *
	 * @param Answer the new answer
	 */
	public void setAnswer(List<Answer> answer) {
		this.answer = answer;
	}
}
