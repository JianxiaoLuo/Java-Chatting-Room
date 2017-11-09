package com.friendlist1028;

import java.awt.Color;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/**
 * 4.连接数据库    ——>设计方法？
 * 5.好友列表xml
 * @author Charlotte
 *
 */
public class Receive extends Thread{
	DataInputStream dis;
	ArrayList<ChatUI> chat;
	public static final int GROUP = 3;
	public static final int ONLY = 4;
	public static final int STATE = 6;
	
	public Receive(DataInputStream dis, ArrayList<ChatUI> chat){
		this.dis = dis;
		this.chat = chat;
		
	}
	
	public void run(){
		System.out.println("Recive Started!");
		while(true){
			int slength;
			int tlength;
			int melength;
			String sender;
			String time;
			String message;
			int type;
			
			
			
			try {
				type = dis.readInt();
				switch(type){
				case ONLY:{
					slength = dis.readInt();
					byte[] s = new byte[slength];
					dis.read(s);
					sender = new String(s);
					
					tlength = dis.readInt();
					byte[] t = new byte[tlength];
					dis.read(t);
					time = new String(t);
					
					melength = dis.readInt();
					byte[] m = new byte[melength];
					dis.read(m);
					message = new String(m);
				
					System.out.println("接收到"+sender+"朋友发来的消息："+message);
					
					boolean ok=false;
					for(int i=0;i<chat.size();i++){
						if(sender.equals(chat.get(i).f)){
							System.out.println("找到了聊天窗口"+sender);
							
							JTextPane message1 = chat.get(i).message1;
							DefaultStyledDocument doc=(DefaultStyledDocument)message1.getDocument();//A document that can be marked up with character and paragraph styles in a manner similar to the Rich Text Format. 
							SimpleAttributeSet a = new SimpleAttributeSet(); //Creates a new attribute set.
							
							a = new SimpleAttributeSet();
							
							StyleConstants.setForeground(a,Color.BLUE);//Sets the foreground color.
						    doc.insertString(doc.getLength(),sender+"  "+time+"\n",a);
							
						    StyleConstants.setForeground(a,Color.BLACK);
						    doc.insertString(doc.getLength(),message+"\n",a);
					
//							chat.get(i).message1.append(message+"\n");
						    
							ok=true;
						}
					}
					
					if(ok==false){
						ChatUI c = new ChatUI(sender);
						c.showUI();
						chat.add(c);
//						c.getMess1().append(message);
						
						JTextPane message1 = c.message1;
						DefaultStyledDocument doc=(DefaultStyledDocument)message1.getDocument();//A document that can be marked up with character and paragraph styles in a manner similar to the Rich Text Format. 
						SimpleAttributeSet a = new SimpleAttributeSet(); //Creates a new attribute set.
						
						StyleConstants.setForeground(a,Color.BLUE);//Sets the foreground color.
					    doc.insertString(doc.getLength(),sender+"  "+time+"\n",a);
//						chat.get(i).message1.append(message+"\n");
						
						a = new SimpleAttributeSet();
					    StyleConstants.setForeground(a,Color.BLACK);
					    doc.insertString(doc.getLength(),message+"\n",a);
						
							
					    
						
						System.out.println("chat中添加了一个窗口"+sender);
					}
					
				}
				case STATE:{
					int mlength;
					byte[] mbyte;
					String state;
					int flength;
					byte[] friend;
					String f;
					
					mlength = dis.readInt();
				    mbyte = new byte[mlength];
				    dis.read(mbyte);
				    state = new String(mbyte);
				    
				    flength = dis.readInt();
				    friend = new byte[flength];
				    dis.read(friend);
				    f = new String(friend);
				    
				    System.out.println("我发送给"+f+"的消息状态为 "+state);
				}
				}
				
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	}
	

}
