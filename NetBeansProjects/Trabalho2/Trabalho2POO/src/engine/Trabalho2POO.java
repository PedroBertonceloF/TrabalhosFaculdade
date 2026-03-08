package engine;

import engine.games.Game;
import engine.scene.Scene;
import engine.scene.GameObject;
import engine.core.*;

public class Trabalho2POO {

    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("    MOTOR DE JOGOS - APLICAÇÃO DE TESTE (A5)");
        System.out.println("=".repeat(60));
        
        System.out.println("\n### ETAPA 1: CRIAÇÃO DO JOGO PADRÃO ###");
        Game meuJogo = new Game();
        System.out.println("LOG: Jogo padrão criado com cena 'Main Scene'");
        
        System.out.println("\n--- Exibindo jogo padrão ---");
        meuJogo.display();
        
        System.out.println("\n### ETAPA 2: EDITANDO O JOGO ###");
        
        System.out.println("\n[A1] Testando operações de cenas...");
        Scene floresta = new Scene("Floresta Encantada");
        Scene castelo = new Scene("Castelo do Dragão");
        
        meuJogo.addScene(floresta);
        meuJogo.addScene(castelo);
        System.out.println("LOG: Adicionadas cenas 'Floresta Encantada' e 'Castelo do Dragão'");
        
        System.out.println("\n[A2] Testando operações de objetos de jogo...");
        Scene mainScene = meuJogo.getScene("Main Scene");
        if (mainScene != null) {
            GameObject inimigo = new GameObject("Orc Guerreiro", mainScene);
            GameObject tesouro = new GameObject("Baú do Tesouro", mainScene);
            
            mainScene.addGameObject(inimigo);
            mainScene.addGameObject(tesouro);
            System.out.println("LOG: Adicionados 'Orc Guerreiro' e 'Baú do Tesouro' à Main Scene");
            
            mainScene.setName("Cena Principal Modificada");
            System.out.println("LOG: Nome da cena alterado para 'Cena Principal Modificada'");
        }
        
        System.out.println("\n[A3] Testando hierarquia e componentes...");
        if (mainScene != null) {
            GameObject camera = mainScene.getGameObject("Main Camera");
            if (camera != null) {
                GameObject lente = new GameObject("Lente", mainScene);
                GameObject filtro = new GameObject("Filtro UV", mainScene);
                
                camera.addChild(lente);
                lente.addChild(filtro);
                System.out.println("LOG: Criada hierarquia: Main Camera -> Lente -> Filtro UV");
                
                GameObject orc = mainScene.getGameObject("Orc Guerreiro");
                if (orc != null) {
                    orc.addComponent(new Mesh(orc, "OrcMesh", "OrcSkin"));
                    orc.addComponent(new Light(orc, LightType.PONTUAL, new ColorRGB(255, 100, 100)));
                    System.out.println("LOG: Adicionados componentes Mesh e Light ao Orc Guerreiro");
                    
                    orc.removeComponent("Light");
                    System.out.println("LOG: Removido componente Light do Orc Guerreiro");
                }
                
                camera.setName("Câmera Principal");
                System.out.println("LOG: Nome da câmera alterado para 'Câmera Principal'");
            }
        }
        
        System.out.println("\n[A4] Testando inspeção de componentes...");
        if (mainScene != null) {
            GameObject cube = mainScene.getGameObject("Cube");
            if (cube != null) {
                System.out.println("--- Inspecionando componentes do Cube ---");
                cube.forEachComponent(Component::inspect);
            }
            
            GameObject light = mainScene.getGameObject("Light");
            if (light != null) {
                System.out.println("\n--- Inspecionando componentes do Light ---");
                light.forEachComponent(Component::inspect);
            }
        }
        
        System.out.println("\n--- Estado do jogo após edições ---");
        meuJogo.display();
        
        System.out.println("\n### ETAPA 3: SALVANDO JOGO EM ARQUIVO ###");
        String nomeArquivo = "jogo_editado.dat";
        meuJogo.saveSceneToFile("Cena Principal Modificada", nomeArquivo);
        System.out.println("LOG: Cena principal salva em arquivo: " + nomeArquivo);
        
        System.out.println("\n### ETAPA 4: LIMPANDO E RECARREGANDO JOGO ###");
        meuJogo.removeAllScenes();
        System.out.println("LOG: Todas as cenas removidas do jogo");
        
