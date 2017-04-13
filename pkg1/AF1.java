package af.pkg1;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 * @author 1531826
 */
public class AF1 extends Application {
    private int windowWidth = 1080;
    private int windowHeight = 720;
    
    //http://www.dummies.com/education/science/physics/how-to-calculate-velocities-of-two-objects-with-different-masses-after-an-elastic-collision/
    
    @Override
    public void start(Stage primaryStage) {
        Color c = Color.CYAN.deriveColor(1, 1, 0.9, 0.8);
        
        Fighter player1 = new PlayerFighter();
        player1.setLayoutX(windowWidth/4);
        player1.setLayoutY(windowHeight/2);
        player1.setColor(c);
        
        Fighter player2 = new PlayerFighter();
        player2.setLayoutX(windowWidth*3/4);
        player2.setLayoutY(windowHeight/2);
        player2.setColor(c.invert());
        
        Pane root = new Pane();
        root.getChildren().addAll(player1, player2);
        
        Scene scene = new Scene(root, windowWidth, windowHeight);
        
        primaryStage.setTitle("AF-1");
        primaryStage.setScene(scene);
        primaryStage.show();
        root.requestFocus();
        root.setSnapToPixel(false);
        
        
        
        EnergyAndForceManagerThread eafmt = new EnergyAndForceManagerThread();
        eafmt.addFighter(player1);
        eafmt.addFighter(player2);
        
        while(true){
            if(!eafmt.isAlive()){
                eafmt.start();
                System.out.println("Force/Energy Manager thread started");
                break;
            }
        }
        
        Force rightForce = new Force(155, 0);
        Force leftForce = (Force)rightForce.getInverse();
        
        Force upForce = new Force(-155, Math.PI / 2);
        Force downForce = (Force)upForce.getInverse();
        
        root.setOnKeyPressed(e ->{
            //PLAYER 1 CONTROL PRESSES
            if(e.getCode() == KeyCode.D && !player1.isRight){
                player1.isRight = true;
                player1.addForce(0, rightForce);
                System.out.println("Right force added");
            }
            else if(e.getCode() == KeyCode.W && !player1.isUp){
                player1.isUp = true;
                player1.addForce(1, upForce);
                System.out.println("Up force added");
            }
            else if(e.getCode() == KeyCode.A && !player1.isLeft){
                player1.isLeft = true;
                player1.addForce(2, leftForce);
                System.out.println("Left force added");
            }
            else if(e.getCode() == KeyCode.S && !player1.isDown){
                player1.isDown = true;
                player1.addForce(3, downForce);
                System.out.println("Down force added");
            }
            else if(e.getCode() == KeyCode.SPACE){
                player1.collideWith(player2);
            }
            
            //PLAYER 2 CONTROL PRESSES
            if(e.getCode() == KeyCode.RIGHT && !player2.isRight){
                player2.isRight = true;
                player2.addForce(0, rightForce);
                System.out.println("Right force added");
            }
            else if(e.getCode() == KeyCode.UP && !player2.isUp){
                player2.isUp = true;
                player2.addForce(1, upForce);
                System.out.println("Up force added");
            }
            else if(e.getCode() == KeyCode.LEFT && !player2.isLeft){
                player2.isLeft = true;
                player2.addForce(2, leftForce);
                System.out.println("Left force added");
            }
            else if(e.getCode() == KeyCode.DOWN && !player2.isDown){
                player2.isDown = true;
                player2.addForce(3, downForce);
                System.out.println("Down force added");
            }
        });
        
        root.setOnKeyReleased(e ->{
            //PLAYER 1 CONTROL RELEASES
            if(e.getCode() == KeyCode.D && player1.isRight){
                player1.isRight = false;
                player1.removeForce(0);
                System.out.println("Right force removed");
            }
            if(e.getCode() == KeyCode.W && player1.isUp){
                player1.isUp = false;
                player1.removeForce(1);
                System.out.println("Up force removed");
            }
            else if(e.getCode() == KeyCode.A && player1.isLeft){
                player1.isLeft = false;
                player1.removeForce(2);
                System.out.println("Left force removed");
            }
            else if(e.getCode() == KeyCode.S && player1.isDown){
                player1.isDown = false;
                player1.removeForce(3);
                System.out.println("Down force removed");
            }
            
            //PLAYER 2 CONTROL RELEASES
            if(e.getCode() == KeyCode.RIGHT && player2.isRight){
                player2.isRight = false;
                player2.removeForce(0);
                System.out.println("Right force removed");
            }
            if(e.getCode() == KeyCode.UP && player2.isUp){
                player2.isUp = false;
                player2.removeForce(1);
                System.out.println("Up force removed");
            }
            else if(e.getCode() == KeyCode.LEFT && player2.isLeft){
                player2.isLeft = false;
                player2.removeForce(2);
                System.out.println("Left force removed");
            }
            else if(e.getCode() == KeyCode.DOWN && player2.isDown){
                player2.isDown = false;
                player2.removeForce(3);
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
