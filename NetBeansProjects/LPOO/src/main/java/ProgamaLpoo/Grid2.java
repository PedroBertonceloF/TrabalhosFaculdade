package ProgamaLpoo;

import java.util.*;

public class Grid2<T> implements Iterable<T>{
    
    
    ArrayList<T> data;
    int m;
    int n;
    
    
    public Grid2(int m, int n){
        if(m<0 || n<0){
            throw new IndexOutOfBoundExcpetion("Fora do itervalo!");
        }
        
        data = new ArrayList<>(m*n);
    }
    
    public ArrayList get(int i ,int j){
        return data.get(i*m+j);
    }
    
    
    public void set(int i, int j, ArrayList data){
            data.set(i, j);
    }
''
    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
