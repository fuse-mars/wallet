package com.nshimiye.bitcoin_java.services;

import java.util.List;

import org.bitcoinj.core.AbstractWalletEventListener;
import org.bitcoinj.core.Address;
import org.bitcoinj.core.AddressFormatException;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.InsufficientMoneyException;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionConfidence;
import org.bitcoinj.core.Wallet;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.script.Script;

/**
 * Userful method that the walletManager will use to compute needed tasks
 * tasks: 
 * 1. send coins
 * 2. prints a message if somebody sends coins to an address of the wallet in charge
 * 3. print all transactions
 * This class also listens to changes on the wallet
 * @author mars
 * Alias BitcoinManager
 */
public class WalletKitWorker extends AbstractWalletEventListener implements Runnable {
	private final WalletAppKit kit;
	private final Wallet wallet;
	public WalletKitWorker(WalletAppKit walletKit){
		walletKit.wallet().addEventListener(this);
		this.wallet = walletKit.wallet();
		this.kit = walletKit;
		
	}
	/**
	 * 
	 * @param coinAmount smallest value taken is 1/100 000 000 = 0.00000001
	 * @param address
	 * @return true if bitcoin has been sent, false otherwise
	 */
	public String sendCoinToAddress(String coinAmount, String address){
		String amount = null;
		try {
	        // How much coins do we want to send?
	        // The Coin class represents a monetary Bitcoin value.
	        // We use the parseCoin function to simply get a Coin instance from a simple String.
	        
			Coin value = Coin.parseCoin(coinAmount);
			Address to = new Address(wallet.getParams(), address);
			
			
            Wallet.SendResult result = wallet.sendCoins(kit.peerGroup(), to, value);
            System.out.println("coins sent. transaction hash: " + result.tx.getHashAsString());
            // you can use a block explorer like https://www.biteasy.com/ to inspect the transaction with the printed transaction hash. 
            amount = value.toFriendlyString();
		} catch (InsufficientMoneyException e) {
            System.out.println("Not enough coins in your wallet. Missing " + e.missing.getValue() + " satoshis are missing (including fees)");
            System.out.println("Failed to send money to: " + wallet.currentReceiveAddress().toString());
            
		} catch(AddressFormatException e){
			System.out.println("Address seems invalid: " + e.getMessage());
			e.printStackTrace();
		}
		return amount;
		}
	/**
	 * Create a new receive address
	 * @return
	 */
	public String createAddress(){
       Address fa = wallet.freshReceiveAddress();
       String address = fa.toString();
       return address;
	}
	/**
	 * return address if key os owned by the current wallet
	 * @param key
	 * @return
	 */
	public String createAddress(ECKey key){
        return null; //not implemented
	}
	/**
	 * keys are added to the wallet directly
	 */
	public void generateKeys(int init){

		for (int i = 0; i < init; i++) {
			//wallet.addKey(key);
			// create a key and add it to the wallet
			wallet.importKey(new ECKey());
		}
	}
	
	/**
	 * 
	 * @param walletFile
	 * @return
	 */
	public List<ECKey> getWalletKeys(){
		List<ECKey> keys;
		
		keys = wallet.getImportedKeys();
		
		return keys;
	}

	// override everything from the event listener class
	// WalletListener wListener = new WalletListener();
	// kit.wallet().addEventListener(wListener);
	@Override
    public void onCoinsReceived(Wallet wallet, Transaction tx, Coin prevBalance, Coin newBalance) {
		System.out.println("-----> coins resceived: " + tx.getHashAsString());
        System.out.println("received: " + tx.getValue(wallet));
		System.out.printf("\ncoins received: keychain = %d, balance = %s, [prev= %s, new= %s]\n", wallet.getKeychainSize(), wallet.getBalance().toFriendlyString(), prevBalance.toFriendlyString(), newBalance.toFriendlyString());
    }

    @Override
    public void onTransactionConfidenceChanged(Wallet wallet, Transaction tx) {
        System.out.println("-----> confidence changed: " + tx.getHashAsString());
        TransactionConfidence confidence = tx.getConfidence();
        System.out.println("new block depth: " + confidence.getDepthInBlocks());
    }

    @Override
    public void onCoinsSent(Wallet wallet, Transaction tx, Coin prevBalance, Coin newBalance) {
        System.out.println("coins sent");
    }

    @Override
    public void onReorganize(Wallet wallet) {
    }

    @Override
    public void onWalletChanged(Wallet wallet) {
    	System.out.printf("\nwallet state change: keychain = %d, balance = %s\n", wallet.getKeychainSize(), wallet.getBalance().toFriendlyString());
    }

    @Override
    public void onKeysAdded(List<ECKey> keys) {
        System.out.println("new key added");
    }

    @Override
    public void onScriptsChanged(Wallet wallet, List<Script> scripts, boolean isAddingScripts) {
        System.out.println("new script added");
    }
    
	@Override
	public void run() {
		String address = this.createAddress();
		System.out.println("running on separate thread, send coins to: " + address);
	}
    
  
}
