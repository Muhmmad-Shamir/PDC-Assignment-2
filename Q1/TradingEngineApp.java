package Q1;
public class TradingEngineApp {
    public static void main(String[] args) {
        long time = System.currentTimeMillis();

        TradeEvent order = new FIXOrderEvent(
            "FIX-Gateway", time, 10000.0, "ORD-101"
        );

        TradeEvent quote = new ReutersQuoteEvent(
            "Reuters-Gateway", time, 100.0, 99.5, 100.5
        );

        order.dispatch();
        System.out.println();
        quote.dispatch();
    }
}