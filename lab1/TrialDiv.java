import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class TrialDiv {
    private static final int[] PRIMES ={2,3,5,7,11,13,17,19,23,29,31,37,41,43,47};
    public static List<BigInteger> trialDivision(BigInteger n) {
        List<BigInteger> div =new ArrayList<>();
        BigInteger cur =n;
        for (int p :PRIMES) {
            BigInteger divisor = BigInteger.valueOf(p);
            while (cur.mod(divisor).equals(BigInteger.ZERO)) {
                div.add(divisor);
                cur = cur.divide(divisor);
            }
        }
        if (!cur.equals(BigInteger.ONE)) {
            div.add(cur);
        }
        return div;
    }
}