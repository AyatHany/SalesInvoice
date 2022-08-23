package com.company.model;

import java.awt.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


import javax.swing.*;


public class FileOperations extends Component {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    Frame f;
    public FileOperations(Frame f) {
        this.f = f;
    }
    public ArrayList<InvoiceHeader> readFile() throws IOException, ParseException {
        ArrayList<InvoiceHeader> invoiceHeader=new ArrayList<InvoiceHeader>();
        String s=null;

        FileInputStream fs = null;
        JFileChooser fc=new JFileChooser();
        fc.setMultiSelectionEnabled(true);
        int result= fc.showOpenDialog(this.f);
        if(result== JFileChooser.APPROVE_OPTION){
            String path =fc.getSelectedFile().getPath();
            fs = new FileInputStream(path);
            int size =fs.available();
            byte[]b = new byte[size];
            fs.read(b);
            s=new String(b);
            fs.close();
        }
        String section="";
        ArrayList<String> array=new ArrayList<String>();
            for(int i=0;i<s.length();i++)
            {
                if(s.charAt(i)!=',' && i<s.length()-1 && s.charAt(i)!='\n' )
                    section+=s.charAt(i);
                else if(i==s.length()-1)
                {
                    section+=s.charAt(i);
                    array.add(section);
                    section="";
                }
                else
                {
                    array.add(section);
                    section="";
                }
            }

            for(int i=0;i<array.size();i+=3){
                InvoiceHeader header= new InvoiceHeader();
                header.setInvoiceNum(Integer.parseInt(array.get(i)));
                header.setInvoiceDate(new SimpleDateFormat("dd-MM-yyyy").parse(array.get(i+1)));
                header.setCustomerName(array.get(i+2));
                invoiceHeader.add(header);


            }
        /*for (String ss : array)
            System.out.println(ss);

         */
        ArrayList<InvoiceLines> invoiceLines=new ArrayList<InvoiceLines>();
        String s2=null;
        FileInputStream fs2 = null;
        //JFileChooser fc=new JFileChooser();
        int result2= fc.showOpenDialog(this.f);
        if(result2== JFileChooser.APPROVE_OPTION){
            String path =fc.getSelectedFile().getPath();
            fs2 = new FileInputStream(path);
            int size =fs2.available();
            byte[]b2 = new byte[size];
            fs2.read(b2);
            s2=new String(b2);
            fs2.close();
        }
        String section2="";
        ArrayList<String> array2=new ArrayList<String>();
        for(int i=0;i<s2.length();i++)
        {
            if(s2.charAt(i)!=',' && i<s2.length()-1 && s2.charAt(i)!='\n' )
                section2+=s2.charAt(i);
            else if(i==s2.length()-1)
            {
                section2+=s2.charAt(i);
                array2.add(section2);
                section2="";
            }
            else
            {
                array2.add(section2);
                section2="";
            }
        }
        /*for(int i =0;i<array2.size();i++){
            System.out.println(i +"" + array2.get(i));
        }*/
        for(int i=0;i<invoiceHeader.size();i++) {
            for (int j = 0; j < array2.size(); j += 4) {
                if(Integer.parseInt(array2.get(j))== invoiceHeader.get(i).getInvoiceNum()) {
                    InvoiceLines lines = new InvoiceLines();
                    //invoiceHeader.get(j).setInvoiceNum(Integer.parseInt(array2.get(i)));
                    lines.setItemName(array2.get(j+1));
                    double d=Double.parseDouble(array2.get(j + 2));
                    lines.setItemPrice((int) (d));
                    double doc= Double.parseDouble(array2.get(j + 3));
                    lines.setCount((int) (doc));
                    invoiceLines.add(lines);
                }

            }
            invoiceHeader.get(i).setInvoiceLines(invoiceLines);
            invoiceLines = new ArrayList<>();
        }
        return invoiceHeader;
    }
    public ArrayList<InvoiceHeader> writeFile() throws IOException {
        //ArrayList<InvoiceHeader> invoiceHeaders = null;
        ArrayList<InvoiceHeader> invoiceHeaders=new ArrayList<>() ;
        ArrayList<InvoiceLines> invoiceLines=new ArrayList<>();
        JFileChooser fc = new JFileChooser();
        FileOutputStream fo = null;
        fc.setMultiSelectionEnabled(true);
        int result = fc.showSaveDialog(this);
        //PrintWriter os = null;
        if(result== JFileChooser.APPROVE_OPTION) {
            String path =fc.getSelectedFile().getPath();
            fo = new FileOutputStream(path);

        }
        String string="";
        for (int i=0;i<invoiceHeaders.size();i++) {
            fo.write(Integer.parseInt(invoiceHeaders.get(i).getInvoiceNum()+","));
            fo.write(Integer.parseInt(format.format(invoiceHeaders.get(i).getInvoiceDate()+",")));
            fo.write(Integer.parseInt(invoiceHeaders.get(i).getCustomerName()));
            fo.write(Integer.parseInt("\n"));

           // string+=invoiceHeaders.get(i).getInvoiceNum()+format.format(invoiceHeaders.get(i).getInvoiceDate())
                //    +invoiceHeaders.get(i).getCustomerName();
            }
        for (int i=0;i<=invoiceHeaders.size();i++){
            for(int j=0;j<=invoiceHeaders.get(i).getInvoiceLines().size();j++){
                //fo.write(invoiceLines.get(i).get);
                fo.write(Integer.parseInt(invoiceLines.get(j).getItemName()+","));
                fo.write(Integer.parseInt(invoiceLines.get(j).getItemPrice()+","));
                fo.write(Integer.parseInt(invoiceLines.get(j).getCount()+","));
                fo.write(invoiceLines.get(j).totalSumOfItem());
                //string+=invoiceLines.get(j).getItemName()+invoiceLines.get(j).getItemPrice()+invoiceLines.get(j).getCount()
                       // +invoiceLines.get(j).totalSumOfItem();
                fo.write(Integer.parseInt("\n"));
            }
        }

        fo.close();

        return invoiceHeaders;
    }


}

class Main {
    public static void main(String[] args){

    }
}