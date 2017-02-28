package de.hhn.it.gvs.cc.poll.vdbf.bd;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.hhn.it.gvs.cc.basics.Token;
import de.hhn.it.gvs.cc.exceptions.ServiceNotAvailableException;
import de.hhn.it.gvs.cc.exceptions.InvalidTokenException;
import de.hhn.it.gvs.cc.poll.vdbf.bd.Poll;
import de.hhn.it.gvs.cc.usermanagement.crvr.bd.User;
import de.hhn.it.gvs.cc.poll.vdbf.bd.Question;

/**
 * The Class PollImp.
 */
public class PollImp implements BDPollService{
	
	/** Standard class logger. **/
	private static Logger pollLog = Logger.getLogger(PollImp.class.getName());
	
	/** The userlist. */
	private HashMap<Token, User> userlist = new HashMap<Token, User>();
	
	private HashMap<Token, Answer> answerlist = new HashMap<Token, Answer>();
	
	public List<Poll> allPolls = new ArrayList<Poll>();
	
	private static PollImp thePollService = null;
    
    /** The questions. */
    private List<Question> questions;
    
    /** The ended. */
    private boolean ended;
    
    /**
     * Instantiates a new poll imp.
     */
    public PollImp() {
	    ended = false;
    }
    
    /**
     * Returns instance of the only poll service
     * 
     * @return the poll service
     */
    public static PollImp getInstance() {
        if(thePollService == null) {
            thePollService = new PollImp();
        }
        return thePollService;
     }
    
    /* (non-Javadoc)
     * @see de.hhn.it.gvs.vdbf.basics.BDPollService#checkToken(de.hhn.it.gvs.cc.basics.Token)
     */
    @Override
	public boolean checkToken(final Token userToken) throws ServiceNotAvailableException, InvalidTokenException {
    	if (userToken == null) {
			throw new InvalidParameterException("Token is null reference");
		}

		if (userToken.getToken() == null) {
			throw new InvalidParameterException("Primary Key in Token is  null reference");
		}

		if(!userlist.containsKey(userToken)) {
			return true;
		} else {
			return true;
		}

	}

	/* (non-Javadoc)
	 * @see de.hhn.it.gvs.vdbf.basics.BDPollService#createPoll(de.hhn.it.gvs.cc.basics.Token, de.hhn.it.gvs.vdbf.basics.Poll)
	 */
	@Override
	public void createPoll(final Token userToken, String pollName) throws ServiceNotAvailableException, InvalidTokenException {
		    	   checkToken(userToken);
		    	   Poll poll = new Poll(pollName, questions);
		    	   poll.addMember(userToken);
		    	   poll.setPollName(pollName);
		    	   pollLog.log(Level.INFO, "The poll " + pollName + " has been created");
		 		
		}

	/* (non-Javadoc)
	 * @see de.hhn.it.gvs.vdbf.basics.BDPollService#startPoll(de.hhn.it.gvs.cc.basics.Token, de.hhn.it.gvs.cc.basics.Token)
	 */
	@Override
	public void startPoll(final Token userToken, final Token pollToken) throws ServiceNotAvailableException, InvalidTokenException {
		  for(Poll p: allPolls) {
	            if(p.getPollToken().equals(pollToken)) {
	            	if(p.getMembers().isEmpty()){
	            		this.ended = true;
	            	}
	            	checkToken(userToken);
	            	p.addMember(userToken);
	            	pollLog.log(Level.INFO, "The users " + userToken + " have been added");
	            }
	        }
		
	}

	/* (non-Javadoc)
	 * @see de.hhn.it.gvs.vdbf.basics.BDPollService#checkStatus(de.hhn.it.gvs.cc.basics.Token, boolean)
	 */
	@Override
	public void checkStatus(final Token userToken, final String status)
			throws ServiceNotAvailableException, InvalidTokenException {
		    	   checkToken(userToken);
		    	   if (status == "Poll in progress") {
		    		pollLog.log(Level.INFO, "The poll has been paused");   
		    	   } else if (status == "Not started") {
		    		pollLog.log(Level.INFO, "The poll has not been started yet");   
		    	   } else if (status == "Started") {
		    		pollLog.log(Level.INFO, "The poll has been started");   
		    	   } else if (status == "Closed") {
		    		pollLog.log(Level.INFO, "The poll has been closed");
		    	   }
		       
	}

	/* (non-Javadoc)
	 * @see de.hhn.it.gvs.vdbf.basics.BDPollService#closePoll(de.hhn.it.gvs.cc.basics.Token, de.hhn.it.gvs.vdbf.basics.Poll)
	 */
	@Override
	public void closePoll(final Token userToken, final Token pollToken) throws ServiceNotAvailableException, InvalidTokenException {
		for(Poll p: allPolls) {
            if(p.getPollToken().equals(pollToken)) {
            	checkToken(userToken);
                p.removeMember(userToken);
                if(p.getMembers().isEmpty()){
                	this.ended = true;
                }
            }
        }
		
	}

	/* (non-Javadoc)
	 * @see de.hhn.it.gvs.vdbf.basics.BDPollService#resultPublish(de.hhn.it.gvs.cc.basics.Token, de.hhn.it.gvs.cc.basics.Token)
	 */
	@Override
	public void resultPublish(final Token userToken, final Token pollToken)
			throws ServiceNotAvailableException, InvalidTokenException {
		    	   checkToken(userToken);
		    	   getAllCurrentParticipants(pollToken);
		    	   getAllRespondedAnswers(pollToken);
	}

	/**
	 * Gets the all responded answers.
	 * @param pollToken 
	 * @return 
	 *
	 * @return the all responded answers
	 */
	private HashMap<Token, User> getAllRespondedAnswers(final Token pollToken) {
		return userlist;
	}

	/**
	 * Gets the all current participants.
	 * @param pollToken 
	 * @return 
	 *
	 * @return the all current participants
	 */
	private HashMap<Token, Answer> getAllCurrentParticipants(final Token pollToken) {
		return answerlist;
	}

	/* (non-Javadoc)
	 * @see de.hhn.it.gvs.vdbf.basics.BDPollService#participatePoll(de.hhn.it.gvs.cc.basics.Token, java.lang.String)
	 */
	@Override
	public void participatePoll(final Token userToken, final String pollName)
			throws ServiceNotAvailableException, InvalidTokenException {
		    	   checkToken(userToken);
	}

	/* (non-Javadoc)
	 * @see de.hhn.it.gvs.vdbf.basics.BDPollService#getPoll(de.hhn.it.gvs.cc.basics.Token, de.hhn.it.gvs.cc.basics.Token)
	 */
	@Override
	public Poll getPoll(final Token pollToken) {
        for (Poll p: allPolls) {
            if (p.getPollToken().equals(pollToken)) {
                return p;
            }
        }
        return null;
	}
	
	public List<Poll> listAllPolls(final Token userToken)
	            throws ServiceNotAvailableException, InvalidTokenException,
	            IllegalArgumentException {
	        return allPolls;
	    }


}
