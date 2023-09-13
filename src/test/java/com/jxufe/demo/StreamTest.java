package com.jxufe.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.jxufe.demo.Person.distinctByKey;

@SpringBootTest
@Slf4j
public class StreamTest {
    private final Logger logger = LoggerFactory.getLogger(StreamTest.class);

    @Test
    public void test() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6);
        long count = list.stream().count();
        logger.info(String.valueOf(count));

        Map<Integer, String> map = new HashMap<>();
        map.put(1, "张老三");
        map.put(2, "张小三");
        map.put(3, "李四");
        map.put(4, "赵五");
        map.put(5, "张六");
        map.put(6, "王八");

        // 3.1根据Map集合的键获取流
        Set<Integer> map1 = map.keySet();
        Stream<Integer> stream3 = map1.stream();
        // 3.2根据Map集合的值获取流
        Collection<String> map2 = map.values();
        Stream<String> stream4 = map2.stream();
        // 3.3根据Map集合的键值对对象获取瑞

    }

    @Test
    public void test2() {
        // 获取stream流
        Stream<String> stream = Stream.of("张老三", "张小三", "李四", "赵五", "刘六", "王七");
        // 需求：过去出姓张的元素
        stream.filter(name -> name.startsWith("张")).forEach(name -> System.out.println(name));
        List<String> collect = stream.collect(Collectors.toList());
    }

    @Test
    // stream根据对象属性去重
    public void test3() {
        List<Person> personList = new ArrayList<Person>();
        personList.add(new Person("Tom", 8900, 23, "male", "New York"));
        personList.add(new Person("Tom", 7000, 25, "male", "Washington"));
        personList.add(new Person("Lily", 7800, 21, "female", "Washington"));
        personList.add(new Person("Anni", 8200, 24, "female", "New York"));
        personList.add(new Person("Owen", 9500, 25, "male", "New York"));
        personList.add(new Person("Alisa", 7900, 26, "female", "New York"));
        List<Person> unique2 = personList.stream()
                .filter(distinctByKey(o -> o.getName()))
                .collect(Collectors.toList());
        unique2.forEach(System.out::println);
    }

    @Test
    public void test4() {
        List<Person> personList = new ArrayList<Person>();
        personList.add(new Person("Tom", 8900, 23, "male", "New York"));
        personList.add(new Person("Tom", 7000, 25, "male", "Washington"));
        personList.add(new Person("Lily", 7800, 21, "female", "Washington"));
        personList.add(new Person("Anni", 8200, 24, "female", "New York"));
        personList.add(new Person("Owen", 9500, 25, "male", "New York"));
        personList.add(new Person("Alisa", 7900, 26, "female", "New York"));
//赋值初始化过程省略
        List<Person> unique2 = personList.parallelStream().collect(
                Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getName() + ";" + o.getSex()))), ArrayList::new)
        );
        unique2.forEach(System.out::println);

    }

}

class Person {
    private String name;  // 姓名
    private int salary; // 薪资
    private int age; // 年龄
    private String sex; //性别
    private String area;  // 地区

    // 构造方法
    public Person(String name, int salary, int age, String sex, String area) {
        this.name = name;
        this.salary = salary;
        this.age = age;
        this.sex = sex;
        this.area = area;
    }
    // 省略了get和set，请自行添加

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", salary=" + salary +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", area='" + area + '\'' +
                '}';
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
