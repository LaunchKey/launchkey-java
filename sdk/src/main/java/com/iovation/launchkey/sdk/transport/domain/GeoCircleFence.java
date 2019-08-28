package com.iovation.launchkey.sdk.transport.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"name", "type", "latitude", "longitude", "radius"})
public class GeoCircleFence {

    @JsonProperty("name")
    private final String fenceName;

    @JsonProperty("latitude")
    private final double latitude;

    @JsonProperty("longitude")
    private final double longitude;

    @JsonProperty("radius")
    private final double radius;

    @JsonProperty("type")
    private final String type = "GEO_CIRCLE";

    public GeoCircleFence(String fenceName, double latitude, double longitude, double radius) {
        this.fenceName = fenceName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
    }
}