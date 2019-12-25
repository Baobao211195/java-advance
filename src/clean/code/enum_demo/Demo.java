package clean.code.enum_demo;

import java.util.function.BiFunction;
import java.util.stream.Stream;

class Movie {
    
    private final Type type;
    enum Type {
        ACTION(PriceService::calculateActionMovieType),
        REGULAR(PriceService::calculateRegularMovieType),
        ADULT(PriceService::calculateAdultMovieType);
        
        public final BiFunction<PriceService, Integer, Integer> priceAlgo;
        
        Type(BiFunction<PriceService, Integer, Integer> priceAlgo) {
            this.priceAlgo = priceAlgo;
        }
        
        public BiFunction<PriceService, Integer, Integer> getPriceAlgo() {
            return priceAlgo;
        }
        
    }
    public Movie(Type type) {
        this.type = type;
    }
    public Type getType() {
        return type;
    }
    
}
interface Repository {
    Double getRepository();
}
interface PriceService {
    public Integer calculateActionMovieType(int day);
    public Integer calculateRegularMovieType(int day);
    public Integer calculateAdultMovieType(int day);
    public Integer calculateMovie(Movie.Type tye, int days);
}
class PriceServiceImpl implements PriceService {
    private Repository repository = () -> 5d;
    
    @Override
    public Integer calculateActionMovieType(int day) {
        return day + 1 + repository.getRepository().intValue();
    }
    
    @Override
    public Integer calculateRegularMovieType(int day) {
        return day + 2 + repository.getRepository().intValue();
    }
    
    @Override
    public Integer calculateAdultMovieType(int day) {
        return day + 3 + repository.getRepository().intValue();
    }

    @Override
    public Integer calculateMovie(Movie.Type type, int day) {
        return type.priceAlgo.apply(this, day);
    }
}
public class Demo {
    public static void main(String[] args) {
        PriceService service = new PriceServiceImpl();
        Stream.of(Movie.Type.values()).forEach(p ->  System.out.println(p.getPriceAlgo().apply(service, 5)));
    }
}
