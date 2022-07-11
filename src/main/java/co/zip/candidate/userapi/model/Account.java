package co.zip.candidate.userapi.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long user_id;
    private Double amount_spent = 0.0;
    @CreationTimestamp
    private Date created_at;
    private Integer status = 1;
    private Date monthly_plan_start_date;

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Double getAmount_spent() {
        return amount_spent;
    }

    public void setAmount_spent(Double amount_spent) {
        this.amount_spent = amount_spent;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getMonthly_plan_start_date() {
        return monthly_plan_start_date;
    }

    public void setMonthly_plan_start_date(Date monthly_plan_start_date) {
        this.monthly_plan_start_date = monthly_plan_start_date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}
