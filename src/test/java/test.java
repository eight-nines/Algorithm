import com.sun.corba.se.impl.io.TypeMismatchException;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class test {
    static int num = 100;





    public static void main(String[] args) {
        //刚刚好
        for(int i = 0; i < 5; i++) {
            new Thread(()->{
                for(int j = 0; j < 10000; j++) {
                    System.out.println(num++);
                }
            }).start();
        }
        System.out.println(num);
    }

    @org.junit.Test
    public void testPublish1(){
        List<Integer> list = new ArrayList<Integer>();

        list.add(1);list.add(2);list.add(3);list.add(4);
        System.out.println(list.subList(5, list.size()));
    }


}



