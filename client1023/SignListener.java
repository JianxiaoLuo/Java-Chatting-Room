package com.client1023;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.friendlist1022.FriendList;

/**
 * This class listen the button on sign in UI.
 * If user click '登录',it will send the content in textfield to server.
 * so it has to implement 3 method
 * 1.actionPerformed
 * 2.an construct method which can accept the content from the Signin UI.
 * 3.init the socket and get streams.
 * @author Charlotte
 *
 */
public class SignListener implements ActionListener{
	/**
	 * uname:user name
	 * upass:user password
	 */
	String uname,upass;
	Socket socket;
	DataInputStream dis;
	DataOutputStream dos;
	JTextField jt1;
	JPasswordField jt2;
	JFrame jf1;
	
	
	/**
	 * The construct method to get the username and userpassword.
	 * @param uname
	 * @param upass
	 */
	public SignListener(JTextField jt1, JPasswordField jt2,JFrame jf1) {
		super();
		this.jt1 = jt1;
		this.jt2 = jt2;
		this.jf1 = jf1;
		
	}

	/**
	 * Initial Socket
	 */
	public void initSocket(){
		try {
			socket = new Socket("localhost",10003);
			System.out.println("Create Socket successfully!" );
		} catch (UnknownHostException e) {
			System.out.println("UnknownHost");
		} catch (IOException e) {
		    System.out.println("创建客户端失败！");
		}
	}
	
	public void getstream(){
		try {
			//获取输出流
			dos = new DataOutputStream(socket.getOutputStream());
			dis = new DataInputStream(socket.getInputStream());
			System.out.println("Get streams successfully!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if("Log in".equals(e.getActionCommand())){
			uname = jt1.getText();
			upass = jt2.getText();
			System.out.println("登录  "+"user name:"+uname+"user password"+upass);
			
			//点击了登录时，再初始化socket和获取stream
			initSocket();
			getstream();
			
			//Send data to server.
			try {
//				dos.writeInt("+##+".getBytes().length);//length of "+##+"
//				dos.write("+##+".getBytes());//"+##+"
				dos.writeInt(1);
				dos.writeInt(uname.getBytes().length);//length of username
				dos.write(uname.getBytes());//byte[] username
				dos.writeInt(upass.getBytes().length);//length of user password
				dos.write(upass.getBytes());//byte[] password
				
				int ok = dis.readInt();
				System.out.println(ok);
				if(ok==1){
					ChatData cd= new ChatData(uname,dis,dos);
//					FriendList fl = new FriendList(uname,dos,dis); //1022版本的friendlist
					com.friendlist1028.FriendList fl = new com.friendlist1028.FriendList(uname,dos,dis);
					fl.show1();
				}
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			jf1.setVisible(false);
		}
		else if(e.getActionCommand().equals("Sign in")){
			System.out.println("注册  "+"user name:"+uname+"user password"+upass);
		
		}
		
	}
	
	

	
}
