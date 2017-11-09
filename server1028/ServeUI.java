package com.server1028;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ServeUI implements ActionListener{
	JTextField jt;
	JFrame jf;
	String s;
	int num;
	JLabel jl;
	
	public void showUI(){
		jf = new JFrame();
		jf.setSize(500,100);
		jf.setDefaultCloseOperation(3);
		jf.setLocationRelativeTo(null);
		jf.setTitle("�������˿�");
		jf.setResizable(false);
		
		FlowLayout la = new FlowLayout();
		jf.setLayout(la);
		
		jt = new JTextField("10003");
		jt.setPreferredSize(new Dimension(250,20));
		jf.add(jt);
		
		JButton jb = new JButton("ȷ��");
		jf.add(jb);
		jb.addActionListener(this);
		
		
		jf.setVisible(true);
		
	}

	public void actionPerformed(ActionEvent e) {
		if("ȷ��".equals(e.getActionCommand())){
			s = jt.getText();
			System.out.println(s);
			num  = Integer.valueOf(s);
			if(num<1000){//1000���µĶ˿ڴ�౻ϵͳ�ض�����ռ��
				JOptionPane.showMessageDialog(null, "·���쳣��");
			}
			else{
				System.out.println(num);
				Serve s = new Serve();
				s.creatServe(num);//����������������Ϊ������Ķ˿ں�
				try {
					s.connect();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		ServeUI sui = new ServeUI();
		sui.showUI();
	}

}
