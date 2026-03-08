package ProgamaLpoo;

public class Color
{
  int r;
  int g;
  int b;

  Color() // black
  {
    // do nothing
  }

  Color(int r, int g, int b)
  {
    this.r = r;
    this.g = g;    
    this.b = b;
  }

  Color(Color c)
  {
    r = c.r;
    g = c.g;    
    b = c.b;
  }

  void print()
  {
    System.out.printf("rgb(%d,%d,%d)\n", r, g, b);
  }

  static final Color red = new Color(255, 0, 0); 
  static final Color green = new Color(0, 255, 0); 
  static final Color blue = new Color(0, 0, 255);
  static final Color black = new Color();

} // Color
