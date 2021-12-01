package ru.job4j.concurrent.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;

public class RolColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public Sums() { }

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }

        public int getRowSum() {
            return rowSum;
        }

        public int getColSum() {
            return colSum;
        }
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] result = new Sums[matrix.length];
        int colSum = 0;
        int rowSum = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                rowSum += matrix[i][j];
                colSum += matrix[j][i];
            }
            result[i] = new Sums(rowSum, colSum);
            colSum = 0;
            rowSum = 0;
        }
        return result;
    }

    public static Sums[] asyncSum(int[][] matrix) {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        Sums[] result = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            int index = i;
            result[i] = new Sums();
            CompletableFuture.runAsync(
                    () -> {
                        int colSum = 0;
                        int rowSum = 0;
                        for (int j = 0; j < matrix[index].length; j++) {
                            rowSum += matrix[index][j];
                            colSum += matrix[j][index];
                        }
                        result[index].setRowSum(rowSum);
                        result[index].setColSum(colSum);
                    }, pool
            );
        }
        return result;
    }
}
