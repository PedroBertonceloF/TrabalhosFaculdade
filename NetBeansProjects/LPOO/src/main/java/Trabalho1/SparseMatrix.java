package Trabalho1;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SparseMatrix implements Matrix {
//Cria os nos
    private static class Node {
        int row, col;
        double value;
        Node nextRow, nextCol;
        //Cria outro no
        Node(int row, int col, double value) {
            this.row = row;
            this.col = col;
            this.value = value;
            this.nextRow = this;
            this.nextCol = this;
        }
    }
//Instancia variavel
    private final int rows, cols;
    private final Node[] rowSentinelas;
    private final Node[] colSentinelas;
//COnstrutor
    public SparseMatrix(int rows, int cols) {
        if (rows <= 0 || cols <= 0)
            throw new IllegalArgumentException("Dimensões devem ser positivas.");
        this.rows = rows;
        this.cols = cols;
        rowSentinelas = new Node[rows];
        colSentinelas = new Node[cols];

        for (int i = 0; i < rows; i++) {
            rowSentinelas[i] = new Node(-1, -1, 0);
            rowSentinelas[i].nextRow = rowSentinelas[i];
        }
        for (int j = 0; j < cols; j++) {
            colSentinelas[j] = new Node(-1, -1, 0);
            colSentinelas[j].nextCol = colSentinelas[j];
        }
    }

    @Override
    public Matrix add(double s) {
        SparseMatrix result = new SparseMatrix(rows, cols);
        for (MatrixElement e : this)
            result.set(e.row(), e.column(), e.value() + s);
        return result;
    }

    @Override
    public Matrix sub(double s) {
        return add(-s);
    }

    @Override
    public Matrix mul(double s) {
        SparseMatrix result = new SparseMatrix(rows, cols);
        for (MatrixElement e : this)
            result.set(e.row(), e.column(), e.value() * s);
        return result;
    }

    @Override
    public Matrix add(Matrix m) throws BadDimensionsException {
        if (rows != m.rows() || cols != m.cols())
            throw new BadDimensionsException();
        SparseMatrix result = new SparseMatrix(rows, cols);
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                result.set(i, j, this.get(i, j) + m.get(i, j));
        return result;
    }

    @Override
    public Matrix sub(Matrix m) throws BadDimensionsException {
        return this.add(m.mul(-1));
    }

    @Override
public Matrix mul(Vector v) throws BadDimensionsException {
    if (v.size() != cols)
        throw new BadDimensionsException();

    SparseMatrix result = new SparseMatrix(rows, 1); // matriz coluna (rows x 1)

    for (int i = 0; i < rows; i++) {
        double sum = 0;
        for (Node n = rowSentinelas[i].nextRow; n != rowSentinelas[i]; n = n.nextRow) {
            sum += n.value * v.get(n.col);//Valor de coluna do nó n vezes o valor do vetor na mesmo valor da coluna de n
        }
        if (sum != 0) {
            result.set(i, 0, sum); // coluna é sempre 0 (única coluna)
        }
    }

    return result;
}


@Override
    public Matrix mul(Matrix m) throws BadDimensionsException {
        if (this.cols != m.rows()) throw new BadDimensionsException();

        Matrix result = new SparseMatrix(this.rows, m.cols());

        for (int i = 0; i < this.rows; i++) {
            Vector rowVec = this.row(i);
            for (int j = 0; j < m.cols(); j++) {
                Vector colVec = m.col(j);
                double value = rowVec.vetMul(colVec);
                if (value != 0) result.set(i, j, value);
            }
        }

        return result;
    } 

    @Override
    public int rows() { return rows; }

    @Override
    public int cols() { return cols; }

    @Override
    public int size() {
        int count = 0;
        for (MatrixElement e : this)
            count++;
        return count;
    }
    
    private void insertInColumn(Node node) {
        int j = node.col;
        Node prevCol = colSentinelas[j];
        Node currCol = prevCol.nextCol;
        while (currCol != colSentinelas[j] && currCol.row < node.row) {
            prevCol = currCol;
            currCol = currCol.nextCol;
        }
        node.nextCol = currCol;
        prevCol.nextCol = node;
    }

    private void removeFromColumn(Node node) {
        int j = node.col;
        Node prevCol = colSentinelas[j];
        Node currCol = prevCol.nextCol;
        while (currCol != colSentinelas[j]) {
            if (currCol == node) {
                prevCol.nextCol = currCol.nextCol;
                //Encurta, logo exclue
                return;
            }
            prevCol = currCol;
            currCol = currCol.nextCol;
        }
    }

    @Override
    public void set(int i, int j, double value) {
        if (i < 0 || i >= rows || j < 0 || j >= cols)
            throw new IndexOutOfBoundsException();

        Node prevRow = rowSentinelas[i];
        Node currRow = prevRow.nextRow;
        while (currRow != rowSentinelas[i] && currRow.col < j) {
            prevRow = currRow;
            currRow = currRow.nextRow;
        }

        if (currRow != rowSentinelas[i] && currRow.col == j) {
            if (value == 0) {
                prevRow.nextRow = currRow.nextRow;
                removeFromColumn(currRow);
            } else {
                currRow.value = value;
            }
        } else if (value != 0) {
            Node newNode = new Node(i, j, value);
            newNode.nextRow = currRow;
            prevRow.nextRow = newNode;
            insertInColumn(newNode);
        }
    }

    

    @Override
    public double get(int i, int j) {
        if (i < 0 || i >= rows || j < 0 || j >= cols)
            throw new IndexOutOfBoundsException();
        for (Node n = rowSentinelas[i].nextRow; n != rowSentinelas[i]; n = n.nextRow)
            if (n.col == j)
                return n.value;
        return 0;
    }

    @Override
    public Vector row(int i) {
        Vector v = new Vector(cols);
        for (Node n = rowSentinelas[i].nextRow; n != rowSentinelas[i]; n = n.nextRow)
            v.set(n.col, n.value);
        return v;
    }

    @Override
    public Vector col(int j) {
        Vector v = new Vector(rows);
        for (Node n = colSentinelas[j].nextCol; n != colSentinelas[j]; n = n.nextCol)
            v.set(n.row, n.value);
        return v;
    }

    @Override
    public void setRow(int i, Vector v) throws BadDimensionsException {
        if (v.size() != cols)
            throw new BadDimensionsException();
        for (int j = 0; j < cols; j++)
            set(i, j, v.get(j));
    }

    @Override
    public void setCol(int j, Vector v) throws BadDimensionsException {
        if (v.size() != rows)
            throw new BadDimensionsException();
        for (int i = 0; i < rows; i++)
            set(i, j, v.get(i));
    }

    @Override
    public Iterator<MatrixElement> iterator() {
        return new Iterator<>() {
            int currentRow = 0;
            Node current = null;

            @Override
            public boolean hasNext() {
                while (currentRow < rows) {
                    if (current == null)
                        current = rowSentinelas[currentRow].nextRow;
                    if (current != rowSentinelas[currentRow])
                        return true;
                    current = null;
                    currentRow++;
                }
                return false;
            }

            @Override
            public MatrixElement next() {
                if (!hasNext())
                    throw new NoSuchElementException();
                MatrixElement e = new MatrixElement(current.row, current.col, current.value);
                current = current.nextRow;
                return e;
            }
        };
    }
}
