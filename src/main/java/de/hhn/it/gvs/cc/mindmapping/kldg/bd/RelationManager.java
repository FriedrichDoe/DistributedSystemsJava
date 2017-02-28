package de.hhn.it.gvs.cc.mindmapping.kldg.bd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hhn.it.gvs.cc.basics.Token;
import de.hhn.it.gvs.cc.exceptions.InvalidTokenException;

/**
 */
public class RelationManager {

	// parent -> children
	private HashMap<Token, List<Token>> relationMap;

	/**
	 * constructor
	 */
	public RelationManager() {
		relationMap = new HashMap<>();
	}

	public HashMap<Token, List<Token>> getRelations() {
		return relationMap;
	}

	public void addChild(Token parentToken, Token childToken) {
		relationMap.get(parentToken).add(childToken);
	}

	/**
	 * returns the parent if possible. if the node doesnt have a parent, it is a
	 * root node
	 * 
	 * @param childNode
	 * @return
	 * @throws InvalidTokenException
	 */
	public Token getParent(Token childNode) throws InvalidTokenException {
		for (Map.Entry<Token, List<Token>> entry : relationMap.entrySet()) {
			if (entry.getValue().contains(childNode)) {
				return entry.getKey();
			}
		}

		if (relationMap.containsKey(childNode)) {
			return null;
		}

		throw new InvalidTokenException("node has no relations");
	}

	/**
	 * returns all children of the node
	 * 
	 * @param userToken
	 * @param nodeToken
	 * @return
	 * @throws InvalidTokenException
	 */
	public List<Token> getChildren(Token userToken, Token nodeToken) throws InvalidTokenException {

		if (relationMap.containsKey(nodeToken)) {
			return relationMap.get(nodeToken);
		} else {
			throw new InvalidTokenException("Node doesn't have children");
		}
	}

	/**
	 * deletes the relation between the nodes and all relations of the children
	 * nodes
	 * 
	 * @param userToken
	 * @param parentNode
	 * @param childNode
	 * @throws InvalidTokenException
	 */
	public boolean deleteRelation(Token userToken, Token parentNode, Token childNode) throws InvalidTokenException {
		if (this.getChildren(userToken, parentNode).size() > 1) {
			List<Token> newList = relationMap.get(parentNode);
			newList.remove(childNode);
			relationMap.put(parentNode, newList);
			deleteRelCasc(childNode);
			return true;
		} else if (this.getChildren(userToken, parentNode).size() == 1
				&& relationMap.get(parentNode).contains(childNode)) {
			relationMap.remove(parentNode);
			deleteRelCasc(childNode);
			return true;
		}

		else {
			if (!relationMap.get(parentNode).contains(childNode)) {
				throw new InvalidTokenException("Relation doesn't exist");
			}
		}
		return false;
	}

	/**
	 * helper method to delete all relatons
	 * 
	 * @param parent
	 */
	private void deleteRelCasc(Token parent) {
		if (!(relationMap.get(parent) == null)) {
			for (Token token : relationMap.get(parent)) {
				deleteRelCasc(token);
			}
			relationMap.remove(parent);
		}
	}

	/**
	 *
	 * @param userToken
	 * @param parentNode
	 * @param childNode
	 */
	public boolean addRelation(Token userToken, Token parentNode, Token childNode) {
		if (relationMap.containsKey(parentNode)) {// only update list if key
													// already exists
			List<Token> newList = relationMap.get(parentNode);
			newList.add(childNode);

			HashMap testMap = relationMap;
			testMap.put(parentNode, newList);

			if (hasCircleRelation(childNode, testMap)) {
				return false;
			}

			relationMap.put(parentNode, newList);
			return true;
		} else {
			List<Token> newList = new ArrayList<>();
			newList.add(childNode);

			HashMap testMap = relationMap;
			testMap.put(parentNode, newList);

			if (hasCircleRelation(childNode, testMap)) {
				return false;
			}

			relationMap.put(parentNode, newList);
			return true;
		}
	}

	/**
	 * returns true if a circle relation is found and false if not
	 * 
	 * @param node
	 * @return
	 */
	private boolean hasCircleRelation(Token node, HashMap<Token, List<Token>> map) {
		// return circleRec(node, node, map);
		return false;
	}

	/**
	 * recusively finds a circle relation with the startpoint
	 * 
	 * @param startpoint
	 * @param currentNode
	 * @return
	 */
	private boolean circleRec(Token startpoint, Token currentNode, HashMap<Token, List<Token>> map) {

		for (Token node : map.get(currentNode)) {

			if (node == startpoint) {
				return true;
			} else if (map.get(node).size() > 0) {
				return circleRec(startpoint, node, map);
			}
		}
		return false;
	}
}
