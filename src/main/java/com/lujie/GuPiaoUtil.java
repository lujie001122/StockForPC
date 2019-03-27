package com.lujie;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.util.ArrayList;

import static java.lang.Thread.sleep;


public class GuPiaoUtil {
    static private String getDataUrl = "http://hq.sinajs.cn/list=";

    static  public String parseCodeStr(String codeStr){
        String[] codeArr = codeStr.split("#");
        String resultStr = "";
        for (int i=0;i<codeArr.length;i++){
            int temp = Integer.valueOf(codeArr[i]);
            if (temp >= 600000){
                resultStr += "sh" + codeArr[i]+",";
            } else {
                resultStr += "sz" + codeArr[i]+",";
            }
        }
        return resultStr.substring(0,resultStr.length()-1);
    }
    static public String[] getGupPiaoData (String codeStr){
        codeStr = parseCodeStr(codeStr);

        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            // 创建httpget.
            HttpGet httpget = new HttpGet("http://hq.sinajs.cn/list=" + codeStr);
            // 执行get请求.
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                String jsonStr =  EntityUtils.toString(entity,"UTF-8");
                if (entity != null) {
                    String newStr = jsonStr.replaceAll("[var ]+[\\w]{15}=+[\\\"]", "");
                    newStr = newStr.substring(0,newStr.length()-3); // or  str=str.Remove(i,str.Length-i);
                    String[] JsonArr = newStr.split("\";");

                    return JsonArr;
                }
            } finally {
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    static public String readGuPiaoFromFile() {
        String pathname = "gupiao.txt"; // 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
        //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
        //不关闭文件会导致资源的泄露，读写文件都同理
        //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
        try (FileReader reader = new FileReader(pathname);
             BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
        ) {
            String line;
            //网友推荐更加简洁的写法
            while ((line = br.readLine()) != null) {
                // 一次读入一行数据
                return line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 写入TXT文件
     */
    static public void writeGuPiaoToFile(String gupiaoStr) {
        try {
            File writeName = new File("gupiao.txt"); // 相对路径，如果没有则要建立一个新的gupiao.txt文件
            writeName.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
            try (FileWriter writer = new FileWriter(writeName);
                 BufferedWriter out = new BufferedWriter(writer)
            ) {
                out.write(gupiaoStr); // \r\n即为换行
                out.flush(); // 把缓存区内容压入文件
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
