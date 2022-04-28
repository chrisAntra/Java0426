package com.example.course.week1;

import java.util.*;

public class Day2 {
    public static void main(String[] args){
        //Start Connection
        try{
            Class c1 = Class.forName("com.example.course.week1.Day2");
            int[] arr1 = new int[2];
            int a= arr1[2];
        }catch (ClassNotFoundException exc) {
            System.out.println(exc);
        }catch(ArrayIndexOutOfBoundsException exc){
            System.out.println(exc);
        }finally {
            System.out.println("finally");
            //close
        }

    }
}
/**
 *              Throwable
 *              /      \
 *             Error   Exception(checked/ uncheck)
 *
 *   Exceptions:
 *      checked(compile)        unchecked(runtime)
 *      FileNotFound             NullPointer
 *      ClassNotFound            ArrayIndexOutOfBound
 *      ...
 *   Error(runtime):
 *      fatal,
 *
 */
/**
 *  try/catch/finally
 */
class TestExp {
    public static void main(String[] args) {

    }
}
/**
 * Finalize
 */
class TestFinalize {
    @Override
    public void finalize() {
        System.out.println("Object be removed");
    }

    public static void main(String[] args) {
        TestFinalize t = new TestFinalize();
        System.gc();//suggestion, not guarantee
    }
}

/**
 *  1. Array
 *      fix size (contiguous O(1))
 *      primitive type/ object type
 *
 *
 */
class Test {
    public static void main(String[] args) {
        new ArrayList<>(); // faster for retrieve
        new LinkedList<>(); // good for insertion and deletion
        new HashMap();
    }
}
/**
 * ArrayList:
 *      1.array
 *      2. access by index O(1)
 *      3. resize O(N)
 *      4. insert/delete(need to resize) O(N)
 *      5. continuous space
 * LinkedList:
 *      1.node + pointer
 *      2. random space
 *      3. insert/ delete for first last O(1);
 *      4. look up O(N) need to traversal
 */
class TestInterator {
    public static void main(String[] args) {
        List<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        Iterator<Integer> it = list.iterator();
        while(it.hasNext()){
            it.next();
            it.remove();
        }
    }
}
/**
 * HashMap
 *      flow:
 *          key, value
 *          1. Node array
 *          2. in bucket -> linkedList/redBlackTree
 *          3. hashcode(find the same bucket) /  equals (compare the keys)
 *          4. putVal(hash(key), key, value, false, true);
 *          5. n-1&hash (hash%size)-> index
 *          6. entry is null or not, if null no collison just insert the node
 *              if not null, check whether is linkedlist node or tree node;
 *              if it is redblack tree, just use the method of tree
 *              otherwise loop through the list;
 *          7. if we exceed tree threshold, we will treefy list to tree
 *          8. check whether exceed the threshold of table, if it is, resize the Node array.
 *
 *
 */
class Student1 {
    String name;
    int id;
    Student1(String name, int id) {
        this.name = name;
        this.id = id;
    }
    @Override
    public int hashCode(){
        return name.hashCode()+id;
    }
    @Override
    public boolean equals(Object object){
        Student1 stu = (Student1) object;
        return this.name.equals(stu.name)&&this.id==stu.id;
    }

    public static void main(String[] args) {
        Student1 s1 = new Student1("x",1);
        Student1 s2 = new Student1("x",1);
        HashMap<Student1, Integer> hm = new HashMap<>();
        hm.put(s1,1);
        System.out.println(hm.get(s2));
    }
}
class TestHashMap {
    public static void main(String[] args) {
        new HashMap<>(); //(n - 1) & hash == hash%n
        new HashSet<>(); //Same mechanism as hashmap

    }

}
/**
 * List                  vs        Set
 *maintain insertion order        will not
 *allow dup                       no dup
 */

/**
 * TreeMap
 *      red black tree
 *      null
 */
class TestTreeMap{
    public static void main(String[] args) {
//        TreeSet<>();
        TreeMap<Integer, Integer> tm = new TreeMap<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return 0;
            }
        });
        tm.put(2,1);
        tm.put(10,1);
        tm.put(null,1);

        System.out.println(tm);
        PriorityQueue<Integer> pq = new PriorityQueue<>((a,b)->{return b-a;});

    }
}

