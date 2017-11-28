package com.augury.model;


import org.neo4j.ogm.annotation.GraphId;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

@JsonIdentityInfo(generator=JSOGGenerator.class)
public abstract class Entity {

    @JsonProperty("id")
    @GraphId
	protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || id == null || getClass() != o.getClass()) return false;

        Entity entity = (Entity) o;

        if (!id.equals(entity.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (id == null) ? -1 : id.hashCode();
    }
}
