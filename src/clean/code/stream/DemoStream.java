package clean.code.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DemoStream {
    
    
    public static void main(String[] args) {
        List<String> st = Arrays.asList("aa", "dfd", "dfd", "adff");
        
        Stream<String> sts = Arrays.asList("a", "dfd", "dfd", "adff").stream();
         
//        sts.map(s -> {
//            System.out.println("map : " + s);
//            return s.toUpperCase();
//        }).filter(p -> {
//            System.out.println("filter : " + p);
//            return p.contains("d");
//        });
//
//        System.out.println("terminal =================> ");
//        sts.collect(Collectors.toList());
        
        // C�ch 1
        Arrays.asList("aa", "rfrrfd", "fd", "dff").stream().collect(() -> new LinkedList(),
                (e1, e2) -> e1.add(e2),
                (c1, c2) -> c1.addAll(c2)); 
        
        // create BiConsumer
        BiConsumer<List<String>, String> biConsumer1       = (c1, c2) -> c1.add(c2);
        BiConsumer<List<String>, List<String>> biConsumer2 = (c1, c2) -> c1.addAll(c2);
        
        // C�ch 2
        Supplier<List<String>> c2s  = ArrayList::new;
        Supplier<List<String>> spLk = LinkedList::new;
        
        List<String> collects = sts.collect(c2s, biConsumer1, biConsumer2);

        Arrays.asList("aa", "rfrrfd", "fd", "dff").stream().collect(ArrayList::new,
                ArrayList::add,
                ArrayList::addAll);

        Arrays.asList("aa", "rfrrfd", "fd", "dff").stream().collect(Collectors.toMap(k -> k.charAt(0), v -> v.charAt(1)));
        
        // create a stream         tyh
        IntStream.range(0,5).map(x -> x*2)
            .forEach(System.out::println);
        
    }
}


