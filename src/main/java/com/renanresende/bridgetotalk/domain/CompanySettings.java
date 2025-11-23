package com.renanresende.bridgetotalk.domain;

import com.renanresende.bridgetotalk.commom.BusinessException;
import org.apache.commons.lang3.StringUtils;

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
    public static CompanySettings createFromPlan(Plan plan) throws BusinessException {

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

    public static CompanySettings updatePlan(CompanySettings newSettings, CompanySettings existingSettings) throws BusinessException {

        if(newSettings == null) {
            throw new BusinessException("Plan can not be null");
        }

        if(newSettings.getPlan() == null) {
            newSettings.setplan(existingSettings.getPlan());
        }


        //Cada plano tem as settings default, mas elas podem ser alteradas/customizadas
        var newDefaultSettingsPLan = createFromPlan(newSettings.getPlan());


        //timezone e langague são usadas as que já existem caso não tenham sido informadas
        existingSettings.setLanguage(StringUtils.isBlank(newSettings.getLanguage()) ? existingSettings.getLanguage() : newSettings.getLanguage());
        existingSettings.setTimezone(StringUtils.isBlank(newSettings.getTimezone()) ? existingSettings.getTimezone() : newSettings.getTimezone());

        //verfivicando se foram enviadas settings customizadas ou se vai usar as defaults do novo plano
        existingSettings.setMaxAgents(newSettings.getMaxAgents() == null ? newDefaultSettingsPLan.getMaxAgents() : newSettings.getMaxAgents());
        existingSettings.setMaxQueues(newSettings.getMaxQueues() == null ? newDefaultSettingsPLan.getMaxQueues() : newSettings.getMaxQueues());
        existingSettings.setUpdatedAt(newDefaultSettingsPLan.getUpdatedAt());
        existingSettings.setplan(newSettings.getPlan());

        return existingSettings;
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
}
