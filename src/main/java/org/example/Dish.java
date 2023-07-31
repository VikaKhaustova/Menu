package org.example;

import javax.persistence.Column;
        import javax.persistence.Entity;
        import javax.persistence.GeneratedValue;
        import javax.persistence.GenerationType;
        import javax.persistence.Id;
        import javax.persistence.Table;

@Entity
@Table(
        name = "Dish"
)
public class Dish {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    @Column(
            name = "myid"
    )
    private long id;
    @Column(
            nullable = false
    )
    private String name;
    private double cost;
    private double weight;
    private boolean discount;

    public Dish() {
    }

    public Dish(String name, double cost, double weight, boolean discount ) {
        this.name = name;
        this.cost = cost;
        this.weight = weight;
        this.discount = discount;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {  return this.cost; }

    public void setCost(double cost) { this.cost = cost;  }

    public double getWeight() { return this.weight; }

    public void setWeight(double weight) { this.weight = weight; }

    public boolean isDiscount() { return this.discount; }

    public void setDiscount(boolean discount) { this.discount = discount; }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                ", weight=" + weight +
                ", discount=" + discount +
                '}';
    }
}
