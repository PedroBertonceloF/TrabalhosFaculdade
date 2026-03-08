package engine.games;

import engine.scene.Scene;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;
import java.util.function.Consumer;

public class Game {
    private List<Scene> scenes;
    
    public Game(){
        this.scenes = new ArrayList<>();
        this.scenes.add(new Scene("Main Scene"));
    }
    
    public void addScene(Scene newScene){
        this.scenes.add(newScene);
        System.out.println("LOG: Cena " + newScene.getName() + "adicionado ao jogo.");
    }
    
    public void writeSceneToFile(String sceneName, String filePath){
        Scene sceneToSave = getScene(sceneName);
        if(sceneToSave == null){
            System.out.println("LOG: ERRO - Cena" + sceneName + "não encontrado.");
            return;
        }
        
        try (PrintWriter writer  = new PrintWriter(filePath, "UTF-8")){
            sceneToSave.SaveToFile(writer);
            System.out.println("LOG: Cena " + sceneName + "salva no arquivo" + filePath);
        }catch(IOException e){
            System.err.println("LOG: ERRO ao escrever no arquivo" + e.getMessage());
        }
    }
    
    public void readSceneFromFile(String filePath){
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String line = reader.readLine();
            if(line != null && line.startsWith("SceneName:")){
                String sceneName = line.substring("SceneName:".length());
                this.addScene(new Scene(sceneName)); 
            }
        }catch(IOException e){
            System.err.println("LOG: Erro ao ler o arquivo da cena: " + e.getMessage());
        }
    }
    
    public void removeScene(String sceneName) {
        boolean removed = this.scenes.removeIf(scene -> scene.getName().equals(sceneName));
        if (removed) {
            System.out.println("LOG: Cena '" + sceneName + "' removida.");
        } else {
            System.err.println("LOG: ERRO - Cena '" + sceneName + "' não encontrada para remoção.");
        }
    }
    
    public void removeAllScenes() {
        this.scenes.clear();
        System.out.println("LOG: Todas as cenas foram removidas.");
    }
    
    public Scene getScene(String sceneName) {
        for (Scene scene : this.scenes) {
            if (scene.getName().equals(sceneName)) {
                return scene;
            }
        }
        return null; 
    }
    
    public void forEachScene(Consumer<Scene> action) {
        this.scenes.forEach(action);
    }
    
    public void display() {
        System.out.println("\n============== EXIBINDO JOGO ==============");
        if (this.scenes.isEmpty()) {
            System.out.println("O Jogo está vazio, sem cenas.");
        } else {
            // Usa o método de travessia para chamar o display de cada cena.
            this.forEachScene(scene->scene.display());
        }
        System.out.println("===========================================\n");
    }
}
