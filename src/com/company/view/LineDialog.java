package com.company.view;

import javax.swing.*;
import java.awt.*;
import java.awt.GridLayout;

public class LineDialog extends JDialog {
    private JTextField itemNameField;
    private JTextField itemCountField;
    private JTextField itemPriceField;
    private JLabel itemName;
    private JLabel itemCount;
    private JLabel itemPrice;
    private JButton Ok;
    private JButton Cancel;

    public LineDialog(Frame2 frame2){
        itemNameField=new JTextField(20);
        itemName=new JLabel("Item Name");

        itemCountField=new JTextField(20);
        itemCount=new JLabel("Item Count");

        itemPriceField=new JTextField(20);
        itemPrice=new JLabel("Item Price");

        Ok.setActionCommand("createItemOk");
        Cancel.setActionCommand("CancelItem");
        Ok.addActionListener(frame2.getListener());
        Cancel.addActionListener(frame2.getListener());
        setLayout(new GridLayout(3,2));

        add(itemName);
        add(itemCount);
        add(itemPrice);
        add(itemNameField);
        add(itemCountField);
        add(itemPriceField);
        add(Ok);
        add(Cancel);
        setModal(true);


        pack();
    }

}
