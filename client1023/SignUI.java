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


public class SignUI{//�ͻ��˽���ͷ�����Ϣ
	private Socket socket;
	OutputStream out;
	DataOutputStream dataout;
	DataInputStream datain;
	JTextArea message1;//�Ի���
	JTextField message2;//���Ϳ�
	JTextField jt;//�����˺���Ϣ���ı���
	JTextField jt1;//�����~̖���ı���
	JPasswordField jt2;
	JTextField fid;//����˽�Ķ�����ı���
	JFrame jf1;
	String s,id;
	
	ArrayList<String> chat;
	
	public static void main(String[] args) {
		SignUI sui = new SignUI();
		sui.information();
	}
	

	//��¼��Ϣ
	public void information(){
		
		jf1 = new JFrame();
		jf1.setBackground(Color.black);
		jf1.setSize(450,350);
		jf1.setDefaultCloseOperation(1);
		jf1.setLocationRelativeTo(null);
		jf1.setTitle("��¼");
//		jf.setResizable(false);
		jf1.setLayout(new FlowLayout());
		
		//account
		JLabel jl1 = new JLabel("Name:");
		jl1.setFont(new Font("΢���ź�",0,20));
		
		jt1 = new JTextField();
		jt1.setFont(new Font("΢���ź�",0,18));
		jt1.setPreferredSize(new Dimension(300,35));
		
		//password
		JLabel jl2 = new JLabel("Password:");
		jl2.setFont(new Font("΢���ź�",0,20));
				
		jt2 = new JPasswordField();
		jt2.setFont(new Font("΢���ź�",0,18));
		jt2.setPreferredSize(new Dimension(300,35));
		
		//��¼��ť
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
	
	
	public void toAll(){//�����������Ⱥ����Ϣ
		//��ȡ������������
		s = message2.getText();

		//��ʾ������ĶԻ���
		message1.setText(message1.getText()+"\r\n"+s+"\r\n");
		message2.setText("");//��������

		//������������ֽ�
		try {
			dataout.write(1);//�������ͣ�Ⱥ��
//			dataout.writeInt(id.getBytes().length);//����Ϣ����
//			dataout.write(id.getBytes());
			dataout.write(1);//��Ϣ���ͣ��ַ���
			dataout.writeInt(s.getBytes().length);//Ҫд���ֽڵĳ���
			dataout.write(s.getBytes());//�������д������
			
			System.out.println("�ͻ���"+this.id+" ����Ⱥ����Ϣ����Ϊ��"+s.getBytes().length+"  �����ǣ�"+s);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void toOne(){
		//��ȡ������������
		s = message2.getText();
		message1.setText(message1.getText()+"\r\n"+s+"\r\n");
		message2.setText("");//��������
		if(fid.getText()!=null){
			try {
				dataout.write(2);//�������ͣ�˽��
//				dataout.writeInt(id.getBytes().length);//����Ϣ����
//				dataout.write(id.getBytes());
				dataout.writeInt(fid.getText().getBytes().length);//�ͻ��˵ĺ���ĳ���
				dataout.write(fid.getText().getBytes());//�ͻ��˵ĺ���
				dataout.write(1);//��Ϣ���ͣ�String
				dataout.writeInt(s.getBytes().length);//��Ϣ����
				dataout.write(s.getBytes());//��Ϣ����
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		else JOptionPane.showMessageDialog(null, "ID���Ϸ���");
	}
	

	
}
