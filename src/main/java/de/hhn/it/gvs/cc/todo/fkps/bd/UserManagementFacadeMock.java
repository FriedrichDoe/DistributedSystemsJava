package de.hhn.it.gvs.cc.todo.fkps.bd;/**
 * Created by wnck on 29/10/16.
 */

import de.hhn.it.gvs.cc.basics.ObjectTokenFactory;
import de.hhn.it.gvs.cc.basics.Token;
import de.hhn.it.gvs.cc.basics.TokenFactory;
import de.hhn.it.gvs.cc.exceptions.IllegalParameterException;
import de.hhn.it.gvs.cc.exceptions.InvalidTokenException;
import de.hhn.it.gvs.cc.exceptions.ServiceNotAvailableException;
import de.hhn.it.gvs.cc.forum.wnck.UserManagementFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashSet;
import java.util.Properties;

public class UserManagementFacadeMock implements UserManagementFacade {
  private static final Logger logger =
          LoggerFactory.getLogger(UserManagementFacadeMock.class);

  public static final String ALWAYS_TRUE_TOKEN_STRING = "always true ...";

  private TokenFactory tokenFactory;
  private int tokenCounter;

  private HashSet<Token> goodTokens;
  private HashSet<Token> badTokens;
  private final Token alwaysTrueToken;

  public UserManagementFacadeMock() {
    Properties properties = new Properties();
    properties.setProperty(TokenFactory.FACTORY_INFO, "Wnck Mock Token Factory");
    goodTokens = new HashSet<>();
    badTokens = new HashSet<>();
    tokenFactory = new ObjectTokenFactory(properties);
    alwaysTrueToken = tokenFactory.createToken(ALWAYS_TRUE_TOKEN_STRING);
    goodTokens.add(alwaysTrueToken);
  }

  @Override
  public void assumeTokenValid(Token userToken) throws InvalidTokenException, IllegalParameterException,
          ServiceNotAvailableException {
    if (userToken == null) {
      throw new IllegalParameterException("user token is null reference.");
    }
    if (goodTokens.contains(userToken)) return;

    if (badTokens.contains(userToken)) throw new InvalidTokenException("This is my own bad token.");

    throw new InvalidTokenException("This is really not my token, even not a bad one.");
  }

  public Token createGoodToken() {
    Token token = createToken();
    goodTokens.add(token);
    return token;
  }

  public Token createBadToken() {
    Token token = createToken();
    badTokens.add(token);
    return token;
  }

  public Token getAlwaysTrueToken() {
    return alwaysTrueToken;
  }

  private Token createToken() {
    Token token = tokenFactory.createToken("token nr " + ++tokenCounter);
    return token;
  }

}
