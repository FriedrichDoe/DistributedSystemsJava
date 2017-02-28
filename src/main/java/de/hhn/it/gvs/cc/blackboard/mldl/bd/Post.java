package de.hhn.it.gvs.cc.blackboard.mldl.bd;

import de.hhn.it.gvs.cc.basics.Token;


public class Post {
    Token postToken;
    Token userToken;
    private String postContent;
    private int posX;
    private int posY;
    private Token blackboardToken;

    public Post(String post, int pX, int pY, Token bToken){

        this.postContent = post;
        this.posX = pX;
        this.posY = pY;
        this.blackboardToken = bToken;
    }

    public void setPostOwnerToken(Token owner){

        userToken = owner;
    }

    public void setPostToken(Token newPostToken){

        postToken = newPostToken;

    }

    public Token getPostToken() {
        return postToken;
    }

    public void setPostInfo(int positionX, int positionY, String postContent) {
        this.posX = positionX;
        this.posY = positionY;
        this.postContent = postContent;
    }
}
