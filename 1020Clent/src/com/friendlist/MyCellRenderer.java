package com.friendlist;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;


public class MyCellRenderer extends JLabel implements ListCellRenderer{
    public MyCellRenderer() {
        setOpaque(true);
    }

    public Component getListCellRendererComponent(JList list,
                                                  Object value,
                                                  int index,
                                                  boolean isSelected,
                                                  boolean cellHasFocus) {

        Friend f = (Friend)value;
        
        JPanel jp = new JPanel();
        jp.setLayout(new  BorderLayout());
        
        jp.setPreferredSize(new Dimension(280,60));
        
        String path = "src/"+f.getPic();
        ImageIcon ima = new ImageIcon(path);
        
        JLabel jl2 = new JLabel(ima);
        jl2.setPreferredSize(new Dimension(25,10));
        jp.add(jl2,BorderLayout.WEST);
        
        
        
        JLabel jl1 = new JLabel();
        jl1.setText(f.getName());
        jp.setName(f.getName());
        jp.add(jl1);
        
        String background;
        Color color = new Color(248,248,255);
        Color foreground;
        

        // check if this cell represents the current DnD drop location
        JList.DropLocation dropLocation = list.getDropLocation();
        if (dropLocation != null
                && !dropLocation.isInsert()
                && dropLocation.getIndex() == index) {

            background = "#a4e2c6";
            foreground = Color.WHITE;

        // check if this cell is selected
        } else if (isSelected) {
            background = "#a4e2c6";
            color =  new Color(255,240,245) ; 
            foreground = Color.WHITE;
            
            JLabel jl3 = new JLabel();
            jl3.setText(f.getWords());
            jp.add(jl3,BorderLayout.EAST);
            
            jl2.setPreferredSize(new Dimension(50,50));

//            System.out.println("CLICK");
        // unselected, and not the DnD drop location
        } else {
            background = "#f0fcff";
            color =  new Color(240,252,255) ; 
            foreground = Color.BLACK;
        };

        jp.setBackground(color);
        jp.setForeground(foreground);

        return jp;
    }

	
	
}
