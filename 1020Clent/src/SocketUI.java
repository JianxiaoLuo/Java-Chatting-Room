import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.friendlist.FriendList;


public class SocketUI implements ActionListener{//客户端界面和发送消息
	private Socket socket;
	OutputStream out;
	DataOutputStream dataout;
	DataInputStream datain;
	JTextArea message1;//对话框
	JTextField message2;//发送框
	JTextField jt;//输入账号信息的文本框
	JTextField fid;//输入私聊对象的文本框
	JFrame jf1;
	String s,id;
	
	ArrayList<String> chat;
	
	public static void main(String[] args) {
		SocketUI sui = new SocketUI();
		sui.information();
	}
	
	public void initSocket(){
		try {
			Socket s = new Socket("localhost",10003);
			this.socket = s;
		} catch (UnknownHostException e) {
			System.out.println("UnknownHost");
		} catch (IOException e) {
			System.out.println("创建客户端失败！");
		}
	}
	
	//登录信息
	public void information(){
		
		jf1 = new JFrame();
		jf1.setSize(500,200);
		jf1.setDefaultCloseOperation(3);
		jf1.setLocationRelativeTo(null);
		jf1.setTitle("客户端");
//		jf.setResizable(false);
		jf1.setLayout(new FlowLayout());
		
		//账号
		JLabel jl = new JLabel("ID:");
		
		jt = new JTextField();
		jt.setPreferredSize(new Dimension(450,20));
		
		//登录按钮
		JButton jb = new JButton("Confirm");
		jb.addActionListener(this);
		
		jf1.add(jl);
		jf1.add(jt);
		jf1.add(jb);
		jf1.setVisible(true);
		
	}
	
	//聊天窗口
	public void show1(){
		JFrame jf = new JFrame();
		jf.setSize(600,500);
		jf.setDefaultCloseOperation(3);
		jf.setLocationRelativeTo(null);
		jf.setTitle(id);
		jf.setLayout(new FlowLayout());
		
		//对话框
		message1 = new JTextArea();
		message1.setFont(new Font("宋体",1,18));//设置字体格式
		message1.setPreferredSize(new Dimension(550, 200));
		jf.add(message1);
		
		//发送框
		message2 = new JTextField();
		message2.setFont(new Font("宋体",1,18));
		message2.setPreferredSize(new Dimension(550, 150));
		jf.add(message2);
		
		
		//私聊对象的信息
		JLabel jl = new JLabel("Friend:");
		fid = new JTextField();
		fid.setPreferredSize(new Dimension(200,20));
		JButton jb1 = new JButton("私聊");
		jb1.addActionListener(this);
		
		jf.add(jl);
		jf.add(fid);
		jf.add(jb1);
		
		//发送的按钮，按下后发送发送框的内容
		JButton bsend = new JButton("群聊");
		bsend.addActionListener(this);
		jf.add(bsend);
		
		
		jf.setVisible(true);
		CTask task = new CTask(message1,datain);//创建一个线程来接收服务器发来的消息
	}
	
	public void stream(){
		try {
			//获取输出流
			out = socket.getOutputStream();
			dataout = new DataOutputStream(out);
			datain = new DataInputStream(socket.getInputStream());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		if("Confirm".equals(e.getActionCommand())){
			id = jt.getText();
			jf1.setVisible(false);
			this.initSocket();
			this.stream();//获得输出流
			this.signin();//向服务器发送登录信息
		}
		else if("群聊".equals(e.getActionCommand()) && out!=null){
			this.toAll();//向服务器发送群聊信息
			
		}
		else if("私聊".equals(e.getActionCommand())){
			this.toOne();//向服务器发送私聊消息
		}
	}
	
	public void signin(){//把登录信息传输过去服务器
		if(id!=null){
			try {
				dataout.write(0);//聊天类型：0 登录信息
				dataout.write(1);//消息类型：1 账号
				dataout.writeInt(id.getBytes().length);//消息长度
				dataout.write(id.getBytes());//消息内容
				System.out.println("登录信息的长度："+id.getBytes().length+"  内容："+id.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.show1();
			FriendList fl = new FriendList(dataout,datain);
			fl.show1();
		}
	}
	
	public void toAll(){//向服务器发送群聊信息
		//获取输入框里的内容
		s = message2.getText();

		//显示在上面的对话框
		message1.setText(message1.getText()+"\r\n"+s+"\r\n");
		message2.setText("");//清空输入框

		//向输出流传入字节
		try {
			dataout.write(1);//聊天类型，群聊
//			dataout.writeInt(id.getBytes().length);//发消息的人
//			dataout.write(id.getBytes());
			dataout.write(1);//消息类型，字符串
			dataout.writeInt(s.getBytes().length);//要写入字节的长度
			dataout.write(s.getBytes());//向输出流写入数据
			
			System.out.println("客户端"+this.id+" 发出群聊消息长度为："+s.getBytes().length+"  内容是："+s);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void toOne(){
		//获取输入框里的内容
		s = message2.getText();
		message1.setText(message1.getText()+"\r\n"+s+"\r\n");
		message2.setText("");//清空输入框
		if(fid.getText()!=null){
			try {
				dataout.write(2);//聊天类型：私聊
//				dataout.writeInt(id.getBytes().length);//发消息的人
//				dataout.write(id.getBytes());
				dataout.writeInt(fid.getText().getBytes().length);//客户端的号码的长度
				dataout.write(fid.getText().getBytes());//客户端的号码
				dataout.write(1);//消息类型：String
				dataout.writeInt(s.getBytes().length);//消息长度
				dataout.write(s.getBytes());//消息内容
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		else JOptionPane.showMessageDialog(null, "ID不合法！");
	}
	

	
}
