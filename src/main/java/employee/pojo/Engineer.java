package employee.pojo;

public class Engineer extends Person {

    private String computerCertificate;

    private String specialized;

    public Engineer(int id, String name, int age, String address, String type, String degree) {
        super(id, name, age, address, type, degree);
    }

    public void setComputerCertificate(String computerCertificate) {
        this.computerCertificate = computerCertificate;
    }

    public String getComputerCertificate() {
        return computerCertificate;
    }

    public void setSpecialized(String specialized) {
        this.specialized = specialized;
    }

    public String getSpecialized() {
        return this.specialized;
    }

}
