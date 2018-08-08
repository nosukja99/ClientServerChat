import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;
import java.util.Scanner;
/**
 * When a client connects the
 * server requests a screen name by sending the client the
 * text "SUBMITNAME", and keeps requesting a name until
 * a unique one is received.  After a client submits a unique
 * name, the server acknowledges with "NAMEACCEPTED".  Then
 * all messages from that client will be broadcast to all other
 * clients that have submitted a unique screen name.  The
 * broadcast messages are prefixed with "MESSAGE "
 * @author hyejeongkim
 *
 */
public class ServerThreadForClient implements Runnable{
	private PrintWriter printWriter;
	private Scanner scanner;
	private HashSet<String> screenNames;
	private HashSet<PrintWriter> hsWriters;
	private BufferedReader br;
	private Socket socket;
	private String clName; 
	/**
	 * constructor with three parameter 
	 * @param socket s
	 * @param HashSet<PrinterWriter> hs
	 * @param String name
	 */
	public ServerThreadForClient(Socket s, HashSet<PrintWriter> hs, String name)
	{
		hsWriters=hs;
		socket =s;
		clName = name;
		try{
			printWriter = new PrintWriter(socket.getOutputStream());
			InputStream istream = socket.getInputStream();
		    br = new BufferedReader(new InputStreamReader(istream));
		     } catch(IOException e){
			e.printStackTrace();
		}
	}
	/**
	 * this method is to read message form client and display message to all client. 
	 */
	@Override
	public void run() {
		String message = "";
		//System.out.println("Server thread for Client: "+Thread.currentThread().getName() + clName);

		while(true){
			try {
				message = br.readLine();
				for (PrintWriter p: hsWriters)
				{
					p.println("MESSAGE "+clName+": "+message);
					p.flush();
				}
			} catch (IOException e) {
					e.printStackTrace();
			}
		}
	}

}