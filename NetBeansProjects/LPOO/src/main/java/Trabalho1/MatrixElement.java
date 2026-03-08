package Trabalho1;

/**
 *
 * @Pedro Bertoncelo Figueiredo
 */
public class MatrixElement
{
    public int row;
    public int col;
    public double value;
    
    public MatrixElement(int row, int col, double value) {
        this.row = row;
        this.col = col;
        this.value = value;
    }

    public int row() {
        return row;
    }

    public int column() {
        return col;
    }

    public double value() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d) = %.2f", row, col, value);
    }
}

