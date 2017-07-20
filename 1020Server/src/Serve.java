

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Serve {
	java.net.ServerSocket serve;
	ArrayList<Client> cl = new ArrayList<>();//存放客户端的数组，用于服务器向客户端发送消息
	
	//创建一个服务器
	public void creatServe(int port){
		try {
			serve = new java.net.ServerSocket(port);//创建在端口号为port的端口创建一个服务器
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//连接客户端的方法
	public void connect() throws IOException{
		 while (true) {
			 System.out.println("等待客户端接入……");   
			 
			 // server尝试接收其他Socket的连接请求，server的accept方法是阻塞式的
	         Socket socket = serve.accept();
	        
	         Client newc = new Client(socket,"",""); //实例化一个Client类的对象。参数为：socket、ID、name
	         cl.add(newc);
	         System.out.println("客户接入");
	         
	         //每接收到一个新的Socket就建立一个新的线程来处理它
	         Task t = new Task(cl,socket);
		 }
	}

}
	

