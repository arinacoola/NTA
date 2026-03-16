import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CanonicalFactor {
    private final List<BigInteger> result = new ArrayList<>();
    private LocalDateTime start;
    private LocalDateTime end;
    private boolean success;

    public List<BigInteger> findCanonicalFactorization(BigInteger n) {
        result.clear();
        success = false;
        start = LocalDateTime.now();
        System.out.println("starting the algorithm: " + start);
        System.out.println("input number: " + n);
        System.out.println();
        BigInteger cur = n;
        if (n.compareTo(BigInteger.TWO) < 0) {
            System.out.println("factorization is defined for integers greater than 1");
            return new ArrayList<>();
        }
        while (cur.compareTo(BigInteger.ONE) > 0) {
            if (MillerRabin.isPrime(cur, 10)) {
                result.add(cur);
                System.out.println("Miller-Rabin test");
                System.out.println("number " + cur + " is prime");
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
                continue;
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
        end = LocalDateTime.now();
        System.out.println("end of algorithm: " + end);
        System.out.println("duration: " + java.time.Duration.between(start, end));
        return new ArrayList<>(result);
    }

    private BigInteger findByTrialDiv(BigInteger n) {
        LocalDateTime start = LocalDateTime.now();
        BigInteger divisor = TrialDiv.trialDivision(n);
        LocalDateTime end = LocalDateTime.now();
        if (divisor != null) {
            System.out.println("the method of trial division");
            System.out.println("start time: " + start);
            System.out.println("end time: " + end);
            System.out.println("divisor found: " + divisor);
            System.out.println("new number n := n / a = " + n.divide(divisor));
            System.out.println();
        }
        return divisor;
    }

    private BigInteger findByPollardRho(BigInteger n) {
        LocalDateTime start = LocalDateTime.now();
        BigInteger divisor = FloydPollardRho.findDiv(n);
        LocalDateTime end = LocalDateTime.now();
        if (divisor != null && !divisor.equals(BigInteger.ONE) && !divisor.equals(n)) {
            System.out.println("rho-Pollard's method");
            System.out.println("start time: " + start);
            System.out.println("end time: " + end);
            System.out.println("divisor found: " + divisor);
            System.out.println("new number n := n / a = " + n.divide(divisor));
            System.out.println();
            return divisor;
        }
        return null;
    }

    private BigInteger findByBM(BigInteger n) {
        BrillhartMorrison bm = new BrillhartMorrison();
        LocalDateTime start = LocalDateTime.now();
        BigInteger divisor = bm.findFactor(n, 250);
        LocalDateTime end = LocalDateTime.now();
        if (divisor != null && !divisor.equals(BigInteger.ONE) && !divisor.equals(n)) {
            System.out.println("the Brillhart-Morrison method");
            System.out.println("start time: " + start);
            System.out.println("end time: " + end);
            System.out.println("divisor found: " + divisor);
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