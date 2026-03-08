package engine.core;

import engine.scene.GameObject;


public class Mesh extends Component{
    
    private String meshName;
    private String materialName;
    
    public Mesh(GameObject gameObject, String meshName, String materialName){
        super(gameObject);
        this.meshName = meshName;
        this.materialName = materialName;
    }
    
    @Override 
    public void inspect() {
        final String indent = "      ";
        System.out.println(indent + "- Component: " + getClassName() + " (do GameObject: '" + gameObject.getName() + "')");
        System.out.println(indent + "  - Mesh Name: " + this.meshName);
        System.out.println(indent + "  - Material Name: " + this.materialName);
    }
}
