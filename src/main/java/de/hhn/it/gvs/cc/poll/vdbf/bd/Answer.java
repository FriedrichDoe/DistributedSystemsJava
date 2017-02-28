package de.hhn.it.gvs.cc.poll.vdbf.bd;

import de.hhn.it.gvs.cc.basics.Token;

/**
 * The Class Answer.
 */
public class Answer {

	/** The answer. */
	private String answer;
	
	/** The answer token. */
	private Token answerToken;
	
	/**
	 * Instantiates a new answer.
	 *
	 * @param answer the answer
	 */
	public Answer(String answer){
		this.answer = answer;
		this.answerToken = new Token(answer);
	}
	
	/**
 	 * getter for unique answer token.
 	 *
 	 * @return pollToken
 	 */
	  public Token getAnswerToken(){
	     return this.answerToken;
	      }
	  
	  public void setAnswerToken(Token answerToken){
		 this.answerToken = answerToken; 
	  }
	  
	  /**
  	 * Gets the answer.
  	 *
  	 * @return the answer
  	 */
  	public String getAnswer() {
		  return this.answer;
	  }
	  
	  /**
  	 * Sets the answer.
  	 *
  	 * @param answer the new answer
  	 */
  	public void setAnswer(String answer){
		  this.answer = answer;
	  }
}
