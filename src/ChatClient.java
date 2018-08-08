import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This class is for chat room for client. 
 * This will start from the CharClientExe and will run the windows for client 
 * It contains GUI for client and will get message from server and display it to all clients
 * @author hyejeongkim
 *
 */
public class ChatClient implements ChatClientInterface {
	private String name;
	private Socket socket;
	private TextField textF =new TextField();
	private TextArea textA = new TextArea();
	private BufferedReader receiveRead;
	private PrintWriter out ;
	/**
	 * CharClient constructor with two parameter. 
	 * @param String name
	 * @param Socket socket
	 */
	public ChatClient(String name, Socket socket)
	{
		this.name = name;
		this.socket = socket;
		VBox vbox = new VBox();
		vbox.getChildren().addAll(textF, textA);
		Stage stage = new Stage();
		stage.setScene(new Scene(vbox));
		stage.setTitle("Chat Room");
		stage.show();

		InputStream istream;
		
		try {
			istream = socket.getInputStream();

		     receiveRead = new BufferedReader(new InputStreamReader(istream));
		     out = new PrintWriter(socket.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * inherit from runnable
	 * it will make its own thread and if it get a message it will broadcast to all clients. 
	 */
	@Override
	public void run() {
		
		//System.out.println("ChatClient run");
		try{

				Thread newThread = new Thread(new Runnable(){	
				public void run(){
					//System.out.println("ChatClient thread ");

					String content = "";
					while(true){
						try {
							content += receiveRead.readLine().substring(8)+"\n";
							textA.setText(content);

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
			
				} 
			});
            newThread.start();

		 } catch (Exception e1) {
				e1.printStackTrace();
		}
		

		textF.setOnAction(e ->{
			out.println(textF.getText());
			out.flush();
			textF.setText("");
		});
		
}
    /**
     * get name method of the client
     * @return String name
     */
	@Override
	public String getName() {
		return name;
	}
    /**
     * get server port 
     * @return socket.getPort()
     */
	@Override
	public int getServerPort() {
		return socket.getPort();
	}

}
