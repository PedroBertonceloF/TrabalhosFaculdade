package Trabalho1;

import java.util.*;

/**
 *
 * @Pedro Bertoncelo Figueiredo
 */
public final class Vector implements Iterable<Double>
{
    private double[] data;
  
  public Vector(int n)
  {
    if (n < 0) {
        throw new IllegalArgumentException("Tamanho do vetor deve ser >= 0");
    }
    data = new double[n];
  }

   //Quantidade de elementos
  public int size()
  {
    return data.length;
  }
  
  //Posição de i
  public double get(int i)
  {
    if (i < 0 || i >= data.length) {
        throw new IndexOutOfBoundsException("Índice fora do intervalo: " + i);
    }
    return data[i];
  }

  //Define o valor de i
  public void set(int i, double s)
  {
    if (i < 0 || i >= data.length) {
            throw new IndexOutOfBoundsException("Índice fora do intervalo: " + i);
        }
        data[i] = s;
  }
  
  public double vetMul(Vector v) throws BadDimensionsException {
        if (size() != v.size())
            throw new BadDimensionsException("Vetores de tamanhos diferentes");

        double sum = 0;
        for (int i = 0; i < size(); i++) {
            sum += get(i) * v.get(i);
        }
        return sum;
    }
  
  @Override
  public Iterator<Double> iterator()
  {
    return new Iterator<Double>() {
        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < data.length;
        }

        @Override
        public Double next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return data[index++];
        }
    };
  }



} // Vector
