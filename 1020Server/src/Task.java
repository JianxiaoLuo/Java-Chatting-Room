
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Task extends Thread{//服务器的线程，不断接收客户端传来的字节
		private Socket socket;
		InputStream in;
		DataInputStream data;
		DataInputStream datain;
		ArrayList<Client> cl;
		char c;
		String s;
		String idS;
		
		public Task(ArrayList<Client> cl,Socket socket){//需要的参数是存放了客户端的数组还有当前客户端的socket
			this.cl = cl;
			this.socket = socket;
			System.out.println("线程开始");
			this.start();
		}
		
		public void run() {
			while(true)
			handlerSocket();
		}
		
		//接收客户端传来的消息
		private void handlerSocket() {
			try {
				//创建缓冲流，接受读取的数据
				in = socket.getInputStream();
				datain = new DataInputStream(in);
				
				int chat =  datain.read();//聊天类型
				
				switch(chat){
				case 0:{
					this.signin();//登录信息
					break;
				}
				case 1:{
					this.toAll();//群聊信息
					break;
				}
				case 2:{
					this.toOne();//私聊信息
					break;
				}
		}} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
		
	public void toAll(){//群聊的方法
		System.out.print("聊天类型为群聊   ");
		int type;
		
		try {//消息的类型
			type = datain.read();
			
			switch (type){//根据消息不同采用不同的方式读
			
			case 1:{//当消息类型是字符串时
				System.out.print("群聊类型为String   ");
				int lenth = 0;
				lenth = datain.readInt();//接收消息的长度
				System.out.print("消息长度是："+lenth);//因为lenth被用来当做字节数组的长度，所以，传过来的lenth一定要是byte的长度
				byte[] b = new byte[lenth];
				datain.read(b);//接收消息
				
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
				lenth = datain.readInt();//接收消息的长度
				System.out.print("  消息长度是："+lenth);
				byte[] b = new byte[lenth];
				datain.read(b);
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
		System.out.print("聊天类型为私聊   ");
		int flenth;//私聊好友的ID的长度
		try {
			flenth = datain.readInt();
			byte[] friend = new byte[flenth];
			datain.read(friend);//接收对方的ID
			
			String f = new String(friend);
			
			System.out.print("私聊对象是："+f+"  ");
			
			int type = datain.read();//接收消息的类型
			
			switch(type){
			case 1:{//接收消息的类型是String
				System.out.print("私聊类型为String   内容为：");
				int lenth = datain.readInt();//接收消息的长度
				byte[] mess = new byte[lenth];
				datain.read(mess);//接收消息
				
				String info = new String(mess);
				String s = idS+":"+info;
				System.out.println(mess.toString());
				for(int i=0;i<cl.size();i++){
					if(f.equals(cl.get(i).getId())){//判断该ID是否为传送对象的id
						DataOutputStream dataout = new DataOutputStream(cl.get(i).getSocket().getOutputStream());
						dataout.writeInt(s.getBytes().length);
						dataout.write(s.getBytes());
						break;
					}
				}
				break;
			}
			default:{
				
				int lenth = datain.readInt();//接收消息的长度
				byte[] mess = new byte[lenth];
				datain.read(mess);//接收消息
				System.out.print("私聊类型为其他"+type+" 长度为："+lenth+"   内容为：");
				System.out.println(mess.toString());
				break;
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
			signin = datain.read();
			System.out.print("聊天类型为登录信息   ");
			
			switch(signin){
			case 1:{//登录信息为1 账号
				int lenth = datain.readInt();//消息的长度
				byte[] id = new byte[lenth];
				datain.read(id);
				
				idS = new String(id);
				cl.get(cl.size()-1).setId(idS);//把该Client的账号设置成输入的账号
				System.out.print("登录信息为1 账号   账号为：");
				System.out.println(idS);
				break;
			}
			default:{
				int lenth = datain.readInt();//消息的长度
				byte[] id = new byte[lenth];
				datain.read(id);
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
}



