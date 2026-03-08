package ProgamaLpoo;

public class Figure2Test
{
  public static void main(String[] args)
  {
    Point2 p = new Point2(1, 2);
    float x = p.x;

    p.x = x + 3;
    p.print("p");

    Point2 q = new Point2(p);

    q.y = x - 3;
    q.print("q");
    System.out.printf("p and q are %s SHALLOW equal\n",
      p == q ? "" : "NOT");

    Color c = Color.green;

    c.r = 100;
    c.print();
  }

} // Figure2Test
