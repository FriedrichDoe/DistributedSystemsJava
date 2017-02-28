package de.hhn.it.gvs.cc.blackboard.mldl.bd;

import de.hhn.it.gvs.cc.basics.ObjectTokenFactory;
import de.hhn.it.gvs.cc.basics.Token;
import de.hhn.it.gvs.cc.exceptions.InvalidTokenException;
import de.hhn.it.gvs.cc.exceptions.ServiceNotAvailableException;


import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;


public class BlackBoardService implements BDBlackboardService {

    private static final Logger logger = Logger.getLogger(BlackBoardService.class.toString());

    private Token userToken;
    private ObjectTokenFactory tokenFactory;
    private List<BlackBoard> blackBoards;
    private List<Post> allPosts;
    private UserManagementBDFacadeMock userManagement;

    public BlackBoardService(){
        logger.info("Creating BlackboardService");
        logger.info("Initiating TokenFactory");
        tokenFactory = new ObjectTokenFactory(null);
        logger.info("Creating Blackboard List");
        blackBoards = new ArrayList<BlackBoard>();
        allPosts = new ArrayList<Post>();
        logger.info("Initiating UserManagement");
        userManagement = new UserManagementBDFacadeMock();
    }


    public Token getBlackboardOwner(String blackboardName) throws ServiceNotAvailableException, InvalidTokenException {
        logger.info("getBlackboardOwner");

        Token owner = null;

        for (int i=0; i<blackBoards.size(); i++){
            BlackBoard b = blackBoards.get(i);
            owner =  b.getOwner(blackboardName);
        }
        return owner;
    }

    public Token getBlackboardToken(String blackboardName) throws ServiceNotAvailableException, InvalidTokenException {
        logger.info("getBlackboardToken with:"+blackboardName);
        Token bToken = null;

        for (int i=0; i<blackBoards.size(); i++){
            BlackBoard b = blackBoards.get(i);
            bToken =  b.getToken();
        }
        return bToken;
    }

    public Token getPostToken(int positionX, int positionY, String postOwner) throws ServiceNotAvailableException, InvalidTokenException {
        return null;
    }

    public Token createBlackboard(Token userToken, String blackboardName, String blackboardDescription) throws ServiceNotAvailableException, InvalidTokenException {

        logger.info("createBlackboard with"+userToken+blackboardName);
        System.out.println("userToken:"+userToken+"blackboardName:"+blackboardName+"blackboardDescription:"+blackboardDescription);
        BlackBoard blackBoard = new BlackBoard(userToken, blackboardName, blackboardDescription);
        Token blackboardToken = tokenFactory.createToken(this);
        blackBoard.setBlackBoardToken(blackboardToken);
        blackBoard.setOwnerToken(userToken);
        blackBoard.setDescription(blackboardDescription);
        blackBoards.add(blackBoard);

        return blackboardToken;

    }

    public void alterBlackboard(Token userToken, Token blackboardToken, String blackboardName, String blackboardDescription) throws ServiceNotAvailableException, InvalidTokenException {

        blackBoards.forEach(BlackBoard -> {
            if (BlackBoard.getToken().equals(blackboardToken)){
                BlackBoard.setBlackBoardInfo(blackboardName,blackboardDescription);

            }


        });

    }

    public Token postToBlackboard(Token userToken, Token blackboardToken, String postContent, int positionX, int positionY, String postOwner) throws ServiceNotAvailableException, InvalidTokenException {
        Post newPost = new Post(postContent, positionX, positionY, blackboardToken);
        Token postToken = tokenFactory.createToken(newPost);
        newPost.setPostOwnerToken(userToken);
        newPost.setPostToken(postToken);

        blackBoards.forEach(list -> {
            if (list.getToken().equals(blackboardToken)) {
                list.addPost(newPost);
            }
        });


        return postToken;

    }

    public void updatePost(Token userToken, Token blackboardToken, Token postToken, int positionX, int positionY, String postContent) throws ServiceNotAvailableException, InvalidTokenException {

        allPosts.forEach(post -> {
            if (post.getPostToken().equals(postToken)) {
                post.setPostInfo(positionX, positionY, postContent);

            }
        });
    }


    public void deletePost(Token userToken, Token postToken) throws ServiceNotAvailableException, InvalidTokenException {

        for (Post post : allPosts) {
            if (post.getPostToken().equals(postToken)) {

                for (BlackBoard blackBoard : blackBoards) {
                    if (blackBoard.getBlackBoardList().contains(blackBoard))
                        blackBoard.deletePost(post);
                }
                blackBoards.remove(post);
                return;
            }
        }

    }


    @Override
    public List<BlackBoard> getBlackBoards(Token userToken) throws ServiceNotAvailableException, InvalidTokenException {
        return blackBoards;
    }

    @Override
    public List<Post> getPosts(Token userToken, Token blackboardToken) throws ServiceNotAvailableException, InvalidTokenException {
        return allPosts;
    }

    @Override
    public Properties getInfo() {
        return null;
    }
}
