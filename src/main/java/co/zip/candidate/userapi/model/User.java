package co.zip.candidate.userapi.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "zip_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private Double monthly_salary;
    private Double monthly_expense;
    @CreationTimestamp
    private Date created_at;

   public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getMonthly_salary() {
        return monthly_salary;
    }

    public void setMonthly_salary(Double monthly_salary) {
        this.monthly_salary = monthly_salary;
    }

    public Double getMonthly_expense() {
        return monthly_expense;
    }

    public void setMonthly_expense(Double monthly_expense) {
        this.monthly_expense = monthly_expense;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}
