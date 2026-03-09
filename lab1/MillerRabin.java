import java.math.BigInteger;
import  java.util.Random;
public class MillerRabin {
    public static boolean isPrime(BigInteger n,int iter) {
        if (n.compareTo(BigInteger.TWO) < 0) {
            return false;
        }
        if (n.equals(BigInteger.TWO) || n.equals(BigInteger.valueOf(3))) {
            return true;
        }
        if (n.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            return false;
        }
        BigInteger t = n.subtract(BigInteger.ONE);
        int s = 0;
        while (t.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            t = t.divide(BigInteger.TWO);
            s++;
        }
        Random rnd = new Random();
        for (int i = 0; i < iter; i++) {
            BigInteger x;
            do {
                x = new BigInteger(n.bitLength(), rnd);
            }
            while (x.compareTo(BigInteger.TWO) < 0 || x.compareTo(n.subtract(BigInteger.TWO)) > 0);
            if (!x.gcd(n).equals(BigInteger.ONE)) {
                return false;
            }
            BigInteger y = x.modPow(t, n);
            if (y.equals(BigInteger.ONE) || y.equals(n.subtract(BigInteger.ONE))) {
                continue;
            }
            boolean found = false;
            for (int j = 1; j < s; j++) {
                y = y.multiply(y).mod(n);
                if (y.equals(n.subtract(BigInteger.ONE))) {
                    found = true;
                    break;
                }
                if (y.equals(BigInteger.ONE)) {
                    return false;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }

}
