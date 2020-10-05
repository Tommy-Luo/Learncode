package com.example.caculator;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import java.util.ArrayList;
import java.math.BigDecimal;
public class IndexInToDuffix {
    public static Stack Houzhui(String s) {
        Stack<String> stacka = new Stack<String>();   //存放操作数
        Stack<String> stackb = new Stack<String>();   //存放操作符
        //需要创建一个字符串存放数，不然2位数及以上会被拆开，不方便后续处理
        String temp = new String();
        HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
        hashMap.put("(", 0);// 设定优先级 +-优先级相同 */优先级相同
        hashMap.put("+", 1);
        hashMap.put("-", 1);
        hashMap.put("*", 2);
        hashMap.put("/", 2);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);  //获取第i个位置的字符
            String m = c + "";
            if (Character.isDigit(c) || c == '.') {
                if (i == s.length() - 1) {
                    temp += m;
                    stacka.push(temp);
                } else
                    //不是第一个位置，要看前一个是不是数字或小数点
                    //if(Character.isDigit(s.charAt(i-1))||s.charAt(i-1)=='.')
                    //{
                    temp += m;
                //}

                //stacka.push(m);
            }//如果c是字符，就将他直接放进a栈中
            else {
                if (temp != "") {
                    stacka.push(temp);
                    temp = "";
                }  //这个判断是为了不让括号后紧接着数字的情况出错
                if (c == '(')
                    stackb.push(m);
                else if (c == ')') {
                    while (!stackb.isEmpty() && !stackb.peek().equals("(")) {
                        String r = stackb.pop();
                        stacka.push(r);
                    }//遇到右括号，输出运算符堆栈中的运算符到操作数堆栈，直到遇到左括号为止
                    if (stackb.peek().equals("(")) stackb.pop();
                } else  //加减乘除的情况
                {
                    //首先是加减，他们的优先级低于乘除，只有优先级大于等于栈顶才弹出
                    switch (c) {
                        case '+':
                        case '-':
                            if ((!stackb.isEmpty()) && hashMap.get(stackb.peek()) > 1) {
                                String t = stackb.pop();
                                stacka.push(t);
                                stackb.push(m);
                            } else {
                                stackb.push(m);
                            }
                            break;
                        case '*':
                        case '/':
                            stackb.push(m);
                            break;
                    }
                }
            }
        }
        while (!stackb.isEmpty()) {
            String q = stackb.pop();
            stacka.push(q);
        }
        return stacka;
    }

    public static String calc(Stack<String> stacka)//计算逆波兰表达式
    {
        ArrayList<String> arr = new ArrayList<String>();
        while (!stacka.isEmpty()) {
            String t = stacka.pop();
            //System.out.println("t="+t);
            arr.add(t);
        }
        ArrayList<String> arr1 = new ArrayList<String>();
        for (int i = arr.size() - 1; i >= 0; i--) {
            int j = arr1.size();
            switch (arr.get(i)) {
                case "+":
                    BigDecimal a = new BigDecimal(arr1.remove(j - 2)).add(new BigDecimal(arr1.remove(j - 2)));
                    arr1.add(String.valueOf(a));
                    break;
                case "-":
                    BigDecimal b = new BigDecimal(arr1.remove(j - 2)).subtract(new BigDecimal(arr1.remove(j - 2)));
                    arr1.add(String.valueOf(b));
                    break;
                case "*":
                    BigDecimal c = new BigDecimal(arr1.remove(j - 2)).multiply(new BigDecimal(arr1.remove(j - 2)));
                    arr1.add(String.valueOf(c));
                    break;
                case "/":
                    BigDecimal d = new BigDecimal(arr1.remove(j - 2)).divide(new BigDecimal(arr1.remove(j - 2)));
                    arr1.add(String.valueOf(d));
                    break;

                default:
                    arr1.add(arr.get(i));
                    break;

            }
        }
        return arr1.get(0);
    }
}
