import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("usage: java Main <number>");
            System.out.println("example: java Main 1449863225586482579");
            return;
        }
        BigInteger n;
        try {
            n = new BigInteger(args[0]);
        }
        catch (NumberFormatException e) {
            System.out.println("input must be an integer");
            return;
        }
        if (n.compareTo(BigInteger.TWO) < 0) {
            System.out.println("number must be greater than 1");
            return;
        }
        System.out.println("input number: " + n);
        System.out.println();
        CanonicalFactor canonFactor = new CanonicalFactor();
        canonFactor.findCanonicalFactorization(n);
        System.out.println();
        canonFactor.printResult();
    }
}