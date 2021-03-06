package Game;
import javafx.application.Platform;
import java.util.*;
import Game.Objects.*;
       

public class Core implements Runnable {
    GameModel model;
    GameView view;
    GameController controller;
    private long dtime = 20;
    private long totalTime = 0;
    private double random;
    
    // Constructor
    public Core(GameModel model, GameView view, GameController controller) {
        this.model = model;
        this.view = view;
        this.controller = controller;
    }
    
    @Override
    public void run() {
        int err = 0;
        while (true) {
            try {
                // Run dit in de JavaFX thread
                Platform.runLater(() -> {
                    view.render();
                    
                    controller.lblScore.setText("Score: "+model.getScore());
                    //controller.lblScore.setText("Score: "+model.getEntities().size());
                    controller.lblLevens.setText("Levens: "+model.getPlayer().getLevens());
                });
                model.update(); 
                
                /**
                 * Random vijand toevoegen
                 */  
                if (totalTime % 1000 ==0){ 
                    random = Math.random()*20;
                    if(random < 10){
                        random = 10;
                    }
                    model.addEnemy(Math.random()*360,random);
                }
                
                /**
                 * Random upgrade toevoegen om de 10 seconden
                 */
                if (totalTime % 10000 ==0){ 
                    random = Math.random()*20;
                    if(random < 10){
                        random = 10;
                    }
                    model.addUpgrade(Math.random()*380,random); 
                }
                
                /**
                 * Automatisch schieten voor de speler
                 */
                if (totalTime % 200 ==0) {
                    model.addBullets(totalTime);
                }
                
                /**
                 * Wacht even voor geen 100% cpu
                 */
                Thread.sleep(dtime); 
                totalTime = totalTime + dtime;
            }catch(Exception e) {
             /*   err++;
                System.out.println("ERROR : run() : "+e.getMessage());
                if (err > 100){
                    System.out.println("ERROR: run() : Too many errors! :(");
                    return;
                }*/
            }
        }
    }
}
