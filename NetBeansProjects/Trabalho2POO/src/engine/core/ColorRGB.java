package engine.core;

public class ColorRGB {
    public int r, g, b;
    public ColorRGB(int r, int g, int b) { this.r = r; this.g = g; this.b = b; }
    @Override public String toString() { return String.format("RGB(%d, %d, %d)", r, g, b); }
}
