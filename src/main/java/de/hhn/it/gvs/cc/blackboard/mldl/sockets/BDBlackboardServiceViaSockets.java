package de.hhn.it.gvs.cc.blackboard.mldl.sockets;

import de.hhn.it.gvs.cc.basics.Token;
import de.hhn.it.gvs.cc.basics.distribute.sockets.Request;
import de.hhn.it.gvs.cc.basics.distribute.sockets.Response;
import de.hhn.it.gvs.cc.blackboard.mldl.bd.BDBlackboardService;
import de.hhn.it.gvs.cc.blackboard.mldl.bd.BlackBoard;
import de.hhn.it.gvs.cc.blackboard.mldl.bd.Post;
import de.hhn.it.gvs.cc.exceptions.IllegalParameterException;
import de.hhn.it.gvs.cc.exceptions.InvalidTokenException;
import de.hhn.it.gvs.cc.exceptions.ServiceNotAvailableException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Properties;

/**
 * Created by malohr on 18.12.16.
 */
public class BDBlackboardServiceViaSockets implements BDBlackboardService {


    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(BDBlackboardServiceViaSockets.class);

    protected static final String CREATEBLACKBOARD = "blackboardservice.createnewblackboard";
    protected static final String USERTOKEN = "myusertoken";
    protected static final String TITLE = "mytitle";
    protected static final String GETBLACKBOARDS = "getblackboards";
    protected static final String DESCRIPTION = "mydesc";

    /**
     * hostname string
     */
    private String hostname;
    /**
     * port integer
     */
    private int port;
    /**
     * socket object
     */
    private Socket socket;
    /**
     * ObjectOutputStream object
     */
    private ObjectOutputStream out;
    /**
     * ObjectInputStream object
     */
    private ObjectInputStream in;

    /**
     * constructor
     *
     * @param myHostname
     * @param myPort
     */
    BDBlackboardServiceViaSockets(final String myHostname, final int myPort) {
        this.hostname = myHostname;
        this.port = myPort;
    }

    /**
     * socket connect method
     *
     * @throws ServiceNotAvailableException
     */
    private void connectService() throws ServiceNotAvailableException {
        try {
            socket = new Socket(hostname, port);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream((socket.getInputStream()));
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new ServiceNotAvailableException(
                    "Cannot connect to host " + hostname + " with port " + port + ".", ex);
        }
    }

    /**
     * socket disconnect method
     */
    private void disconnectService() {
        try {
            if (out != null) {
                out.close();
                out = null;
            }
            if (in != null) {
                in.close();
                in = null;
            }

            if (socket != null) {
                socket.close();
                socket = null;
            }
        } catch (IOException ex) {
            // Problems disconnecting should not terminate the interaction. So
            // we only log the problem.
            logger.warn("Problems disconnecting from service: " + ex.getMessage());
        }
    }

    private Response sendAndGetResponse(final Request request) throws ServiceNotAvailableException {
        // send request and wait for the response
        try {
            connectService();
            logger.debug("sending request " + request);
            out.writeObject(request);
            logger.debug("wait for response ...");
            Response response = (Response) in.readObject();
            logger.debug("Got response " + response);
            disconnectService();
            return response;
        } catch (Exception ex) {
            throw new ServiceNotAvailableException("Communication problem: " + ex.getMessage(), ex);
        }
    }

    private void rethrowException(final Response response)
            throws InvalidTokenException, IllegalParameterException, ServiceNotAvailableException {
        Exception remoteException = response.getExceptionObject();

        // check all acceptable exception types and rethrow
        if (remoteException instanceof InvalidTokenException) {
            throw (InvalidTokenException) remoteException;
        }

        if (remoteException instanceof IllegalParameterException) {
            throw (IllegalParameterException) remoteException;
        }

        if (remoteException instanceof ServiceNotAvailableException) {
            throw (ServiceNotAvailableException) remoteException;
        }

        remoteException.printStackTrace();
        throw new ServiceNotAvailableException("Unknown exception received in response object.", remoteException);
    }


    @Override
    public Token getBlackboardOwner(String blackboardName) throws ServiceNotAvailableException, InvalidTokenException {
        return null;
    }

    @Override
    public Token getBlackboardToken(String blackboardName) throws ServiceNotAvailableException, InvalidTokenException {
        return null;
    }

    @Override
    public Token getPostToken(int positionX, int positionY, String postOwner) throws ServiceNotAvailableException, InvalidTokenException {
        return null;
    }

    @Override
    public Token createBlackboard(Token userToken, String blackboardName, String blackboardDescription) throws ServiceNotAvailableException, InvalidTokenException, IllegalParameterException {
        // build request
        Request request = new Request(CREATEBLACKBOARD).addParameter(USERTOKEN, userToken).addParameter(TITLE, blackboardName).addParameter(DESCRIPTION, blackboardDescription);

        // Communicate with the service
        Response response = sendAndGetResponse(request);

        // handle the bad case
        if (response.isException()) {
            rethrowException(response);
        }

        return (Token) response.getReturnObject();
    }

    @Override
    public void alterBlackboard(Token userToken, Token blackboardToken, String blackboardName, String blackboardDescription) throws ServiceNotAvailableException, InvalidTokenException {

    }

    @Override
    public Token postToBlackboard(Token userToken, Token blackboardToken, String postContent, int positionX, int positionY, String postOwner) throws ServiceNotAvailableException, InvalidTokenException {
        return null;
    }

    @Override
    public void updatePost(Token userToken, Token blackboardToken, Token postToken, int positionX, int positionY, String postContent) throws ServiceNotAvailableException, InvalidTokenException {

    }

    @Override
    public void deletePost(Token userToken, Token postToken) throws ServiceNotAvailableException, InvalidTokenException {

    }

    @Override
    public List<BlackBoard> getBlackBoards(Token userToken) throws ServiceNotAvailableException, InvalidTokenException {
        return null;
    }

    @Override
    public List<Post> getPosts(Token userToken, Token blackboardToken) throws ServiceNotAvailableException, InvalidTokenException {
        return null;
    }

    @Override
    public Properties getInfo() {
        return null;
    }
}
