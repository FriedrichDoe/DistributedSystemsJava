package de.hhn.it.gvs.cc.blackboard.mldl.rmi;

import de.hhn.it.gvs.cc.basics.Token;
import de.hhn.it.gvs.cc.blackboard.mldl.bd.BlackBoard;
import de.hhn.it.gvs.cc.blackboard.mldl.bd.BlackBoardService;
import de.hhn.it.gvs.cc.blackboard.mldl.bd.Post;
import de.hhn.it.gvs.cc.exceptions.InvalidTokenException;
import de.hhn.it.gvs.cc.exceptions.ServiceNotAvailableException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by malohr on 28.11.16.
 */
public class RmiBlackBoardServiceImpl implements RmiBlackBoardService{

    BlackBoardService service;

    public RmiBlackBoardServiceImpl(final BlackBoardService blackboardService) {
        this.service = blackboardService;
    }

    @Override
    public Token getBlackboardOwner(String blackboardName) throws ServiceNotAvailableException, InvalidTokenException, RemoteException {
        return service.getBlackboardOwner(blackboardName);
    }

    @Override
    public Token getBlackboardToken(String blackboardName) throws ServiceNotAvailableException, InvalidTokenException, RemoteException {
        return service.getBlackboardToken(blackboardName);
    }

    @Override
    public Token getPostToken(int positionX, int positionY, String postOwner) throws ServiceNotAvailableException, InvalidTokenException, RemoteException {
        return service.getPostToken(positionX,positionY,postOwner);
    }

    @Override
    public Token createBlackboard(Token userToken, String blackboardName, String blackboardDescription) throws ServiceNotAvailableException, InvalidTokenException, RemoteException {
        return service.createBlackboard(userToken,blackboardName,blackboardDescription);
    }

    @Override
    public void alterBlackboard(Token userToken, Token blackboardToken, String blackboardName, String blackboardDescription) throws ServiceNotAvailableException, InvalidTokenException, RemoteException {
         service.alterBlackboard(userToken,blackboardToken,blackboardName,blackboardDescription);
    }

    @Override
    public Token postToBlackboard(Token userToken, Token blackboardToken, String postContent, int positionX, int positionY, String postOwner) throws ServiceNotAvailableException, InvalidTokenException, RemoteException {
        return service.postToBlackboard(userToken,blackboardToken,postContent,positionX,positionY,postOwner);
    }

    @Override
    public void updatePost(Token userToken, Token blackboardToken, Token postToken, int positionX, int positionY, String postContent) throws ServiceNotAvailableException, InvalidTokenException, RemoteException {

        service.updatePost(userToken,blackboardToken,postToken,positionX,positionY,postContent);
    }

    @Override
    public void deletePost(Token userToken, Token postToken) throws ServiceNotAvailableException, InvalidTokenException, RemoteException {

        service.deletePost(userToken,postToken);
    }

    @Override
    public List<BlackBoard> getBlackBoards(Token userToken) throws ServiceNotAvailableException, InvalidTokenException, RemoteException {
        return service.getBlackBoards(userToken);
    }

    @Override
    public List<Post> getPosts(Token userToken, Token blackboardToken) throws ServiceNotAvailableException, InvalidTokenException, RemoteException {
        return service.getPosts(userToken,blackboardToken);
    }
}
