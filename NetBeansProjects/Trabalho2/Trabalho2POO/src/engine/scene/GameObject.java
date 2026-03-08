package engine.scene;

import engine.core.Component;
import engine.core.Transform;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.io.PrintWriter;

public class GameObject {
    private String name;
    private List<Component> components;
    private List<GameObject> children;
    private GameObject parent;
    private Scene scene;
    
    public GameObject(String name, Scene scene) {
        this.name = name;
        this.scene = scene;
        this.components = new ArrayList<>();
        this.children = new ArrayList<>();
        this.parent = null;
        this.components.add(new Transform(this)); 
    }
    
    public <T extends Component> void addComponent(T newComponent) {
        for (Component existingComponent : components) {
            if (existingComponent.getClass() == newComponent.getClass()) {
                System.err.println("LOG: ERRO - GameObject '" + name + "' já possui um componente do tipo '" + newComponent.getClassName() + "'.");
                return;
            }
        }
        this.components.add(newComponent);
    }
    
    public <T extends Component> T getComponent(Class<T> componentClass) {
        for (Component c : components) {
            if (componentClass.isInstance(c)) {
                return componentClass.cast(c);
            }
        }
        return null;
    }
    
    public void removeComponent(String className) {
        if (className.equals("Transform")) {
            System.err.println("LOG: ERRO - O componente Transform não pode ser removido.");
            return;
        }
        boolean removed = this.components.removeIf(c -> c.getClassName().equals(className));
        if (!removed) {
            System.err.println("LOG: ERRO - Componente '" + className + "' não encontrado em '" + name + "'.");
        }
    }
    
    public void removeAllComponents(){
        this.components.removeIf(component -> !(component instanceof Transform));
    }
    
    public void forEachComponent(Consumer<Component> action) {
        this.components.forEach(action);
    }
    
    public void addChild(GameObject child) {
        child.parent = this; // Define o pai do filho
        this.children.add(child);
    }
    
    public void removeChild(String childName) {
        this.children.removeIf(child -> child.getName().equals(childName));
    }
    
    public void removeAllChildren() {
        this.children.clear();
    }
    
    public void forEachChild(Consumer<GameObject> action) {
        this.children.forEach(action);
    }
    
    public GameObject getChild(String childName) {
        for (GameObject child : children) {
            if (child.getName().equals(childName)) {
                return child;
            }
        }
        return null;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void display(String indent) {
        String parentName = (this.parent != null) ? this.parent.getName() : "null";
        System.out.println(indent + "GameObject: " + this.name + " (Pai: " + parentName + ")");
        
        System.out.println(indent + "Components");
        for (Component component : components) {
            component.inspect();
        }
        
        if (!children.isEmpty()) {
            System.out.println(indent + "   Filho:");
            for (GameObject fi : children) {
                fi.display(indent + "    "); // Aumenta a identação para os filhos
            }
      
        }
    }
    
    public void saveToFile(PrintWriter writer, int depth){
        
        String indent = "\t".repeat(depth);
        
        writer.println(indent + "GameObject: " + this.name);
        
        for(GameObject child : children){
            child.saveToFile(writer, depth + 1);
        }
        
        writer.println(indent + "EndGameObject");
    }
}
