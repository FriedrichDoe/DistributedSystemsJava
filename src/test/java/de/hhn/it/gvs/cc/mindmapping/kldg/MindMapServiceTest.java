package de.hhn.it.gvs.cc.mindmapping.kldg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.hhn.it.gvs.cc.basics.Token;
import de.hhn.it.gvs.cc.exceptions.IllegalParameterException;
import de.hhn.it.gvs.cc.exceptions.InvalidTokenException;
import de.hhn.it.gvs.cc.exceptions.ServiceNotAvailableException;
import de.hhn.it.gvs.cc.mindmapping.kldg.bd.MindMappingService;

/**
 */
public class MindMapServiceTest {
	MindMappingService theService;
	Token userToken;
	Token mindMapToken;
	Token nodeToken;

	@Before
	public void setUp() throws InvalidTokenException, ServiceNotAvailableException, IllegalParameterException {
		theService = new MindMappingService();
		userToken = new Token("12345");
		mindMapToken = theService.createMindmap(userToken, "MyMindMap");
		nodeToken = theService.addNode(userToken, "MyNode", mindMapToken);
	}

	@Test
	public void createNewMindMapIsNotNullTest() throws InvalidTokenException, ServiceNotAvailableException {

		assertNotNull(theService.createMindmap(userToken, "NewMindMap"));
	}

	@Test
	public void addNodeIsNotNullTest()
			throws IllegalParameterException, InvalidTokenException, ServiceNotAvailableException {

		assertNotNull(theService.addNode(userToken, "aNode", mindMapToken));
	}

	@Test
	public void deleteNodeTest() throws IllegalParameterException, InvalidTokenException, ServiceNotAvailableException {
		Token newToken = theService.addNode(userToken, "deleteThis", mindMapToken);
		assertTrue(theService.deleteNode(userToken, newToken, mindMapToken));
	}

	@Test
	public void updateNodeTest() throws IllegalParameterException, InvalidTokenException, ServiceNotAvailableException {
		theService.updateNode(userToken, nodeToken, mindMapToken, "NodeChanged");
		assertEquals("NodeChanged", theService.getMindMap(mindMapToken).getNode(nodeToken).getName());
	}

	@Test
	public void showMindMapTest()
			throws IllegalParameterException, ServiceNotAvailableException, InvalidTokenException {

		assertNotNull(theService.showMindmap(userToken, mindMapToken));
	}

	@Test
	public void addRelationAndDeleteRelationDoesWhatItShouldTest()
			throws IllegalParameterException, InvalidTokenException, ServiceNotAvailableException {
		Token newToken = theService.addNode(userToken, "newMMToken", mindMapToken);
		assertTrue(theService.addRelation(userToken, mindMapToken, nodeToken, newToken));
		assertTrue(theService.showRelations(userToken, mindMapToken).get(nodeToken).contains(newToken));
		assertTrue(theService.deleteRelation(userToken, mindMapToken, nodeToken, newToken));
		assertNull(theService.showRelations(userToken, mindMapToken).get(nodeToken));
	}

	@Test
	public void deleteRelationWithMultipleChildRelations()
			throws InvalidTokenException, ServiceNotAvailableException, IllegalParameterException {
		Token token1 = theService.addNode(userToken, "node1", mindMapToken);
		Token token2 = theService.addNode(userToken, "node2", mindMapToken);
		Token token3 = theService.addNode(userToken, "node3", mindMapToken);
		Token token4 = theService.addNode(userToken, "node4", mindMapToken);
		Token token5 = theService.addNode(userToken, "node5", mindMapToken);

		assertTrue(theService.addRelation(userToken, mindMapToken, nodeToken, token1));
		assertTrue(theService.addRelation(userToken, mindMapToken, token1, token2));
		assertTrue(theService.addRelation(userToken, mindMapToken, token1, token3));
		assertTrue(theService.addRelation(userToken, mindMapToken, token3, token4));
		assertTrue(theService.addRelation(userToken, mindMapToken, token4, token5));

		assertTrue(theService.deleteRelation(userToken, mindMapToken, nodeToken, token1));

		assertNull(theService.showRelations(userToken, mindMapToken).get(nodeToken));
		assertNull(theService.showRelations(userToken, mindMapToken).get(token1));
		assertNull(theService.showRelations(userToken, mindMapToken).get(token2));
		assertNull(theService.showRelations(userToken, mindMapToken).get(token3));
		assertNull(theService.showRelations(userToken, mindMapToken).get(token4));
		assertNull(theService.showRelations(userToken, mindMapToken).get(token5));

	}

	@Test(expected = InvalidTokenException.class)
	public void invalidTokenExceptionWillBeThrownTest()
			throws IllegalParameterException, InvalidTokenException, ServiceNotAvailableException {
		Token testUser = new Token("");
		theService.showMindmap(testUser, mindMapToken);

	}

}
