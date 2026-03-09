import java.math.BigInteger;

public class TrialDiv {
    private static final int[] PRIMES ={2,3,5,7,11,13,17,19,23,29,31,37,41,43,47};
    public static BigInteger trialDivision(BigInteger n) {
        for (int p :PRIMES) {
            BigInteger div = BigInteger.valueOf(p);
            if (n.mod(div).equals(BigInteger.ZERO)) {
                return div;
            }
        }
        return null;
    }
}