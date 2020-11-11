package main;

import java.util.concurrent.LinkedBlockingQueue;

public class Seller extends Client {

	public Seller(LinkedBlockingQueue<TradeOrder> queue, String name, TradeOrder sellOffer) {
		super.name = name;
		super.tradeOrder = sellOffer;
		super.queue = queue;
		super.id = idGen.getNewId();
	}

	@Override
	public void run() {
		try {
			while (!Thread.currentThread().isInterrupted()) {
				System.out.println("Seller: " + Thread.currentThread().getId() + " is running");
				// throws InterruptedException if other thread calls interrupt() method
				DAOService.addSeller(this);

				postSellOffer();
			}
			// break the while loop if interrupted exception occurred
		} catch (Exception e) {
			Thread.currentThread().interrupt();
			System.out.println("Seller: " + Thread.currentThread().getId() + " stopped");
		}
	}

	@Override
	protected void trade(TradeOrder t) throws InterruptedException {
		super.trade(t);
		if (this.tradeOrder.getStockNumber() != 0) {
			postSellOffer();
		}
	}

	protected void postSellOffer() throws InterruptedException {
		System.out.println("Seller " + getClientName() + " on thread " + Thread.currentThread().getId()
				+ "  has posted: " + getTradeOrder().toString());
		TradeOrder sellOffer = tradeOrder;
		if (queue.offer(sellOffer)) {
			queue.notifyAll();
		}
		wait();
	}

}
