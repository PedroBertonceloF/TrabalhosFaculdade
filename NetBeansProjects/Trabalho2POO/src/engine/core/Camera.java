package engine.core;

import engine.scene.GameObject;

public class Camera extends Component{
    private ProjectionType projectionType;
    private double fieldOfView;
    private double nearClipPlane;
    private double farClipPlane;
    
    public Camera(GameObject gameObject, ProjectionType projectionType, double fov, double near, double far){
        super(gameObject);
        this.projectionType = projectionType;
        this.fieldOfView = fov;
        this.nearClipPlane = near;
        this.farClipPlane = far;
    }
    
    @Override
    public void inspect() {
        final String indent = "      ";
        System.out.println(indent + "- Component: " + getClassName() + " (do GameObject: '" + gameObject.getName() + "')");
        System.out.println(indent + "  - Projection: " + this.projectionType);
        System.out.println(indent + "  - Field of View: " + this.fieldOfView);
        System.out.println(indent + "  - Clipping Planes: (Near: " + this.nearClipPlane + ", Far: " + this.farClipPlane + ")");
    }
}
