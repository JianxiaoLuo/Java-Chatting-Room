

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
	ArrayList<Client> cl = new ArrayList<>();//��ſͻ��˵����飬���ڷ�������ͻ��˷�����Ϣ
	
	//����һ��������
	public void creatServe(int port){
		try {
			serve = new java.net.ServerSocket(port);//�����ڶ˿ں�Ϊport�Ķ˿ڴ���һ��������
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//���ӿͻ��˵ķ���
	public void connect() throws IOException{
		 while (true) {
			 System.out.println("�ȴ��ͻ��˽��롭��");   
			 
			 // server���Խ�������Socket����������server��accept����������ʽ��
	         Socket socket = serve.accept();
	        
	         Client newc = new Client(socket,"",""); //ʵ����һ��Client��Ķ��󡣲���Ϊ��socket��ID��name
	         cl.add(newc);
	         System.out.println("�ͻ�����");
	         
	         //ÿ���յ�һ���µ�Socket�ͽ���һ���µ��߳���������
	         Task t = new Task(cl,socket);
		 }
	}

}
	

