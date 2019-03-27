package com.lujie;

import javafx.scene.control.TableRow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.TableView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class GuPiao extends JFrame {
    private JTable table;
    private JTextField textField;

    public GuPiao() throws HeadlessException {
        setTitle("杰哥自选股");    //设置显示窗口标题
        setSize(400,200);    //设置窗口显示尺寸
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    //置窗口是否可以关闭
        setVisible(true);    //设置窗口是否可见

    }

    public void addTableData(String[] jsonArr){
        int jsonStrLength = jsonArr.length;
        Object[][] tableDate=new Object[jsonStrLength][6];
        for(int i=0;i<jsonStrLength;i++)
        {
            String[] temArr = jsonArr[i].split(",");
            tableDate[i][0] = temArr[0];//股票名
            tableDate[i][1] = temArr[2];//昨收盘价
            tableDate[i][2] = temArr[1];//开盘价
            tableDate[i][3] = temArr[3];//当前价
            NumberFormat ddf1=NumberFormat.getNumberInstance() ;
            ddf1.setMaximumFractionDigits(3);
            String riseNum = ddf1.format((Float.valueOf(temArr[3])  - Float.valueOf(temArr[2]))) ;
            tableDate[i][4] = riseNum;//涨跌
            Float  amountNum = Float.valueOf(riseNum)/Float.valueOf(temArr[2]) * 100;
            DecimalFormat decimalFormat=new DecimalFormat("0.000");
            String amountNumStr = decimalFormat.format(amountNum)+"%";
            tableDate[i][5] = amountNumStr;//涨幅
        }
        String[] name={"股票名","昨收盘价","开盘价","当前价","涨跌","涨幅"};
        if (this.table == null){
            this.table=new JTable(tableDate,name);
            this.add(new JScrollPane(table));
            setVisible(true);
        }else {
            DefaultTableModel tableModel = new DefaultTableModel(tableDate, name);
            this.table.setModel(tableModel);
        }
    }

}
