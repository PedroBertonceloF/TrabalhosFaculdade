package ProgamaLpoo;

public class Circle2
{
  // TODO
    Point2 Centro;
    float raio;
    Color cor;
    
    Circle2(Point2 Centro, float raio, Color cor){
        this.Centro = new Point2(10, 2);
        this.raio = 314;
        this.cor = Color.red;
        
    }
  
    void print(String label){
        label = "Circulo";
        System.out.printf("%s(%g,%g)\n", label, Centro, raio);
        System.out.println("Cor: ");
        cor.print();
    }
  
} // Circle2
