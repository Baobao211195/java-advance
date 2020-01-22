package clean.code.lamda;

import java.util.function.Predicate;

public class Apple {
    private String name;
    private Double weight;

    public Apple(String name, Double weight) {
        super();
        this.name = name;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public Double getWeight() {
        return weight;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public boolean isIp11() {
        return this.getName().equals("ip11");
    }

    public Predicate<Apple> isIp10() {
        return p -> p.getName().equals("ip11");
    }

}