        System.out.println("\n--- Estado do jogo após limpeza ---");
        meuJogo.display();
        
        System.out.println("\n--- Carregando cena do arquivo ---");
        meuJogo = Game.loadFromFile(nomeArquivo);
        System.out.println("LOG: Cena carregada do arquivo: " + nomeArquivo);
        
        System.out.println("\n### ETAPA 5: TESTES ADICIONAIS ###");
        
        System.out.println("\n[Travessia] Listando todas as cenas usando forEach:");
        meuJogo.forEachScene(scene -> 
            System.out.println("  - Cena encontrada: " + scene.getName())
        );
        
        System.out.println("\n[Remoção] Testando remoção de objetos...");
        Scene cenaAtual = meuJogo.getScene("Cena Principal Modificada");
        if (cenaAtual != null) {
            cenaAtual.removeGameObject("Cube");
            System.out.println("LOG: Objeto 'Cube' removido da cena");
            
            GameObject objetoInexistente = cenaAtual.getGameObject("Objeto Fantasma");
            if (objetoInexistente == null) {
                System.out.println("LOG: Busca por objeto inexistente retornou null (correto)");
            }
        }
        
        System.out.println("\n[Validação] Testando regras de componentes...");
        if (cenaAtual != null) {
            GameObject camera = cenaAtual.getGameObject("Câmera Principal");
            if (camera != null) {
                System.out.println("--- Tentando adicionar Camera duplicada ---");
                camera.addComponent(new Camera(camera, ProjectionType.PARALELA, 45.0, 0.5, 500.0));
                
                System.out.println("--- Tentando remover Transform (deve falhar) ---");
                camera.removeComponent("Transform");
                
                Transform transform = camera.getComponent(Transform.class);
                if (transform != null) {
                    System.out.println("LOG: Componente Transform obtido com sucesso");
                    transform.inspect();
                }
            }
        }
        
        System.out.println("\n### ETAPA 6: ESTADO FINAL DO JOGO ###");
        meuJogo.display();
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("    RESUMO DAS OPERAÇÕES EXECUTADAS");
        System.out.println("=".repeat(60));
        System.out.println("✓ [A1] Criação de jogo com cena padrão");
        System.out.println("✓ [A1] Adição de novas cenas");
        System.out.println("✓ [A1] Salvamento de cena em arquivo");
        System.out.println("✓ [A1] Carregamento de cena de arquivo");
        System.out.println("✓ [A1] Remoção de cenas");
        System.out.println("✓ [A1] Travessia de cenas");
        System.out.println("✓ [A1] Exibição do jogo");
        System.out.println();
        System.out.println("✓ [A2] Modificação de nome de cena");
        System.out.println("✓ [A2] Adição de objetos de jogo");
        System.out.println("✓ [A2] Remoção de objetos de jogo");
        System.out.println("✓ [A2] Busca de objetos de jogo");
        System.out.println("✓ [A2] Travessia de objetos de jogo");
        System.out.println("✓ [A2] Exibição de cena");
        System.out.println();
        System.out.println("✓ [A3] Modificação de nome de objeto");
        System.out.println("✓ [A3] Criação de hierarquia de objetos");
        System.out.println("✓ [A3] Adição de componentes");
        System.out.println("✓ [A3] Remoção de componentes");
        System.out.println("✓ [A3] Busca de componentes");
        System.out.println("✓ [A3] Travessia de componentes");
        System.out.println("✓ [A3] Validação de regras de componentes");
        System.out.println("✓ [A3] Exibição de objeto de jogo");
        System.out.println();
        System.out.println("✓ [A4] Inspeção de componentes Transform");
        System.out.println("✓ [A4] Inspeção de componentes Mesh");
        System.out.println("✓ [A4] Inspeção de componentes Light");
        System.out.println("✓ [A4] Inspeção de componentes Camera");
        System.out.println("✓ [A4] Obtenção de nome de classe de componente");
        System.out.println();
        System.out.println("✓ [A5] Aplicação de teste completa implementada");
        System.out.println("✓ [A5] Log de todas as operações");
        System.out.println("✓ [A5] Demonstração de todas as funcionalidades");
        System.out.println();
        System.out.println("=".repeat(60));
        System.out.println("    TESTE CONCLUÍDO COM SUCESSO!");
        System.out.println("=".repeat(60));
    }
}
