package de.hhn.it.gvs.cc.blackboard.mldl.bd;
import de.hhn.it.gvs.cc.basics.Token;

import javax.rmi.CORBA.StubDelegate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;


public class BlackBoard {

    static Token blackboardToken;
    Token userToken;
    private String blackboardName;
    private String blackboardDescription;
    public List<Post> postList;
    public List<BlackBoard> blackboardList;

    public BlackBoard(Token userToken, String name, String description){

        this.blackboardDescription = description;
        this.blackboardName = name;
        this.userToken = userToken;
        postList = new ArrayList<Post>() ;
        blackboardList = new ArrayList<BlackBoard>();


    }

    public void setOwnerToken(Token owner){

        userToken = owner;
    }

    public String getName(){
        return blackboardName;
    }


    public Token getOwner(String bName){

        if(bName == this.blackboardName){

        return userToken;
        }

        else{
            return null;
        }

    }

    public Token getToken(){


        return blackboardToken;

    }

    public void setBlackBoardToken(Token newBlackboardToken){

        blackboardToken = newBlackboardToken;

    }


    public void setDescription(String description) {

        this.blackboardDescription = description;
    }

    public void addPost(Post newPost) {
        postList.add(newPost);
    }


    public void setBlackBoardInfo(String blackboardName, String blackboardDescription) {
        this.blackboardDescription = blackboardDescription;
        this.blackboardName = blackboardName;
    }


    public void deletePost(Post post) {
        if (postList.contains(post))
            postList.remove(post);
    }

    public List<BlackBoard> getBlackBoardList() {
        return blackboardList;
    }
}
