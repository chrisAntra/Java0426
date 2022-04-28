package com.example.course.week1;

import org.w3c.dom.ls.LSOutput;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;

public class Day3 {
    static int fobb (int i) {
        if(i==0||i==1) return i;
        return fobb(i-1)+fobb(i-2);
    }

    public static void main(String[] args) {
        new String();
        System.out.println(fobb(3));
    }
}
/**
 * Java memory model
 *      stack + heap
 *
 *
 *      method(stu reference)
 *      stack                            stack
 *      thread 1                        thread2
 *         heap (stu obj)
 *
 *      heap:
 *      [eden][survivor0][s1]    young gene   (parallelNewGC/parallelGC) STW
 *      [  old             ]    old gene      (parallelOldGC/ConcurrentMarkSweep)
 *      [                 ]    perm gene metadata class def, method exe (method area)
 *
 *      we cannot force jvm to run gc
 *      System.gc();
 *      minor GC: parallelNewGC/parallelGC
 *      major GC: happened old
 *      full GC: both in young + old
 *      flow of creation:
 *          new Object() -> eden(full, after few fc) -> promote s0, s1 -> promote old
 *
 *      GC root:
 *          class, classLoader, monitor
 *      ConcurrentMarkSweep:
 *          1.initial Mark(STW)
 *          2. concurrent mark
 *          3. final mark(STW)
 *          4. concurrent sweep
 *
 *     G1
 *     [E][][S][][][][]
 *     [][O][][][][][]
 *     ....
 *
 *
 */

/**
 * Thread
 *      create new thread?
 *          1. extend a thread class
 *          2. provide a runnable type instance
 *      Thread lifecycle:
 *          1. create thread -> new
 *          2. invoke start -> active
 *          3. sleep/wait   -> wait/ block     notify/notify all
 *          4. terminated
 */
class TestThread extends Thread {
    @Override
    public void run() {
        try{
        Thread.sleep(3000);
        }catch (Exception ex){}
        System.out.println(Thread.currentThread().getName());
    }

    public static void main(String[] args) throws Exception{
//        System.out.println(Thread.currentThread().getName());
//        TestThread t1  =new TestThread();
        Thread t1 = new Thread(()->{
            System.out.println(Thread.currentThread().getName()+" by using runnable");
        });
        t1.start();

        t1.join();
    }
}
/**
 * volatile
 *       t1        t2
 *      cache      cache
 *
 *          memory
 *     1.visibility
 *     2. happen before(prevent reordering)
 */
class Test2{

    static volatile boolean flag =true;

    public static void main(String[] args) throws Exception {
        Thread t1 = new Thread(()->{
            try{
                Thread.sleep(3000);
            }catch(Exception e){}
            flag = false;
            System.out.println("We have changed the flag");
        });
        Thread t2 = new Thread(()->{
            while(flag) {
            }
            System.out.println("get out of the loop");
        });

        t1.start();
        t2.start();
//        t1.join();
//        t2.join();
    }
}
/**
 * synchronized (monitor)
 *  pro
 *      1.threadsafe
 *      2. visibility, no need use with volatile
 *  cons
 *      no fair lock
 *      try lock
 */
class MyBlockQueue {
    int capacity =10;

    Queue<Integer> que = new LinkedList<>();
    //producer
    //"this" monitor of instance
    //trying to get monitor of the class
    synchronized static void dummymethod(){

    }
    synchronized void add(int ele) throws Exception{
        while(que.size()==capacity){
            this.wait(); //give up the monitor, wait in the waiting list for the mbq monitor
        }
        que.add(ele);
        this.notifyAll();
    }
    //consumer monitor of mbq
    synchronized int poll() throws Exception{
        while(que.size()==0) {
            this.wait();
        }
        int res=que.poll();
        this.notifyAll();
        return res;
    }

    public static void main(String[] args) {
        MyBlockQueue mbq = new MyBlockQueue();
        MyBlockQueue mbq2 = new MyBlockQueue();
        Thread producer = new Thread(()->{
            try{
//                MyBlockQueue.dummymethod(); //try to get the class monitor
//                MyBlockQueue.dummymethod();// same
                mbq.add(10); //mbq
                mbq2.add(10);//mbq2
            }catch (Exception ex){}

        });
        Thread consumer = new Thread(()->{
            try{
                mbq.poll();
            }catch (Exception ex){}

        });
    }
}
/**
 * race condition
 *      i
 *      thread1   thread2     1+1+1=3
 *      i=1       block
 *      i++       block
 *                i=2
 *                i++
 *
 */
class TestRace {
    static  int i=0;
    synchronized static void increment(){
        for(int j=0;j<10000;j++) {
            i++;
        }
    }
    //t1 t2
    //1  1
    //2  2

    public static void main(String[] args) throws Exception{
        Thread t1 = new Thread(()->{
            increment();//tyr to acquire monitor of class TestRace
        });
        Thread t2 = new Thread(()->{
            increment();
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);

    }
}
/**
 * volatile itself cannot provide threadsafe!
 * threadsafe
 *   synchronized
 *   volatile + CAS
 *   threadsafe collections
 */
