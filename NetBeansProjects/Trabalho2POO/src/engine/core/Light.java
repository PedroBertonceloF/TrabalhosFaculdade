package engine.core;

import engine.scene.GameObject;

/**
 *
 * @author User
 */
public class Light extends Component{
    private LightType type;
    private ColorRGB color;
    
    public Light(GameObject gameObject, LightType type, ColorRGB color){
        super(gameObject);
        this.type = type;
        this.color = color;
    }
    
    @Override
    public void inspect() {
        final String indent = "      ";
        System.out.println(indent + "- Component: " + getClassName() + " (do GameObject: '" + gameObject.getName() + "')");
        System.out.println(indent + "  - Type: " + this.type);
        System.out.println(indent + "  - Color: " + this.color.toString());
    }
}
