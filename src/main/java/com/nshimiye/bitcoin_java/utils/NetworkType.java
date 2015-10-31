package com.nshimiye.bitcoin_java.utils;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.params.UnitTestParams;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.params.MainNetParams;
// The available network options are:
// - MainNetParams
// - TestNet3Params
// - RegTestParams
// While developing your application you probably want to use the Regtest mode and run your local bitcoin network. Run bitcoind with the -regtest flag
// To test you app with a real network you can use the testnet. The testnet is an alternative bitcoin network that follows the same rules as main network. Coins are worth nothing and you can get coins for example from http://faucet.xeno-genesis.com/
// 
public enum NetworkType {
	DEVELOPMENT("org.bitcoin.regtest", RegTestParams.get()), // NetworkParameters.ID_REGTEST 
	TEST("org.bitcoinj.unittest", UnitTestParams.get()), // NetworkParameters.ID_UNITTESTNET 
	STAGE("org.bitcoin.test", TestNet3Params.get()), // NetworkParameters.ID_TESTNET 
	PRODUCTION("org.bitcoin.production", MainNetParams.get()); //NetworkParameters.ID_MAINNET 
	
	private String id;
	public String getId(){
		return this.id;
	}
	private NetworkParameters params;
	public NetworkParameters getParams(){
		return params;
	}
	private NetworkType(String id, NetworkParameters params){
		this.id = id;
		this.params = params;
	}
}
