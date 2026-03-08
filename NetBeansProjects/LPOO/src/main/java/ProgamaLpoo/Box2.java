package ProgamaLpoo;


public class Box2
{
  // TODO
    Point2 cantoSD;
    Point2 cantoIE;
    Line2 Diagonal;
    
    Box2(Point2 cantoSd, Point2 CantoIE){
        this.cantoSD = new Point2(1, 2);
        this.cantoIE = new Point2(3, 4);
        this.Diagonal = new Line2(cantoSD, cantoIE);
    }
    
    
    
    void print(String label)
  {
        label = "Pontos da caixa";
        System.out.printf("%s(%g,%g)\n", label, cantoSD, cantoIE);
        System.out.println("Diagonal: ");
        Diagonal.print();
    
  }
} // Box2
