package ru.cedra.metrics.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by tignatchenko on 25/04/17.
 */
@Entity
public class ChatUser {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column
    private String telegramUsername;


    @Column
    private Long telegramChatId;

    @Column
    private String yaToken;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "chatUser", cascade = CascadeType.ALL)
    @JsonIgnore
    private ChatState chatState;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "chatUser")
    private Set<Metric> metrics = new HashSet<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTelegramUsername() {
        return telegramUsername;
    }

    public void setTelegramUsername(String telegramUsername) {
        this.telegramUsername = telegramUsername;
    }

    public String getYaToken() {
        return yaToken;
    }

    public void setYaToken(String yaToken) {
        this.yaToken = yaToken;
    }

    public ChatState getChatState() {
        return chatState;
    }

    public void setChatState(ChatState chatState) {
        this.chatState = chatState;
    }

    public Set<Metric> getMetrics() {
        return metrics;
    }

    public void setMetrics(Set<Metric> metrics) {
        this.metrics = metrics;
    }

    public Long getTelegramChatId() {
        return telegramChatId;
    }

    public void setTelegramChatId(Long telegramChatId) {
        this.telegramChatId = telegramChatId;
    }
}
