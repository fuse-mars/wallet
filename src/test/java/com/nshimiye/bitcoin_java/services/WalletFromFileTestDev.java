package com.nshimiye.bitcoin_java.services;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.bitcoinj.core.Wallet;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.params.RegTestParams;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.nshimiye.bitcoin_java.utils.NetworkType;

public class WalletFromFileTestDev {
	private WalletAppKit kit;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		Wallet twallet = new Wallet(RegTestParams.get());
		twallet.saveToFile(new File("dev.wallet"));
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
	public void testGetWalletKitFromFile() {
		kit = WalletKitFactory.getWalletKitFromFile(new File("dev.wallet"));
		assertNotNull(kit);
		assertTrue(kit.isRunning());
		WalletKitFactory.shutdownWalletKit(NetworkType.DEVELOPMENT);
		assertFalse(kit.isRunning());
	}

}
