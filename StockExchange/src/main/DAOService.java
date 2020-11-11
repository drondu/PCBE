package main;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class DAOService {

	private static final List<Transaction> transactions = new CopyOnWriteArrayList<Transaction>();
	private static final List<Client> sellers = new CopyOnWriteArrayList<>();
	private static final List<Client> buyers = new CopyOnWriteArrayList<>();

	public static void addTransactionToConfirmedList(Transaction transaction) {
		transactions.add(transaction);
	}

	public static void addSeller(Seller seller) {
		addClient(seller, sellers);
	}

	public static void addBuyer(Buyer buyer) {
		addClient(buyer, buyers);
	}

	public static void updateSeller(Seller seller) {
		updateClient(seller, sellers);
	}

	public static void updateBuyer(Buyer buyer) {
		updateClient(buyer, buyers);
	}

	private static void addClient(Client client, List<Client> list) {
		list.add(client);
	}

	private static void updateClient(Client client, List<Client> list) {
		Optional<Client> findFirst = list.stream().filter(el -> el.hasSameId(client)).findFirst();
		if (findFirst.isPresent()) {
			Client toUpdate = findFirst.get();
			list.set(list.indexOf(toUpdate), client);
		}
	}

	public static List<TradeOrder> getSellOffers() {
		return getTradeOrders(sellers);
	}

	public static List<TradeOrder> getBuyRequests() {
		return getTradeOrders(buyers);
	}

	private static List<TradeOrder> getTradeOrders(List<Client> list) {
		List<TradeOrder> collect = list.stream().map(el -> el.getTradeOrder()).collect(Collectors.toList());
		return removeEmptyStocks(collect);
	}

	public static List<Client> getSellers() {
		return sellers;
	}

	public static List<Client> getBuyers() {
		return buyers;
	}

	private static List<TradeOrder> removeEmptyStocks(List<TradeOrder> list) {
		List<TradeOrder> listOfNulls = list.stream().filter(el -> el.outOfStock()).collect(Collectors.toList());
		list.removeAll(listOfNulls);
		return list;
	}

	public static List<Transaction> getTransactions() {
		return transactions;
	}
}
