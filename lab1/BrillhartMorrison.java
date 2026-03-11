import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class BrillhartMorrison {
    List<Integer> factorBase = new ArrayList<>();
    public void buildFactorBase(BigInteger n) {
        factorBase.clear();
        factorBase.add(-1);
        double ln = Math.log(n.doubleValue());
        double lnln = Math.log(ln);
        double L = Math.exp(Math.sqrt(ln * lnln));
        double a = 1.0 / Math.sqrt(2);
        int upBound = (int) Math.pow(L, a);
        for (int p = 2; p <= upBound; p++) {
            if (!isPrime(p)) {
                continue;
            }
            if (p == 2) {
                continue;
            }
            if (legendre(n, p) == 1) {
                factorBase.add(p);
            }
        }
    }
    private boolean isPrime(int x) {
        if (x < 2) {
            return false;
        }
        for (int i= 2; i *i <= x; i++) {
            if (x % i == 0) {
                return false;
            }
        }
        return true;
    }

    private int legendre(BigInteger n,int p) {
        BigInteger mod = BigInteger.valueOf(p);
        BigInteger pow = BigInteger.valueOf((p - 1) / 2);
        BigInteger val = n.mod(mod).modPow(pow, mod);
        if (val.equals(BigInteger.ZERO)) {
            return 0;
        }
        if (val.equals(BigInteger.ONE)) {
            return 1;
        }
        if (val.equals(mod.subtract(BigInteger.ONE))) {
            return -1;
        }
        return 0;
    }

    private BigInteger sqrt(BigInteger n) {
        BigInteger l = BigInteger.ZERO;
        BigInteger r = n;
        BigInteger reslt = BigInteger.ZERO;
        while (l.compareTo(r) <= 0) {
            BigInteger mid = l.add(r).divide(BigInteger.TWO);
            BigInteger sq = mid.multiply(mid);
            if (sq.compareTo(n) <= 0) {
                reslt = mid;
                l = mid.add(BigInteger.ONE);
            }
            else {
                r = mid.subtract(BigInteger.ONE);
            }
        }
        return reslt;
    }
}
