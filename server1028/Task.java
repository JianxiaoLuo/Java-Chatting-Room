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
public class Task extends Thread{//服务器的线程，不断接收客户端传来的字节
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
		
		public Task(ArrayList<Client> cl,Socket socket){//需要的参数是存放了客户端的数组还有当前客户端的socket
			this.cl = cl;
			this.socket = socket;
			System.out.println("线程开始");
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
		
		//接收客户端传来的消息
		private void handlerSocket() {
			try {
				int chat =  dis.read();//聊天类型
				
				switch(chat){
				case LOGIN:{
					System.out.println("1Login");
					this.login();//登录信息
					break;
				}
				case SIGNIN:{
					this.signin();//注册信息
					break;
				}
				case GROUP:{
					this.toOne();//群聊信息
					break;
				}
				case ONLY:{
					this.toOne();//私聊信息
					break;
				}
				
				case OFFLINE:{
					this.offline();//下线信息
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
		
			//此处添加查询方法，检验用户名和密码的正确性
			if(true){
			dos.writeInt(1);
			Client newc = new Client(socket,uname,uname); //实例化一个Client类的对象。参数为：socket、ID、name
	        cl.add(newc);
	        System.out.println("一个新用户上线，name为"+uname);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
	}

	public void toAll(){//群聊的方法
		System.out.print("聊天类型为群聊   ");
		int type;
		
		try {//消息的类型
			type = dis.read();
			
			switch (type){//根据消息不同采用不同的方式读
			
			case 1:{//当消息类型是字符串时
				System.out.print("群聊类型为String   ");
				int lenth = 0;
				lenth = dis.readInt();//接收消息的长度
				System.out.print("消息长度是："+lenth);//因为lenth被用来当做字节数组的长度，所以，传过来的lenth一定要是byte的长度
				byte[] b = new byte[lenth];
				dis.read(b);//接收消息
				
				//toString和new String的区别？？
				String s = new String(b);
				System.out.println("   群聊信息为："+s);
				
				if(b!=null){//当字符串不为空时，执行群发操作
	            	System.out.println("服务器接收到："+b.toString());
	            	if(cl.size()>1){//如果接入的客户端的个数超过一个，则有群发的必要
	            		for(int i=0;i<cl.size();i++){
	            			if(!socket.equals(cl.get(i).getSocket()) && cl.get(i)!=null){//发给自己之外的客户端
	            				DataOutputStream dataout = new DataOutputStream(cl.get(i).getSocket().getOutputStream());
	            				try{
	            				//发送到客户端的也要符合协议，长度要是发送所有内容的字节长度
	            				dataout.writeInt((idS+":"+s).getBytes().length);
	            				dataout.write((idS+":"+s).getBytes());
	            				System.out.println(i+"服务器传输成功！！");}
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
				System.out.print("群聊消息是其他"+type);
				int lenth = 0;
				lenth = dis.readInt();//接收消息的长度
				System.out.print("  消息长度是："+lenth);
				byte[] b = new byte[lenth];
				dis.read(b);
				System.out.println("消息内容是："+b);
				break;
			}
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void toOne(){//私聊的方法
		System.out.print("私聊   ");
		
		int ulength;//发送者的ID长度
		int tlength;
		int flength;//私聊好友的ID的长度
		int melength;//消息的长度
		String uname;//发送者的ID
		String time;
		String message;//消息
		String f;//接受者的ID
		
		try {
			//接收发送者的消息
			ulength = dis.readInt();
			byte[] sender = new byte[ulength];
			dis.read(sender);//接收发送者的ID
			
			tlength = dis.readInt();
			byte[] tbyte = new byte[tlength];
			dis.read(tbyte);
			
			melength = dis.readInt();
			byte[] mes = new byte[melength];
			dis.read(mes);
			
			flength = dis.readInt();
			byte[] friend = new byte[flength];
			dis.read(friend);//接收对方的ID
			
			uname = new String(sender);
			message = new String(mes);
			f = new String(friend);
			
			System.out.println(uname+" 发送了 "+message+"给"+f);
			
			//转发给接收方
			boolean ok = false;
			for(int i = 0;i<cl.size();i++){
				if(f.equals(cl.get(i).getId())){
					System.out.println("找到了id为"+f);
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
				System.out.println("服务器说：对方不在线"+f);
				for(int i =0;i<cl.size();i++){
					if(cl.get(i).getId().equals(uname)){
						System.out.println("服务器找到了发送方，发送状态");
						DataOutputStream dos = new DataOutputStream(cl.get(i).getSocket().getOutputStream());
						String s = "对方不在线";
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
	
	public void signin(){//登录信息的方法
		int signin;
		try {
			signin = dis.read();
			System.out.print("聊天类型为登录信息   ");
			
			switch(signin){
			case 1:{//登录信息为1 账号
				int lenth = dis.readInt();//消息的长度
				byte[] id = new byte[lenth];
				dis.read(id);
				
				idS = new String(id);
				cl.get(cl.size()-1).setId(idS);//把该Client的账号设置成输入的账号
				System.out.print("登录信息为1 账号   账号为：");
				System.out.println(idS);
				break;
			}
			default:{
				int lenth = dis.readInt();//消息的长度
				byte[] id = new byte[lenth];
				dis.read(id);
				String idS = new String(id);
				System.out.print("登录信息为其他  "+signin+"长度为："+lenth+" 内容为：");
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
			System.out.println("下线信息");
			
			ulength = dis.readInt();
			ubyte = new byte[ulength];
			dis.read(ubyte);
			uname = new String(ubyte);
						
			tlength = dis.readInt();
			tbyte = new byte[tlength];
			dis.read(tbyte);
			time = new String(tbyte);
			
			System.out.println(uname+"用户于"+time+"时间，下线！");
			
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



