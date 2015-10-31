# wallet
Using WalletAppKit from Bicoinj, this is a simple implementation that allows people to receive bitcoins

# Pre-requisites
* bitcoinj (0.13.3) from maven
* gradle (2.7.0)

# Running
* `gradle runApp`

# Overview of the code
There are two main classes: `RemoteExchange` and `WalletController`. the former runs on either the main bitcoin network or the testnet network. The latter runs on the regnet, which require the developer to create their own local network
For this local network
* [Install bitcoind]()
* run `bitcoind -regtest`
