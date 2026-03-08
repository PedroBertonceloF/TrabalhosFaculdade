package engine.core;

import engine.scene.GameObject;
import engine.util.Vector;

public class Transform extends Component{
    
    private Vector position;
    private Vector rotation;
    private double scale;
    
    
    public Transform(GameObject gameObject){
        super(gameObject);
        this.position = new Vector(0, 0, 0);
        this.rotation = new Vector(0, 0, 0);
        this.scale = 1.0;
    }
    
    @Override
    public void inspect() {
        final String indent = "      "; // Recuo para os atributos
        System.out.println(indent + "- Component: " + getClassName() + " (do GameObject: '" + gameObject.getName() + "')");
        System.out.println(indent + "  - Position: " + position.toString());
        System.out.println(indent + "  - Rotation: " + rotation.toString());
        System.out.println(indent + "  - Scale: " + String.format("%.2f", scale));
    }
}
