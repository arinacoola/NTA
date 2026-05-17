public class BasisShortener{
    private double[][] basis;
    private double[][] orthogonal;
    private double[][] mu;
    private double[] squaredNorms;

    public ReductionStats reduce(double[][] inputBasis, double delta) {
        long start = System.nanoTime();
        basis=copyMatrix(inputBasis);
        int n =basis.length;
        int swaps = 0;
        computeGramSchmidt();
        int k = 1;
        while (k < n){
            for (int j = k - 1;j >= 0;j--) {
                if (Math.abs(mu[k][j]) >0.5) {
                    long r = Math.round(mu[k][j]);
                    for (int col = 0;col < basis[k].length;col++) {
                        basis[k][col] -= r * basis[j][col];
                    }
                    computeGramSchmidt();
                }
            }
            double l=squaredNorms[k];
            double r =(delta -mu[k][k - 1] *mu[k][k - 1])*squaredNorms[k - 1];
            if (l < r) {
                swapRows(k,k - 1);
                swaps++;
                computeGramSchmidt();
                k = Math.max(k - 1, 1);
            }
            else {
                k++;
            }
        }
        long end = System.nanoTime();
        double hadamard = countHadamardRatio(basis);
        double firstNorm = vectorNorm(basis[0]);
        return new ReductionStats(basis, end - start, swaps, hadamard, firstNorm
        );
    }

    private void computeGramSchmidt() {
        int n = basis.length;
        int m =basis[0].length;
        orthogonal=new double[n][m];
        mu=new double[n][n];
        squaredNorms = new double[n];
        for (int i = 0;i < n;i++) {
            orthogonal[i]=basis[i].clone();
            for (int j = 0;j < i;j++) {
                mu[i][j] = dot(basis[i],orthogonal[j]) / dot(orthogonal[j],orthogonal[j]);
                for (int col=0;col < m; col++) {
                    orthogonal[i][col] -=mu[i][j] * orthogonal[j][col];
                }
            }
            squaredNorms[i]=dot(orthogonal[i],orthogonal[i]);
        }
    }

    private void swapRows(int i,int j) {
        double[] tmp =basis[i];
        basis[i] = basis[j];
        basis[j] =tmp;
    }

    private double dot(double[] a,double[] b) {
        double sum = 0.0;
        for (int i = 0;i < a.length; i++) {
            sum += a[i]*b[i];
        }
        return sum;
    }

    private double vectorNorm(double[] vector) {
        return Math.sqrt(dot(vector, vector));
    }

    private double[][] copyMatrix(double[][] matrix) {
        double[][] copy =new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            copy[i] = matrix[i].clone();
        }
        return copy;
    }

    private double countHadamardRatio(double[][] matrix) {
        double vol=Math.abs(determinant(matrix));
        double prod =1.0;
        for (double[] row :matrix){
            prod *= vectorNorm(row);
        }
        if (prod == 0.0) {
            return 0.0;
        }
        return Math.pow(vol/prod, 1.0 / matrix.length);
    }

    private double determinant(double[][] matrix) {
        int n = matrix.length;
        double[][] a= copyMatrix(matrix);
        double det = 1.0;
        for (int i = 0;i < n;i++) {
            int pivot=i;
            for (int row = i + 1;row < n; row++) {
                if (Math.abs(a[row][i]) > Math.abs(a[pivot][i])) {
                    pivot = row;
                }
            }
            if (Math.abs(a[pivot][i]) < 1e-12) {
                return 0.0;
            }
            if (pivot != i) {
                double[] temp = a[i];
                a[i] = a[pivot];
                a[pivot] = temp;
                det *= -1;
            }
            det *= a[i][i];
            for (int row = i + 1; row < n; row++) {
                double factor = a[row][i] / a[i][i];
                for (int col = i; col < n; col++) {
                    a[row][col] -= factor * a[i][col];
                }
            }
        }
        return det;
    }
}