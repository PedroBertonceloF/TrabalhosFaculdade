package Trabalho1;

/**
 *
 * @Pedro Bertoncelo Figueiredo
 */
public class Main {
    public static void main(String[] args) {
        try {
            // Criação das matrizes 3x3
            Matrix full = new FullMatrix(3, 3);
            Matrix sparse = new SparseMatrix(3, 3);
                
            for (int i = 0; i < full.rows(); i++) {
            // Preenchendo as duas matrizes
                for (int j = 0; j < full.cols(); j++) {
                    double value = i + j + 1;
                    //Coluna + linha + 1 para a Full
                    full.set(i, j, value);
                    if ((i + j) % 2 == 0) {
                        //Elementos que a soma dao em par
                        sparse.set(i, j, value); 
                    }
                }
            }

            // Impressão
            System.out.println("FullMatrix:");
            printMatrix(full);
            System.out.println("SparseMatrix:");
            printMatrix(sparse);

            // Escalares
            System.out.println("Full + 2:");
            printMatrix(full.add(2));
            System.out.println("Full - 1:");
            printMatrix(full.sub(1));
            System.out.println("Full * 3:");
            printMatrix(full.mul(3));
            System.out.println("Sparse + 2:");
            printMatrix(sparse.add(2));
            System.out.println("Sparse - 1:");
            printMatrix(sparse.sub(1));
            System.out.println("Sparse * 3:");
            printMatrix(sparse.mul(3));

            // Operações entre matrizes
            System.out.println("Full + Sparse:");
            printMatrix(full.add(sparse));
            System.out.println("Full + Full:");
            printMatrix(full.add(full));
            System.out.println("Full - Sparse:");
            printMatrix(full.sub(sparse));
            System.out.println("Full - Full:");
            printMatrix(full.sub(full));
            System.out.println("Full * Sparse:");
            printMatrix(full.mul(sparse));
            System.out.println("Full * Full:");
            printMatrix(full.mul(full));

            // Vetor para multiplicar
            Vector v = new Vector(3);
            v.set(0, 1);
            v.set(1, 2);
            v.set(2, 3);
            System.out.println("Full * Vector:");
            printMatrix(full.mul(v));
            System.out.println("Sparse * Vector:");
            printMatrix(sparse.mul(v));

            // Dimensões
            System.out.println("Full rows: " + full.rows());
            System.out.println("Full cols: " + full.cols());
            System.out.println("Full size: " + full.size());
            System.out.println("\nSparse rows: " + sparse.rows());
            System.out.println("Sparse cols: " + sparse.cols());
            System.out.println("Sparse size: " + sparse.size());
            
            // get/set
            System.out.println("\nValor em full(1,2): " + full.get(1, 2));
            full.set(1, 2, 8);
            System.out.println("Novo valor em full(1,2): " + full.get(1, 2));
            System.out.println("\nValor em sparse(2,1): " + sparse.get(2, 1));
            sparse.set(1, 2, 8);
            System.out.println("Novo valor em sparse(2,1): " + sparse.get(2, 1));

            // row / col
            Vector row1 = full.row(1);
            Vector col2 = full.col(2);
            Vector row2 = sparse.row(2);
            Vector col1 = sparse.col(1);
            System.out.println("\nLinha 1 de Full:");
            printVector(row1);
            System.out.println("Coluna 2 de Full:");
            printVector(col2);
            System.out.println("\nLinha 2 da Sparse:");
            printVector(row2);
            System.out.println("Coluna 1 da Sparse:");
            printVector(col1);

            // setRow / setCol
            Vector newRow = new Vector(3);
            newRow.set(0, 10);
            newRow.set(1, 20);
            newRow.set(2, 30);
            full.setRow(0, newRow);
            sparse.setRow(1, newRow);
            System.out.println("\nFull apos setRow(0, newRow):");
            printMatrix(full);
            System.out.println("\nSparse apos setRow(1, newRow):");
            printMatrix(sparse);

            Vector newCol = new Vector(3);
            newCol.set(0, 7);
            newCol.set(1, 8);
            newCol.set(2, 9);
            full.setCol(1, newCol);
            sparse.setCol(0,newCol);
            System.out.println("\nFull apos setCol(1, newCol):");
            printMatrix(full);
            System.out.println("\nSparse apos setRow(0, newCol");
            printMatrix(sparse);
            
            // Iterador (MatrixElement), da Sparse
            System.out.println("Iterando SparseMatrix:");
            for (MatrixElement e : sparse) {
                System.out.println("[" + e.row + "," + e.col + "] = " + e.value);
            }
            
            System.out.println("\nIterando FullMatrix: ");
            for(MatrixElement e : full){
                System.out.println("[" + e.row + "," + e.col + "] = " + e.value);
            }

        } catch (BadDimensionsException e) {
            System.err.println("Erro de dimensao: " + e.getMessage());
        }
    }

    private static void printMatrix(Matrix m) {
        for (int i = 0; i < m.rows(); i++) {
                printVector(m.row(i));
            }
        System.out.println();
    }

    private static void printVector(Vector v) {
        for (int i = 0; i < v.size(); i++) {
            System.out.printf("%6.1f ", v.get(i));
        }
        System.out.println("\n");
    }
}
