package ProgamaLpoo;

public class Line2 extends Shape2
{
  private Point2 p1;
  private Point2 p2;


   public Line2(float x1, float y1, float x2, float y2)
  {
    p1 = new Point2(x1, y1);
    p2 = new Point2(x2, y2);
  }

   public Line2(Point2 p1, Point2 p2)
  {
    this.p1 = new Point2(p1); // deep-copy
    this.p2 = new Point2(p2); // deep-copy
  }

   public Point2 getP1(){
       return p1;
   }
   
   public void setP1(Point2 p){
       p.x = p1.x;
       p.y = p1.y;
   }
   public void print()
  {
    p1.print("p1");
    p2.print("p2");
    color.print();
  }

} // Line2
