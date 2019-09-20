package com.iovation.launchkey.sdk.transport.domain;

import com.fasterxml.jackson.annotation.*;

import java.util.Objects;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = TerritoryFence.class, name = "TERRITORY"),
        @JsonSubTypes.Type(value = GeoCircleFence.class, name = "GEO_CIRCLE")
})
public class Fence {

    @JsonProperty("type")
    private final String fenceType;

    @JsonProperty("name")
    private final String fenceName;

    @JsonCreator
    public Fence(@JsonProperty("name") String fenceName,
                 @JsonProperty("type") String fenceType) {
        this.fenceName = fenceName;
        this.fenceType = fenceType;
    }

    public String getFenceName() {
        return fenceName;
    }

    public String getType() {
        return fenceType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Fence)) return false;
        Fence that = (Fence) o;
        return Objects.equals(getFenceName(), that.getFenceName()) &&
                Objects.equals(getType(), that.getType());
    }
}
