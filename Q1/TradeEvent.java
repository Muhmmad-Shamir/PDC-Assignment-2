package Q1;
public class TradeEvent {
    protected String senderId;
    protected long timestamp;
    protected double basePrice;

    public TradeEvent(String senderId, long timestamp, double basePrice) {
        this.senderId = senderId;
        this.timestamp = timestamp;
        this.basePrice = basePrice;
    }

    public void dispatch() {
        System.out.println("Sender: " + senderId);
        System.out.println("Timestamp: " + timestamp);
        System.out.println("Base price: " + basePrice);
    }
}