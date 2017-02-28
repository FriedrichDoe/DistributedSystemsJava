package de.hhn.it.gvs.cc.poll.vdbf.bd;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import de.hhn.it.gvs.cc.poll.vdbf.bd.Question;
import de.hhn.it.gvs.cc.teamSession.mbhs.bd.TeamSessionService;
import de.hhn.it.gvs.cc.basics.Token;
import de.hhn.it.gvs.cc.exceptions.InvalidTokenException;
import de.hhn.it.gvs.cc.exceptions.ServiceNotAvailableException;

/**
 * The Class Poll.
 */
public class Poll implements Serializable{
	/** serialVersionUID to be updated when class changes. */
	private static final long serialVersionUID = -2540648242742441270L;
	
	/** The Constant NUM_ROUNDS. */
	public static final int NUM_ROUNDS = 5;

    /** The Constant NUM_QUESTIONS_PER_ROUND. */
    public static final int NUM_QUESTIONS_PER_ROUND = 3;

	/** The poll name. */
	private String pollName;
	
	/** The Question. */
	List<Question> question = new ArrayList <Question>();
	
	/** The poll. */
	private Poll poll;
	
	/** The poll token. */
	private Token pollToken;
	
	private List<Token> members = new ArrayList<Token>();
	

	/**
	 * Instantiates a new poll.
	 *
	 * @param pollName the poll name
	 * @param question the question
	 */
	public Poll(final String pollName, final List<Question> Pquestion) {
		question = Pquestion;
		this.pollToken = new Token(pollName);
		PollImp.getInstance().allPolls.add(this);
	}

	/**
	 * Gets the poll.
	 *
	 * @return the poll
	 */
	public final Poll getPoll() {
		return poll;
	}
	
	  /**
  	 * Gets the poll token.
  	 *
  	 * @return the poll token
  	 */
  	public final Token getPollToken(){
	     return this.pollToken;
	      }
  	
  	/**
	   * Sets the poll token.
	   *
	   * @param pollToken the new poll token
	   */
	  public final void setPollToken(final Token pollToken) {
  		this.pollToken = pollToken;
  	}
	
	/**
	 * Getter of teamsession members
	 * 
	 * @return List of Users in the teamsession
	 */
	    public final List<Token> getMembers() {
	        return members;
	    }
	    
  	/**
	   * Gets the poll name.
	   *
	   * @return the poll name
	   */
	  public final String getPollName(){
  		return this.pollName;
  	}
  	
  	/**
	   * Sets the poll name.
	   *
	   * @param pollName the new poll name
	   */
	  public final void setPollName(final String pollName){
  		this.pollName = pollName;
  	}
	/**
	 * Gets the question.
	 *
	 * @return the question
	 */
	public final List<Question> getQuestion() {
		return this.question;
	}
	
	
	/**
	 * Sets the question.
	 *
	 * @param Question the new question
	 */
	public final void setQuestion(final List<Question> Question) {
		this.question = Question;
	}
	
	public final void addMember(final Token userToken) throws ServiceNotAvailableException, InvalidTokenException {
	       if(!members.contains(userToken))
	           members.add(userToken);
	    }
	    
    public final void removeMember(final Token userToken) {
	        if(members.contains(userToken))
	            members.remove(userToken);
	    }
	
}
