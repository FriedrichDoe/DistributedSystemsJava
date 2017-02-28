package de.hhn.it.gvs.cc.mindmapping.kldg.bd;

import de.hhn.it.gvs.cc.basics.Token;

/**
 */
public class MMNode {

	private String name;
	private Token nodeToken;
	private Token creatorToken;

	public MMNode(String newName, Token newToken) {
		name = newName;
		nodeToken = newToken;

	}

	public void setCreatorToken(Token newToken) {
		creatorToken = newToken;
	}

	public void setName(Token userToken, String newString) {
		name = newString;
	}

	public String getName() {
		return name;
	}

	public Token getNodeToken() {
		return nodeToken;
	}

	public Token getCreatorToken() {
		return creatorToken;
	}
}
