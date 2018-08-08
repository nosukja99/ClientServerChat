import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * The client follows the Chat Protocol which is as follows.
 * When the server sends "SUBMITNAME" the client replies with the
 * desired screen name.  The server will keep sending "SUBMITNAME"
 * requests as long as the client submits screen names that are
 * already in use.  When the server sends a line beginning
 * with "NAMEACCEPTED" the client is now allowed to start
 * sending the server arbitrary strings to be broadcast to all
 * chatters connected to the server.  When the server sends a
 * line beginning with "MESSAGE " then all characters following
 * this string should be displayed in its message area.
 * @author hyejeongkim
 *
 */
public class ChatClientExec implements ChatClientExecInterface{
	int iPort = 6000;
	/**
	 * set port number if there is a input
	 * @param int port
	 */
	public void setPort(int port)
	{
		int iPort = port;
	}
	/**
	 * this method is for the protocol such as asked screen name and answer to it
	 * it will check the message from server and get the screen name and wait until the name accepted
	 * after get the nameaccepted message from server it will start chat client class with new thread for each client. 
	 */
	@Override
	public void startClient() throws Exception {
        Socket sock = new Socket("localhost", iPort);
		
		//Scanner scanner = new Scanner(sock.getInputStream());
		PrintWriter printWriter = new PrintWriter(sock.getOutputStream());
		InputStream istream = sock.getInputStream();
		BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));
		String name = "";
		String message = "";
		
		while(true)
		{
			message = receiveRead.readLine();
			//System.out.println(message);
			//System.out.println("chatClientExec While");
		
			while(message.equals("SUBMITNAME")){
				//System.out.println("while - ");
				name = JOptionPane.showInputDialog(null,"Choose a screen name: ", "Screen name selection");
				printWriter.println(name);
				printWriter.flush();
				message = receiveRead.readLine();
				
				if(message.equals("NAMEACCEPTED")){
					//System.out.println("Client: NAMEACCEPTED");

					ChatClient chatClient = new ChatClient(name, sock);
					Thread thCC = new Thread(chatClient);
					thCC.start();
					return;
				}
			}

		}
	}
}

