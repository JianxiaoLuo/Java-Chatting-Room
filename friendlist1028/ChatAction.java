package com.friendlist1028;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/**
 * 聊天窗口的监听类
 * @author Charlotte
 *
 */
public class ChatAction implements ActionListener{
	String f;
	String uname;
	JTextPane message1;
	JTextField message2;
	DataOutputStream dos;
	DateFormat format;
	String time;
	Date date;

	public ChatAction(String f,JTextPane message1,JTextField message2){
		this.f = f;
		this.message1 = message1;
		this.message2 = message2;
		dos = FriendList.getDos();
		this.uname = FriendList.getUname();
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println(uname+"发送给："+f+message2.getText());
		
		date=new Date();
		format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		time=format.format(date);
		
		
		try {
			showText();//显示到message1上
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		
		//发送消息的规则
		try {
			dos.writeInt(4);
			
			dos.writeInt(uname.getBytes().length);
			dos.write(uname.getBytes());
			
			dos.writeInt(time.getBytes().length);
			dos.write(time.getBytes());
			
			dos.writeInt(message2.getText().getBytes().length);
			dos.write(message2.getText().getBytes());
			
			dos.writeInt(f.getBytes().length);
			dos.write(f.getBytes());
			
			message2.setText("");//传输完后再清空输入框
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
	}
	
	
	private void showText() throws Exception{
		
		DefaultStyledDocument doc=(DefaultStyledDocument)message1.getDocument();//A document that can be marked up with character and paragraph styles in a manner similar to the Rich Text Format. 
		SimpleAttributeSet a = new SimpleAttributeSet(); //Creates a new attribute set.
		
		a = new SimpleAttributeSet();
		
		StyleConstants.setForeground(a,Color.GREEN);//Sets the foreground color.
	    doc.insertString(doc.getLength(),uname+"  "+time+"\n",a);
		
	    StyleConstants.setForeground(a,Color.BLACK);
	    doc.insertString(doc.getLength(),message2.getText()+"\n",a);
		
		
	    
	    
	    
	    
//	    doc.setCharacterAttributes(0,2,a,false);//设置指定范围的文字样式
	              
		
	}

}
