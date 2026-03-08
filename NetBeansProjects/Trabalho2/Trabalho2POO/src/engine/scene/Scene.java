package engine.scene;

import engine.core.Camera;
import engine.core.ColorRGB;
import engine.core.Light;
import engine.core.LightType;
import engine.core.Mesh;
import engine.core.ProjectionType;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
        
public class Scene {
    private String name;
    private List<GameObject> gameObjects;
    
    public Scene(String name){
        this.name = name;
        this.gameObjects = new ArrayList<>();
        
        GameObject cube = new GameObject("Cube", this);
        cube.addComponent(new Mesh(cube, "PrimitiveCube", "DefaultMaterial"));
        this.gameObjects.add(cube);
        
        GameObject light = new GameObject("Light", this);
        light.addComponent(new Light(light, LightType.DIRECIONAL, new ColorRGB(255,255,255)));
        this.gameObjects.add(light);
        
        GameObject camera = new GameObject("Main Camera", this);
        camera.addComponent(new Camera(camera, ProjectionType.PERSPECTIVA, 60.0, 0.1, 1000.0));
        this.gameObjects.add(camera);
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    //Adiciona novo objeto de jogo
    public void addGameObject(GameObject gameObject) {
        this.gameObjects.add(gameObject);
        // Futuramente, você precisará definir o pai e a cena do gameObject aqui.
    }
    
    //Remove dado o nome
    public void removeGameObject(String gameObjectName) {
        this.gameObjects.removeIf(go -> go.getName().equals(gameObjectName));
    }
    
    //Remove todos os objetos de jogo
    public void removeAllGameObjects() {
        this.gameObjects.clear();
    }
    
    //Acha o objeto pelo nome
    public GameObject getGameObject(String gameObjectName) {
        for (GameObject go : this.gameObjects) {
            if (go.getName().equals(gameObjectName)) {
                return go;
            }
        }
        return null;
    }
    
    //Tipo um iterator, vai consumindo cada função
    public void forEachGameObject(Consumer<GameObject> action) {
        this.gameObjects.forEach(action);
    }
    
    public void display(){
        System.out.println("--- Exibindo Cena: "+this.name + " ---");
        for(GameObject go: this.gameObjects){
            go.display("  ");
        }
    }
    
    public void saveToFile(PrintWriter writer){
        writer.println("SceneName: " + this.name);
        
        for(GameObject go: gameObjects){
            go.saveToFile(writer, 0);
        }
    
        writer.println("EndScene");
    }
}
