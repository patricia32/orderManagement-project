/**
 * Clasa pentru stocarea informatiilor despre lcient
 */
package Model;

public class Client {
    private int id;
    private String name;
    private int age;
    private int budget;

    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", budget=" + budget +
                '}';
    }
    public Client(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public int getBudget() {
        return budget;
    }
    public void setBudget(int budget) {
        this.budget = budget;
    }

    /**
     * constructor
     * @param id
     * @param name
     * @param age
     * @param budget
     */
    public Client(int id, String name, int age, int budget) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.budget = budget;
    }
}
