package com.nshimiye.bitcoin_java.services;

import static org.junit.Assert.*;

import org.bitcoinj.kits.WalletAppKit;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.nshimiye.bitcoin_java.utils.NetworkType;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WalletKitFactoryTestProd {
	private WalletAppKit kit = null;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		kit = WalletKitFactory.getWalletKit(NetworkType.PRODUCTION);
	}

	@After
	public void tearDown() throws Exception {
		kit = null;
	}

	@Test
	public void test_1_ShutdownWalletKit() {
		assertNotNull(kit);
		assertTrue(kit.isRunning());
		WalletKitFactory.shutdownWalletKit(NetworkType.PRODUCTION);
		assertFalse(kit.isRunning());
	}

}
