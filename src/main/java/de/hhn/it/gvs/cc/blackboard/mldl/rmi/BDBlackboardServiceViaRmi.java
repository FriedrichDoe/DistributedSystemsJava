package de.hhn.it.gvs.cc.blackboard.mldl.rmi;

import de.hhn.it.gvs.cc.basics.Token;
import de.hhn.it.gvs.cc.blackboard.mldl.bd.BDBlackboardService;
import de.hhn.it.gvs.cc.blackboard.mldl.bd.BlackBoard;
import de.hhn.it.gvs.cc.blackboard.mldl.bd.Post;
import de.hhn.it.gvs.cc.exceptions.InvalidTokenException;
import de.hhn.it.gvs.cc.exceptions.ServiceNotAvailableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Properties;

/**
 * Created by malohr on 28.11.16.
 */
public class BDBlackboardServiceViaRmi implements BDBlackboardService {

    private static final Logger logger = LoggerFactory.getLogger(BDBlackboardServiceViaRmi.class);

    private String host = "localhost";
    private int port = 1083;
    private RmiBlackBoardService service;

    private void connectRmi() throws ServiceNotAvailableException {

        logger.info("Accessing registry..");

        try {
            Registry registry = LocateRegistry.getRegistry(host, port);
            service = (RmiBlackBoardService) registry.lookup(RmiBlackBoardService.RMI_KEY);
        } catch (RemoteException | NotBoundException e) {
            String errorMessage = "Cannot connect to service on host:" + host + " with port: " + port;
            throw new ServiceNotAvailableException(errorMessage, e);
        }
    }

    public BDBlackboardServiceViaRmi(String myHost, int myPort) {
        this.host = myHost;
        this.port = myPort;
    }


    @Override
    public Token getBlackboardOwner(String blackboardName) throws ServiceNotAvailableException, InvalidTokenException {
        logger.info("Getting Blackboard Owner");
        connectRmi();
        try {
            logger.info("Getting the Blackboard Owner was successful");
            return service.getBlackboardOwner(blackboardName);
        } catch (RemoteException e) {
            logger.info("Getting the Blackboard Owner failed!");
            throw new ServiceNotAvailableException(e);
        }
    }

    @Override
    public Token getBlackboardToken(String blackboardName) throws ServiceNotAvailableException, InvalidTokenException {
        logger.info("Getting Blackboard Token");
        connectRmi();
        try {
            logger.info("Getting the Blackboard Token was successful");
            return service.getBlackboardToken(blackboardName);
        } catch (RemoteException e) {
            logger.info("Getting the Blackboard Token failed!");
            throw new ServiceNotAvailableException(e);
        }
    }

    @Override
    public Token getPostToken(int positionX, int positionY, String postOwner) throws ServiceNotAvailableException, InvalidTokenException {
        logger.info("Getting Post Token");
        connectRmi();
        try {
            logger.info("Getting the Post Token was successful");
            return service.getPostToken(positionX,positionY,postOwner);
        } catch (RemoteException e) {
            logger.info("Getting the Post Token failed!");
            throw new ServiceNotAvailableException(e);
        }
    }

    @Override
    public Token createBlackboard(Token userToken, String blackboardName, String blackboardDescription) throws ServiceNotAvailableException, InvalidTokenException {
        logger.info("creating a new Blackboard...");
        connectRmi();
        try {
            logger.info("Blackboard has been created!");
            return service.createBlackboard(userToken,blackboardName,blackboardDescription);
        } catch (RemoteException e) {
            logger.info("Blackboard has not been created");
            throw new ServiceNotAvailableException(e);
        }
    }

    @Override
    public void alterBlackboard(Token userToken, Token blackboardToken, String blackboardName, String blackboardDescription) throws ServiceNotAvailableException, InvalidTokenException {

        logger.info("update Blackboard info");
        connectRmi();
        try {
            logger.info("Updating Blackboard was successful");
            service.alterBlackboard(userToken,blackboardToken,blackboardName,blackboardDescription);
        } catch (RemoteException e) {
            logger.info("Updating Blackboard failed!");
            throw new ServiceNotAvailableException(e);
        }

    }

    @Override
    public Token postToBlackboard(Token userToken, Token blackboardToken, String postContent, int positionX, int positionY, String postOwner) throws ServiceNotAvailableException, InvalidTokenException {
        logger.info("Post to Blackboard");
        connectRmi();
        try {
            logger.info("Posting to Blackboard was successful");
            return service.postToBlackboard(userToken,blackboardToken,postContent,positionX,positionY,postOwner);
        } catch (RemoteException e) {
            logger.info("Posting to Blackboard failed!");
            throw new ServiceNotAvailableException(e);
        }
    }

    @Override
    public void updatePost(Token userToken, Token blackboardToken, Token postToken, int positionX, int positionY, String postContent) throws ServiceNotAvailableException, InvalidTokenException {

        logger.info("Updating Post...");
        connectRmi();
        try {
            logger.info("Updating Post was successful");
            service.updatePost(userToken,blackboardToken,postToken,positionX,positionY,postContent);
        } catch (RemoteException e) {
            logger.info("Updating Post failed!");
            throw new ServiceNotAvailableException(e);
        }
    }

    @Override
    public void deletePost(Token userToken, Token postToken) throws ServiceNotAvailableException, InvalidTokenException {

        logger.info("Deleting Post...");
        connectRmi();
        try {
            logger.info("Post has been deleted");
            service.deletePost(userToken,postToken);
        } catch (RemoteException e) {
            logger.info("Deleting Post failed!");
            throw new ServiceNotAvailableException(e);
        }
    }

    @Override
    public List<BlackBoard> getBlackBoards(Token userToken) throws ServiceNotAvailableException, InvalidTokenException {
        logger.info("getting Blackboard List");
        connectRmi();
        try {
            logger.info("getting the Blackboard List was successful");
            return service.getBlackBoards(userToken);
        } catch (RemoteException e) {
            logger.info("getting the Blackboard List failed!");
            throw new ServiceNotAvailableException(e);
        }
    }

    @Override
    public List<Post> getPosts(Token userToken, Token blackboardToken) throws ServiceNotAvailableException, InvalidTokenException {
        logger.info("getting Post List...");
        connectRmi();
        try {
            logger.info("getting the Post List was successful");
            return service.getPosts(userToken,blackboardToken);
        } catch (RemoteException e) {
            logger.info("getting the Post List failed!");
            throw new ServiceNotAvailableException(e);
        }
    }

    @Override
    public Properties getInfo() {
        return null;
    }
}
