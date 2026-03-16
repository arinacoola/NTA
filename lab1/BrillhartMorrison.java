import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
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

    private static class SmoothRel{
        BigInteger b;
        BigInteger val;
        int[] exp;
        SmoothRel(BigInteger b, BigInteger val, int[] exp) {
            this.b = b;
            this.val = val;
            this.exp = exp;
        }
    }

    private List<Integer> factorBase = new ArrayList<>();
    private List<SmoothRel> smoothRels = new ArrayList<>();

    public void buildFactorBase(BigInteger n) {
        factorBase.clear();
        factorBase.add(-1);
        double ln = Math.log(n.doubleValue());
        double lnln = Math.log(ln);
        double L = Math.exp(Math.sqrt(ln * lnln));
        double a = 1.0 / Math.sqrt(2);
        int upBound = Math.max(2,(int) Math.pow(L, a));
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
        if (p == 2) {
            BigInteger r = n.mod(BigInteger.valueOf(8));
            if (r.equals(BigInteger.ONE) || r.equals(BigInteger.valueOf(7))) {
                return 1;
            }
            if (r.equals(BigInteger.valueOf(3)) || r.equals(BigInteger.valueOf(5))) {
                return -1;
            }
            return 0;
        }
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

    private void saveIfSmooth(BigInteger b, BigInteger val) {
        FactorRes fr = factorOverBase(val);
        if (fr.smooth) {
            for (SmoothRel rel : smoothRels) {
                if (rel.b.equals(b)) {
                    return;
                }
            }
            smoothRels.add(new SmoothRel(b, val, fr.exp));
            System.out.print("B-smooth: " + val + " = ");
            for (int i = 0; i < fr.exp.length; i++) {
                if (fr.exp[i] > 0) {
                    System.out.print(factorBase.get(i) + "^" + fr.exp[i] + " ");
                }
            }
            System.out.println();
        }
    }

    public void generateSequence(BigInteger n,int steps) {
        buildFactorBase(n);
        smoothRels.clear();
        BigInteger sq = sqrt(n);
        BigInteger a0 = sq;
        BigInteger u = a0;
        BigInteger v = BigInteger.ONE;
        BigInteger bMinTwo = BigInteger.ZERO;
        BigInteger bMinOne= BigInteger.ONE;
        BigInteger b0 = a0;
        BigInteger val0 = symmetricMod(b0.multiply(b0), n);
        System.out.println("b0 = " + b0 + ", b0^2 mod n = " + val0);
        saveIfSmooth(b0, val0);
        bMinTwo = bMinOne;
        bMinOne = b0;
        for (int i = 0; i < steps; i++) {
            BigInteger nextV = n.subtract(u.multiply(u)).divide(v);
            BigInteger nextA = sq.add(u).divide(nextV);
            BigInteger nextU = nextA.multiply(nextV).subtract(u);
            BigInteger nextB = nextA.multiply(bMinOne).add(bMinTwo);
            BigInteger val = symmetricMod(nextB.multiply(nextB), n);
            System.out.println("b = " + nextB + ", b^2 mod n = " + val);
            saveIfSmooth(nextB, val);
            bMinTwo = bMinOne;
            bMinOne = nextB;
            u = nextU;
            v = nextV;
        }
    }


    public void printFactorBase() {
        System.out.println("Factor base: " + factorBase);
    }

    public void printSmoothRelations() {
        System.out.println("Saved B-smooth relations: " + smoothRels.size());
        for (int i = 0; i < smoothRels.size(); i++) {
            SmoothRel rel = smoothRels.get(i);
            System.out.println("Relation " + i);
            System.out.println("b = " + rel.b);
            System.out.println("val = " + rel.val);
            System.out.println("exp = " + Arrays.toString(rel.exp));
            System.out.println();
        }
    }

    private int[] mod2Vector(int[] exp) {
        int[] v = new int[exp.length];
        for (int i = 0; i < exp.length; i++) {
            v[i] = exp[i] & 1;
        }
        return v;
    }

    private boolean isZeroVector(int[] v) {
        for (int x : v) {
            if (x != 0) {
                return false;
            }
        }
        return true;
    }

    private boolean sameVector(int[] u, int[] v) {
        if (u.length != v.length) {
            return false;
        }
        for (int i = 0; i < u.length; i++) {
            if (u[i] != v[i]) {
                return false;
            }
        }
        return true;
    }

    private int[] findLinearDependencyGauss(List<int[]> vectors) {
        int rows = vectors.size();
        int cols = vectors.get(0).length;
        int[][] matrix = new int[rows][cols + rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = vectors.get(i)[j] & 1;
            }
            matrix[i][cols + i] = 1;
        }
        int curRow = 0;
        for (int col = 0; col < cols && curRow < rows; col++) {
            int selectRow = -1;
            for (int r = curRow; r < rows; r++) {
                if (matrix[r][col] == 1) {
                    selectRow = r;
                    break;
                }
            }
            if (selectRow == -1) {
                continue;
            }
            if (selectRow != curRow) {
                int[] tmp = matrix[selectRow];
                matrix[selectRow] = matrix[curRow];
                matrix[curRow] = tmp;
            }
            for (int r = 0; r < rows; r++) {
                if (r != curRow && matrix[r][col] == 1) {
                    for (int c = col; c < cols + rows; c++) {
                        matrix[r][c] ^= matrix[curRow][c];
                    }
                }
            }
            curRow++;
        }
        for (int r = 0; r < rows; r++) {
            boolean zeroLeft = true;
            for (int c = 0; c < cols; c++) {
                if (matrix[r][c] != 0) {
                    zeroLeft = false;
                    break;
                }
            }
            if (zeroLeft) {
                int[] dep = new int[rows];
                for (int i = 0; i < rows; i++) {
                    dep[i] = matrix[r][cols + i];
                }
                return dep;
            }
        }
        return null;
    }

    private BigInteger factor(BigInteger n, int[] dep) {
        BigInteger X = BigInteger.ONE;
        int[] totalExp = new int[factorBase.size()];
        for (int i = 0; i < dep.length; i++) {
            if (dep[i] == 1) {
                SmoothRel rel = smoothRels.get(i);
                X = X.multiply(rel.b).mod(n);
                for (int j = 0; j < totalExp.length; j++) {
                    totalExp[j] += rel.exp[j];
                }
            }
        }
        if ((totalExp[0] & 1) != 0) {
            return null;
        }
        BigInteger Y = BigInteger.ONE;
        for (int i = 1; i < totalExp.length; i++) {
            int gamma = totalExp[i] / 2;
            if (gamma == 0) {
                continue;
            }
            BigInteger p = BigInteger.valueOf(factorBase.get(i));
            Y = Y.multiply(p.pow(gamma)).mod(n);
        }
        X = X.mod(n);
        Y = Y.mod(n);
        if (X.equals(Y) || X.equals(n.subtract(Y).mod(n))) {
            return null;
        }
        BigInteger d1 = X.subtract(Y).abs().gcd(n);
        if (!d1.equals(BigInteger.ONE) && !d1.equals(n)) {
            return d1;
        }
        BigInteger d2 = X.add(Y).gcd(n);
        if (!d2.equals(BigInteger.ONE) && !d2.equals(n)) {
            return d2;
        }
        return null;
    }

    public BigInteger findFactor(BigInteger n, int steps) {
        buildFactorBase(n);
        smoothRels.clear();
        BigInteger sq = sqrt(n);
        BigInteger a0 = sq;
        BigInteger u = a0;
        BigInteger v = BigInteger.ONE;
        BigInteger bPrevPrev = BigInteger.ZERO;
        BigInteger bPrev = BigInteger.ONE;
        BigInteger b0 = a0;
        BigInteger val0 = symmetricMod(b0.multiply(b0), n);
        System.out.println("b0 = " + b0 + ", b0^2 mod n = " + val0);
        saveIfSmooth(b0, val0);
        bPrevPrev = bPrev;
        bPrev = b0;
        for (int step = 0; step < steps; step++) {
            BigInteger nextV = n.subtract(u.multiply(u)).divide(v);
            BigInteger nextA = sq.add(u).divide(nextV);
            BigInteger nextU = nextA.multiply(nextV).subtract(u);
            BigInteger nextB = nextA.multiply(bPrev).add(bPrevPrev);
            BigInteger val = symmetricMod(nextB.multiply(nextB), n);
            System.out.println("b = " + nextB + ", b^2 mod n = " + val);
            saveIfSmooth(nextB, val);
            if (!smoothRels.isEmpty()) {
                List<int[]> vectors = new ArrayList<>();
                for (SmoothRel rel : smoothRels) {
                    vectors.add(mod2Vector(rel.exp));
                }
                int last = vectors.size() - 1;
                if (isZeroVector(vectors.get(last))) {
                    int[] dependVector = new int[vectors.size()];
                    dependVector[last] = 1;
                    BigInteger d = factor(n, dependVector);
                    if (d != null) {
                        return d;
                    }
                }
                for (int j = 0; j < last; j++) {
                    if (sameVector(vectors.get(j), vectors.get(last))) {
                        int[] dependVector = new int[vectors.size()];
                        dependVector[j] = 1;
                        dependVector[last] = 1;
                        BigInteger d = factor(n, dependVector);
                        if (d != null) {
                            return d;
                        }
                    }
                }
                if (vectors.size() >= factorBase.size()) {
                    int[] dependVector = findLinearDependencyGauss(vectors);
                    if (dependVector != null) {
                        BigInteger d = factor(n, dependVector);
                        if (d != null) {
                            return d;
                        }
                    }
                }
            }
            bPrevPrev = bPrev;
            bPrev = nextB;
            u = nextU;
            v = nextV;
        }
        return null;
    }
}

