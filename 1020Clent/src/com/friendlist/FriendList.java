package com.friendlist;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


//PS!这是好友列表，但还没做好。
public class FriendList implements ActionListener{
	Vector<Friend> v = new Vector<Friend>();
	Friend f;
	JTextArea message1;
	JTextField message2;
	DataOutputStream dataout;
	DataInputStream datain;
	
	ArrayList<String> chat = new ArrayList<String>();//记录打开的聊天窗口
	

	public FriendList(DataOutputStream dataout, DataInputStream datain) {
		super();
		this.dataout = dataout;
		this.datain = datain;
	}


	
	public void show1(){//创建jList并显示
		JFrame jf = new JFrame();
		jf.setSize(300, 600);
		jf.setDefaultCloseOperation(3);
		jf.setResizable(false);
		jf.setTitle("好友列表");
		jf.setLocation(1000, 300);
		
		Friend f1 = new Friend("Apple",001,"111.JPG","我今天好开心~");
		Friend f2 = new Friend("Tomato",002,"222.JPG","好饱..嗝~");
		Friend f3 = new Friend("Potato",003,"333.jpg","");
		v.add(f1) ;
		v.add(f2);
		v.add(f3);
		
		JList<Friend> jl = new JList(v);
		JScrollPane jScrollPane = new JScrollPane(jl);
		jl.setPreferredSize(new Dimension(280,550) );
		
		MyCellRenderer cr = new MyCellRenderer();
		jl.setCellRenderer(cr);
		jl.addMouseListener(new MouseAdapter() {//使用MouseListener，必须重写所有的方法，而MouseAdaption只需要实现我关心的方法
		    public void mouseClicked(MouseEvent evt) {
		        JList list = (JList)evt.getSource();
		        if (evt.getClickCount() == 2) {
		        	
		            int index = list.getSelectedIndex();    //已选项的下标
                    f =(Friend) list.getModel().getElementAt(index);  //取出数据
                    System.out.println(f.getName());//实现获取点击的Friend的名字
		        
                    chat.add(f.getName());
                    FriendList.this.chatUI();//内部类调用外部类的方法
		        } 
		        
		        else if (evt.getClickCount() == 3) {

		        }
		    }
		});
		
		jf.add(jl);
		
		jf.setVisible(true);
		
	}
	
	public void chatUI(){
		JFrame jf = new JFrame();
		jf.setSize(600,500);
		jf.setDefaultCloseOperation(3);
		jf.setLocationRelativeTo(null);
		jf.setTitle(f.getName());
		jf.setLayout(new FlowLayout());
		
		//对话框
		message1 = new JTextArea();
		message1.setPreferredSize(new Dimension(550, 200));
		jf.add(message1);
		
		//发送框
		message2 = new JTextField();
		message2.setPreferredSize(new Dimension(550, 150));
		jf.add(message2);
		
		JButton jb1 = new JButton("发送");
		jb1.addActionListener(this);
		
		jf.add(jb1);
		
		jf.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("发送")){
			//获取输入框里的内容
			String s = message2.getText();
			message1.setText(message1.getText()+"\r\n"+s+"\r\n");
			message2.setText("");//清空输入框
			if(f.getName()!=null){
				try {
					dataout.write(2);//聊天类型：私聊
					dataout.writeInt(f.getName().getBytes().length);//客户端的号码的长度
					dataout.write(f.getName().getBytes());//客户端的号码
					dataout.write(1);//消息类型：String
					dataout.writeInt(s.getBytes().length);//消息长度
					dataout.write(s.getBytes());//消息内容
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

	public ArrayList<String> getChat() {//获取聊天窗口的方法
		return chat;
	}

	public void setChat(ArrayList<String> chat) {
		this.chat = chat;
	}
	
	
	

}
