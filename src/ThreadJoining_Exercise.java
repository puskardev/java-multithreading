import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ThreadJoining_Exercise {
    public static void main(String[] args) throws InterruptedException {
        BigInteger getResult = calculateResult(new BigInteger("2"), new BigInteger("3"), new BigInteger("4"), new BigInteger("5"));
        System.out.println("Final result is " + getResult);
    }
    public static BigInteger calculateResult(BigInteger base1, BigInteger power1, BigInteger base2, BigInteger power2) throws InterruptedException {
        BigInteger result = BigInteger.ZERO;
        /*
            Calculate result = ( base1 ^ power1 ) + (base2 ^ power2).
            Where each calculation in (..) is calculated on a different thread
        */
        List<PowerCalculatingThread> threads = new ArrayList<>();
        threads.add(new PowerCalculatingThread(base1, power1));
        threads.add(new PowerCalculatingThread(base2, power2));

        for (PowerCalculatingThread thread: threads) {
            thread.start();
        }

        for (PowerCalculatingThread thread: threads) {
            thread.join();
        }
        
        for (PowerCalculatingThread thread: threads) {
            result = result.add(thread.getResult()) ;
        }

        return result;
    }

    private static class PowerCalculatingThread extends Thread {
        private BigInteger result = BigInteger.ONE;
        private BigInteger base;
        private BigInteger power;

        public PowerCalculatingThread(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
           /*
           Implement the calculation of result = base ^ power
           */
            this.result = calculate(base, power);

        }

        public BigInteger calculate(BigInteger base, BigInteger power) {
            return base.pow(power.intValue());
        }

        public BigInteger getResult() { return result; }
    }
}
