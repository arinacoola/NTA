import java.math.BigInteger;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        BigInteger n = new BigInteger("1449863225586482579");
        CanonicalFactor canonFactor = new CanonicalFactor();
        List<BigInteger> factorization = canonFactor.findCanonicalFactorization(n);
        System.out.println();
        canonFactor.printResult();
    }
}
