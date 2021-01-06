package test;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

public class Test {
    public static void main(String[] args) {
        URL url = Test.class.getResource("/Sample2.txt");
        File file = new File(url.getFile());
        try(FileInputStream fileInputStream = new FileInputStream(file)){
            int i= 0;
            while(true){
                byte[] buff = new byte[10];
                int n = fileInputStream.read(buff,i * buff.length,buff.length);
                System.out.println(n);
                System.out.println(new String(buff));
                for(byte b : ";".getBytes()){
                    System.out.printf("%x\n",b);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
