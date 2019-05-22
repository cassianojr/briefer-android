package br.com.briefer.briefer.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties("__v")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Briefing implements Serializable {

    @JsonProperty("_id")
    private String id;
    private String clName;
    private String clPhone;
    private String clEmail;
    private String examples;
    private int numPages;
    private boolean hasVisual;
    private boolean hasLogo;
    private boolean hasCurrent;
    private String description;
    private String projTitle;
    private String socialMedia;
    private String outline;
    private String objective;

    @JsonProperty("createdBy")
    private String createdBy;

    private List<String> features;
    private Budget budget;

    public Briefing(){
    }

    /**
     *
     * @param clName client name
     * @param clPhone phone of client
     * @param clEmail email of client
     * @param examples examples of projects
     * @param numPages number of pages
     * @param hasVisual if project has visual
     * @param hasLogo if project has logo
     * @param hasCurrent if project has current
     * @param description description of project
     * @param projTitle title of project
     * @param socialMedia social media of project
     * @param outline outline of project
     * @param objective objective of project
     * @param features list of features
     * @param budget budget
     */
    public Briefing(String clName, String clPhone, String clEmail, String examples, int numPages, boolean hasVisual, boolean hasLogo, boolean hasCurrent, String description, String projTitle, String socialMedia, String outline, String objective, List<String> features, Budget budget, String createdBy) {
        this.clName = clName;
        this.clPhone = clPhone;
        this.clEmail = clEmail;
        this.examples = examples;
        this.numPages = numPages;
        this.hasVisual = hasVisual;
        this.hasLogo = hasLogo;
        this.hasCurrent = hasCurrent;
        this.description = description;
        this.projTitle = projTitle;
        this.socialMedia = socialMedia;
        this.outline = outline;
        this.objective = objective;
        this.features = features;
        this.budget = budget;
        this.createdBy = createdBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClName() {
        return clName;
    }

    public void setClName(String clName) {
        this.clName = clName;
    }

    public String getClPhone() {
        return clPhone;
    }

    public void setClPhone(String clPhone) {
        this.clPhone = clPhone;
    }

    public String getClEmail() {
        return clEmail;
    }

    public void setClEmail(String clEmail) {
        this.clEmail = clEmail;
    }

    public String getExamples() {
        return examples;
    }

    public void setExamples(String examples) {
        this.examples = examples;
    }

    public int getNumPages() {
        return numPages;
    }

    public void setNumPages(int numPages) {
        this.numPages = numPages;
    }

    public boolean isHasVisual() {
        return hasVisual;
    }

    public void setHasVisual(boolean hasVisual) {
        this.hasVisual = hasVisual;
    }

    public boolean isHasLogo() {
        return hasLogo;
    }

    public void setHasLogo(boolean hasLogo) {
        this.hasLogo = hasLogo;
    }

    public boolean isHasCurrent() {
        return hasCurrent;
    }

    public void setHasCurrent(boolean hasCurrent) {
        this.hasCurrent = hasCurrent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProjTitle() {
        return projTitle;
    }

    public void setProjTitle(String projTitle) {
        this.projTitle = projTitle;
    }

    public String getSocialMedia() {
        return socialMedia;
    }

    public void setSocialMedia(String socialMedia) {
        this.socialMedia = socialMedia;
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

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }
}
