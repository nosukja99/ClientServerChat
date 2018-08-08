import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
/**
 * GUI controller for chat room
 * @author hyejeongkim
 *
 */
public class GUIController extends Application {

	Button startServer, startClient, exitB;
	Label title1, title2;
	int count=0;
	int CAHT_ROOM_PORT = 6000;
	VBox upVbox; 
	HBox botHbox;
	Stage stage;
/**
 * start stage
 */
	@Override
	public void start(Stage arg0) throws Exception {
		startServer = new Button ("Start the Server");
		startClient = new Button ("Start each Client");
		exitB = new Button ("Exit");
		title1 = new Label ("Chat Room Controller");
		title2 = new Label ("1. Start the server. \n2. Start a client.  \n3. Enter a screen name in the client's GUI.  "
				+ "\n4. Start more clients. \n5. Enter a message in a client's GUI.");
		
		startServer.setOnAction(e->{
			if (count == 0)
			{
				ChatServerExec chatServerExec = new ChatServerExec(CAHT_ROOM_PORT);
				count ++;
				chatServerExec.startServer();
				System.out.println("after thread");
			}
			else if(count>0)
				JOptionPane.showMessageDialog(null, "The server is already on");
			});
		
		startClient.setOnAction(e ->{
			if (count == 0)
			{
				JOptionPane.showMessageDialog(null, "Error, Start the server first");
				
			}
			else
			{
				ChatClientExec chatClientExec = new ChatClientExec();
				chatClientExec.setPort(CAHT_ROOM_PORT);
				try {
					chatClientExec.startClient();
				} catch (Exception e1) {
			
					e1.printStackTrace();
				}
			}
		});
		exitB.setOnAction(e->{
			System.exit(0);
		});
		
		startServer.setTooltip(new Tooltip("Start the Server"));
		startClient.setTooltip(new Tooltip("Start each Client"));
		exitB.setTooltip(new Tooltip("Exit"));
		
		startServer.setMnemonicParsing(true);
		startServer.setText(" _Server");
		startClient.setMnemonicParsing(true);
		startClient.setText(" _Client");
		exitB.setMnemonicParsing(true);
		exitB.setText("_Exit");
		
		upVbox = new VBox();
		botHbox=new HBox();

		upVbox.getChildren().addAll(title1, title2);
		upVbox.setAlignment(Pos.CENTER);
		upVbox.setStyle("-fx-padding: 5;" + 
                "-fx-border-style: solid inside;" + 
                "-fx-border-width: 1;" +
                "-fx-border-insets: 10;" + 
                "-fx-border-radius: 0;" + 
                "-fx-border-color: black;");
		botHbox.getChildren().addAll(startServer, startClient, exitB);
		botHbox.setAlignment(Pos.CENTER);
		
		BorderPane MainPane = new BorderPane();
		Insets insets=new Insets(10);
		MainPane.setCenter(upVbox);
		MainPane.setBottom(botHbox);
		
		Scene scene = new Scene(MainPane, 800, 300); 
		stage = new Stage();	
		stage.setScene(scene);
		stage.setTitle("Chat Room Controller");
		stage.show();
	}
/**
 * main
 * @param String[] args
 */
	public static void main(String[] args) {
		launch(args);
	}
}
