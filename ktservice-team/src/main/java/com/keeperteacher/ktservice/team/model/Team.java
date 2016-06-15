package com.keeperteacher.ktservice.team.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.keeperteacher.ktservice.model.PersistedObject;
import com.keeperteacher.ktservice.model.ValidationProtocol;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "ktservice_team")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Team extends PersistedObject {

    @Column(name = "name")
    @Size(min = 1, max = 255)
    @NotNull
    @Pattern(regexp = ValidationProtocol.ALPHANUMERIC_WITH_SPACES_REGEX, message = "Team name must be alphanumeric!")
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
