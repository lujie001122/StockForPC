package com.lujie;

import javax.swing.*;
import java.awt.*;

import static java.lang.Thread.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        String guPiaoCode = JOptionPane.showInputDialog("输入股票代码，以#号分割",GuPiaoUtil.readGuPiaoFromFile());
        GuPiaoUtil.writeGuPiaoToFile(guPiaoCode);
        if (guPiaoCode.length() >= 6){
            GuPiao guPiao = new GuPiao();
            while (true){
                try {
                    sleep(1000);
                    String[] dataStr =  GuPiaoUtil.getGupPiaoData(guPiaoCode);
                    guPiao.addTableData(dataStr);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
