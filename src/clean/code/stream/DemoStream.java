package clean.code.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DemoStream {
    
    
    public static void main(String[] args) {
        List<String> st = Arrays.asList("a", "dfd", "dfd", "adff");
        
        Stream<String> sts = st.stream();
         
        sts = sts.map(s -> {
            System.out.println("map : " + s);
            return s.toUpperCase();
        }).filter(p -> {
            System.out.println("filter : " + p);
            return p.contains("d");
        });
        
        System.out.println("terminal =================> ");
        sts.collect(Collectors.toList());
        
    }
}


