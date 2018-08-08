import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

/**
 * chat server which implements Runnable
 * This top-level thread listens for a client to connect, 
 * asks for the client's Screen Name, checks it for duplication,
 * and if ok, starts a new thread by which the server will
 * interact with this client. 
 * @author hyejeongkim
 *
 */
public class ChatServer implements Runnable{
	private HashSet<String> screenNames;
	private HashSet<PrintWriter> hsWriters;
	private String name;
	private final String sNameRequest = "SUBMITNAME";
	private int CAHT_ROOM_PORT;
	/** 
	 * constructor with port number
	 * @param int icrpot
	 */
	public ChatServer(int icrpot)
	{
		CAHT_ROOM_PORT = icrpot;
		screenNames = new HashSet<String>();
		hsWriters = new HashSet<PrintWriter>();
	}
	/**
	 * constructor without port number
	 */
	public ChatServer( )
	{
		screenNames = new HashSet<String>();
		hsWriters = new HashSet<PrintWriter>();
	}	
	/**
	 * Override method
	 * listens for a client to connect, 
     * asks for the client's Screen Name, checks it for duplication,
     * and if ok, starts a new thread
     * This thread allows the server
     * to interact with many clients at once.  The names of all
     * clients are kept in a field HashSet<String> names to allow
     * checking for duplicate screen names, and the output streams 
     * are kept in a field named HashSet<PrintWriter> writers, in order
     * that each message can be retransmitted to all the clients.
	 */
	@Override
	public void run() {
		try {
			ServerSocket server = new ServerSocket(CAHT_ROOM_PORT);
			BufferedReader brBufferedReader = null;
			String clName = null;
			System.out.println("Waiting for clients to connect . . ." + this.CAHT_ROOM_PORT);

			while (true) {
			        Socket client = server.accept();
					System.out.println("Recieved connection from "+client.getInetAddress()+" on port "+client.getPort());
					PrintWriter printWriter = new PrintWriter(client.getOutputStream());

					printWriter.println(sNameRequest);
					printWriter.flush();

					brBufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));		

					while(true){
							clName = brBufferedReader.readLine();
							System.out.println(clName);
						    boolean blnExists = screenNames.contains(clName);
						    if(blnExists == false && !clName.equals("null"))
						    {
								printWriter.println("NAMEACCEPTED");
								printWriter.flush();
								screenNames.add(clName);
								hsWriters.add(printWriter);
								ServerThreadForClient ts = new ServerThreadForClient(client, hsWriters, clName);
								Thread thST = new Thread(ts);
								thST.start();
								break;
						    }else
						    {
								printWriter.println(sNameRequest);
								printWriter.flush();
						    }
					}
				}
			}catch(Exception ex){
				System.out.println(ex.getMessage());
			}
	}
}