package engine.core;

import engine.scene.GameObject;

/**
 *
 * @author User
 */
public abstract class Component {
 
    protected final GameObject gameObject;
    
    
    public Component(GameObject gameObject){
        if(gameObject == null){
            throw new IllegalArgumentException("GameObject não pode ser null!");
        }
        this.gameObject = gameObject;
    }
    
    public GameObject getGameObject(){
        return gameObject;
    }
    
    //Da a classe mas não da o pacote por causa do Simple Name
    public  String getClassName(){
        return this.getClass().getSimpleName();
 }
    
    public abstract void inspect();
}
