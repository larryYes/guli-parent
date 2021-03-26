package com.atguigu.demo.test;

/**
 * @author by liuguangjin
 * @Description TODO
 * @Date 21/3/18
 */

public class Test {
    @org.junit.Test
    public void run() {
        Character i1 = 'A';
        Character i2 = 'A';
        Character i3 = new Character('A');
        String i4 = "A";
        System.out.println(i1==i2);
        System.out.println(i1==i3);
        System.out.println(i2.equals(i4));
    }
}
