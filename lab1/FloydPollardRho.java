import java.math.BigInteger;
public class FloydPollardRho {
    private static BigInteger func(BigInteger x,BigInteger n){
        return x.multiply(x).add(BigInteger.ONE).mod(n);
    }
    public static BigInteger findDiv(BigInteger n) {
        if (n.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            return BigInteger.TWO;
        }
        for (BigInteger start = BigInteger.TWO; start.compareTo(BigInteger.valueOf(20)) <= 0; start = start.add(BigInteger.ONE)) {
            BigInteger x = start;
            BigInteger y = start;
            BigInteger d = BigInteger.ONE;
            while (d.equals(BigInteger.ONE)) {
                x = func(x, n);
                y = func(func(y, n), n);
                d = x.subtract(y).abs().gcd(n);
                if (x.equals(y)) {
                    break;
                }
                if (!d.equals(BigInteger.ONE)) {
                    break;
                }
            }
            if (!d.equals(BigInteger.ONE) && !d.equals(n)) {
                return d;
            }
        }
        return null;
    }
}

