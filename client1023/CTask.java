package com.client1023;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import javax.swing.JTextArea;


public class CTask extends Thread{//client 的线程，用来不断地接收从服务器传来的字节
	
	JTextArea message1;
	Socket socket;
	InputStream in;
	DataInputStream datain;
	String s1,s2;
	String s;
	char c;
	
	//构造方法接收两个框和客户端
	public CTask(JTextArea message1,DataInputStream datain) {
		this.message1 = message1;
		this.datain = datain;
		this.start();

	}

	//创建一个线程，接收从服务器传来的消息
	public void run() {
		while(true){
		try {
			int lenth = datain.readInt();//消息长度
			byte[] b= new byte[lenth];
			//从输入流读取字符
			datain.read(b);//消息内容
			if(b!=null){
				String s = new String(b);
				
				System.out.println("客户端接受到的消息："+s);
//				System.out.println(message1);
				message1.setText(message1.getText()+s+"\r\n");//把字符串显示在对话框上，但是不能清空原有的对话记录，因此显示的内容加上message1.getText()
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
	}
	
	
	
	

}
