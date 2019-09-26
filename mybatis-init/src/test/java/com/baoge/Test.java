package com.baoge;

/**
 * @Author shaoxubao
 * @Date 2019/9/26 10:47
 */
public class Test {
    public static void main(String[] args) {

        Double d = 3.0;
        System.out.println(String.format("%.0f", d));

        String  a = "150.0% < 指标 <= 30.0%";
        System.out.println(a.replaceAll("\\.0", ""));
    }
}
