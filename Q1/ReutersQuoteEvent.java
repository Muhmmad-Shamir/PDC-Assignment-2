package Q1;
public class ReutersQuoteEvent extends TradeEvent {
    private double bidPrice;
    private double askPrice;

    public ReutersQuoteEvent(String senderId, long timestamp,
                             double basePrice, double bidPrice,
                             double askPrice) {
        super(senderId, timestamp, basePrice);
        this.bidPrice = bidPrice;
        this.askPrice = askPrice;
    }


@Override
public void dispatch() {
    double spread = askPrice - bidPrice;

    System.out.println("----- Reuters Market Quote -----");
    System.out.println("Sender: " + senderId);
    System.out.println("Timestamp: " + timestamp);
    System.out.println("Base price: " + basePrice);
    System.out.println("Bid price: " + bidPrice);
    System.out.println("Ask price: " + askPrice);
    System.out.println("Bid-ask spread: " + spread);
}

}