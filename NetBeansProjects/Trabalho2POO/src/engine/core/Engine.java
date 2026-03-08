package engine.core;

import engine.games.Game;
import engine.scene.Scene;
import engine.scene.GameObject;

public class Engine {
 
     public static void main(String[] args) {
        System.out.println("### INÍCIO DO TESTE DA TAREFA A1 ###");
        
        Game meuJogo = new Game();
        System.out.println("LOG: Jogo padrão criado.");
        meuJogo.display();
        
         System.out.println("--- Editando o jogo ---");
         
         meuJogo.addScene(new Scene("Fase da Floresta"));
         meuJogo.addScene(new Scene("Castelo na Floresta"));
         meuJogo.display();
         
         meuJogo.writeSceneToFile("Main Scene", "main_scene.dat");
           
        meuJogo.removeScene("Fase da Floresta");
        meuJogo.display();
        
        meuJogo.removeAllScenes();
        System.out.println("LOG: Jogo esvaziado para teste de carregamento.");
        meuJogo.display();
        
        System.out.println("--- Carregando cena do arquivo... ---");
        meuJogo.readSceneFromFile("main_scene.dat");
        
        meuJogo.display();
        
        //Até aqui é o A1 do Trabalho
        
        System.out.println("\n--- Testando métodos da Cena (A2) ---");
        Scene mainScene = meuJogo.getScene("Main Scene");
            if(mainScene != null) {
                mainScene.addGameObject(new GameObject("Inimigo 1", mainScene));
                mainScene.display();
                mainScene.removeGameObject("Cube");
                mainScene.display();
            }
            
        System.out.println("\n\n--- INÍCIO DO TESTE DA TAREFA A3 ---");

        // Reutilizamos a cena principal para os testes

        if (mainScene != null) {
            // Pegamos a câmera como nosso GameObject de teste
            GameObject cameraGO = mainScene.getGameObject("Main Camera");

            if (cameraGO != null) {

                // Teste 1: Hierarquia e Adição de Filhos
                // ------------------------------------------
                System.out.println("\n[A3] Teste 1: Adicionando um GameObject filho...");
                GameObject Lente = new GameObject("Lente da Câmera", mainScene);
                cameraGO.addChild(Lente);

                // Teste 2: Regras de Gerenciamento de Componentes
                // -------------------------------------------------
                System.out.println("\n[A3] Teste 2: Regras de Componentes...");

                System.out.println("--> Tentando adicionar um componente Camera duplicado:");
                cameraGO.addComponent(new engine.core.Camera(cameraGO, ProjectionType.PERSPECTIVA, 75.0, 0.1, 1000.0)); // Deve dar erro

                System.out.println("\n--> Tentando remover o componente Transform:");
                cameraGO.removeComponent("Transform"); // Deve dar erro

                System.out.println("\n--> Obtendo um componente com getComponent:");
                Transform transform = cameraGO.getComponent(Transform.class);
                if (transform != null) {
                    System.out.println("LOG: Sucesso! Componente 'Transform' obtido via getComponent.");
                } else {
                    System.err.println("LOG: FALHA ao obter o componente 'Transform'.");
                }
            }

            // Teste 3: Exibição Completa da Hierarquia
            // -----------------------------------------
            System.out.println("\n[A3] Teste 3: Exibição da Hierarquia Completa da Cena:");
            mainScene.display();
        }

        System.out.println("\n--- FIM DO TESTE DA TAREFA A3 ---");
        
        
        System.out.println("\n\n--- INÍCIO DO TESTE DA TAREFA A4 ---");

        if (mainScene != null) {
            
            GameObject cameraGO = mainScene.getGameObject("Main Camera");
            
            System.out.println("\n[A4] Teste 1: Inspecionando o componente Mesh do 'Cube'...");
            GameObject cubeGO = mainScene.getGameObject("Cube");
            if (cubeGO != null) {
                Mesh meshComponent = cubeGO.getComponent(Mesh.class);
                if (meshComponent != null) {
                    System.out.println("LOG: Sucesso! Componente 'Mesh' obtido.");
                    // Chamando inspect() diretamente para verificar a saída detalhada.
                    meshComponent.inspect();
                }
            }

            System.out.println("\n[A4] Teste 2: Inspecionando o componente Light do 'Light'...");
            GameObject lightGO = mainScene.getGameObject("Light");
            if (lightGO != null) {
                Light lightComponent = lightGO.getComponent(Light.class);
                if (lightComponent != null) {
                    System.out.println("LOG: Sucesso! Componente 'Light' obtido.");
                    lightComponent.inspect();
                }
            }

            System.out.println("\n[A4] Teste 3: Inspecionando o componente Camera da 'Main Camera'...");
            // Reutilizando a variável cameraGO do teste da A3
            if (cameraGO != null) {
                Camera cameraComponent = cameraGO.getComponent(Camera.class);
                if (cameraComponent != null) {
                    System.out.println("LOG: Sucesso! Componente 'Camera' obtido.");
                    cameraComponent.inspect();
                }
            }
        }

        System.out.println("\n--- FIM DO TESTE DA TAREFA A4 ---");
        
        
        
        }
     
     
}
    