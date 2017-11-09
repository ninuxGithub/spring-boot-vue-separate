package com.example.demo.lambda;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * http://www.jdon.com/idea/java/10-example-of-lambda-expressions-in-java8.html
 */
public class LambdaTest {

    public static void main(String[] args) {
        //线程的lambda使用
        new Thread(() -> {
            System.out.println("我是多线程：Hello Lambda");
        }).start();


        List<String> features = Arrays.asList("Lambda", "Default-Method", "System-Api", "Date and Time A", "java", "jack", "json");
        for (String f : features) {
            System.out.println(f);
        }
        //集合的lambda使用
        features.forEach((n) -> {
            System.out.println(n);
        });

        System.out.println("++++++++++++++++++++++++++++++++++++++++++++");
        filter(features, (str) -> str.toString().startsWith("D"));


        //以j大头， 字符长度为4个字符的字符串
        Predicate<String> startWithJ = (s) -> s.startsWith("j");
        Predicate<String> fourLetter = (s) -> s.length() == 4;

        features.stream().filter(startWithJ.and(fourLetter)).forEach((n) -> {
            System.out.println(n);
        });


        System.out.println("++++++++++++++++++++++++map reduce");
        //map reduce
        List<Integer> costBeforeTax = Arrays.asList(100, 200, 300, 400, 500);
        for (Integer cost : costBeforeTax) {
            double price = cost + .12 * cost;
            System.out.println(price);
        }

        costBeforeTax.stream().map((c) -> c + 0.12 * c).forEach(System.out::println);


        double bill = costBeforeTax.stream()
                .map((c) -> c + 0.12 * c)
                .reduce((sum, c) -> sum + c)
                .get();

        System.out.println("bill:"+ bill);



        System.out.println("collect 方法");
        List<String> collect = features.stream().filter(x -> x.length() > 4).collect(Collectors.toList());
        System.out.println(collect);

        List<String> G7 = Arrays.asList("USA", "Japan", "France", "Germany","Italy", "U.K.","Canada");
        String joinStr = G7.stream().map(x -> x.toUpperCase()).collect(Collectors.joining(","));
        System.out.println(joinStr);


        List<Integer> numbers = Arrays.asList(9, 10, 3, 4, 7, 3, 4);
        List<Integer> powList = numbers.stream().map((i) -> i * i).distinct().collect(Collectors.toList());
        System.out.println("对平方进行distinct："+ powList);

        IntSummaryStatistics intSummaryStatistics = numbers.stream().mapToInt((x) -> x).summaryStatistics();
        System.out.println("max :"+ intSummaryStatistics.getMax());
        System.out.println("main :"+ intSummaryStatistics.getMin());
        System.out.println("average :"+ intSummaryStatistics.getAverage());
        System.out.println("sum :"+ intSummaryStatistics.getSum());

    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static void filter(List<String> list, Predicate predicate) {
        list.stream().filter((l) -> (predicate.test(l))).forEach((l) -> {
            System.out.println(l);
        });
    }

}


/**
 * 你可以使用 下面语法实现Lambda:
 * <p>
 * (params) -> expression
 * (params) -> statement
 * (params) -> { statements }
 * <p>
 * 带参数的例子可以在：baseController.java中找到
 */