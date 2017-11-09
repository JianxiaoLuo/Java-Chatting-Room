package com.friendlist1028;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class ChatUI extends JFrame{
	JTextPane message1;
	JTextField message2;
	String f;
	ArrayList<ChatUI> chat;
	
//	public static void main(String args[]){
//		ChatUI c = new ChatUI();
//		c.showUI();
//	}
//	
	public ChatUI(){
		
	}
	
	public ChatUI(String f){
		this.f = f;
	}
	
	public String getFriend(){
		return f;
	}

	public void showUI(){
		//JFrame jf = new JFrame();
		this.setSize(600,500);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setTitle(f);
		this.setLayout(new FlowLayout());
		this.addWindowListener(new WindowAdapter() {
	        @Override
	        public void windowClosing(WindowEvent e) {
	        System.out.println("WindowClosing...");
	        }
	        @Override
	        public void windowClosed(WindowEvent e) {
	        System.out.println("WindowClosed...");
	        
	        chat = FriendList.getChat();
	        System.out.println(chat);
	        System.out.println(this);
	        for(int i=0;i<chat.size();i++){
	        	System.out.println("i="+i);
	        	System.out.println(chat.get(i));
	        	if(f.equals(chat.get(i).getTitle())){
	        		chat.remove(i);
	        		i--;
	        		System.out.println("Delete successfully...");
	        		break;
	        	}
	        }
	        }
	    });
		
		//对话框
		message1 = new JTextPane();
		message1.setPreferredSize(new Dimension(550, 200));
		message1.setEditable(false);//设置不可编辑
		
		JScrollPane scrollpane = new JScrollPane(message1);
		
		this.add(scrollpane);
		
		
		//发送框
		message2 = new JTextField();
		message2.setPreferredSize(new Dimension(550, 150));
//		message2.setHorizontalAlignment(JTextField.LEFT);
		this.add(message2);
		
		JButton jb1 = new JButton("发送");
		
		ChatAction ca = new ChatAction(f,message1,message2);
		jb1.addActionListener(ca);
		
		this.add(jb1);
		
		this.setVisible(true);
	}
	
	public JTextPane getMess1(){
		return message1;
	}
	
}
