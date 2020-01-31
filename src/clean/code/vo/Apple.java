package clean.code.vo;

import java.util.function.Predicate;

public class Apple {
    private String name;
    private Double weight;
    
    private Ipod ipod;
    
    public Apple() {
        System.out.println("apple is created!");
    }
    
    public Apple(String name, Double weight) {
        super();
        this.name = name;
        this.weight = weight;
    }
    
    public Apple(String name) {
        super();
        System.out.println("apple is created vs only name!");
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Ipod getIpod() {
        return ipod;
    }

    public void setIpod(Ipod ipod) {
        this.ipod = ipod;
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
