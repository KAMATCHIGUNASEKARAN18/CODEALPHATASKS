import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Stock {
    private String ticker;
    private double price;

    public Stock(String ticker, double price) {
        this.ticker = ticker;
        this.price = price;
    }

    public String getTicker() {
        return ticker;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

class Portfolio {
    private Map<String, Integer> holdings; // Ticker to number of shares

    public Portfolio() {
        holdings = new HashMap<>();
    }

    public void buyStock(String ticker, int quantity) {
        if (quantity <= 0) {
            System.out.println("Quantity must be positive.");
            return;
        }
        holdings.put(ticker, holdings.getOrDefault(ticker, 0) + quantity);
    }

    public void sellStock(String ticker, int quantity) {
        if (quantity <= 0) {
            System.out.println("Quantity must be positive.");
            return;
        }
        if (holdings.containsKey(ticker)) {
            int currentQuantity = holdings.get(ticker);
            if (currentQuantity >= quantity) {
                holdings.put(ticker, currentQuantity - quantity);
                if (holdings.get(ticker) == 0) {
                    holdings.remove(ticker);
                }
            } else {
                System.out.println("Insufficient shares to sell.");
            }
        } else {
            System.out.println("Stock not in portfolio.");
        }
    }

    public double getPortfolioValue(Map<String, Stock> marketData) {
        double totalValue = 0;
        for (Map.Entry<String, Integer> entry : holdings.entrySet()) {
            String ticker = entry.getKey();
            int quantity = entry.getValue();
            Stock stock = marketData.get(ticker);
            if (stock != null) {
                totalValue += stock.getPrice() * quantity;
            }
        }
        return totalValue;
    }

    @Override
    public String toString() {
        return holdings.toString();
    }
}

public class StockTradingPlatform {
    private Map<String, Stock> marketData;
    private Portfolio portfolio;

    public StockTradingPlatform() {
        marketData = new HashMap<>();
        portfolio = new Portfolio();
        // Adding some initial stocks to the market
        marketData.put("AAPL", new Stock("AAPL", 175.0));
        marketData.put("GOOGL", new Stock("GOOGL", 2800.0));
        marketData.put("MSFT", new Stock("MSFT", 310.0));
    }

    public void displayMarketData() {
        System.out.println("Market Data:");
        for (Stock stock : marketData.values()) {
            System.out.printf("%s: $%.2f%n", stock.getTicker(), stock.getPrice());
        }
    }

    public void buyStock(String ticker, int quantity) {
        Stock stock = marketData.get(ticker);
        if (stock != null) {
            portfolio.buyStock(ticker, quantity);
            System.out.printf("Bought %d shares of %s.%n", quantity, ticker);
        } else {
            System.out.println("Stock not found in market data.");
        }
    }

    public void sellStock(String ticker, int quantity) {
        if (marketData.containsKey(ticker)) {
            portfolio.sellStock(ticker, quantity);
            System.out.printf("Sold %d shares of %s.%n", quantity, ticker);
        } else {
            System.out.println("Stock not found in market data.");
        }
    }

    public void displayPortfolio() {
        System.out.println("Portfolio:");
        System.out.println(portfolio);
        System.out.printf("Total Portfolio Value: $%.2f%n", portfolio.getPortfolioValue(marketData));
    }

    public static void main(String[] args) {
        StockTradingPlatform platform = new StockTradingPlatform();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n1. Display Market Data");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. Display Portfolio");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    platform.displayMarketData();
                    break;
                case 2:
                    System.out.print("Enter stock ticker: ");
                    String buyTicker = scanner.nextLine().toUpperCase();
                    System.out.print("Enter quantity: ");
                    int buyQuantity = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    platform.buyStock(buyTicker, buyQuantity);
                    break;
                case 3:
                    System.out.print("Enter stock ticker: ");
                    String sellTicker = scanner.nextLine().toUpperCase();
                    System.out.print("Enter quantity: ");
                    int sellQuantity = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    platform.sellStock(sellTicker, sellQuantity);
                    break;
                case 4:
                    platform.displayPortfolio();
                    break;
                case 5:
                    running = false;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
        scanner.close();
    }
}
