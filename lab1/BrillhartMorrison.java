import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class BrillhartMorrison {
    private static class FactorRes {
        boolean smooth;
        int[] exp;
        FactorRes(boolean smooth, int[] exp) {
            this.smooth = smooth;
            this.exp = exp;
        }
    }
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

    private BigInteger symmetricMod(BigInteger x, BigInteger n) {
        BigInteger smtrcX = x.mod(n);
        if (smtrcX.compareTo(n.divide(BigInteger.TWO)) > 0) {
            smtrcX = smtrcX.subtract(n);
        }
        return smtrcX;
    }

    private FactorRes factorOverBase(BigInteger val) {
        BigInteger cur = val;
        int[] exp = new int[factorBase.size()];
        if (cur.signum() < 0) {
            exp[0] = 1;
            cur = cur.negate();
        }
        for (int i = 1; i < factorBase.size(); i++) {
            BigInteger p = BigInteger.valueOf(factorBase.get(i));
            while (cur.mod(p).equals(BigInteger.ZERO)) {
                exp[i]++;
                cur = cur.divide(p);
            }
        }
        boolean smooth = cur.equals(BigInteger.ONE);
        return new FactorRes(smooth, exp);
    }

    public void generSequence(BigInteger n,int steps) {
        buildFactorBase(n);
        BigInteger sq = sqrt(n);
        BigInteger a0 = sq;
        BigInteger u = a0;
        BigInteger v = BigInteger.ONE;
        BigInteger bMinTwo = BigInteger.ZERO;
        BigInteger bMinOne= BigInteger.ONE;
        for (int i = 0; i < steps; i++) {
            BigInteger nextV = n.subtract(u.multiply(u)).divide(v);
            BigInteger nextA = sq.add(u).divide(nextV);
            BigInteger nextU = nextA.multiply(nextV).subtract(u);
            BigInteger nextB = nextA.multiply(bMinOne).add(bMinTwo);
            BigInteger val = symmetricMod(nextB.multiply(nextB), n);
            FactorRes fr = factorOverBase(val);
            if (fr.smooth) {
                System.out.print("B-smooth: " + val + " = ");
                for (int j = 0; j < fr.exp.length; j++) {
                    if (fr.exp[j] > 0) {
                        System.out.print(factorBase.get(j) + "^" + fr.exp[j] + " ");
                    }
                }
                System.out.println();
            }
            System.out.println("b = " + nextB + ", b^2 mod n = " + val);
            bMinTwo = bMinOne;
            bMinOne = nextB;
            u = nextU;
            v = nextV;
        }
    }
}
