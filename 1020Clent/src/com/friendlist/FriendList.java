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


//PS!���Ǻ����б�����û���á�
public class FriendList implements ActionListener{
	Vector<Friend> v = new Vector<Friend>();
	Friend f;
	JTextArea message1;
	JTextField message2;
	DataOutputStream dataout;
	DataInputStream datain;
	
	ArrayList<String> chat = new ArrayList<String>();//��¼�򿪵����촰��
	

	public FriendList(DataOutputStream dataout, DataInputStream datain) {
		super();
		this.dataout = dataout;
		this.datain = datain;
	}


	
	public void show1(){//����jList����ʾ
		JFrame jf = new JFrame();
		jf.setSize(300, 600);
		jf.setDefaultCloseOperation(3);
		jf.setResizable(false);
		jf.setTitle("�����б�");
		jf.setLocation(1000, 300);
		
		Friend f1 = new Friend("Apple",001,"111.JPG","�ҽ���ÿ���~");
		Friend f2 = new Friend("Tomato",002,"222.JPG","�ñ�..��~");
		Friend f3 = new Friend("Potato",003,"333.jpg","");
		v.add(f1) ;
		v.add(f2);
		v.add(f3);
		
		JList<Friend> jl = new JList(v);
		JScrollPane jScrollPane = new JScrollPane(jl);
		jl.setPreferredSize(new Dimension(280,550) );
		
		MyCellRenderer cr = new MyCellRenderer();
		jl.setCellRenderer(cr);
		jl.addMouseListener(new MouseAdapter() {//ʹ��MouseListener��������д���еķ�������MouseAdaptionֻ��Ҫʵ���ҹ��ĵķ���
		    public void mouseClicked(MouseEvent evt) {
		        JList list = (JList)evt.getSource();
		        if (evt.getClickCount() == 2) {
		        	
		            int index = list.getSelectedIndex();    //��ѡ����±�
                    f =(Friend) list.getModel().getElementAt(index);  //ȡ������
                    System.out.println(f.getName());//ʵ�ֻ�ȡ�����Friend������
		        
                    chat.add(f.getName());
                    FriendList.this.chatUI();//�ڲ�������ⲿ��ķ���
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
		
		//�Ի���
		message1 = new JTextArea();
		message1.setPreferredSize(new Dimension(550, 200));
		jf.add(message1);
		
		//���Ϳ�
		message2 = new JTextField();
		message2.setPreferredSize(new Dimension(550, 150));
		jf.add(message2);
		
		JButton jb1 = new JButton("����");
		jb1.addActionListener(this);
		
		jf.add(jb1);
		
		jf.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("����")){
			//��ȡ������������
			String s = message2.getText();
			message1.setText(message1.getText()+"\r\n"+s+"\r\n");
			message2.setText("");//��������
			if(f.getName()!=null){
				try {
					dataout.write(2);//�������ͣ�˽��
					dataout.writeInt(f.getName().getBytes().length);//�ͻ��˵ĺ���ĳ���
					dataout.write(f.getName().getBytes());//�ͻ��˵ĺ���
					dataout.write(1);//��Ϣ���ͣ�String
					dataout.writeInt(s.getBytes().length);//��Ϣ����
					dataout.write(s.getBytes());//��Ϣ����
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

	public ArrayList<String> getChat() {//��ȡ���촰�ڵķ���
		return chat;
	}

	public void setChat(ArrayList<String> chat) {
		this.chat = chat;
	}
	
	
	

}
