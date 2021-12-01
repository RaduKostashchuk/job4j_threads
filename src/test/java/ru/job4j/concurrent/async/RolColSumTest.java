package ru.job4j.concurrent.async;

import org.junit.Test;

import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class RolColSumTest {

    @Test
    public void whenRunSyncSumOnSquareMatrix() {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RolColSum.Sums[] result = RolColSum.sum(matrix);
        assertThat(result[0].getColSum(), is(12));
        assertThat(result[0].getRowSum(), is(6));
        assertThat(result[1].getColSum(), is(15));
        assertThat(result[1].getRowSum(), is(15));
        assertThat(result[2].getColSum(), is(18));
        assertThat(result[2].getRowSum(), is(24));
    }

    @Test
    public void whenRunSyncSumOnZeroMatrix() {
        int[][] matrix = {{0}};
        RolColSum.Sums[] result = RolColSum.sum(matrix);
        assertThat(result[0].getColSum(), is(0));
    }

    @Test
    public void whenRunAsyncSumOnSquareMatrix() throws InterruptedException {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RolColSum.Sums[] result = RolColSum.asyncSum(matrix);
        Thread.sleep(100);
        assertThat(result[0].getColSum(), is(12));
        assertThat(result[0].getRowSum(), is(6));
        assertThat(result[1].getColSum(), is(15));
        assertThat(result[1].getRowSum(), is(15));
        assertThat(result[2].getColSum(), is(18));
        assertThat(result[2].getRowSum(), is(24));
    }

    @Test
    public void whenCompareSyncAndAsync() {
        int dim = 2000;
        int[][] matrix = new int[dim][dim];
        Random rd = new Random();
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                matrix[i][j] = rd.nextInt();
            }
        }
        long time1 = System.nanoTime();
        RolColSum.asyncSum(matrix);
        long time2 = System.nanoTime();
        RolColSum.sum(matrix);
        long time3 = System.nanoTime();
        long timeAsync = time2 - time1;
        long timeSync = time3 - time2;
        System.out.println("Time async: " + timeAsync);
        System.out.println("Time sync: " + timeSync);
        assertThat(timeAsync < timeSync, is(true));

    }
}