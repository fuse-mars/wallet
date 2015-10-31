package com.nshimiye.bitcoin_java.controllers;

import org.bitcoinj.kits.WalletAppKit;

import com.nshimiye.bitcoin_java.services.WalletKitFactory;
import com.nshimiye.bitcoin_java.services.WalletKitWorker;
import com.nshimiye.bitcoin_java.utils.NetworkType;


public class RemoteExchange {

	public static void main(String[] args) {
		if(args.length <= 0){
			System.err.println("You must provide the network type: PRODUCTION or STAGE");
			System.exit(-1);
		}
		WalletAppKit kit;
		if(args[0].equals("PRODUCTION")){ // connect to the real bitcoin network
			kit = WalletKitFactory
					.getWalletKit(NetworkType.PRODUCTION);		
		}else { // connect to the testnet network
			kit = WalletKitFactory
					.getWalletKit(NetworkType.STAGE);	
		}

		WalletKitWorker ww = new WalletKitWorker(kit);
		
		System.out.println(kit.wallet());

		// kit.wallet().getBalanceFuture(Coin.COIN,
		// Wallet.BalanceType.AVAILABLE).get();
		(new Thread(ww)).start();

        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException ignored) {
        	WalletKitFactory.shutdownWalletKit(NetworkType.DEVELOPMENT);
        }

	}
	
}
