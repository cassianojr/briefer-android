package br.com.briefer.briefer.model;

import java.util.List;

public class Briefing {

    private String cl_name;
    private String cl_phone;
    private String cl_email;
    private String examples;
    private int num_pages;
    private boolean has_visual;
    private boolean has_logo;
    private boolean has_current;
    private String description;
    private String proj_title;
    private String social_media;
    private String outline;
    private String objective;

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    private User createdBy;
    private List<String> features;
    private Budget budget;


    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    public String getCl_name() {
        return cl_name;
    }

    public void setCl_name(String cl_name) {
        this.cl_name = cl_name;
    }

    public String getCl_phone() {
        return cl_phone;
    }

    public void setCl_phone(String cl_phone) {
        this.cl_phone = cl_phone;
    }

    public String getCl_email() {
        return cl_email;
    }

    public void setCl_email(String cl_email) {
        this.cl_email = cl_email;
    }

    public String getExamples() {
        return examples;
    }

    public void setExamples(String examples) {
        this.examples = examples;
    }

    public int getNum_pages() {
        return num_pages;
    }

    public void setNum_pages(int num_pages) {
        this.num_pages = num_pages;
    }

    public boolean isHas_visual() {
        return has_visual;
    }

    public void setHas_visual(boolean has_visual) {
        this.has_visual = has_visual;
    }

    public boolean isHas_logo() {
        return has_logo;
    }

    public void setHas_logo(boolean has_logo) {
        this.has_logo = has_logo;
    }

    public boolean isHas_current() {
        return has_current;
    }

    public void setHas_current(boolean has_current) {
        this.has_current = has_current;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProj_title() {
        return proj_title;
    }

    public void setProj_title(String proj_title) {
        this.proj_title = proj_title;
    }

    public String getSocial_media() {
        return social_media;
    }

    public void setSocial_media(String social_media) {
        this.social_media = social_media;
    }

    public String getOutline() {
        return outline;
    }

    public void setOutline(String outline) {
        this.outline = outline;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public List<String> getFeatures() {
        return features;
    }

    public void setFeatures(List<String> features) {
        this.features = features;
    }
}
