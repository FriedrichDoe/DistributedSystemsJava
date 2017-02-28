package de.hhn.it.gvs.cc.mindmapping.kldg.bd;

import java.util.HashMap;
import java.util.List;

import de.hhn.it.gvs.cc.basics.Token;
import de.hhn.it.gvs.cc.exceptions.IllegalParameterException;
import de.hhn.it.gvs.cc.exceptions.InvalidTokenException;
import de.hhn.it.gvs.cc.exceptions.ServiceNotAvailableException;

/*
 * Interface of Mindmapping Service
 * @version 2016-10-09
 *
 */
public interface BDMindmappingService {

	/**
	 * creates a new mindmap
	 * 
	 * @param userToken
	 * @param name
	 *            name of the Mindmap
	 * @throws ServiceNotAvailableException
	 * @throws InvalidTokenException
	 */
	public Token createMindmap(Token userToken, String name) throws ServiceNotAvailableException, InvalidTokenException;

	/**
	 * shows the selected mindmap
	 * 
	 * @param mindMapToken
	 *            name of mindmap that should be shown
	 * @return
	 * @throws ServiceNotAvailableException
	 */
	abstract MindMap showMindmap(Token userToken, Token mindMapToken)
			throws ServiceNotAvailableException, IllegalParameterException, InvalidTokenException;

	/**
	 * change the name of a string
	 * 
	 * @param userToken
	 * @param nodeToken
	 * @param mindMapToken
	 * @param newString
	 *            new string
	 * @throws ServiceNotAvailableException
	 * @throws InvalidTokenException
	 */
	public void updateNode(Token userToken, Token nodeToken, Token mindMapToken, String newString)
			throws ServiceNotAvailableException, InvalidTokenException, IllegalParameterException;

	/**
	 * deletes the selected string
	 * 
	 * @param userToken
	 * @param nodeToken
	 *            name of string
	 * @param mindMapToken
	 * @throws ServiceNotAvailableException
	 */
	public boolean deleteNode(Token userToken, Token nodeToken, Token mindMapToken)
			throws ServiceNotAvailableException, InvalidTokenException, IllegalParameterException;

	/**
	 * add a new string
	 * 
	 * @param userToken
	 * @param newNode
	 *            name of new node
	 * @throws ServiceNotAvailableException
	 * @throws InvalidTokenException
	 */
	public Token addNode(Token userToken, String newNode, Token mindMapToken)
			throws ServiceNotAvailableException, InvalidTokenException, IllegalParameterException;

	/**
	 * add a relation to a string
	 * 
	 * @param userToken
	 * @param mindMapToken
	 * @param parentToken
	 *            parent of the relation
	 * @param childToken
	 *            child of the relation
	 * @return
	 * @throws ServiceNotAvailableException
	 * @throws InvalidTokenException
	 */
	public boolean addRelation(Token userToken, Token mindMapToken, Token parentToken, Token childToken)
			throws ServiceNotAvailableException, InvalidTokenException;

	/**
	 * delete the selected relation
	 * 
	 * @param userToken
	 * @param mindMapToken
	 * @param parentToken
	 *            parent of the relation
	 * @param childToken
	 *            child of the relation
	 * @return
	 * @throws ServiceNotAvailableException
	 * @throws InvalidTokenException
	 */
	public boolean deleteRelation(Token userToken, Token mindMapToken, Token parentToken, Token childToken)
			throws ServiceNotAvailableException, InvalidTokenException;

	public HashMap<Token, List<Token>> showRelations(Token userToken, Token mindMapToken)
			throws ServiceNotAvailableException, InvalidTokenException;

}
