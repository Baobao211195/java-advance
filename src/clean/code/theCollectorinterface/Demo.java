package clean.code.theCollectorinterface;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;
import clean.code.vo.Apple;

import static java.util.stream.Collector.Characteristics.IDENTITY_FINISH;

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

     public static <A> List<A> takeWhile(List<A> list, Predicate<A> pc) {
        int i = 0;
         for (A item: list) {
             if(!pc.test(item)) {
                return list.subList(0, i);
             }
             i++;
         }
         return list;
     }

    public static boolean isPrime(List<Integer> primes, int candidate) {
        int candidateRoot = (int) Math.sqrt((double) candidate);
        return takeWhile(primes, i -> i <= candidateRoot)
                .stream()
                .noneMatch(p -> candidate % p == 0);
    }

    @Override
    public Supplier<Map<Boolean, List<Integer>>> supplier() {
        return () -> new HashMap<Boolean, List<Integer>>() {
            {
                put(true, new ArrayList<Integer>());
                put(false, new ArrayList<Integer>());
            }
        };

    }

    @Override
    public BiConsumer<Map<Boolean, List<Integer>>, Integer> accumulator() {
        return (Map<Boolean, List<Integer>> map, Integer candidate) -> {
            map.get(isPrime(map.get(true), candidate)).add(candidate);
        };
    }

    @Override
    public BinaryOperator<Map<Boolean, List<Integer>>> combiner() {
        return (Map<Boolean, List<Integer>> map1,
                Map<Boolean, List<Integer>> map2) -> {
            map1.get(true).addAll(map2.get(true));
            map1.get(false).addAll(map2.get(false));
            return map1;
        };
    }

    @Override
    public Function<Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> finisher() {
        return Function.identity();
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(EnumSet.of(IDENTITY_FINISH));
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
        return Collections.unmodifiableSet(EnumSet.of(IDENTITY_FINISH, Characteristics.CONCURRENT));
    }
    
}