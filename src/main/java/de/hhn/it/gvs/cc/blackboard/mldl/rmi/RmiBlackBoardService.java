package de.hhn.it.gvs.cc.blackboard.mldl.rmi;

import de.hhn.it.gvs.cc.basics.Token;
import de.hhn.it.gvs.cc.blackboard.mldl.bd.BlackBoard;
import de.hhn.it.gvs.cc.blackboard.mldl.bd.Post;
import de.hhn.it.gvs.cc.exceptions.InvalidTokenException;
import de.hhn.it.gvs.cc.exceptions.ServiceNotAvailableException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


public interface RmiBlackBoardService extends Remote {

    String RMI_KEY = "BlackboardRmi";

    /**
     * Gets the Blackboard owner.
     *
     * @param blackboardName - defines the blackboard name
     * @return returns the blackboard owner
     * @throws ServiceNotAvailableException the service not available exception
     * @throws InvalidTokenException the invalid token exception
     */
    public Token getBlackboardOwner (String blackboardName) throws ServiceNotAvailableException, InvalidTokenException, RemoteException;


    /**
     * Gets the blackboard Token.
     *
     * @param blackboardName
     * @return blackboardToken
     * @throws ServiceNotAvailableException the service not available exception
     * @throws InvalidTokenException the invalid token exception
     */
    public Token getBlackboardToken(String blackboardName) throws ServiceNotAvailableException, InvalidTokenException, RemoteException;


    /**
     * In order to receive a specific token for a post the position has to be provided and the owner
     *
     * @param positionX position of the post (x)
     * @param positionY position of the post (y)
     * @return postToken
     * @throws ServiceNotAvailableException the service not available exception
     * @throws InvalidTokenException the invalid token exception
     */
    public Token getPostToken(int positionX, int positionY, String postOwner) throws ServiceNotAvailableException, InvalidTokenException, RemoteException;

    /**
     * A user creates and owns a blackboard.
     *
     * @param userToken
     * @param blackboardName
     * @param blackboardDescription
     * @return postToken
     * @throws ServiceNotAvailableException the service not available exception
     * @throws InvalidTokenException the invalid token exception
     */
    public Token createBlackboard(Token userToken, String blackboardName, String blackboardDescription) throws ServiceNotAvailableException, InvalidTokenException, RemoteException;

    /**
     * The owner of a blackboard can alter the blackboard details.
     *
     * @param userToken
     * @param blackboardToken
     * @param blackboardName
     * @param blackboardDescription
     * @throws ServiceNotAvailableException the service not available exception
     * @throws InvalidTokenException the invalid token exception
     */
    public void alterBlackboard(Token userToken, Token blackboardToken, String blackboardName, String blackboardDescription) throws ServiceNotAvailableException, InvalidTokenException, RemoteException;

    /**
     * Each user can post to a blackboard. The user becomes the post owner.
     *
     * @param userToken
     * @param blackboardToken
     * @param postContent
     * @param positionX
     * @param positionY
     * @param postOwner
     * @throws ServiceNotAvailableException the service not available exception
     * @throws InvalidTokenException the invalid token exception
     */
    public Token postToBlackboard(Token userToken, Token blackboardToken, String postContent, int positionX, int positionY, String postOwner) throws ServiceNotAvailableException, InvalidTokenException, RemoteException;

    /**
     * A post owner can update the post
     *
     * @param userToken
     * @param postToken
     * @param blackboardToken
     * @param positionX
     * @param positionY
     * @param postContent
     * @throws ServiceNotAvailableException the service not available exception
     * @throws InvalidTokenException the invalid token exception
     */
    public void updatePost(Token userToken, Token blackboardToken, Token postToken, int positionX, int positionY, String postContent) throws ServiceNotAvailableException, InvalidTokenException, RemoteException;

    /**
     * Deletes the post
     *
     * @param userToken the user token
     * @param postToken
     * @throws ServiceNotAvailableException the service not available exception
     * @throws InvalidTokenException the invalid token exception
     */
    public void deletePost(Token userToken, Token postToken) throws ServiceNotAvailableException, InvalidTokenException, RemoteException;


    /**
     * gets all blackboards.
     *
     * @param userToken
     * @throws ServiceNotAvailableException
     *             the service not available exception
     * @throws InvalidTokenException
     *             the invalid token exception
     */
    public List<BlackBoard> getBlackBoards(Token userToken)
            throws ServiceNotAvailableException, InvalidTokenException, RemoteException;

    /**
     * gets all posts.
     *
     * @param userToken
     * @throws ServiceNotAvailableException
     *             the service not available exception
     * @throws InvalidTokenException
     *             the invalid token exception
     */
    public List<Post> getPosts(Token userToken, Token blackboardToken)
            throws ServiceNotAvailableException, InvalidTokenException, RemoteException;
}
