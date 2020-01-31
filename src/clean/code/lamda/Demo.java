package clean.code.lamda;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import clean.code.funInf.CampaignProcessor;

public class Demo {
    
    private static Apple app;
    
    public static void main(String[] args) throws Exception {
        List<Apple> apples = createApples();
        Predicate<Apple> appPre = p -> p.getName().equals("ip10");
        
        List<Apple> ip11 = filterApple(apples, Apple::isIp11);
        
        List<Apple> ip10 = filterApple(apples, appPre);
        
        int portNumber = 1337;
        Runnable r = () -> {
            return;
        };
        
        final Apple apdf = new Apple();
        Runnable rap = () -> System.out.println(app);
        
        app = new Apple();
        
        
        
        // define a function interface chính là định nghĩa
        // behavior 
        // sau đó áp dụng behavior cho đối tượng
        CampaignProcessor cp = c -> {
            Apple ap = new Apple();
            // do stuff
            // do something
            
            return ap;
                 
        };
        
        Apple process = cp.process(new Apple());
    }
    private static Apple create(CampaignProcessor cp) throws Exception {
       return cp.process(new Apple());
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

