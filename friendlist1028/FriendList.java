package com.friendlist1028;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.client1023.ChatData;


/**
 * �����б����ʾ��
 * uname:��ǰ�û���
 * @author Charlotte
 *
 */
public class FriendList extends JFrame{
	static String uname;
	Vector<Friend> v = new Vector<Friend>();
	Friend f;
	JTextArea message1;
	JTextField message2;
	static DataOutputStream dos;
	static DataInputStream dis;
	//��ȡ��ǰʱ��ı���
	DateFormat format;
	String time;
	Date date;
	
	static ArrayList<ChatUI> chat = new ArrayList<ChatUI>();//��¼�򿪵����촰��
	
/**
 * @param uname  user name of this user
 * @param dataout  streams of this socket
 * @param datain
 */
	public FriendList(String uname,DataOutputStream dataout, DataInputStream datain) {
		super();
		this.uname = uname;
		this.dos = dataout;
		this.dis = datain;
	}


	
	public void show1(){//����jList����ʾ
		//JFrame jf = new JFrame(uname);
		this.setSize(300, 600);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setTitle("�����б�");
		this.setLocation(1000, 300);
		
		Friend f1 = new Friend("111",001,"111.JPG","�ҽ���ÿ���~");
		Friend f2 = new Friend("222",002,"222.JPG","�ñ�..��~");
		Friend f3 = new Friend("333",003,"333.jpg","");
		v.add(f1) ;
		v.add(f2);
		v.add(f3);
		
		JList<Friend> jl = new JList(v);
		JScrollPane jScrollPane = new JScrollPane(jl);
		jl.setPreferredSize(new Dimension(280,550) );
		
		MyCellRenderer cr = new MyCellRenderer();
		jl.setCellRenderer(cr);
		
		Mouse m = new Mouse(chat);
		jl.addMouseListener(m);
		
		this.add(jl);
		
		this.addWindowListener(new WindowAdapter() {//���ڵļ�����
	        @Override
	        public void windowClosing(WindowEvent e) {
	        System.out.println("WindowClosing...");
	        }
	        @Override
	        public void windowClosed(WindowEvent e) {
	        System.out.println("WindowClosed...");
	        
	        date=new Date();
			format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			time=format.format(date);
	        
	        try {
				dos.writeInt(5);
				dos.writeInt(uname.getBytes().length);
				dos.write(uname.getBytes());
				dos.writeInt(time.getBytes().length);
				dos.write(time.getBytes());
				
				System.out.println(uname+"�û���"+time+"ʱ�䣬���ߣ�");
				
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        
	        }
	    });
		
		this.setVisible(true);
		
//		ChatData cd = new ChatData(uname,dis,dos);
		Receive r = new Receive(dis,chat);
		r.start();
	}
	

	public static ArrayList<ChatUI> getChat() {//��ȡ���촰�ڵķ���
		return chat;
	}

	public void setChat(ArrayList<ChatUI> chat) {
		this.chat = chat;
	}
	
	public static DataInputStream getDis(){
		return dis;
	}
	
	public static DataOutputStream getDos(){
		return dos;
	}
	
	public static String getUname(){
		return uname;
	}
	
	
	

}
