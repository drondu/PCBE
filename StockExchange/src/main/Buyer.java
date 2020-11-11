package main;

import java.util.concurrent.LinkedBlockingQueue;

public class Buyer extends Client {

	public Buyer(LinkedBlockingQueue<TradeOrder> queue, String name, TradeOrder buyRequest) {
		super.name = name;
		super.tradeOrder = buyRequest;
		super.queue = queue;
		super.id = idGen.getNewId();
	}

	@Override
	public void run() {
		try {
			while (!Thread.currentThread().isInterrupted()) {
				System.out.println("Buyer: " + Thread.currentThread().getId() + " is running");
				// throws InterruptedException if other thread calls interrupt() method
				DAOService.addBuyer(this);

				processOffers();
			}
			// break the while loop if interrupted exception occurred
		} catch (Exception e) {
			Thread.currentThread().interrupt();
			System.out.println("Buyer: " + Thread.currentThread().getId() + " stopped");
		}

	}

	private void trade(Seller seller, Buyer buyer) throws InterruptedException {
		Transaction transaction = createTransaction(seller, buyer);
		DAOService.addTransactionToConfirmedList(transaction);
		System.out.println("Transaction: " + transaction);

		TradeOrder buyerTO = buyer.getTradeOrder();
		TradeOrder sellerTO = seller.getTradeOrder();

		buyer.trade(sellerTO);
		seller.trade(buyerTO);

		DAOService.updateSeller(seller);
		DAOService.updateBuyer(buyer);
	}

	private void processOffers() throws InterruptedException {
		System.out.println("Buyer  " + getClientName() + " on thread " + Thread.currentThread().getId()
				+ "  has posted: " + tradeOrder.toString());
		TradeOrder taken = queue.take();
		if (tradeOrder.canTrade(taken)) {
			Seller seller = (Seller) DAOService.getSellers().stream().filter(el -> taken.hasSameId(el.getTradeOrder()))
					.findAny().orElse(null);
			trade(seller, this);
		} else {
			queue.offer(taken);
			queue.notifyAll();
		}
		while (!queue.peek().equals(tradeOrder)) {
			wait();
		}
	}

}
