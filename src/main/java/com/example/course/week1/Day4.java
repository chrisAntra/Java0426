package com.example.course.week1;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Day4 {
}
/**
 * synchronized
 * volatile  + cas
 * thread safe collections
 */

class TestAtomic {
    static AtomicInteger ai = new AtomicInteger();
    static {
        ai.set(0);
    }
    static void add() {
        for(int i=0;i<10000;i++){
            ai.incrementAndGet();
        }
    }

    public static void main(String[] args) throws Exception{
        Thread t1 = new Thread(()->{
            add();
        });
        Thread t2 = new Thread(()->{
            add();
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(ai.get());
    }
}
/**
 * CompareAndSetInt(o, offset,     v ,      v + delta)) atomic instruction
 *                    i        expect           newVal
 *  T1   T2
 *  i=1  i=1
 *  i=2
 *       i=2
 *       i=3
 *
 * optimistic lock (version)
 * when we less write than read
 *
 * disadvan:
 *      if too many write op, waste cpu usage
 *      A B A
 */


/**
 * synchronized
 *      disadvantages:
 *          no fair lock
 *          no try lock
 *          performance slow
 *          one waiting list
 * ReentrantLock
 *          lock()
 *          tryLock()
 *          t1 <- t2
 *
 */
/**
 *    pro1                         consumer
 *    pro2  [][][][][][][]...[]
 */
class MyBlockQueue2 {
    private Queue<Integer> que;
    private int capa =10;
    private ReentrantLock lock = new ReentrantLock(true);
    private Condition producer = lock.newCondition();
    private Condition consumer = lock.newCondition();
    public MyBlockQueue2(){ que = new LinkedList<>();}
    public void add(int ele) {

        lock.lock();
        //...
        try{
            while(que.size()==capa) {
                producer.await();
            }
            System.out.println("Start adding...");
            que.add(ele);
            consumer.signalAll();
        }catch (Exception exc){

        }finally {
           lock.unlock();
        }
    }
    public int poll(){
        lock.lock();
        int val=-1;
        try{
            while(que.size()==0) {
                consumer.await();
            }
            System.out.println("Start polling...");
            val = que.poll();
            producer.signalAll();

        }catch(Exception exc){

        }finally{
            lock.unlock();
        }
        return val;

    }
}
/**
 * DeadLock
 *      l1    l2
 *      thread 1 hold l1, try get l2 ...
 *      thread 2 hold l2, try get l1 ...
 *      Object o1
 *      Object o2
 *      Thread1:
 *      new Thread(()->{
 *          synchronized(o1) {
 *              //thread 1 (o1)
 *              synchronized(o2) {
 *                  ...
 *              }
 *          }
 *      })
 *      Thread2:
 *  *      new Thread(()->{
 *  *          synchronized(o2) {     //o1 ->o2
 *                  //thread 2(o2)
 *  *              synchronized(o1) {
 *  *                  ...
 *  *              }
 *  *          }
 *  *      })
 */
/**
 * How to solve deadlock?
 *      1. release lock after certain time
 *      2. lock in order
 *      3. look up table
 */

/**
 * ThreadPool
 *      reuse the worker thread
 *      [..][][][][][][][...][...][task4][task3][t2][t1]  worker thread
 *      CacheThreadPool
 *                           ThreadPoolExecutor(int corePoolSize,          //3 core work thread
 *                                               int maximumPoolSize, //extra thread
*                                                 long keepAliveTime,   //idle for this time period
 *                                                  TimeUnit unit,
*                                                 BlockingQueue<Runnable> workQueue)
 *      SingleThreadPool
 *      FixedThreadPool
 *
 *
 *
 *
 *      ScheduledThreadPool
 *
 *      ForkJoinPool
 *
 *     [big task]      [1^2 +2^2 +3^3+ ...+ 10^2]
 *     / \    \
 *    t1 t2   t3
 *    w1 [t1]
 *    w2 [t2]
 *    w3 [t3]
 *        join and return
 *    Completeable
 */
class TestThreadPool {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool();
        Executors.newScheduledThreadPool();
        new ForkJoinPool();
    }
}
