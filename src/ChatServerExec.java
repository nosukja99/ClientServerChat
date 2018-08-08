import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
/**
 * A multithreaded chat room server.  
 * The  chat server executive calls startServer, which runs 
 * ChatServer in a thread, in order not to block the GUI.
 * @author hyejeongkim
 *
 */
public class ChatServerExec implements ChatServerExecInterface{
	private int CAHT_ROOM_PORT;
	
	/** 
	 * constructor with chat room port number
	 * @param int CAHT_ROOM_PORT
	 */
	public ChatServerExec(int CAHT_ROOM_PORT)
	{
		this.CAHT_ROOM_PORT = CAHT_ROOM_PORT;
	}
	/**
	 * make a new thread for chat server class
	 * and start the thread
	 */
	@Override
	   public void startServer(){	
		ChatServer cs = new ChatServer(CAHT_ROOM_PORT);
		Thread thCS = new Thread(cs);
		thCS.start();
	}
}