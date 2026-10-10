package Q1;
public class FIXOrderEvent extends TradeEvent {
    private String clOrdId;

    public FIXOrderEvent(String senderId, long timestamp,
                         double basePrice, String clOrdId) {
        super(senderId, timestamp, basePrice);
        this.clOrdId = clOrdId;
    }


@Override
public void dispatch() {
    double fee = basePrice * 0.0015;
    double totalPrice = basePrice + fee;

    System.out.println("----- FIX Stock Purchase -----");
    System.out.println("Sender: " + senderId);
    System.out.println("Timestamp: " + timestamp);
    System.out.println("Order ID: " + clOrdId);
    System.out.println("Base price: " + basePrice);
    System.out.println("Transaction fee: " + fee);
    System.out.println("Total purchase amount: " + totalPrice);
    System.out.println("Simulated purchase processed.");
}

}