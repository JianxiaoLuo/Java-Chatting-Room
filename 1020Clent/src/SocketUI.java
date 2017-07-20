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
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.friendlist.FriendList;


public class SocketUI implements ActionListener{//�ͻ��˽���ͷ�����Ϣ
	private Socket socket;
	OutputStream out;
	DataOutputStream dataout;
	DataInputStream datain;
	JTextArea message1;//�Ի���
	JTextField message2;//���Ϳ�
	JTextField jt;//�����˺���Ϣ���ı���
	JTextField fid;//����˽�Ķ�����ı���
	JFrame jf1;
	String s,id;
	
	ArrayList<String> chat;
	
	public static void main(String[] args) {
		SocketUI sui = new SocketUI();
		sui.information();
	}
	
	public void initSocket(){
		try {
			Socket s = new Socket("localhost",10003);
			this.socket = s;
		} catch (UnknownHostException e) {
			System.out.println("UnknownHost");
		} catch (IOException e) {
			System.out.println("�����ͻ���ʧ�ܣ�");
		}
	}
	
	//��¼��Ϣ
	public void information(){
		
		jf1 = new JFrame();
		jf1.setSize(500,200);
		jf1.setDefaultCloseOperation(3);
		jf1.setLocationRelativeTo(null);
		jf1.setTitle("�ͻ���");
//		jf.setResizable(false);
		jf1.setLayout(new FlowLayout());
		
		//�˺�
		JLabel jl = new JLabel("ID:");
		
		jt = new JTextField();
		jt.setPreferredSize(new Dimension(450,20));
		
		//��¼��ť
		JButton jb = new JButton("Confirm");
		jb.addActionListener(this);
		
		jf1.add(jl);
		jf1.add(jt);
		jf1.add(jb);
		jf1.setVisible(true);
		
	}
	
	//���촰��
	public void show1(){
		JFrame jf = new JFrame();
		jf.setSize(600,500);
		jf.setDefaultCloseOperation(3);
		jf.setLocationRelativeTo(null);
		jf.setTitle(id);
		jf.setLayout(new FlowLayout());
		
		//�Ի���
		message1 = new JTextArea();
		message1.setFont(new Font("����",1,18));//���������ʽ
		message1.setPreferredSize(new Dimension(550, 200));
		jf.add(message1);
		
		//���Ϳ�
		message2 = new JTextField();
		message2.setFont(new Font("����",1,18));
		message2.setPreferredSize(new Dimension(550, 150));
		jf.add(message2);
		
		
		//˽�Ķ������Ϣ
		JLabel jl = new JLabel("Friend:");
		fid = new JTextField();
		fid.setPreferredSize(new Dimension(200,20));
		JButton jb1 = new JButton("˽��");
		jb1.addActionListener(this);
		
		jf.add(jl);
		jf.add(fid);
		jf.add(jb1);
		
		//���͵İ�ť�����º��ͷ��Ϳ������
		JButton bsend = new JButton("Ⱥ��");
		bsend.addActionListener(this);
		jf.add(bsend);
		
		
		jf.setVisible(true);
		CTask task = new CTask(message1,datain);//����һ���߳������շ�������������Ϣ
	}
	
	public void stream(){
		try {
			//��ȡ�����
			out = socket.getOutputStream();
			dataout = new DataOutputStream(out);
			datain = new DataInputStream(socket.getInputStream());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		if("Confirm".equals(e.getActionCommand())){
			id = jt.getText();
			jf1.setVisible(false);
			this.initSocket();
			this.stream();//��������
			this.signin();//����������͵�¼��Ϣ
		}
		else if("Ⱥ��".equals(e.getActionCommand()) && out!=null){
			this.toAll();//�����������Ⱥ����Ϣ
			
		}
		else if("˽��".equals(e.getActionCommand())){
			this.toOne();//�����������˽����Ϣ
		}
	}
	
	public void signin(){//�ѵ�¼��Ϣ�����ȥ������
		if(id!=null){
			try {
				dataout.write(0);//�������ͣ�0 ��¼��Ϣ
				dataout.write(1);//��Ϣ���ͣ�1 �˺�
				dataout.writeInt(id.getBytes().length);//��Ϣ����
				dataout.write(id.getBytes());//��Ϣ����
				System.out.println("��¼��Ϣ�ĳ��ȣ�"+id.getBytes().length+"  ���ݣ�"+id.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.show1();
			FriendList fl = new FriendList(dataout,datain);
			fl.show1();
		}
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
