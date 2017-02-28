package de.hhn.it.gvs.cc.mindmapping.kldg.bd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hhn.it.gvs.cc.basics.ObjectTokenFactory;
import de.hhn.it.gvs.cc.basics.Token;
import de.hhn.it.gvs.cc.exceptions.IllegalParameterException;
import de.hhn.it.gvs.cc.exceptions.InvalidTokenException;
import de.hhn.it.gvs.cc.exceptions.ServiceNotAvailableException;

/**
 */
public class MindMappingService implements BDMindmappingService {
	private Token userToken;
	private ObjectTokenFactory tokenFactory = new ObjectTokenFactory(null);
	private ArrayList<MindMap> mindMapList = new ArrayList<>();

	public void MindMappingService() {
		tokenFactory = new ObjectTokenFactory(null);

	}

	@Override
	public Token createMindmap(Token userToken, String name)
			throws ServiceNotAvailableException, InvalidTokenException {
		validateToken(userToken);
		MindMap mindmap = new MindMap(name);
		mindMapList.add(mindmap);
		Token mindMapToken = tokenFactory.createToken(mindmap);
		validateToken(mindMapToken);
		mindmap.setCreatorToken(userToken);
		mindmap.setMindMapToken(mindMapToken);
		return mindMapToken;
	}

	@Override
	public MindMap showMindmap(Token userToken, Token mindMapToken)
			throws ServiceNotAvailableException, IllegalParameterException, InvalidTokenException {
		validateToken(userToken);
		validateToken(mindMapToken);
		return getMindMap(mindMapToken);
	}

	@Override
	public void updateNode(Token userToken, Token nodeToken, Token mindMapToken, String newString)
			throws ServiceNotAvailableException, InvalidTokenException, IllegalParameterException {
		validateToken(userToken);
		validateToken(nodeToken);
		validateToken(mindMapToken);
		getMindMap(mindMapToken).getNode(nodeToken).setName(userToken, newString);
	}

	@Override
	public boolean deleteNode(Token userToken, Token nodeToken, Token mindMapToken)
			throws ServiceNotAvailableException, InvalidTokenException, IllegalParameterException {
		validateToken(userToken);
		validateToken(nodeToken);
		validateToken(mindMapToken);
		getMindMap(mindMapToken).deleteNode(nodeToken);

		return true;
	}

	@Override
	public Token addNode(Token userToken, String newNode, Token mindMapToken)
			throws ServiceNotAvailableException, InvalidTokenException, IllegalParameterException {
		validateToken(userToken);
		validateToken(mindMapToken);
		Token nodeToken = tokenFactory.createToken(newNode);
		getMindMap(mindMapToken).addNode(newNode, nodeToken);
		return nodeToken;
	}

	@Override
	public boolean addRelation(Token userToken, Token mindMapToken, Token parentToken, Token childToken)
			throws ServiceNotAvailableException, InvalidTokenException {
		validateToken(userToken);
		validateToken(mindMapToken);
		validateToken(parentToken);
		validateToken(childToken);
		return getMindMap(mindMapToken).getRelationManager().addRelation(userToken, parentToken, childToken);

	}

	@Override
	public boolean deleteRelation(Token userToken, Token mindMapToken, Token parentToken, Token childToken)
			throws ServiceNotAvailableException, InvalidTokenException {
		validateToken(userToken);
		validateToken(mindMapToken);
		validateToken(parentToken);
		validateToken(childToken);
		return getMindMap(mindMapToken).getRelationManager().deleteRelation(userToken, parentToken, childToken);

	}

	@Override
	public HashMap<Token, List<Token>> showRelations(Token userToken, Token mindMapToken)
			throws ServiceNotAvailableException, InvalidTokenException {
		validateToken(userToken);
		validateToken(mindMapToken);
		return getMindMap(mindMapToken).getRelationManager().getRelations();
	}

	public void setUserToken(Token userToken) {
		this.userToken = userToken;
	}

	public MindMap getMindMap(Token mindMapToken) {
		for (MindMap mm : mindMapList) {
			if (mm.MMToken == mindMapToken)
				return mm;

		}
		return null;
	}

	public void validateToken(Token checkToken) throws InvalidTokenException {
		if (checkToken == null || checkToken.getToken() == null || checkToken.getToken() == "")
			throw new InvalidTokenException("Token is not valid");
	}

}
