import java.util.Random;

public class PerformanceResearch {
    public static void main(String []args) throws InterruptedException {
        int maxThreadNum = 64;
        int size = 700;
        double [][]matrixA = new double[size][size];
        double[] colB = new double[size];

        for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
                matrixA[i][j] = new Random().nextInt(100);
            colB[i] = new Random().nextInt(100);
        }

        for(int i = 1; i < maxThreadNum + 1; i *= 2)
            System.out.println("Result for" + i + " threads " + execute(matrixA, colB, i, size) + " microseconds");

    }

    private static double execute(double [][] matrixA, double[] colB, int threadNum, int size) throws InterruptedException
    {
        double []X = new double[size];
        long nanos = System.nanoTime();
        SLAESolution.solveSLAE(matrixA, colB, X, threadNum);
        return ((double)System.nanoTime() - nanos) / 10e+3;
    }

}
