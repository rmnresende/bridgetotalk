package com.renanresende.bridgetotalk.domain;

import com.renanresende.bridgetotalk.application.port.in.command.UpdateCompanySettingsCommand;
import com.renanresende.bridgetotalk.domain.exception.BusinessException;

import java.time.Instant;

public class CompanySettings {

    private Integer maxAgents;
    private Integer maxQueues;
    private String timezone;
    private String language;
    private Instant updatedAt;
    private Plan plan;

    public CompanySettings(
            int maxAgents,
            int maxQueues,
            String timezone,
            String language,
            Instant updatedAt,
            Plan plan
    ) throws BusinessException {
        this.maxAgents = validatePositive(maxAgents, "maxAgents");
        this.maxQueues = validatePositive(maxQueues, "maxQueues");

        this.timezone = timezone;
        this.language = language;
        this.updatedAt = updatedAt == null ? Instant.now() : updatedAt;
        this.plan = plan;
    }

    // Factory method para criação inicial
    //TODO verificar crate company
    public static CompanySettings createFromPlan(Plan plan) {

        if(plan == null) {
            plan = Plan.BASIC;
        }

        return new CompanySettings(
                plan.getDefaultPlanMaxAgents(),
                plan.getDefaultPlanMaxQueues(),
                "UTC",                           // default timezone
                "en",                                    // default language
                Instant.now(),
                plan
        );
    }

    public void applyUpdate(UpdateCompanySettingsCommand update) {

        if (update == null) {
            throw new BusinessException("Update data can not be null");
        }

        Plan updatedPlan = update.plan() == null
                ? this.getPlan()
                : update.plan();

        this.setplan(updatedPlan);

        var defaultSettingsForPlan = createFromPlan(updatedPlan);

        this.setMaxAgents(
                update.maxAgents() == null
                        ? defaultSettingsForPlan.getMaxAgents()
                        : update.maxAgents()
        );

        this.setMaxQueues(
                update.maxQueues() == null
                        ? defaultSettingsForPlan.getMaxQueues()
                        : update.maxQueues()
        );

        this.setTimezone(update.timezone() == null ? this.getTimezone() : update.timezone());
        this.setLanguage(update.language() == null ? this.getLanguage() : update.language());
        this.setUpdatedAt(Instant.now());
    }

    public Integer getMaxAgents() { return maxAgents; }
    public Integer getMaxQueues() { return maxQueues; }
    public String getTimezone() { return timezone; }
    public String getLanguage() { return language; }
    public Instant getUpdatedAt() { return updatedAt; }
    public Plan getPlan() { return plan; }

    public void setMaxAgents(int maxAgents) throws BusinessException {
        this.maxAgents = validatePositive(maxAgents, "maxAgents");
    }

    public void setMaxQueues(int maxQueues) throws BusinessException {
        this.maxQueues = validatePositive(maxQueues, "maxQueues");
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setplan(Plan plan) {
        this.plan = plan;
    }

    private int validatePositive(int value, String field) throws BusinessException {
        if (value <= 0) {
            throw new BusinessException(field + " must be greater than zero");
        }
        return value;
    }

    private static boolean isBlank(String text) {
        return text == null || text.trim().isEmpty();
    }
}
