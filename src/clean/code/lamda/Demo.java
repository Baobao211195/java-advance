package clean.code.lamda;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Demo {
    
    public static void main(String[] args) {
        List<Apple> apples = createApples();
        Predicate<Apple> appPre = p -> p.getName().equals("ip10");
        List<Apple> ip10 = filterApple(apples, appPre);
    }

    private static List<Apple> createApples() {
        return Arrays.asList(
            new Apple("ip11", 211d),
            new Apple("ip10", 210d),
            new Apple("ip9", 209d),
            new Apple("ip8", 208d),
            new Apple("ip7", 207d));
    }

    private static List<Apple> filterApple(List<Apple> apples, Predicate<Apple> appPre) {
        return apples.stream().filter(appPre).collect(Collectors.toList());
    }
   
}

