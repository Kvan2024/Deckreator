package tapmana.model.dto;

import jakarta.validation.constraints.NotEmpty;

public class DeckDto {
    @NotEmpty(message = "Can not have blank name")
    String name;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}