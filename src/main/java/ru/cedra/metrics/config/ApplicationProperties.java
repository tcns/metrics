package ru.cedra.metrics.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to JHipster.
 *
 * <p>
 *     Properties are configured in the application.yml file.
 * </p>
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private String botToken;

    private String botUsername;

    private String yaClientId;

    private String yaClientSecret;

    public String getBotToken() {
        return botToken;
    }

    public String getBotUsername() {
        return botUsername;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

    public void setBotUsername(String botUsername) {
        this.botUsername = botUsername;
    }

    public String getYaClientId() {
        return yaClientId;
    }

    public void setYaClientId(String yaClientId) {
        this.yaClientId = yaClientId;
    }

    public String getYaClientSecret() {
        return yaClientSecret;
    }

    public void setYaClientSecret(String yaClientSecret) {
        this.yaClientSecret = yaClientSecret;
    }
}
