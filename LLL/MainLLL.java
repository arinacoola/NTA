import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainLLL {
    public static void main(String[] args) throws IOException {
        ReductExp experiment = new ReductExp();
        ResultWr writer =new ResultWr();
        double[] deltas = {0.5, 0.75, 0.90, 0.95, 0.99};
        List<DeltaSummary> mainRes = new ArrayList<>();
        for (double delta :deltas) {
            DeltaSummary summary = experiment.runForDelta(delta, 30, 50, -20, 20, writer);
            mainRes.add(summary);
        }
        writer.writeSummary("lll.csv",mainRes);
        System.out.println("saved to lll.csv");
        int[] sizes = {30, 40, 50, 60};
        double fixedDelta=0.95;
        List<SizeSum> sizeRes = new ArrayList<>();
        for (int size : sizes){
            SizeSum summary = experiment.runForSize(size,fixedDelta, 20, -20, 20);
            sizeRes.add(summary);
        }
        writer.writeSizeSummary("lll_sizes.csv", sizeRes);
        System.out.println("saved to lll_sizes.csv");
    }
}