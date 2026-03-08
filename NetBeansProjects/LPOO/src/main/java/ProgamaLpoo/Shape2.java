package ProgamaLpoo;

public class Shape2 {
   public Color color = Color.black;//Shallow-copy
   private int id;
   Shape2 next;
   Shape2 prev;
   
   protected Shape2(int id){
       this.id = id;
   }
}
