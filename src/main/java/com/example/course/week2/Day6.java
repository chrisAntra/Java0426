package com.example.course.week2;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Day6 {
    public static void main(String[] args) {
        System.out.println("hello");
    }
}
/**
 * parallel stream
 *                                        [1,2,3]                    [2,4,..]
 *      [1,2,3,4,5,6] -> forkJoinPool ->  [4,5]    ->(step1)            ...          ->filter(>8) (step2)-> list
 *                                         [6]                      [12]
 */
class TestParallel {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1,2,3,4,5,6);
        List<Integer> res= list.parallelStream()
                .map(x->{
                    System.out.println(Thread.currentThread().getName()+" :step1");
                    return 2*x;
                })
                .filter(x->{
                    System.out.println(Thread.currentThread().getName()+" :step2");
                    return x>8;
                })
                .collect(()->new LinkedList<Integer>(),(ls,val)->{ls.add(val);},(l1,l2)->{l1.addAll(l2);});
        System.out.println(res);

    }
}
/**
 * Completable Future vs                     Future
 * fully asynchronous                        get() will block main method
 * chain operation
 * allOf() combine all the prev res
 * anyOf()
 *
 *
 */
class TestF {
    public static void main(String[] args) throws Exception{
        ExecutorService tp = Executors.newFixedThreadPool(2);
        Future<Integer> ft = tp.submit(()->{
            try{
                Thread.sleep(3000);
            }catch (Exception ex){}
            return 1;});
        System.out.println("main is blocking...");
        final int res = ft.get();
        System.out.println(res);//block main
        Future<Integer> ft2  = tp.submit(()->{
            return 2*res;
        });
        System.out.println("other logic");
        //...
    }
}
class TestCF {
    public static void main(String[] args) {
//        CompletableFuture cf = CompletableFuture
//                .supplyAsync(()->{
//                    try {
//                        Thread.sleep(3000);
//                    }catch (Exception ex){}
//                    return 1;
//                })
//                .thenApply(x->{return 2*x;})
//                .thenAccept(x->{
//                    System.out.println(x);
//                });
//        System.out.println("In main1");
//        //CompletableFuture<Integer> cf2 = cf.thenApply(x->{return 2*x;});
//        System.out.println("In main2");
//        System.out.println(cf2.join());
//        cf.join();//blocking method
        List<CompletableFuture> list = new ArrayList<>();
        //adding request to list
        for(int i=0;i<5;i++) {
            final int x = i;
            list.add(CompletableFuture.supplyAsync(()->{
                //...send request


                try{
                    Thread.sleep(x*1000);
                }catch(Exception ex){}
                System.out.println("step1");
                return "I am the res of "+ x+"th request";
            }));
        }
        // use all of to combine the res without blocking
        CompletableFuture f = CompletableFuture.allOf(list.toArray(new CompletableFuture[0]));

        f.thenAccept(Viod-> {

            for(CompletableFuture fu: list) {
                System.out.println("step2");
                System.out.println(fu.join());
            }
        }).join();
    }
}