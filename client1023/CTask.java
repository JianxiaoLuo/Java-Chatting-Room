package com.client1023;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import javax.swing.JTextArea;


public class CTask extends Thread{//client ���̣߳��������ϵؽ��մӷ������������ֽ�
	
	JTextArea message1;
	Socket socket;
	InputStream in;
	DataInputStream datain;
	String s1,s2;
	String s;
	char c;
	
	//���췽������������Ϳͻ���
	public CTask(JTextArea message1,DataInputStream datain) {
		this.message1 = message1;
		this.datain = datain;
		this.start();

	}

	//����һ���̣߳����մӷ�������������Ϣ
	public void run() {
		while(true){
		try {
			int lenth = datain.readInt();//��Ϣ����
			byte[] b= new byte[lenth];
			//����������ȡ�ַ�
			datain.read(b);//��Ϣ����
			if(b!=null){
				String s = new String(b);
				
				System.out.println("�ͻ��˽��ܵ�����Ϣ��"+s);
//				System.out.println(message1);
				message1.setText(message1.getText()+s+"\r\n");//���ַ�����ʾ�ڶԻ����ϣ����ǲ������ԭ�еĶԻ���¼�������ʾ�����ݼ���message1.getText()
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
	}
	
	
	
	

}
