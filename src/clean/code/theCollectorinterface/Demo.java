package clean.code.theCollectorinterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import clean.code.vo.Apple;

public class Demo {
    
    public static void main(String[] args) {
        List<Apple> apls = createApples();
        
        List<Apple> apls2 = apls.stream().collect(new ToListCollector<Apple>());
        apls2.forEach(System.out::println);
    }
    
    private static List<Apple> createApples() {
        return Arrays.asList(
            new Apple("ip11", 211d),
            new Apple("ip10", 210d),
            new Apple("ip9", 209d),
            new Apple("ip8", 208d),
            new Apple("ip7", 207d));
    }


}

class PrimeCollector implements Collector<Integer, Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> {

    @Override
    public Supplier<Map<Boolean, List<Integer>>> supplier() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public BiConsumer<Map<Boolean, List<Integer>>, Integer> accumulator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public BinaryOperator<Map<Boolean, List<Integer>>> combiner() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Function<Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> finisher() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<Characteristics> characteristics() {
        // TODO Auto-generated method stub
        return null;
    }
    
}

class ToListCollector<T> implements Collector<T, List<T>, List<T>> {

    @Override
    public Supplier<List<T>> supplier() {
        return ArrayList::new;  // () -> new ArrayList<>();
    }

    @Override
    public BiConsumer<List<T>, T> accumulator() {
        return List::add;       // (list, item) -> list.add(item);
    }
    
    @Override
    public Function<List<T>, List<T>> finisher() {
        return Function.identity();
    }

    @Override
    public BinaryOperator<List<T>> combiner() {
        return (list1, list2) -> {
            list1.addAll(list2);
            return list1;
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH, Characteristics.CONCURRENT));
    }
    
}