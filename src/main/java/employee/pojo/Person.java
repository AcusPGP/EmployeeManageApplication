package employee.pojo;

public class Person implements IGeneral {
    private String name;
    private int id;
    private int age;
    private String address;
    private String type;
    private String level;

    public Person(int id, String name, int age, String address, String type, String level) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.id = id;
        this.type = type;
        this.level = level;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

    public String toString() {
        return id + "@" + name + "@" + age + "@" + address + "@" + type + "@" + level;
    }

    public void setNewId(String newId) {
        this.id = Integer.parseInt(newId);
    }
    public void setNewAge(String newAge) {
        this.age = Integer.parseInt(newAge);
    }
}
