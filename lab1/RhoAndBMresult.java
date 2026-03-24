import java.math.BigInteger;

public class RhoAndBMresult {
    public static void main(String[] args) {
        BigInteger[] numbers = {
                new BigInteger("1495056764861639599")
                /*new BigInteger("1021514194991569"),
                new BigInteger("4000852962116741"),
                new BigInteger("15196946347083"),
                new BigInteger("499664789704823"),
                new BigInteger("269322119833303"),
                new BigInteger("679321846483919"),
                new BigInteger("96267366284849"),
                new BigInteger("61333127792637"),
                new BigInteger("2485021628404193")*/
        };
        for (BigInteger n : numbers) {
            System.out.println();
            System.out.println("number: " + n);
            System.out.println();
            long start1 = System.nanoTime();
            BigInteger d1 = FloydPollardRho.findDiv(n);
            long end1 = System.nanoTime();
            long start2 = System.nanoTime();
            BrillhartMorrison bm = new BrillhartMorrison();
            BigInteger d2 = bm.findFactor(n, 5000);
            long end2 = System.nanoTime();
            System.out.println("Pollard rho divisor: " + d1);
            System.out.printf("Pollard rho time: %.3f ms%n", (end1 - start1) / 1000000.0);
            if (d2 != null && !d2.equals(BigInteger.ONE) && !d2.equals(n)) {
                System.out.println("Brillhart-Morrison divisor: " + d2);
            }
            else {
                System.out.println("Brillhart-Morrison divisor: not found");
            }
            System.out.printf("Brillhart-Morrison time: %.3f ms%n", (end2 - start2) / 1000000.0);
            System.out.println();
        }
    }
}