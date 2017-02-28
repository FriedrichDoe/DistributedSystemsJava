package de.hhn.it.gvs.cc.blackboard.mldl.sockets;

import de.hhn.it.gvs.cc.basics.BDCCService;
import de.hhn.it.gvs.cc.basics.Token;
import de.hhn.it.gvs.cc.basics.distribute.sockets.AbstractServeOneClient;
import de.hhn.it.gvs.cc.basics.distribute.sockets.Request;
import de.hhn.it.gvs.cc.basics.distribute.sockets.Response;
import de.hhn.it.gvs.cc.blackboard.mldl.bd.BDBlackboardService;
import de.hhn.it.gvs.cc.blackboard.mldl.bd.BlackBoard;
import de.hhn.it.gvs.cc.exceptions.IllegalParameterException;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

/**
 * Created by malohr on 18.12.16.
 */
public class BlackBoardServiceServeOneClient extends AbstractServeOneClient {

    BDBlackboardService blackboardService;
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(BlackBoardServiceServeOneClient.class);
    /**
     * constructor
     *
     * @param socket
     * @param service
     * @throws IOException
     * @throws IllegalParameterException
     */
    public BlackBoardServiceServeOneClient(Socket socket, BDCCService service) throws IOException, IllegalParameterException {
        super(socket, service);
        blackboardService = (BDBlackboardService) service;
    }


    @Override
    public void run() {
        super.run();

        try {
            Request request = (Request) in.readObject();
            Response response = null;
            String methodToCall = request.getMethodToCall();
            switch (methodToCall) {
                case BDBlackboardServiceViaSockets.CREATEBLACKBOARD:
                    response = createTodo(request);
                    break;
                case BDBlackboardServiceViaSockets.GETBLACKBOARDS:
                    response = getTodos(request);
                    break;

                default:
            }
            out.writeObject(response);
        } catch (IOException pE) {
            pE.printStackTrace();
        } catch (ClassNotFoundException pE) {
            pE.printStackTrace();
        }
    }

    private Response createTodo(Request pRequest) {
        logger.debug("createTodo method entry");
        try {
            Token token = blackboardService.createBlackboard((Token) pRequest.getParameter(BDBlackboardServiceViaSockets.USERTOKEN),
                    (String) pRequest.getParameter(BDBlackboardServiceViaSockets.TITLE), (String) pRequest.getParameter(BDBlackboardServiceViaSockets.DESCRIPTION));
            return new Response(pRequest, token);
        } catch (Exception ex) {
            return new Response(pRequest, ex);
        }
    }

    private Response getTodos(Request pRequest) {
        logger.debug("getTodos method entry");
        try {
            List<BlackBoard> list = blackboardService
                    .getBlackBoards((Token) pRequest.getParameter(BDBlackboardServiceViaSockets.USERTOKEN));
            return new Response(pRequest, list);
        } catch (Exception ex) {
            return new Response(pRequest, ex);
        }
    }

}
