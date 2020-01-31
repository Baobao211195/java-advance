package clean.code.vo;

public class Ipod {
    
    private String branch;
    private String name;
    private String manufacture;

    public String getBranch() {
        return branch;
    }

    public String getName() {
        return name;
    }

    public String getManufacture() {
        return manufacture;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Ipod(String branch, String name, String manufacture) {
        super();
        this.branch = branch;
        this.name = name;
        this.manufacture = manufacture;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

}
