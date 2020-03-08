package MathOperations;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.Random;

import static org.junit.Assert.*;

public class VectorOperationsTest {

    private RealVector[] vectors1;
    private RealVector[] vectors2;
    private int dim = 0;
    private final double EPS = 1E-10;

    private void randomDoubleInit(RealVector vec1)
    {
        if(vec1 != null)
        {
            for(int i = 0; i < dim; i++)
            {
                vec1.setEntry(i, new Random().nextDouble());
            }
        }
        else
            throw new NullPointerException();

    }
    @Test
    public void dotProductTest()
    {
        dim = Math.abs(new Random().nextInt() % 1000) + 1;
        vectors1 = new RealVector[dim];
        vectors2 = new RealVector[dim];
        for(int i = 0; i < dim; i++)
        {
            vectors1[i] = new ArrayRealVector(dim);
            vectors2[i] = new ArrayRealVector(dim);
            randomDoubleInit(vectors1[i]);
            randomDoubleInit(vectors2[i]);

            Assert.assertEquals(vectors1[i].dotProduct(vectors2[i]),
                    MathOperations.VectorOperations.dotProduct(vectors1[i], vectors2[i]), EPS);
        }
    }

}