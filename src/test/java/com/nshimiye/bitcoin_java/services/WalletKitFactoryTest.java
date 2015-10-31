package com.nshimiye.bitcoin_java.services;

import static org.junit.Assert.assertNotNull;

import org.bitcoinj.kits.WalletAppKit;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.nshimiye.bitcoin_java.utils.NetworkType;

public class WalletKitFactoryTest {
	WalletAppKit kit = null;
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
		if (kit != null) {
			if (kit.isRunning()) {
				System.out.println("shutting down again");
				kit.stopAsync();
				kit.awaitTerminated();
			}
		}
	}

	@Test
	public void testDEVGetWallet() {
		// make sure you have a bitcoind running locally
		// you find out how to run it(on mac) from
		// https://github.com/bitcoin/bitcoin/blob/master/doc/build-osx.md
		WalletAppKit dwallet = WalletKitFactory.getWalletKit(NetworkType.DEVELOPMENT);
		kit = dwallet;
		assertNotNull(dwallet);

	}

	@Test
	public void testTESTGetWalletKit() {
		// @TODO unittest network type fails to start
		// WalletAppKit twallet = WalletFactory.getWalletKit(NetworkType.TEST);
		// kit = twallet;
		// assertNotNull(twallet);
	}

	@Test
	public void testSTAGEGetWalletKit() {
//		WalletAppKit swallet = WalletKitFactory.getWalletKit(NetworkType.STAGE);
//		kit = swallet;
//		assertNotNull(swallet);

	}

}
