package org.example.projet_java.model;

public class Anomalie {
    protected String type;
    protected String description;

    public Anomalie(String type, String description) {
        this.type = type;
        this.description = description;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
