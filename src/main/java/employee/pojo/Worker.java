package employee.pojo;

import java.util.List;


public class Worker extends Person {

    List<String> skills;
    public Worker(int id, String name, int age, String address, String type, String level) {
        super(id, name, age, address, type, level);
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public List<String> getSkills() {
        return this.skills;
    }
}
