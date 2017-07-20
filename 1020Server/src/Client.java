//用户的资料
import java.net.Socket;
import java.util.ArrayList;


public class Client {
	String id;
	String name;
	Socket socket;
	ArrayList<Friend> friend;
	
	
	public Client(Socket socket,String id,String name) {
		super();
		this.id = id;
		this.name = name;
		this.socket = socket;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	
	
}
