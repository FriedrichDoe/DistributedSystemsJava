package de.hhn.it.gvs.cc.blackboard.mldl.nonJunit;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hhn.it.gvs.cc.blackboard.mldl.bd.BlackBoardService;
import de.hhn.it.gvs.cc.blackboard.mldl.rmi.RmiBlackBoardService;
import de.hhn.it.gvs.cc.blackboard.mldl.rmi.RmiBlackBoardServiceImpl;

/**
 */
public class LaunchBlackboardOnlyTestServer {

	private static final Logger logger = LoggerFactory.getLogger(LaunchBlackboardOnlyTestServer.class);

	public static void main(String[] args) throws RemoteException, InterruptedException {
		Registry registry = LocateRegistry.createRegistry(1099);
		logger.info("Registry has been created");
		BlackBoardService service = new BlackBoardService();
		logger.info("Service has been created");
		RmiBlackBoardServiceImpl rmiServiceImpl = new RmiBlackBoardServiceImpl(service);
		RmiBlackBoardService proxy = (RmiBlackBoardService) UnicastRemoteObject.exportObject(rmiServiceImpl, 0);
		registry.rebind(RmiBlackBoardService.RMI_KEY, proxy);
		logger.info("Proxy is ready " + proxy);
		Thread.sleep(400);
	}
}
