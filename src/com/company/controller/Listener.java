package com.company.controller;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.company.model.*;

import com.company.view.Frame2;
import com.company.view.HeaderDialog;
import com.company.view.LineDialog;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;




public class Listener extends Component implements ActionListener , ListSelectionListener {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private HeaderDialog headerDialog;
    private LineDialog lineDialog;
    Frame2 f ;
    public Listener(Frame2 f) {
        this.f = f;

    }
    FileOperations ff=new FileOperations(this.f);
    private void deleteInvoice(){
        int selectedRows= f.getjTable1().getSelectedRow();
        InvoiceHeader inv = f.getHeader().get(selectedRows);
        for (int i=0;i<f.getHeader().size();i++){
            if (f.getHeader().get(i).getInvoiceNum()==inv.getInvoiceNum()){
                f.getHeader().remove(i);
            }
        }
        f.getInvoicetableheader().fireTableDataChanged();


    }
    private void createNewInvoice(){
        headerDialog=new HeaderDialog(f);
        headerDialog.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "Load File":
                try {
                    ArrayList<InvoiceHeader> h=ff.readFile();
                    f.setHeader(h);

                    f.setInvoicetableheader(new InvoiceHeaderTableModel(f.getHeader()));
                    f.getjTable1().setModel(f.getInvoicetableheader());

                    for(int i = 0;i<f.getHeader().size();i++)
                    {
                        System.out.println("Invoice "+f.getHeader().get(i).getInvoiceNum()+"\n" +
                                "{\n"+ "  "+(f.getHeader().get(i).getInvoiceDate())+", "
                                +f.getHeader().get(i).getCustomerName());

                        for(int j = 0;j<f.getHeader().get(i).getInvoiceLines().size();j++)
                        {
                            System.out.println("Invoice"+f.getHeader().get(i).getInvoiceLines().get(j).getItemName()+", "
                                    +f.getHeader().get(i).getInvoiceLines().get(j).getItemPrice() +", "
                                    +f.getHeader().get(i).getInvoiceLines().get(j).getCount());
                        }

                        System.out.println("}\n");


                    }

                    /*System.out.println(h.get(0).getCustomerName());
                    System.out.println(h.get(1).getCustomerName());
                    System.out.println(h.get(0).getInvoiceLines().get(0).getItemName());
                    System.out.println(h.get(1).getInvoiceLines().get(0).getItemName());
                    System.out.println(h.get(0).getTotal());
                    System.out.println(h.get(1).getTotal());*/
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
                break;
            case "Save file":
                try {
                    ArrayList<InvoiceHeader>hh;
                    hh=ff.writeFile();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                break;
            case "Save":
                    ArrayList<InvoiceHeader>hh;
                try {
                    hh=ff.writeFile();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                break;
            case "Create new invoice":
                createNewInvoice();
                break;
            case "Delete invoice":
                deleteInvoice();
                break;
            case "Cancel":
               cancel();
                break;
            case "createInvOk":
                try {
                    createInvoiceOk();
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
                break;
            case "CancelInv":
                createInvoiceCancel();
                break;

        }
    }
    private void cancel() {
        headerDialog.setVisible(false);
        headerDialog.dispose();
        headerDialog=null;
    }
    private void createInvoiceCancel() {
        headerDialog.setVisible(false);
        headerDialog.dispose();
        headerDialog=null;
    }
    private void createInvoiceOk() throws ParseException {
        String date=headerDialog.getDateField().getText();
        int num=f.getNextNum();
        String name=headerDialog.getCustomerNameField().getText();
        headerDialog.setVisible(false);
        headerDialog.dispose();
        headerDialog=null;

        InvoiceHeader header= new InvoiceHeader();
        header.setInvoiceNum(num);
        header.setInvoiceDate(format.parse(date));
        header.setCustomerName(name);
        f.getHeader().add(header);
        f.getInvoicetableheader().fireTableDataChanged();

    }
    @Override
    public void valueChanged(ListSelectionEvent e) {
        int selectedRows= f.getjTable1().getSelectedRow();
        System.out.println(selectedRows +"aloooo1");

        if (selectedRows>=0) {
            System.out.println(selectedRows+"aloooo2");

            InvoiceHeader inv = f.getHeader().get(selectedRows);
            f.getjLabel5().setText(String.valueOf(inv.getInvoiceNum()));
            f.getjLabel6().setText(inv.getCustomerName());
            f.getjLabel7().setText(String.valueOf(inv.getInvoiceDate()));
            f.getjLabel8().setText(String.valueOf(inv.getTotal()));

            f.setLineTableModel(new invoiceLineTableModel(inv.getInvoiceLines()));
            f.getjTable2().setModel(f.getLineTableModel());


        }

    }
}
