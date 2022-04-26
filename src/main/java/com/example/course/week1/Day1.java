package com.example.course.week1;

import java.util.LinkedList;
import java.util.List;

public class Day1 {
    public static void main(String[] args) {
        Parent p = new Child();
        LinkedList<Integer> list = new LinkedList();
    }
//    static void display(){
//        System.out.println("wo input");
//    }
//    static void display(int a){
//        System.out.println("with input: "+a);
//    }
}
class Parent{
    void display(){
        System.out.println("In Parent");
    }
}
class Child extends Parent{

    @Override
    void display(){
        System.out.println("In the child");
    }
}
/*
OOP Concept:
    Polymorphism:
        method overriding: run time
        method overloading: compile time
        upcasting: loosing coupling
    Inheritance:
    Encapsulation:
        access modifier:
            public
            private
            protected
            default
    Abstraction:
        Interface:
            cannot constructor
            static final
            public abstract
            after java 8: static& default method
            after java 9: private method

        Abstract class:



 */
interface A{
    int a =1;
    void disp();
    static void disp(int a){
        System.out.println();
    }
}
interface B{
    int a=2;
}
//class Test1 {
//    static {
//        System.out.println("This is a static block");
//    }
//    {
//        System.out.println("This is a non static");
//    }
//
//    public static void main(String[] args) {
//        System.out.println("Start");
//    }
//}

//    static int a1=1;
//    @Override
//    public void disp(){}
//
//    public static void disp(int a){
//        Test1 t1 = new Test1();
//        Test1 t2 = new Test1();
//        t1.a1 =2;
//        System.out.println("t1: "+t1.a1);
//        System.out.println("t2: "+t2.a1);
//    }
//    public static void main(String[] args) {
//        disp(1000);
//    }

/*
static
    method
    variable
    class: inner class
    static block vs non-static


 */

/*
Java pass by value
 */
class Student {
    String name ="xx";
}
class Test1 {
    static int a;
    final int testVar=1;
    Test1(){
//        testVar=1;
    }
//    static void changeStuName(Student stu){
//        stu.name="xx1";
//        //System.out.println(a);
//    }
    static void changeStu(Student stu) {
        stu = new Student();
    }

    public static void main(String[] args) {
        Student s1 = new Student(); //stack vs heap s1(address value)->object(name)
        System.out.println("before: "+s1.name);
        changeStu(s1);
        System.out.println(s1.name);
    }
}
/*
final
    variable: (final cannot use with volatile) cannot change value in the variable
    method: cannot be overriding
    class: cannot be inherited
immutable(String, String Pool)
 */
//class Test2{
//    public static void main(String[] args) {
////        //Integer int -128- 127
////        int a=128;
////        int a1=128;
////        System.out.println(a==a1);// ==
////        StringBuilder sb= new StringBuilder();
//
////        String test="abc";
////        String test= new String("abc").intern();
////        String test1="abc";
////        System.out.println(test==test1);
//
////        for(int i=0;i<test.length();i++){
////            //"","a","ba","cba"
////            sb.insert(0,test.charAt(i));
////        }
////        System.out.println(sb.toString());
//    }
//}
final class ImmuList<T> {
    private final List<T> list;
    public ImmuList(List<T> list) {
        //deep copy
        this.list = new LinkedList<>(list);
        //List<List>
        // list1(orig)[xx1,xx2,xx3] this.list [xx4,xx5,xx6]
    }
    public List<T> getList(){
        //deep copy
        return new LinkedList<>(this.list);
    }
}
class TestInner {
    class Node{}

    public static void main(String[] args) {
        TestInner t = new TestInner();
        Node n = t.new Node();
    }

}