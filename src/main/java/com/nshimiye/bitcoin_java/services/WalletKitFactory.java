package com.nshimiye.bitcoin_java.services;

import java.io.File;

import org.bitcoinj.core.Wallet;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.store.UnreadableWalletException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nshimiye.bitcoin_java.utils.NetworkType;

/**
 * create and destroys four wallet kits <br>
 * source:
 * https://github.com/bitcoinj/bitcoinj/blob/master/examples
 * /src/main/java/org/bitcoinj/examples/Kit.java
 * 
 * @author mars
 *
 */
public class WalletKitFactory {
	private static WalletAppKit realWallet = null;
	private static WalletAppKit stageWallet = null;
//TODO	private static WalletAppKit testWallet = null;
	private static WalletAppKit devWallet = null;
	private static final Logger LOGGER = LoggerFactory.getLogger(WalletKitFactory.class);

	// The available options are:
	// - MainNetParams
	// - TestNet3Params
	// - RegTestParams
	// While developing your application you probably want to use the Regtest
	// mode and run your local bitcoin network. Run bitcoind with the -regtest
	// flag
	// To test you app with a real network you can use the testnet. The testnet
	// is an alternative bitcoin network that follows the same rules as main
	// network. Coins are worth nothing and you can get coins for example from
	// http://faucet.xeno-genesis.com/
	//

	public static WalletAppKit getWalletKit(NetworkType net) {
		WalletAppKit walletKit;
		switch (net) {
		case PRODUCTION:
			if (realWallet == null)
				realWallet = new WalletAppKit(
						NetworkType.PRODUCTION.getParams(), new File("."),
						"production");
			walletKit = realWallet;
			break;
		case STAGE:
			if (stageWallet == null)
				stageWallet = new WalletAppKit(NetworkType.STAGE.getParams(),
						new File("."), "stage");
			walletKit = stageWallet;
			break;
		case TEST:
			//TODO find out why testing net does not work
//			if (testWallet == null)
//				testWallet = new WalletAppKit(NetworkType.TEST.getParams(),
//						new File("."), "testing");
//			walletKit = testWallet;
//			break;
		case DEVELOPMENT:
		default:
			if (devWallet == null)
				devWallet = new WalletAppKit(
						NetworkType.DEVELOPMENT.getParams(), new File("."),
						"development");
			walletKit = devWallet;
			break;
		}

		if (!walletKit.isRunning()) {
			initializeWalletKit(walletKit);
		}

		return walletKit;
	}

	/**
	 * given a saved wallet file, shutdown the existing walletkit and
	 * start it with a new wallet using settings from the file
	 * @param file
	 * @return
	 */
	public static WalletAppKit getWalletKitFromFile(File file) {
		Wallet wallet;
		WalletAppKit kit = null;
		try {
			wallet = Wallet.loadFromFile(file);

		// find the type of network this saved wallet uses
		NetworkType net = getNetworkType(wallet.getNetworkParameters().getId());
		
		// shutdown first
		shutdownWalletKit(net);
		if(file.exists()){
			// create custom wallet
			File walletDir = file.getAbsoluteFile().getParentFile();
			String filename = file.getName();
			LOGGER.info("dir = {}, file = {}", walletDir.getName(), filename);
			String prefix = filename;
					//.split(".")[0];
			if(walletDir != null){
				if(walletDir.isDirectory()){
					kit = new WalletAppKit(net.getParams(), walletDir, prefix);
					initializeWalletKit(kit);
					setWalletKitType(wallet.getNetworkParameters().getId(), kit);
				}else{
					// kit will be left null
				}	
			}

		}
		
		} catch (UnreadableWalletException e) {
			// TODO Auto-generated catch block
			System.err
					.println("cannot create wallet" + e.getLocalizedMessage());
			wallet = null;
		
		}
		return kit;
	}

	private static void initializeWalletKit(WalletAppKit kit) {

		// In case you want to connect with your local bitcoind tell the kit to
		// connect to localhost.
		// You must do that in reg test mode.
		if (kit.params().getId().equals(NetworkType.DEVELOPMENT.getId())) {
			kit.connectToLocalHost();
		}
		
		kit.setAutoSave(
				kit.params().getId().equals(NetworkType.PRODUCTION.getId())
				); //for production this should be true
		
		// Now we start the kit and sync the blockchain.
		// bitcoinj is working a lot with the Google Guava libraries. The
		// WalletAppKit extends the AbstractIdleService. Have a look at the
		// introduction to Guava services:
		// https://code.google.com/p/guava-libraries/wiki/ServiceExplained
		kit.startAsync(); // non-blocking call
		kit.awaitRunning(); //this is a blocking call
		


		// To observe wallet events (like coins received) we implement a
		// EventListener class that extends the AbstractWalletEventListener
		// bitcoinj then calls the different functions from the EventListener
		// class
		// I have put the listening methods inside the WalletKit Worker


	}

	public static void shutdownWalletKit(NetworkType net) {
		WalletAppKit kit;
		switch (net) {
		case PRODUCTION:
			kit = realWallet;
			break;
		case STAGE:
			kit = stageWallet;
			break;
		case TEST:
//TODO			kit = testWallet;
//			break;
		case DEVELOPMENT:
		default:
			kit = devWallet;
			break;
		}
		
		// Make sure to properly shut down all the running services when you
		// manually want to stop the kit. The WalletAppKit registers a runtime
		// ShutdownHook so we actually do not need to worry about that when our
		// application is stopping.
		if (kit != null) {
			if (kit.isRunning()) {
				System.out.println("shutting down again");
				kit.stopAsync();
				kit.awaitTerminated();
			}
		}
	}
	
	private static void setWalletKitType(String id, WalletAppKit kit){
		
		if (id.equals(NetworkType.PRODUCTION.getId())) {
			realWallet = kit;
		} else if (id.equals(NetworkType.STAGE.getId())) {
			 stageWallet = kit;
//TODO		} else if (id.equals(NetworkType.TEST.getId())) {
//			testWallet = kit;
		} else { // assume dev
			devWallet = kit;
		}
	}
	private static NetworkType getNetworkType(String id){
		
		if (id.equals(NetworkType.PRODUCTION.getId())) {
			return NetworkType.PRODUCTION;
		} else if (id.equals(NetworkType.STAGE.getId())) {
			return NetworkType.STAGE;
//TODO		} else if (id.equals(NetworkType.TEST.getId())) {
//			return NetworkType.TEST;
		} else { // assume dev
			return NetworkType.DEVELOPMENT;
		}
	}
}
