import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class SLAESolutionTests {


    @Test
    public void test3x3MatrixDimension() throws FileNotFoundException, InterruptedException {
        FileReader fr = new FileReader("/home/yxodu/IdeaProjects/Lab4/src/main/resources/tests");
        double [][]matrix = new double[][]
                {
                        {1.5, 2.6, -5.1},
                        {-2.3, 5.2, -1.2},
                        {4.4, -2.1, 3.8},
                };
        double []colB = new double[]{3.2, -5.1, 2.9};
        double []X = new double[colB.length];
        double []expectedX = new double[] {0.9360496, -0.7344123, -0.7265485};

        SLAESolution.solveSLAE(matrix, colB, X, 5);
        Assert.assertTrue(compareResults(X, expectedX));
    }


    @Test
    public void test4x4MatrixDimension() throws FileNotFoundException, InterruptedException {
        FileReader fr = new FileReader("/home/yxodu/IdeaProjects/Lab4/src/main/resources/tests");
        double [][]matrix = new double[][]
                {
                        {3.6, 2.1, -7.2, 10.2},
                        {-2.1, 4.7, 2.8, 6.4},
                        {4.4, -2.1, 3.8, 2.6},
                        {1.5, -8.4, 3.1, 2.6}
                };
        double []colB = new double[]{5.3, -4.5, 5.1, 11.2};
        double[] X = new double[colB.length];
        double []expectedX = new double[] {0.521516056, -1.184862653, -0.2110883517, 0.4304821212};

        SLAESolution.solveSLAE(matrix, colB, X,5);
        Assert.assertTrue(compareResults(X, expectedX));
    }


    @Test
    public void test5x5MatrixDimension() throws FileNotFoundException, InterruptedException {
        FileReader fr = new FileReader("/home/yxodu/IdeaProjects/Lab4/src/main/resources/tests");
        double [][]matrix = new double[][]
                {
                        {-4.2, 11.0, 8.4, -7.5, -3.6},
                        {-1.8, 1.1, 5.4, 4.1, -3.2},
                        {9.4, -2.4, 3.3, -4.3, 11.4},
                        {-4.2, 3.0, -1.4, 2.5, -2.1},
                        {-3.1, 4.4, 6.2, -9.8, 1.2},
                };
        double []colB = new double[]{2.9, -1.9, 2.1, -3.4, -5.3};
        double[]X = new double[colB.length];
        double []expectedX = new double[] {2.11571853, 0.853216961, -0.455662459, -0.1955890568, -1.322577226};

        SLAESolution.solveSLAE(matrix, colB, X,5);
        Assert.assertTrue(compareResults(X, expectedX));
    }

    @Test
    public void test6x6MatrixDimension() throws FileNotFoundException, InterruptedException {
        FileReader fr = new FileReader("/home/yxodu/IdeaProjects/Lab4/src/main/resources/tests");
        double [][]matrix = new double[][]
                {
                        {3.0, 2.0, 7.2, 5.3, 8.1, -5.3},
                        {2.4, 3.2, 8.5, -3.0, 18.4, -3.2},
                        {-3.1, 2.6, -9.0, 2.2, -7.1, -3.2},
                        {-3.1, 7.5, 2.4, -3.7, 6.5, 3.7},
                        {-2.3, 4.2, -2.9, -4.8, 1.6, 2.4},
                        {-8.7, 1.6, 4.2, -1.1, 4.6, 3.2}
                };
        double []colB = new double[]{1.2, -5.4, 4.1, -3.4, 6.9, -8.7};
        double[]X = new double[colB.length];
        double []expectedX = new double[] {-2.605509336, 6.44751309, 16.5115455, -19.32151947, -15.23381001, -19.4408090};

        SLAESolution.solveSLAE(matrix, colB, X,5);
        Assert.assertTrue(compareResults(X, expectedX));
    }


    public boolean compareResults(double []X, double []expectedX)
    {
        double eps = 10-5;

        for(int i = 0; i < X.length; i++)
        {
            if(Math.abs(X[i] - expectedX[i]) > eps)
                return false;
        }
        return true;
    }
}
