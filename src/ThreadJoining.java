/*
* Joining threads
* */

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThreadJoining {
    public static void main(String[] args) throws InterruptedException {
        List<Long> inputNumbers = Arrays.asList(100L, 234L, 56L, 97L, 184L);

        List<FactorialThread> threads = new ArrayList<>();

        for(long inputNum: inputNumbers) {
            threads.add(new FactorialThread(inputNum));
        }

        for (Thread thread: threads) {
            //thread.setDaemon(true);
            thread.start();
        }

        for (Thread thread: threads) {
            // Thread.join(timeout) -> after the timeout time the thread will return.
            thread.join(2000);
        }

        for (int i=0; i < inputNumbers.size(); i++) {
            FactorialThread factorialThread = threads.get(i);
            if (factorialThread.isFinished()) {
                System.out.println("Factorial of " + inputNumbers.get(i) + " is " + factorialThread.getResult());
            } else {
                System.out.println("The calculation for " + inputNumbers.get(i) + " is still in progress");
            }
        }
    }

    private static class FactorialThread extends Thread{
        private final long inputNumber;
        private BigInteger result = BigInteger.ZERO;
        private boolean isFinished = false;

        public FactorialThread(long inputNumber) {
            this.inputNumber = inputNumber;
        }

        @Override
        public void run() {
            this.result = factorial(inputNumber);
            this.isFinished = true;
        }

        private BigInteger factorial(long inputNumber) {
            BigInteger tempResult = BigInteger.ONE;

            for (long i = inputNumber; i > 0; i--) {
                tempResult = tempResult.multiply(new BigInteger((Long.toString(i))));
            }
            return tempResult;
        }

        public boolean isFinished() {
            return isFinished;
        }

        public BigInteger getResult() {
            return result;
        }
    }
}
