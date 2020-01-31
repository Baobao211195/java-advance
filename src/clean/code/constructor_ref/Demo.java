package clean.code.constructor_ref;

import java.util.function.Function;
import java.util.function.Supplier;
import clean.code.vo.Apple;

public class Demo {
    
    
    public static void main(String[] args) {
        
        
        Supplier<Apple> sAp = Apple::new; // don't print apple is created!
        
        Apple a = new Apple(); // print apple is created!
        
        Apple a1 = sAp.get();
        
        //Apple a2 = sAp.get();
        
        
        Function<String, Apple> fAp = Apple::new; // don't print apple is created vs only name!
        
        Apple a3 = fAp.apply("iphon11");
        
        
        
        
    }

}
