package Trabalho1;

import java.util.*;

/**
 *
 * @Pedro Bertoncelo Figueiredo
 */
public class FullMatrix implements Matrix{
    
    private final int rows;
    private final int cols;
    private final double[][] data;

    //Para fazer uma zerada, só com as linhas e colunas
    public FullMatrix(int rows, int cols) {
        
        this.rows = rows;
        this.cols = cols;
        data = new double[rows][cols];
    }

    //Para fazer uma já pronta
    public FullMatrix(Matrix m) {
        
        this.rows = m.rows();
        this.cols = m.cols();
        data = new double[rows][cols];

        for (int i = 0; i < rows; i++) {
            
            for (int j = 0; j < cols; j++) {
                try {
                    data[i][j] = m.get(i, j);
                } catch (IndexOutOfBoundsException e) {
                    data[i][j] = 0;
                }
            }
        }
    }
    
 // Operações escalares
 @Override
    public Matrix add(double s) {
        
        FullMatrix result = new FullMatrix(rows, cols);
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                result.data[i][j] = this.data[i][j] + s;
        return result;
    }

    @Override
    public Matrix sub(double s) {
        return add(-s);
    }

    @Override
    public Matrix mul(double s) {
        
        FullMatrix result = new FullMatrix(rows, cols);
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                result.data[i][j] = this.data[i][j] * s;
        return result;
    }

    // Operações com matrizes
    @Override
    public Matrix add(Matrix m) throws BadDimensionsException {
        
        if (rows != m.rows() || cols != m.cols())
            throw new BadDimensionsException();

        FullMatrix result = new FullMatrix(rows, cols);
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                result.data[i][j] = this.data[i][j] + m.get(i, j);
        return result;
    }

    @Override
    public Matrix sub(Matrix m) throws BadDimensionsException {
        //Multiplica a matriz por -1, ai só faz o add
        return this.add(m.mul(-1));
    }

    @Override
    public Matrix mul(Vector v) throws BadDimensionsException {
        
        if (v.size() != cols){
            throw new BadDimensionsException();
        }
        FullMatrix result = new FullMatrix(rows, 1); // resultado é uma coluna
        for (int i = 0; i < rows; i++) {
            double sum = 0;
            for (int j = 0; j < cols; j++) {
                sum += data[i][j] * v.get(j);
            }
            result.set(i, 0, sum);
        }
        return result;
    }
    
    @Override
public Matrix mul(Matrix m) throws BadDimensionsException {
    if (cols() != m.rows())
        throw new BadDimensionsException();

    Matrix result = new FullMatrix(rows(), m.cols());
    for (int i = 0; i < rows(); i++) {
        Vector row = row(i);
        for (int j = 0; j < m.cols(); j++) {
            Vector col = m.col(j);
            //Vetor de linha * vetor de coluna
            result.set(i, j, row.vetMul(col));
        }
    }
    return result;
}



    // Getters de dimensão
    @Override
    public int rows() {
        return rows;
    }

    @Override
    public int cols() {
        return cols;
    }
    
    @Override
    public int size() {
        return rows * cols;
        }


    // Acesso a elemento
    @Override
    public double get(int i, int j) throws IndexOutOfBoundsException {
        if (i < 0 || i >= rows || j < 0 || j >= cols)
            throw new IndexOutOfBoundsException();
        return data[i][j];
    }

    @Override
    public void set(int i, int j, double v) throws IndexOutOfBoundsException {
        if (i < 0 || i >= rows || j < 0 || j >= cols)
            throw new IndexOutOfBoundsException();
        data[i][j] = v;
    }

    // Linhas e colunas como vetor
    @Override
    public Vector row(int i) throws IndexOutOfBoundsException {
        if (i < 0 || i >= rows)
            throw new IndexOutOfBoundsException();
        Vector row = new Vector(cols);
        for (int j = 0; j < cols; j++)
            row.set(j, data[i][j]);
        return row;
    }

    @Override
    public Vector col(int j) throws IndexOutOfBoundsException {
        if (j < 0 || j >= cols)
            throw new IndexOutOfBoundsException();
        Vector col = new Vector(rows);
        for (int i = 0; i < rows; i++)
            col.set(i, data[i][j]);
        return col;
    }

    @Override
    public void setRow(int i, Vector v) throws BadDimensionsException {
        if (v.size() != cols)
            throw new BadDimensionsException();
        for (int j = 0; j < cols; j++)
            data[i][j] = v.get(j);
    }

    @Override
    public void setCol(int j, Vector v) throws BadDimensionsException {
        if (v.size() != rows)
            throw new BadDimensionsException();
        for (int i = 0; i < rows; i++)
            data[i][j] = v.get(i);
    }

    // Iterator 
    @Override
    public Iterator<MatrixElement> iterator() {
        return new Iterator<MatrixElement>() {
            private int i = 0, j = 0;

            @Override
            public boolean hasNext() {
                while (i < rows) {
                    if (j >= cols) {
                        //acabou as colunas dessa linha
                        i++;
                        j = 0;
                    } else if (i < rows && data[i][j] == 0) {
                        //elemento é zero proxima coluna   
                        j++;
                    } else {
                        //proximo válido
                        break;
                    }
                }
                return i < rows && j < cols;
            }

            @Override
            public MatrixElement next() {
                if (!hasNext()) throw new NoSuchElementException();
                MatrixElement elem = new MatrixElement(i, j, data[i][j]);
                j++;
                return elem;
            }
        };
    }
}    
