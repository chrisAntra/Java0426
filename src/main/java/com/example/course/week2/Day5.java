package com.example.course.week2;


import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Day5 {
    public static void main(String[] args) throws Exception{
//        System.out.println("hello");
        //Executor
        //ExecutorService
//
//        Runnable task1 = new Runnable() {
//            @Override
//            public void run() {
//
//                System.out.println("I am runnable");
//            }
//        };
//        ExecutorService threadPool = Executors.newCachedThreadPool();
//        Future<?> f0 = threadPool.submit(task1);
//        f0.get();
//        Callable task2 = new Callable() {
//            @Override
//            public Object call() throws Exception {
//                throw new Exception("dummy exception");
//                //return "I am callbale";
//            }
//
//        };
//        Future<String> f =threadPool.submit(task2);
//        try{
//            System.out.println(f.get());
//        }catch (Exception ex){
//            System.out.println(ex);
//        }
        ScheduledExecutorService es = Executors.newScheduledThreadPool(2);
        es.scheduleAtFixedRate(()->{
            System.out.println("I am scheduled runnbale");
        }, 2000,1000,TimeUnit.MILLISECONDS);
        Executor threadpool = Executors.newFixedThreadPool(3);
    }
}
/**
 * ThreadPool
 *      ThreadPoolExceutor
 *          Singlethreadpool
 *          CacheThreadPool
 *          Fixedthreadpool
 *      ForkJoinPool
 *          Forkjoinpool
 *              thread 1 [t1] [t2][t3]
 *              thread 2
 *      ScheduledThreadPoolExecutor
 *          scheduledthreadpool
 *
 *Runnable          vs           Callable
 * void                         return val
 * cannot throw checked exc      can
 *
 * Interface: we use to them to wrap our thead implementation, they provide us the method to work with the thread pool
 *      Executor(execute(runnable)), ExecutorService (submit(runnable, callable)), ScheduleExecutorService(schedule)
 *
 * ThreadPool Implementations:
 *      Executors (provide multiple threadPool implementations)
 *      ThreadPoolExecutor()
 *      ScheduledThreadPoolExecutor
 *      ForkJoinPool
 *
 *
 */

/**
 * Java 8 new features
 *      Functional interface
 *      Lambda expression
 *      stream Api
 *          get rid of the for loop
 *          chain operation
 *          list.stream().filter().forEach()/collect()
 *      Optional<>
 *          Student
 *          Optional.of(stu);
 *          op.isPresent();
 */

/**
 *      Functional Interface
 *          only contains one abstract method
 *          runnable void run();
 *          callable V call() throws
 *          comparator int compare(T a1, T a2)
 *          supplier T get()
 *          consumer    void accept(T t)
 *          function    R apply(T t)
 *          predicate boolean test(T t)
 *          ...
 *     Lambda expression
 *          used with functional interface
 *          replace annoynomous class
 */


class Test {
    public static void main(String[] args) {
        String str = null;

        Optional<String> op = Optional.ofNullable(str);
        System.out.println(op.orElseGet(()->{
            System.out.println("this is a supplier");
            //...
            return "Defautl";

        }));
        Function<Integer, String> function = (x) -> String.valueOf(x);
        System.out.println(function.apply(10).getClass());
    }

}
/**
 * stream Api
 *  *          get rid of the for loop
 *  *          chain operation
 *  *          list.stream().filter().forEach()/collect()
 *              intermediate step
 *                  stream()
 *                  map()
 *                  filter()
 *                  mapToInt()
 *                  ..
 *
 *              collect() //need terminated step to execute
 *              forEach()
 *              reduce()
 *  List<Integer> list
 *      {1,2,3,4} -> multiply by 2,  filter > 5
 *
 */
class TestStream {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1,1,2,2,2,3,3,4);
//        List<Integer> res = new LinkedList<>();
//        for(int ele: list) {
//            int temp = 2*ele;
//            if(temp>5) {
//                res.add(temp);
//            }
//        }
//        System.out.println(res);
        System.out.println(list.stream().map(x->2*x).filter(x->x>5).collect(Collectors.toList()));
//        //convert List of Integer -> int[]
//        int[] arr = list.stream().mapToInt(x->(int)x).toArray();
//        // int[] -> list
//        List<Integer> converted = Arrays.stream(arr).boxed().collect(Collectors.toList());
        HashMap<Integer,Integer> hm = list.stream().collect(()->new HashMap<Integer, Integer>(),(res, val)->{
            res.put(val,res.getOrDefault(val,0)+1);
        },(h1,h2)->{});
        int sum = list.stream().reduce(0,(res1,val)->{return res1+=val;});
        System.out.println(sum);
        System.out.println(hm.entrySet().stream().filter(x->(x.getValue()>2)).collect(Collectors.toList()));
//        System.out.println(hm);





//        Arrays.stream(arr).forEach(System.out::println);


    }
}
//leetcode 49 group anagram resovle by using stream api
class TestStream1 {

    static List<List<String>> group(List<String> input) {
        return input.stream().collect(()->new HashMap<String, List<String>>(), (res, val)->{
            char[] temp = val.toCharArray();
            Arrays.sort(temp);
            String key = new String(temp);
            if(res.containsKey(key)){
                res.get(key).add(val);
            }else{
                List<String> tempList = new LinkedList<>();
                tempList.add(val);
                res.put(key,tempList);
            }
        },(r1,r2)->{}).values().stream().collect(Collectors.toList());
    }

    public static void main(String[] args) {
        List<String> list = Arrays.asList("aab","aba","abc");
        System.out.println(group(list));
    }
}