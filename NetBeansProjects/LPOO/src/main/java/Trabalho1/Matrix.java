package Trabalho1;

import java.util.*;

/**
 *
 * @Pedro Bertoncelo Figueiredo
 */
public interface Matrix extends Iterable<MatrixElement>
{
    //Escalares
  Matrix add(double s);
  Matrix sub(double s);
  Matrix mul(double s);
  
  //Com matrizes
  Matrix add(Matrix m) throws BadDimensionsException;
  Matrix sub(Matrix m) throws BadDimensionsException;
  Matrix mul(Vector v) throws BadDimensionsException;
  Matrix mul(Matrix m) throws BadDimensionsException;
  
  //Dimensões
  int rows();
  int cols();
  int size();
  
  //Acesso e mod
  void set(int i, int j, double s);
  double get(int i, int j);
  
  //Vetores de linha/coluna
  Vector row(int i);
  Vector col(int j);
  void setRow(int i, Vector v) throws BadDimensionsException;
  void setCol(int j, Vector v) throws BadDimensionsException;
  
  @Override
  Iterator<MatrixElement> iterator();//Herda de matrix element

} // Matrix
