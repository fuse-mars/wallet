package com.nshimiye.bitcoin_java.services;

import static org.junit.Assert.*;

import org.bitcoinj.kits.WalletAppKit;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.nshimiye.bitcoin_java.utils.NetworkType;

public class WalletKitFactoryTestDev {

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
	}

	@Test
	public void test_1_GetWalletKit() {
		WalletAppKit dwallet = WalletKitFactory.getWalletKit(NetworkType.DEVELOPMENT);
		assertNotNull(dwallet);
	}

}
