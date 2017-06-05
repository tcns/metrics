package ru.cedra.metrics.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Date;


/**
 * Created by tignatchenko on 02/05/17.
 */
@Entity
@Table(indexes = {
    @Index(columnList = "date")
})
public class CommonStats {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "date", nullable = false)
    private Timestamp date;

    @Column
    private Integer visits;

    @Column
    private Integer calls;

    @Column
    private Integer goalAchievements;

    @Column
    private Float budget;

    @Column
    private Float clickCost;

    @Column
    private Integer deals;



    @JsonIgnore
    @ManyToOne
    private Metric metric;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Integer getVisits() {
        return visits;
    }

    public void setVisits(Integer visits) {
        this.visits = visits;
    }

    public Integer getCalls() {
        return calls;
    }

    public void setCalls(Integer calls) {
        this.calls = calls;
    }

    public Integer getGoalAchievements() {
        return goalAchievements;
    }

    public void setGoalAchievements(Integer goalAchievements) {
        this.goalAchievements = goalAchievements;
    }

    public Float getBudget() {
        return budget;
    }

    public void setBudget(Float budget) {
        this.budget = budget;
    }

    public Float getClickCost() {
        return clickCost;
    }

    public void setClickCost(Float clickCost) {
        this.clickCost = clickCost;
    }

    public Metric getMetric() {
        return metric;
    }

    public void setMetric(Metric metric) {
        this.metric = metric;
    }

    public Integer getDeals() {
        return deals;
    }

    public void setDeals(Integer deals) {
        this.deals = deals;
    }
}
