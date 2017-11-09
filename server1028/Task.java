package com.server1028;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;


/**
 * This class receive the bytes from clients.
 * @author Charlotte
 *
 */
public class Task extends Thread{//���������̣߳����Ͻ��տͻ��˴������ֽ�
		private Socket socket;
		DataOutputStream dos;
		DataInputStream dis;
		ArrayList<Client> cl;
		char c;
		String s;
		String idS;
	    
		//Define constants represent different types.
		public static final int LOGIN = 1;
		public static final int SIGNIN = 2;
		public static final int GROUP = 3;
		public static final int ONLY = 4;
		public static final int OFFLINE = 5;
		
		public Task(ArrayList<Client> cl,Socket socket){//��Ҫ�Ĳ����Ǵ���˿ͻ��˵����黹�е�ǰ�ͻ��˵�socket
			this.cl = cl;
			this.socket = socket;
			System.out.println("�߳̿�ʼ");
			this.getstreams();
			this.start();
		}
		
		public void run() {
			while(true)
			handlerSocket();
		}
		
		/**
		 * This class get streams of the socket.
		 */
		private void getstreams(){
			try {
				
				dis = new DataInputStream(socket.getInputStream());
				dos = new DataOutputStream(socket.getOutputStream());
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//���տͻ��˴�������Ϣ
		private void handlerSocket() {
			try {
				int chat =  dis.read();//��������
				
				switch(chat){
				case LOGIN:{
					System.out.println("1Login");
					this.login();//��¼��Ϣ
					break;
				}
				case SIGNIN:{
					this.signin();//ע����Ϣ
					break;
				}
				case GROUP:{
					this.toOne();//Ⱥ����Ϣ
					break;
				}
				case ONLY:{
					this.toOne();//˽����Ϣ
					break;
				}
				
				case OFFLINE:{
					this.offline();//������Ϣ
					break;
				}
		}} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
		
	private void login() {
			// TODO Auto-generated method stub
		try {
			int namelen = dis.readInt();
			byte[] name = new byte[namelen];
			dis.read(name);
			
			String uname = new String(name);
			
			int passlen = dis.readInt();
			byte[] pass = new byte[passlen];
			dis.read(pass);
		
			String upass = new String(pass);
			
			System.out.println("Server Task Username:"+uname+" UserPass:"+upass);
		
			//�˴���Ӳ�ѯ�����������û������������ȷ��
			if(true){
			dos.writeInt(1);
			Client newc = new Client(socket,uname,uname); //ʵ����һ��Client��Ķ��󡣲���Ϊ��socket��ID��name
	        cl.add(newc);
	        System.out.println("һ�����û����ߣ�nameΪ"+uname);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
	}

	public void toAll(){//Ⱥ�ĵķ���
		System.out.print("��������ΪȺ��   ");
		int type;
		
		try {//��Ϣ������
			type = dis.read();
			
			switch (type){//������Ϣ��ͬ���ò�ͬ�ķ�ʽ��
			
			case 1:{//����Ϣ�������ַ���ʱ
				System.out.print("Ⱥ������ΪString   ");
				int lenth = 0;
				lenth = dis.readInt();//������Ϣ�ĳ���
				System.out.print("��Ϣ�����ǣ�"+lenth);//��Ϊlenth�����������ֽ�����ĳ��ȣ����ԣ���������lenthһ��Ҫ��byte�ĳ���
				byte[] b = new byte[lenth];
				dis.read(b);//������Ϣ
				
				//toString��new String�����𣿣�
				String s = new String(b);
				System.out.println("   Ⱥ����ϢΪ��"+s);
				
				if(b!=null){//���ַ�����Ϊ��ʱ��ִ��Ⱥ������
	            	System.out.println("���������յ���"+b.toString());
	            	if(cl.size()>1){//�������Ŀͻ��˵ĸ�������һ��������Ⱥ���ı�Ҫ
	            		for(int i=0;i<cl.size();i++){
	            			if(!socket.equals(cl.get(i).getSocket()) && cl.get(i)!=null){//�����Լ�֮��Ŀͻ���
	            				DataOutputStream dataout = new DataOutputStream(cl.get(i).getSocket().getOutputStream());
	            				try{
	            				//���͵��ͻ��˵�ҲҪ����Э�飬����Ҫ�Ƿ����������ݵ��ֽڳ���
	            				dataout.writeInt((idS+":"+s).getBytes().length);
	            				dataout.write((idS+":"+s).getBytes());
	            				System.out.println(i+"����������ɹ�����");}
	            				catch (Exception e){
	            					cl.set(i,null);
	            				}
	            			}
	            		}
	            	}
	            }
				break;
			}
			default:{
				System.out.print("Ⱥ����Ϣ������"+type);
				int lenth = 0;
				lenth = dis.readInt();//������Ϣ�ĳ���
				System.out.print("  ��Ϣ�����ǣ�"+lenth);
				byte[] b = new byte[lenth];
				dis.read(b);
				System.out.println("��Ϣ�����ǣ�"+b);
				break;
			}
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void toOne(){//˽�ĵķ���
		System.out.print("˽��   ");
		
		int ulength;//�����ߵ�ID����
		int tlength;
		int flength;//˽�ĺ��ѵ�ID�ĳ���
		int melength;//��Ϣ�ĳ���
		String uname;//�����ߵ�ID
		String time;
		String message;//��Ϣ
		String f;//�����ߵ�ID
		
		try {
			//���շ����ߵ���Ϣ
			ulength = dis.readInt();
			byte[] sender = new byte[ulength];
			dis.read(sender);//���շ����ߵ�ID
			
			tlength = dis.readInt();
			byte[] tbyte = new byte[tlength];
			dis.read(tbyte);
			
			melength = dis.readInt();
			byte[] mes = new byte[melength];
			dis.read(mes);
			
			flength = dis.readInt();
			byte[] friend = new byte[flength];
			dis.read(friend);//���նԷ���ID
			
			uname = new String(sender);
			message = new String(mes);
			f = new String(friend);
			
			System.out.println(uname+" ������ "+message+"��"+f);
			
			//ת�������շ�
			boolean ok = false;
			for(int i = 0;i<cl.size();i++){
				if(f.equals(cl.get(i).getId())){
					System.out.println("�ҵ���idΪ"+f);
					Socket re = cl.get(i).getSocket();
					DataOutputStream senddos = new DataOutputStream(re.getOutputStream());
					senddos.writeInt(4);
					
					senddos.writeInt(ulength);
					senddos.write(sender);
					
					senddos.writeInt(tlength);
					senddos.write(tbyte);
					
					senddos.writeInt(melength);
					senddos.write(mes);
					ok=true;
				}
			}
			if (ok==false){
				System.out.println("������˵���Է�������"+f);
				for(int i =0;i<cl.size();i++){
					if(cl.get(i).getId().equals(uname)){
						System.out.println("�������ҵ��˷��ͷ�������״̬");
						DataOutputStream dos = new DataOutputStream(cl.get(i).getSocket().getOutputStream());
						String s = "�Է�������";
						dos.writeInt(6);
						dos.writeInt(s.getBytes().length);
						dos.write(s.getBytes());
						
						dos.writeInt(flength);
						dos.write(friend);
					}
				}
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void signin(){//��¼��Ϣ�ķ���
		int signin;
		try {
			signin = dis.read();
			System.out.print("��������Ϊ��¼��Ϣ   ");
			
			switch(signin){
			case 1:{//��¼��ϢΪ1 �˺�
				int lenth = dis.readInt();//��Ϣ�ĳ���
				byte[] id = new byte[lenth];
				dis.read(id);
				
				idS = new String(id);
				cl.get(cl.size()-1).setId(idS);//�Ѹ�Client���˺����ó�������˺�
				System.out.print("��¼��ϢΪ1 �˺�   �˺�Ϊ��");
				System.out.println(idS);
				break;
			}
			default:{
				int lenth = dis.readInt();//��Ϣ�ĳ���
				byte[] id = new byte[lenth];
				dis.read(id);
				String idS = new String(id);
				System.out.print("��¼��ϢΪ����  "+signin+"����Ϊ��"+lenth+" ����Ϊ��");
				System.out.println(idS);
				break;
			}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void offline(){
		
		int ulength;
		int tlength;
		byte[] ubyte;
		byte[] tbyte;
		String uname;
		String time;
		
		try {
			System.out.println("������Ϣ");
			
			ulength = dis.readInt();
			ubyte = new byte[ulength];
			dis.read(ubyte);
			uname = new String(ubyte);
						
			tlength = dis.readInt();
			tbyte = new byte[tlength];
			dis.read(tbyte);
			time = new String(tbyte);
			
			System.out.println(uname+"�û���"+time+"ʱ�䣬���ߣ�");
			
			for(int i=0;i<cl.size();i++){
				if(cl.get(i).getId().equals(uname)){
					cl.remove(i);
					break;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}



