package de.hhn.it.gvs.cc.blackboard.mldl.bd;

import de.hhn.it.gvs.cc.basics.ObjectTokenFactory;
import de.hhn.it.gvs.cc.basics.Token;
import de.hhn.it.gvs.cc.basics.TokenFactory;
import de.hhn.it.gvs.cc.exceptions.IllegalParameterException;
import de.hhn.it.gvs.cc.exceptions.InvalidTokenException;
import de.hhn.it.gvs.cc.exceptions.ServiceNotAvailableException;
import de.hhn.it.gvs.cc.forum.wnck.UserManagementFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Properties;

/**
 * Created by malohr on 05.12.16.
 */
public class UserManagementBDFacadeMock implements UserManagementFacade {


    private static final Logger logger =
            LoggerFactory.getLogger(UserManagementBDFacadeMock.class);

    private TokenFactory tokenFactory;
    private int tokenCount;

    private HashSet<Token> blackboardTokens;
    private HashSet<Token> userTokens;

    public UserManagementBDFacadeMock() {
        Properties properties = new Properties();
        properties.setProperty(TokenFactory.FACTORY_INFO, "Wnck Mock Token Factory");
        blackboardTokens = new HashSet<>();
        userTokens = new HashSet<>();
        tokenFactory = new ObjectTokenFactory(properties);

    }

    @Override
    public void assumeTokenValid(Token userToken) throws InvalidTokenException, IllegalParameterException,
            ServiceNotAvailableException {
        if (userToken == null) {
            throw new IllegalParameterException("user token is null");
        }
        if (userTokens.contains(userToken)) return;

        if (blackboardTokens.contains(userToken)) throw new InvalidTokenException("This is the userToken");

        throw new InvalidTokenException("Token not valid - Expected (blackBoardToken) - Actual (userToken)");
    }


    private Token createToken() {
        Token token = tokenFactory.createToken("New token: " + ++tokenCount);
        return token;
    }


    public Token generateUserToken() {
        Token token = createToken();
        userTokens.add(token);
        return token;
    }

    public Token generateBlackboardToken() {
        Token token = createToken();
        blackboardTokens.add(token);
        return token;
    }
}
