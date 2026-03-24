import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CanonicalFactor {
    private final List<BigInteger> result = new ArrayList<>();
    private LocalDateTime algorithmStartTime;
    private LocalDateTime algorithmEndTime;
    private boolean success;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss.SSS");

    public List<BigInteger> findCanonicalFactorization(BigInteger n) {
        result.clear();
        success = false;
        long totalStartNano = System.nanoTime();
        algorithmStartTime = LocalDateTime.now();
        System.out.println("algorithm started at: " + formatTime(algorithmStartTime));
        System.out.println("input number: " + n);
        System.out.println();
        BigInteger cur = n;
        if (n.compareTo(BigInteger.TWO) < 0) {
            System.out.println("factorization is defined for integers greater than 1");
            algorithmEndTime = LocalDateTime.now();
            long totalEndNano = System.nanoTime();
            printAlgorithmFinish(totalStartNano, totalEndNano);
            return new ArrayList<>();
        }
        while (cur.compareTo(BigInteger.ONE) > 0) {
            if (MillerRabin.isPrime(cur, 10)) {
                LocalDateTime foundTime = LocalDateTime.now();
                result.add(cur);
                System.out.println("Miller-Rabin test");
                System.out.println("number " + cur + " is prime");
                System.out.println("prime number identified at: " + formatTime(foundTime));
                System.out.println("add " + cur + " to the result");
                System.out.println();
                success = true;
                break;
            }
            BigInteger divisor = findByTrialDiv(cur);
            if (divisor != null) {
                result.add(divisor);
                cur = cur.divide(divisor);
                continue;
            }
            divisor = findByPollardRho(cur);
            if (divisor != null) {
                result.add(divisor);
                cur = cur.divide(divisor);
                //після знайденого дільника методом Полларда не повертаємось на початок повного циклу, а переходимо до B-M.(на парі цих змін не було)
                if (MillerRabin.isPrime(cur, 10)) {
                    LocalDateTime foundTime = LocalDateTime.now();
                    result.add(cur);
                    System.out.println("Miller-Rabin test");
                    System.out.println("number " + cur + " is prime");
                    System.out.println("prime number identified at: " + formatTime(foundTime));
                    System.out.println("add " + cur + " to the result");
                    System.out.println();
                    success = true;
                    break;
                }
                BigInteger bmDivisor = findByBM(cur);
                if (bmDivisor != null) {
                    result.add(bmDivisor);
                    cur = cur.divide(bmDivisor);
                    continue;
                }
                finishWithFail();
                break;
            }
            divisor = findByBM(cur);
            if (divisor != null) {
                result.add(divisor);
                cur = cur.divide(divisor);
                continue;
            }
            finishWithFail();
            break;
        }
        Collections.sort(result);
        algorithmEndTime = LocalDateTime.now();
        long totalEndNano = System.nanoTime();
        printAlgorithmFinish(totalStartNano, totalEndNano);
        return new ArrayList<>(result);
    }

    private BigInteger findByTrialDiv(BigInteger n) {
        long startNano = System.nanoTime();
        BigInteger divisor = TrialDiv.trialDivision(n);
        long endNano = System.nanoTime();
        if (divisor != null) {
            LocalDateTime foundTime = LocalDateTime.now();
            System.out.println("the method of trial division");
            System.out.println("divisor found: " + divisor);
            System.out.println("found at: " + formatTime(foundTime));
            System.out.printf("method duration: %.3f ms%n", nanoToMs(endNano - startNano));
            System.out.println("new number n := n / a = " + n.divide(divisor));
            System.out.println();
        }
        return divisor;
    }

    private BigInteger findByPollardRho(BigInteger n) {
        long startNano = System.nanoTime();
        BigInteger divisor = FloydPollardRho.findDiv(n);
        long endNano = System.nanoTime();
        if (divisor != null && !divisor.equals(BigInteger.ONE) && !divisor.equals(n)) {
            LocalDateTime foundTime = LocalDateTime.now();
            System.out.println("rho-Pollard's method");
            System.out.println("divisor found: " + divisor);
            System.out.println("found at: " + formatTime(foundTime));
            System.out.printf("method duration: %.3f ms%n", nanoToMs(endNano - startNano));
            System.out.println("new number n := n / a = " + n.divide(divisor));
            System.out.println();
            return divisor;
        }
        return null;
    }

    private BigInteger findByBM(BigInteger n) {
        BrillhartMorrison bm = new BrillhartMorrison();
        long startNano = System.nanoTime();
        BigInteger divisor = bm.findFactor(n, 500);
        long endNano = System.nanoTime();
        if (divisor != null && !divisor.equals(BigInteger.ONE) && !divisor.equals(n)) {
            LocalDateTime foundTime = LocalDateTime.now();
            System.out.println("the Brillhart-Morrison method");
            System.out.println("divisor found: " + divisor);
            System.out.println("found at: " + formatTime(foundTime));
            System.out.printf("method duration: %.3f ms%n", nanoToMs(endNano - startNano));
            System.out.println("new number n := n / a = " + n.divide(divisor));
            System.out.println();
            return divisor;
        }
        return null;
    }

    private void  finishWithFail() {
        System.out.println("I can't find the canonical factorization of the number:(");
        System.out.println();
    }

    private void printAlgorithmFinish(long totalStartNano, long totalEndNano) {
        System.out.println("algorithm finished at: " + formatTime(algorithmEndTime));
        System.out.printf("total algorithm duration: %.3f ms%n", nanoToMs(totalEndNano - totalStartNano));
        System.out.println();
    }

    private String formatTime(LocalDateTime time) {
        return time.format(FORMATTER);
    }

    private double nanoToMs(long nano) {
        return nano / 1000000.0;
    }

    public void printResult() {
        if (!success) {
            System.out.println("factorization was not completed");
            return;
        }
        System.out.println("canonical decomposition: ");
        for (int i = 0; i < result.size(); i++) {
            if (i > 0) {
                System.out.print(" * ");
            }
            System.out.print(result.get(i));
        }
        System.out.println();
    }

}