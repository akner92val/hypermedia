package com.mlavrenko.common.jpa.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "javax.persistence.schema-generation")
public class JpaSchemaGenerationProperties {

    private String databaseAction;
    private String scriptsAction;
    private String createSource;
    private String scriptsCreateTarget;
    private String createScriptSource;
    private String dropSource;
    private String dropScriptSource;
    private String scriptsDropTarget;

    public String getDatabaseAction() {
        return databaseAction;
    }

    public void setDatabaseAction(String databaseAction) {
        this.databaseAction = databaseAction;
    }

    public String getScriptsAction() {
        return scriptsAction;
    }

    public void setScriptsAction(String scriptsAction) {
        this.scriptsAction = scriptsAction;
    }

    public String getCreateSource() {
        return createSource;
    }

    public void setCreateSource(String createSource) {
        this.createSource = createSource;
    }

    public String getScriptsCreateTarget() {
        return scriptsCreateTarget;
    }

    public void setScriptsCreateTarget(String scriptsCreateTarget) {
        this.scriptsCreateTarget = scriptsCreateTarget;
    }

    public String getCreateScriptSource() {
        return createScriptSource;
    }

    public void setCreateScriptSource(String createScriptSource) {
        this.createScriptSource = createScriptSource;
    }

    public String getDropSource() {
        return dropSource;
    }

    public void setDropSource(String dropSource) {
        this.dropSource = dropSource;
    }

    public String getDropScriptSource() {
        return dropScriptSource;
    }

    public void setDropScriptSource(String dropScriptSource) {
        this.dropScriptSource = dropScriptSource;
    }

    public String getScriptsDropTarget() {
        return scriptsDropTarget;
    }

    public void setScriptsDropTarget(String scriptsDropTarget) {
        this.scriptsDropTarget = scriptsDropTarget;
    }
}
