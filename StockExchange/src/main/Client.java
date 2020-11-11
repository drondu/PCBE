package main;

import java.util.concurrent.LinkedBlockingQueue;

public abstract class Client implements Runnable {

	protected String name;
	protected TradeOrder tradeOrder;
	protected LinkedBlockingQueue<TradeOrder> queue = null;
	protected String id;
	protected final ShortUniqueIDGenerator idGen = new ShortUniqueIDGenerator();

	public void editClientAction(TradeOrder tradeOrder) throws InterruptedException {
		this.tradeOrder = tradeOrder;
	}

	public Transaction createTransaction(Seller seller, Buyer buyer) {
		return new Transaction(buyer, seller,
				seller.getTradeOrder().compareToAndReturnMinByStockNb(buyer.getTradeOrder()));
	}

	protected void trade(TradeOrder t) throws InterruptedException {
		int here = this.tradeOrder.getStockNumber();
		int there = t.getStockNumber();
		if (here > there) {
			editClientAction(
					new TradeOrder(this.tradeOrder.getStockCompany(), here - there, tradeOrder.getStockPrice()));
		} else {
			editClientAction(new TradeOrder(this.tradeOrder.getStockCompany(), 0, tradeOrder.getStockPrice()));
		}
	}

	public boolean hasSameId(Client c) {
		return this.getId().equals(c.getId());
	}

	public String getClientName() {
		return name;
	}

	public String getId() {
		return id;
	}

	public TradeOrder getTradeOrder() {
		return tradeOrder;
	}

	@Override
	public String toString() {
		return "Client [name=" + name + ", tradeOrder=" + tradeOrder + ", id=" + id + "]";
	}

}