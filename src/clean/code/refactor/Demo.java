package clean.code.refactor;

import clean.code.vo.Apple;

import java.util.function.Supplier;

public class Demo {

    public static void main(String[] args) {
        Apple ap1 = new Apple();
//        String name = getAppleName(ap1);

        Supplier<Apple> sap1 = Apple::new;
//        String name1 = getAppleName(sap1.get());
    }

    private static String getAppleName (Apple apple){
        return apple.getName();
    }

}
