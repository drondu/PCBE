package main;

public class TradeOrder {

	private String stockCompany;
	private int stockNumber;
	private double stockPrice;
	private String id;
	private final ShortUniqueIDGenerator idGen = new ShortUniqueIDGenerator();

	public TradeOrder(String stockCompany, int stockNumber, double stockPrice) {
		this.stockCompany = stockCompany;
		this.stockNumber = stockNumber;
		this.stockPrice = stockPrice;
		this.id = idGen.getNewId();
	}

	public boolean canTrade(TradeOrder t) {
		return this.getStockCompany().equals(t.getStockCompany()) && this.getStockPrice() == t.stockPrice;
	}

	public TradeOrder compareToAndReturnMinByStockNb(TradeOrder t) {
		return new TradeOrder(t.getStockCompany(), Math.min(this.stockNumber, t.stockNumber), t.stockPrice);
	}

	public boolean hasSameId(TradeOrder t) {
		return this.getId().equals(t.getId());
	}

	public boolean outOfStock() {
		return this.getStockNumber() == 0;
	}

	public String getStockCompany() {
		return stockCompany;
	}

	public int getStockNumber() {
		return stockNumber;
	}

	public double getStockPrice() {
		return stockPrice;
	}

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return "TradeOrder [stockCompany=" + stockCompany + ", stockNumber=" + stockNumber + ", stockPrice="
				+ stockPrice + "]";
	}

}
