package ProgamaLpoo;

public class Point2
{
  float x;
  float y;

  Point2(float x, float y)
  {
    this.x = x;
    this.y = y;
  }

  Point2(Point2 p)
  {
    x = p.x;
    y = p.y;
  }

  void print(String label)
  {
    System.out.printf("%s(%g,%g)\n", label, x, y);
  }

} // Point2
