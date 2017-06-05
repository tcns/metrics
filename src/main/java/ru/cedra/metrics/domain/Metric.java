package ru.cedra.metrics.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by tignatchenko on 23/04/17.
 */
@Entity
@Table(name = "metric")
public class Metric implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column
    private String name;

    @Column(name = "metric_name")
    private String countName;

    @Column
    private String campainIds;

    @Column
    private String goalId;

    @Column
    private Integer clearIncome;

    @Column
    private Float rent;

    @Column
    private Float saleConversion;

    @Column
    private Float avgCheck;

    @Column
    private Float siteConversion;

    @Column
    private Float clickPrice;

    @Column
    private String reportTime;

    @Column
    private String askTime;

    @Column
    private Integer monthCount;

    @Column
    private Integer monthDeals;

    @Column
    private Timestamp reportFromDate;


    @JsonIgnore
    @ManyToOne
    private ChatUser chatUser;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "metric")
    private Set<CommonStats> commonStats = new HashSet<>();


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

    public String getCountName() {
        return countName;
    }

    public void setCountName(String countName) {
        this.countName = countName;
    }

    public String getGoalId() {
        return goalId;
    }

    public void setGoalId(String goalId) {
        this.goalId = goalId;
    }

    public Integer getClearIncome() {
        return clearIncome;
    }

    public void setClearIncome(Integer clearIncome) {
        this.clearIncome = clearIncome;
    }

    public Float getRent() {
        return rent;
    }

    public void setRent(Float rent) {
        this.rent = rent;
    }

    public Float getSaleConversion() {
        return saleConversion;
    }

    public void setSaleConversion(Float saleConversion) {
        this.saleConversion = saleConversion;
    }

    public Float getAvgCheck() {
        return avgCheck;
    }

    public void setAvgCheck(Float avgCheck) {
        this.avgCheck = avgCheck;
    }

    public Float getSiteConversion() {
        return siteConversion;
    }

    public void setSiteConversion(Float siteConversion) {
        this.siteConversion = siteConversion;
    }

    public Float getClickPrice() {
        return clickPrice;
    }

    public void setClickPrice(Float clickPrice) {
        this.clickPrice = clickPrice;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public String getAskTime() {
        return askTime;
    }

    public void setAskTime(String askTime) {
        this.askTime = askTime;
    }



    public ChatUser getChatUser() {
        return chatUser;
    }

    public void setChatUser(ChatUser chatUser) {
        this.chatUser = chatUser;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Имя:\t").append(name).append('\n');
        sb.append("Название счетчика:\t").append(countName).append('\n');
        sb.append("Название цели:\t").append(goalId).append('\n');
        sb.append("Чистая прибыль:\t").append(clearIncome).append('\n');
        sb.append("Рентабельность бизнеса:\t").append(rent).append('\n');
        sb.append("Конверсия отдела продаж:\t").append(saleConversion).append('\n');
        sb.append("Средний чек:\t").append(avgCheck).append('\n');
        sb.append("Конверсия сайта:\t").append(siteConversion).append('\n');
        sb.append("Цена клика:\t").append(clickPrice).append('\n');
        sb.append("Время отчета:\t").append(reportTime).append('\n');
        sb.append("Время вопросов:\t").append(askTime);
        return sb.toString();
    }

    public Integer getMonthCount() {
        return monthCount;
    }

    public void setMonthCount(Integer monthCount) {
        this.monthCount = monthCount;
    }

    public Integer getMonthDeals() {
        return monthDeals;
    }

    public void setMonthDeals(Integer monthDeals) {
        this.monthDeals = monthDeals;
    }

    public Set<CommonStats> getCommonStats() {
        return commonStats;
    }

    public void setCommonStats(Set<CommonStats> commonStats) {
        this.commonStats = commonStats;
    }

    public Date getReportFromDate() {
        return reportFromDate;
    }

    public void setReportFromDate(Timestamp reportFromDate) {
        this.reportFromDate = reportFromDate;
    }

    public String getCampainIds() {
        return campainIds;
    }

    public void setCampainIds(String campainIds) {
        this.campainIds = campainIds;
    }
}
