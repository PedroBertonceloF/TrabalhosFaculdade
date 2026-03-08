package engine.util;

public class Vector {
    private double x;
    private double y;
    private double z;
    
    public Vector(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public double getX(){return x;}
    public void setX(double x){this.x = x;}
    public double getY() { return y; }
    public void setY(double y) { this.y = y; }
    public double getZ() { return z; }
    public void setZ(double z) { this.z = z; }
    
    public Vector add(Vector other){
        return new Vector(x + other.x, y + other.y, z + other.z);
    }
    
    public Vector scale(double factor){
        return new Vector(x*factor, y*factor, z*factor);
    }
    
    public Vector sub(Vector other){
        return new Vector(x - other.x, y - other.y, z - other.z);
    }
    
    public double length(){
        return Math.sqrt(x*x + y*y + z*z);
    }
    
    public Vector normalize(){
        double len = length();
        if(len == 0)return new Vector(0,0,0);
        return new Vector(x/len,y/len, z/len);
    }
    
    public double dot(Vector other){
        return x * other.x + y*other.y+ z * other.z;
    }
    
    public Vector cross(Vector other){
        return new Vector(
                y*other.z - z*other.y,
                z*other.x - x*other.z,
                x*other.y -y * other.x
        );
    }
    
    
    @Override
    public String toString(){
        return String.format("%.2f, %.2f, %.2f", x, y,z);
    }
}
