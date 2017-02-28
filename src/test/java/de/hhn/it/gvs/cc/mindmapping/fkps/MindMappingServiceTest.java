package de.hhn.it.gvs.cc.mindmapping.fkps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.hhn.it.gvs.cc.basics.ObjectTokenFactory;
import de.hhn.it.gvs.cc.basics.Token;
import de.hhn.it.gvs.cc.exceptions.IllegalParameterException;
import de.hhn.it.gvs.cc.exceptions.InvalidTokenException;
import de.hhn.it.gvs.cc.exceptions.ServiceNotAvailableException;
import de.hhn.it.gvs.cc.mindmapping.kldg.bd.MindMap;
import de.hhn.it.gvs.cc.mindmapping.kldg.bd.MindMappingService;

public class MindMappingServiceTest {

	private MindMappingService MindMapping;
	private ObjectTokenFactory tokenObj;
	private Token nodeToken;
	private Token userToken;
	private Token mindMapToken;

	private String mindMapName1 = "hello";
	private String nodeName2 = "olleh";
	private String nodeName3 = "";

	@Before
	public void setUp() throws Exception {
		tokenObj = new ObjectTokenFactory(null);
		userToken = tokenObj.createToken(this);
		MindMapping = new MindMappingService();
	}

	@Test
	public void createMindMapTest() {

		try {

			Token MindMapToken = MindMapping.createMindmap(userToken, mindMapName1);

			assertNotNull(MindMapping.getMindMap(MindMapToken));

		} catch (ServiceNotAvailableException | InvalidTokenException e) {
			System.out.println("createMindMapTest ERROR");
			e.printStackTrace();
		}

	}

	@Test
	public void showMindMapTest() {

		try {
			mindMapToken = MindMapping.createMindmap(userToken, mindMapName1);

			MindMap mapBack = MindMapping.showMindmap(userToken, mindMapToken);

			assertNotNull(mapBack);
			assertEquals(mapBack.getName(), mindMapName1);

		} catch (ServiceNotAvailableException | InvalidTokenException | IllegalParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void addNodeTest() {
		try {
			mindMapToken = MindMapping.createMindmap(userToken, mindMapName1);

			Token mapToken = MindMapping.addNode(userToken, nodeName2, mindMapToken);

			assertNotNull(mapToken);

		} catch (ServiceNotAvailableException | InvalidTokenException | IllegalParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void deleteNodeTest() {

		try {
			mindMapToken = MindMapping.createMindmap(userToken, mindMapName1);

			Token mapToken = MindMapping.addNode(userToken, nodeName2, mindMapToken);

			assertTrue(MindMapping.deleteNode(userToken, mapToken, mindMapToken));
		} catch (ServiceNotAvailableException | InvalidTokenException | IllegalParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void addRelationTest() {

		try {
			mindMapToken = MindMapping.createMindmap(userToken, mindMapName1);

			Token mapToken = MindMapping.addNode(userToken, nodeName2, mindMapToken);

			assertTrue(MindMapping.addRelation(userToken, mindMapToken, mindMapToken, mapToken));
		} catch (ServiceNotAvailableException | InvalidTokenException | IllegalParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void deleteRelationTest() {

		try {
			mindMapToken = MindMapping.createMindmap(userToken, mindMapName1);

			Token mapToken = MindMapping.addNode(userToken, nodeName2, mindMapToken);

			MindMapping.addRelation(userToken, mindMapToken, mindMapToken, mapToken);

			assertTrue(MindMapping.deleteRelation(userToken, mindMapToken, mindMapToken, mapToken));

		} catch (ServiceNotAvailableException | InvalidTokenException | IllegalParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test(expected = InvalidTokenException.class)
	public void deleteFalseRelationTest() throws ServiceNotAvailableException, InvalidTokenException {

		Token mapToken = null;

		mindMapToken = MindMapping.createMindmap(userToken, mindMapName1);

		MindMapping.deleteRelation(userToken, mindMapToken, mindMapToken, mapToken);

	}

	@Test
	public void updateNodeTest() {

		try {
			mindMapToken = MindMapping.createMindmap(userToken, mindMapName1);

			Token mapToken = MindMapping.addNode(userToken, nodeName2, mindMapToken);

			MindMapping.updateNode(userToken, mapToken, mindMapToken, nodeName3);

			assertEquals(MindMapping.getMindMap(mindMapToken).getNode(mapToken).getName(), nodeName3);
		} catch (ServiceNotAvailableException | InvalidTokenException | IllegalParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
