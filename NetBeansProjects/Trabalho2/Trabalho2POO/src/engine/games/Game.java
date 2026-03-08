package engine.games;

import engine.scene.GameObject;
import engine.scene.Scene;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.io.File;
import java.util.Scanner;
import java.util.Stack;

public class Game {
    private List<Scene> scenes;
    
    public Game(){
        this.scenes = new ArrayList<>();
        this.scenes.add(new Scene("Main Scene"));
    }
    
    public void addScene(Scene newScene){
        this.scenes.add(newScene);
        System.out.println("LOG: Cena " + newScene.getName() + " adicionado ao jogo.");
    }
    
    public void saveGameToFile(String filePath){
        try (PrintWriter writer  = new PrintWriter(filePath, "UTF-8")){
            System.out.println("LOG: Iniciando salvamento do jogo em " + filePath + "...");
            
            for (Scene scene : scenes) {
            scene.saveToFile(writer);
            }
            
            System.out.println("LOG: Jogo completo salvo com sucesso.");
            
        }catch(IOException e){
            System.err.println("LOG: ERRO ao salvar o jogo" + e.getMessage());
        }
    }
    
    public void saveSceneToFile(String sceneName, String filePath) {
        Scene sceneToSave = getScene(sceneName);
        if (sceneToSave == null) {
            System.err.println("LOG: ERRO - Cena '" + sceneName + "' não encontrada para salvar.");
            return;
        }
        try (PrintWriter writer = new PrintWriter(filePath, "UTF-8")) {
            sceneToSave.saveToFile(writer);
            System.out.println("LOG: Cena '" + sceneName + "' salva no arquivo '" + filePath + "'.");
        } catch (IOException e) {
            System.err.println("LOG: ERRO ao salvar a cena: " + e.getMessage());
        }
    }
    
    public static Game loadFromFile(String filePath){
        Game game = new Game();
        game.removeAllScenes();
        
        try(Scanner scanner = new Scanner(new File(filePath))){
            Scene currentScene = null;
            
            Stack<GameObject> parentStack = new Stack<>();
            
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                String trimmedLine = line.trim();
                
                if(trimmedLine.startsWith("Scene:")){
                    currentScene = new Scene(trimmedLine.substring("Scene:".length()));
                    currentScene.removeAllGameObjects();
                    game.addScene(currentScene);
                }else if(trimmedLine.startsWith("GameObject:")){
                    if(currentScene != null){
                        GameObject newGO = new GameObject(trimmedLine.substring("GameObject:".length()), currentScene);
                        if (parentStack.isEmpty()) {
                            currentScene.addGameObject(newGO);
                        } else {
                            parentStack.peek().addChild(newGO);
                        }
                        parentStack.push(newGO);
                    }
                    
                }else if (trimmedLine.equals("EndGameObject")) {
                    if (!parentStack.isEmpty()) {
                        parentStack.pop();
                    }
                }
            }
            System.out.println("LOG: Jogo carregado de '" + filePath + "'.");
        }catch (IOException e) {
        System.err.println("LOG: ERRO ao carregar o jogo: " + e.getMessage());
        return null;
        }
        return game;
        }
    
    public void removeScene(String sceneName) {
        boolean removed = this.scenes.removeIf(scene -> scene.getName().equals(sceneName));
        if (removed) {
            System.out.println("LOG: Cena " + sceneName + " removida.");
        } else {
            System.err.println("LOG: ERRO - Cena " + sceneName + " não encontrada para remoção.");
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
