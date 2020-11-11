package main;

public class Transaction {
	private Buyer buyer;
	private Seller seller;
	private TradeOrder tradeOrder;

	public Transaction(Buyer buyer, Seller seller, TradeOrder tradeOrder) {
		this.buyer = buyer;
		this.seller = seller;
		this.tradeOrder = tradeOrder;
	}

	public Buyer getBuyer() {
		return buyer;
	}

	public Seller getSeller() {
		return seller;
	}

	public TradeOrder getTradeOrder() {
		return tradeOrder;
	}

	@Override
	public String toString() {
		return "Transaction [buyer=" + buyer.getClientName() + ", seller=" + seller.getClientName() + ", tradeOrder="
				+ tradeOrder + "]";
	}

}
