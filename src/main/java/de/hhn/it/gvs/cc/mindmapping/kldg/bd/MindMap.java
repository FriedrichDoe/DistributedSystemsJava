package de.hhn.it.gvs.cc.mindmapping.kldg.bd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hhn.it.gvs.cc.basics.Token;

/**
 */
public class MindMap {
	Token creatorToken;
	Token MMToken;
	private String MMname;
	private ArrayList<MMNode> nodes;
	private RelationManager theRelations;
	private HashMap<String, List<String>> relations;

	public MindMap(String name) {
		this.MMname = name;
		nodes = new ArrayList<>();
		theRelations = new RelationManager();
		relations = new HashMap<>();

	}

	/**
	 * adds a new node to the MindMap
	 * 
	 * @param newName
	 *            name of node
	 * @return succeed or not
	 */
	public boolean addNode(String newName, Token nodeToken) {
		for (MMNode node : nodes) {
			if (node.getName() == newName) {
				return false;
			}

		}
		nodes.add(new MMNode(newName, nodeToken));

		return true;
	}

	public void deleteNode(Token nodeToken) {
		for (MMNode node : nodes) {
			if (node.getNodeToken() == nodeToken) {
				node = null;

			}

		}

	}

	public void setCreatorToken(Token creator) {
		creatorToken = creator;
	}

	public void setMindMapToken(Token newToken) {
		MMToken = newToken;
	}

	public String getName() {
		return MMname;
	}

	/**
	 * search the List for the right node
	 * 
	 * @param nodeToken
	 *            node which is searched
	 * @return
	 */
	public MMNode getNode(Token nodeToken) {
		for (MMNode node : nodes) {

			if (node.getNodeToken() == nodeToken) {
				return node;
			}

		}
		return null;
	}

	/**
	 * get the relationmanager for the wanted mindmap
	 * 
	 * @return
	 */
	public RelationManager getRelationManager() {
		return theRelations;
	}

	public HashMap<String, List<String>> getAllNodeRelations() {

		relations.clear();

		for (Map.Entry<Token, List<Token>> entry : theRelations.getRelations().entrySet()) {
			List<String> childString = new ArrayList<>();
			for (Token child : entry.getValue()) {
				childString.add(child.toString());
			}
			relations.put(entry.getKey().toString(), childString);
		}

		return relations;
	}
}
