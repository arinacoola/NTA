import java.math.BigInteger;
public class FloydPollardRho {
    private static BigInteger func(BigInteger x,BigInteger n){
        BigInteger sq =x.multiply(x);
        BigInteger shift = sq.add(BigInteger.ONE);
        return shift.mod(n);
    }
    public static BigInteger findDiv(BigInteger n) {
        if (n.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            return BigInteger.TWO;
        }
        BigInteger x0=BigInteger.TWO;
        BigInteger y0=BigInteger.TWO;
        BigInteger d=BigInteger.ONE;
        while (d.equals(BigInteger.ONE)) {
            x0 = func(x0, n);
            y0 = func(func(y0,n),n);
            BigInteger differ = x0.subtract(y0).abs();
            d=differ.gcd(n);
        }
        if (d.equals(n)) {
            return null;
        }
        return d;
    }
}
