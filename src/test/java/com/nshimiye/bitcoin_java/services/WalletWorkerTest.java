package com.nshimiye.bitcoin_java.services;

import static org.junit.Assert.*;

import java.util.List;

import org.bitcoinj.core.ECKey;
import org.bitcoinj.kits.WalletAppKit;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.nshimiye.bitcoin_java.utils.NetworkType;
/**
 *  While developing runs in Regtest mode
 *  so you need to run your local bitcoin network. Run bitcoind with the -regtest flag
 *  instructions can be found by following this link: https://github.com/bitcoin/bitcoin/blob/master/doc/build-osx.md
 * @author mars
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WalletWorkerTest {
	private WalletAppKit kit;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		kit = null;
	}

	@Test
	public void testWalletWorker() {
		kit = WalletKitFactory.getWalletKit(NetworkType.DEVELOPMENT);
		assertNotNull("The wallet kit should exist by now", kit);
		assertTrue("The wallet kit needs to be running in order to create a wallet kit worker", kit.isRunning());
		WalletKitWorker ww = new WalletKitWorker(kit);
		assertNotNull("wallet worker should not be null", ww);
	}

	/**
	 * this part needs a working address
	 */
	@Test
	public void testSendCoinToAddress() {
		kit = WalletKitFactory.getWalletKit(NetworkType.DEVELOPMENT);
		WalletKitWorker ww = new WalletKitWorker(kit);
		String address = "msHmShWqxWFK81gX4SS4X4YDSssDktRT8Z";
		String amount = ww.sendCoinToAddress("0.0001", address);
		assertNotNull("amount should be same as what you specified instead of null", amount);
		assertNotEquals( "Sending bitcoin should return the amount sent", amount, "");
		
		amount = ww.sendCoinToAddress("0.0000000001", address);
		assertNull("Sending amount less than 1/100 000 000 is not allowed and should return null", amount);
		
	}

	@Test
	public void testCreateAddress() {
		kit = WalletKitFactory.getWalletKit(NetworkType.DEVELOPMENT);
		WalletKitWorker ww = new WalletKitWorker(kit);
		String address = ww.createAddress();
		assertNotEquals( "address should be a non empty string", address, "");
	}

	@Test
	public void testGetWalletKeys() {
		kit = WalletKitFactory.getWalletKit(NetworkType.DEVELOPMENT);
		WalletKitWorker ww = new WalletKitWorker(kit);
		List<ECKey> keys = ww.getWalletKeys();
		assertNotNull(keys);
	}

}
