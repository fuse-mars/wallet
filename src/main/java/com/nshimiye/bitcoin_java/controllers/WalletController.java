package com.nshimiye.bitcoin_java.controllers;

import org.bitcoinj.kits.WalletAppKit;

import com.nshimiye.bitcoin_java.services.WalletKitFactory;
import com.nshimiye.bitcoin_java.services.WalletKitWorker;
import com.nshimiye.bitcoin_java.utils.NetworkType;

public class WalletController {

	public static void main(String[] args) {

		WalletAppKit kit = WalletKitFactory
				.getWalletKit(NetworkType.DEVELOPMENT);

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
