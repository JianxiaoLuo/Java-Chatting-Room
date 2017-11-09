package com.friendlist1028;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * this class action to the friendlist click.
 * @author Charlotte
 *
 */
public class Mouse implements MouseListener{
	ArrayList<ChatUI> chat;
	Friend f;

	public Mouse (ArrayList<ChatUI> chat){
		this.chat = chat;
	}
	
	
	public void mouseClicked(MouseEvent evt) {
        JList list = (JList)evt.getSource();
        if (evt.getClickCount() == 2) {
        	
            int index = list.getSelectedIndex();    //��ѡ����±�
            f =(Friend) list.getModel().getElementAt(index);  //ȡ������
            System.out.println(f.getName());//ʵ�ֻ�ȡ�����Friend������
        
            ChatUI cui = new ChatUI(f.getName());
            cui.showUI();
            chat.add(cui);
            System.out.println("chat�������һ������"+f.getName());
        } 
        
        else if (evt.getClickCount() == 3) {

        }
    }


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}


