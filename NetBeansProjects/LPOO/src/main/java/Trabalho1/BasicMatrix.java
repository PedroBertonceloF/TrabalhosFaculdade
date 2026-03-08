package Trabalho1;

/**
 *
 * @author User
 */

import java.util.*;

public class BasicMatrix implements Matrix {
    private final int rows;
    private final int cols;
    private final double[][] data;

    public BasicMatrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        data = new double[rows][cols];
    }

    // Escalares
    @Override
    public Matrix add(double s) {
        BasicMatrix result = new BasicMatrix(rows, cols);
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                result.set(i, j, this.get(i, j) + s);
        return result;
    }

    @Override
    public Matrix sub(double s) {
        return this.add(-s);
    }

    @Override
    public Matrix mul(double s) {
        BasicMatrix result = new BasicMatrix(rows, cols);
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                result.set(i, j, this.get(i, j) * s);
        return result;
    }

    // Com matrizes
    @Override
    public Matrix add(Matrix m) throws BadDimensionsException {

        BasicMatrix result = new BasicMatrix(rows, cols);
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                result.set(i, j, this.get(i, j) + m.get(i, j));
        return result;
    }

    @Override
    public Matrix sub(Matrix m) throws BadDimensionsException {

        BasicMatrix result = new BasicMatrix(rows, cols);
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                result.set(i, j, this.get(i, j) - m.get(i, j));
        return result;
    }

    @Override
    public Matrix mul(Matrix m) throws BadDimensionsException {
        if (this.cols != m.rows())
            throw new BadDimensionsException("Multiplicação inválida.");
        BasicMatrix result = new BasicMatrix(this.rows, m.cols());
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < m.cols(); j++) {
                double sum = 0;
                for (int k = 0; k < this.cols; k++)
                    sum += this.get(i, k) * m.get(k, j);
                result.set(i, j, sum);
            }
        }
        return result;
    }

    @Override
    public Matrix mul(Vector v) throws BadDimensionsException {
        if (this.cols != v.size())
            throw new BadDimensionsException("Multiplicação com vetor inválida.");
        BasicMatrix result = new BasicMatrix(this.rows, 1);
        for (int i = 0; i < this.rows; i++) {
            double sum = 0;
            for (int j = 0; j < this.cols; j++)
                sum += this.get(i, j) * v.get(j);
            result.set(i, 0, sum);
        }
        return result;
    }

    // Dimensões
    @Override
    public int rows() { return rows; }
    @Override
    public int cols() { return cols; }
    @Override
    public int size() { return rows * cols; }

    // Acesso e modificação
    @Override
    public void set(int i, int j, double s) {
        data[i][j] = s;
    }

    @Override
    public double get(int i, int j) {
        return data[i][j];
    }

    // Vetores
    @Override
    public Vector row(int i) {
        Vector v = new Vector(cols);
        for (int j = 0; j < cols; j++)
            v.set(j, data[i][j]);
        return v;
    }

    @Override
    public Vector col(int j) {
        Vector v = new Vector(rows);
        for (int i = 0; i < rows; i++)
            v.set(i, data[i][j]);
        return v;
    }

    @Override
    public void setRow(int i, Vector v) throws BadDimensionsException {
        if (v.size() != cols)
            throw new BadDimensionsException("Dimensão da linha incompatível.");
        for (int j = 0; j < cols; j++)
            data[i][j] = v.get(j);
    }

    @Override
    public void setCol(int j, Vector v) throws BadDimensionsException {
        if (v.size() != rows)
            throw new BadDimensionsException("Dimensão da coluna incompatível.");
        for (int i = 0; i < rows; i++)
            data[i][j] = v.get(i);
    }

    // Iterador
    @Override
    public Iterator<MatrixElement> iterator() {
        return new Iterator<MatrixElement>() {
            int i = 0, j = 0;

            @Override
            public boolean hasNext() {
                return i < rows;
            }

            @Override
            public MatrixElement next() {
                MatrixElement e = new MatrixElement(i, j, get(i, j));
                j++;
                if (j >= cols) {
                    j = 0;
                    i++;
                }
                return e;
            }
        };
    }
}
