package main;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class StockExchangeApp {

	public static void main(String[] args) {
		LinkedBlockingQueue<TradeOrder> queue = new LinkedBlockingQueue<>();

		Client s1 = new Seller(queue, "Adi", new TradeOrder("Nok", 50, 4.6));
		Client s2 = new Seller(queue, "Ghita", new TradeOrder("Nok", 300, 4.5));
		Client b1 = new Buyer(queue, "Lili", new TradeOrder("Nok", 50, 4.6));
		Client b2 = new Buyer(queue, "Lola", new TradeOrder("Nok", 175, 4.5));
		Client b3 = new Buyer(queue, "Lulu", new TradeOrder("Nok", 50, 4.5));

		try {
			ExecutorService threadPool = Executors.newFixedThreadPool(5);

			threadPool.execute(s1);
			threadPool.execute(s2);
			threadPool.execute(b1);
			threadPool.execute(b2);
			threadPool.execute(b3);
			s2.editClientAction(new TradeOrder("Nok", 500, 4.5));
			b3.editClientAction(new TradeOrder("Nok", 70, 4.5));

			executorServiceShutdown(threadPool);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// no ExecutorService version
		/*
		 * Thread t1 = new Thread(s1); Thread t2 = new Thread(s2);
		 * s2.editClientAction(new TradeOrder("Nok", 500, 4.5)); Thread t3 = new
		 * Thread(b3); Thread t4 = new Thread(b2); Thread t5 = new Thread(b1);
		 * t1.start(); t2.start(); t3.start(); t4.start(); t5.start();
		 *
		 * try { t1.join(); t2.join(); t3.join(); t4.join(); t5.join(); } catch
		 * (InterruptedException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

		System.out.println("Sell offers: " + DAOService.getSellOffers());
		System.out.println("Buy requests: " + DAOService.getBuyRequests());
		System.out.println("Sellers: " + DAOService.getSellers());
		System.out.println("Buyers: " + DAOService.getBuyers());
		System.out.println("Transactions: " + DAOService.getTransactions());
	}

	private static void executorServiceShutdown(ExecutorService executorService) {
		executorService.shutdown();

		try {
			if (!executorService.awaitTermination(800, TimeUnit.MILLISECONDS)) {
				executorService.shutdownNow();
			}
		} catch (InterruptedException e) {
			executorService.shutdownNow();
		}

	}
}
