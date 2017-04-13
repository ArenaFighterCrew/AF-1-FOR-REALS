package af.pkg1;

import java.util.ArrayList;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author 1531826
 */
public class AF2 extends Application {
    private int windowWidth = 1080;
    private int windowHeight = 720;
    
    @Override
    public void start(Stage primaryStage) {
        ArrayList<Fighter> fighters = new ArrayList<Fighter>();
        
        int maxX = 5;
        int maxY = 5;
        
        for(int i = 1; i < maxX + 1; i++){
            for(int j = 1; j < maxY + 1; j++){
                Fighter f = new PlayerFighter();
                f.setLayoutX(windowWidth * i / (maxX + 1));
                f.setLayoutY(windowHeight * j / (maxY + 1));
                fighters.add(f);
            }
        }
        
        Pane root = new Pane();
        root.getChildren().addAll(fighters);
        
        Scene scene = new Scene(root, windowWidth, windowHeight);
        
        primaryStage.setTitle("AF-Test-1");
        primaryStage.setScene(scene);
        primaryStage.show();
        root.requestFocus();
        root.setSnapToPixel(false);
        
        EnergyAndForceManagerThread eafmt = new EnergyAndForceManagerThread();
        for(Fighter fig: fighters){
            eafmt.addFighter(fig);
        }
        
        while(true){
            if(!eafmt.isAlive()){
                eafmt.start();
                System.out.println("Manager thread started");
                break;
            }
        }
        
        Force rightForce = new Force(150, 0);
        Force leftForce = (Force)rightForce.getInverse();
        
        Force upForce = new Force(-150, Math.PI / 2);
        Force downForce = (Force)upForce.getInverse();
        
        PlayerFighter player1 = new PlayerFighter();
        
        root.setOnKeyPressed(e ->{
            //PLAYER 1 CONTROL PRESSES
            if(e.getCode() == KeyCode.D && !player1.isRight){
                for(Fighter f: fighters){
                    f.addForce(0, rightForce);
                }
                player1.isRight = true;
                System.out.println("Right force added");
            }
            else if(e.getCode() == KeyCode.W && !player1.isUp){
                for(Fighter f: fighters){
                    f.addForce(1, upForce);
                }
                player1.isUp = true;
                System.out.println("Up force added");
            }
            else if(e.getCode() == KeyCode.A && !player1.isLeft){
                for(Fighter f: fighters){
                    f.addForce(2, leftForce);
                }
                player1.isLeft = true;
                System.out.println("Left force added");
            }
            else if(e.getCode() == KeyCode.S && !player1.isDown){
                for(Fighter f: fighters){
                    f.addForce(3, downForce);
                }
                player1.isDown = true;
                System.out.println("Down force added");
            }
        });
        
        root.setOnKeyReleased(e ->{
            //PLAYER 1 CONTROL RELEASES
            if(e.getCode() == KeyCode.D && player1.isRight){
                for(Fighter f: fighters){
                    f.removeForce(0);
                }
                player1.isRight = false;
                System.out.println("Right force removed");
            }
            if(e.getCode() == KeyCode.W && player1.isUp){
                for(Fighter f: fighters){
                    f.removeForce(1);
                }
                player1.isUp = false;
                System.out.println("Up force removed");
            }
            else if(e.getCode() == KeyCode.A && player1.isLeft){
                for(Fighter f: fighters){
                    f.removeForce(2);
                }
                player1.isLeft = false;
                System.out.println("Left force removed");
            }
            else if(e.getCode() == KeyCode.S && player1.isDown){
                for(Fighter f: fighters){
                    f.removeForce(3);
                }
                player1.isDown = false;
                System.out.println("Down force removed");
            }
        });
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
