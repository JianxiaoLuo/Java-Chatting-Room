package com.client1023;

import java.awt.Color;
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
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.friendlist1022.FriendList;


public class SignUI{//客户端界面和发送消息
	private Socket socket;
	OutputStream out;
	DataOutputStream dataout;
	DataInputStream datain;
	JTextArea message1;//对话框
	JTextField message2;//发送框
	JTextField jt;//输入账号信息的文本框
	JTextField jt1;//输入賬號的文本框
	JPasswordField jt2;
	JTextField fid;//输入私聊对象的文本框
	JFrame jf1;
	String s,id;
	
	ArrayList<String> chat;
	
	public static void main(String[] args) {
		SignUI sui = new SignUI();
		sui.information();
	}
	

	//登录信息
	public void information(){
		
		jf1 = new JFrame();
		jf1.setBackground(Color.black);
		jf1.setSize(450,350);
		jf1.setDefaultCloseOperation(1);
		jf1.setLocationRelativeTo(null);
		jf1.setTitle("登录");
//		jf.setResizable(false);
		jf1.setLayout(new FlowLayout());
		
		//account
		JLabel jl1 = new JLabel("Name:");
		jl1.setFont(new Font("微软雅黑",0,20));
		
		jt1 = new JTextField();
		jt1.setFont(new Font("微软雅黑",0,18));
		jt1.setPreferredSize(new Dimension(300,35));
		
		//password
		JLabel jl2 = new JLabel("Password:");
		jl2.setFont(new Font("微软雅黑",0,20));
				
		jt2 = new JPasswordField();
		jt2.setFont(new Font("微软雅黑",0,18));
		jt2.setPreferredSize(new Dimension(300,35));
		
		//登录按钮
		JButton jb1 = new JButton("Log in");
//		jb1.addActionListener(this);
		
		//sign in
		JButton jb2 = new JButton("Sign in");
//		jb2.addActionListener(this);
		
		jf1.add(jl1);
		
		jf1.add(jt1);
		jf1.add(jl2);
		jf1.add(jt2);
		jf1.add(jb1);
		jf1.add(jb2);
		
		SignListener sl = new SignListener(jt1,jt2,jf1);
		jb1.addActionListener(sl);
		jb2.addActionListener(sl);
		//This code should be wrote before setVisible.
		
		jf1.setVisible(true);
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
