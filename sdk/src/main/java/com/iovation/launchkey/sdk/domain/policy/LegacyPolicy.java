package com.iovation.launchkey.sdk.domain.policy;

import com.iovation.launchkey.sdk.domain.servicemanager.ServicePolicy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LegacyPolicy implements Policy {

    private final int amount;
    private final boolean inherence;
    private final boolean knowledge;
    private final boolean possession;
    private final boolean denyRootedJailbroken;
    private final List<GeoCircleFence> fences;
    private final List<ServicePolicy.TimeFence> timeFences;

    public LegacyPolicy(int amount, boolean inherence, boolean knowledge, boolean possession, boolean denyRootedJailbroken, List<GeoCircleFence> fences, List<ServicePolicy.TimeFence> timeFences) {
        this.amount = amount;
        this.inherence = inherence;
        this.knowledge = knowledge;
        this.possession = possession;
        this.denyRootedJailbroken = denyRootedJailbroken;
        this.fences = fences;
        this.timeFences = timeFences;
    }

    public int getAmount() {
        return amount;
    }

    public boolean isInherence() {
        return inherence;
    }

    public boolean isKnowledge() {
        return knowledge;
    }

    public boolean isPossession() {
        return possession;
    }

    @Override
    public boolean getDenyRootedJailbroken() {
        return denyRootedJailbroken;
    }

    @Override
    public boolean getDenyEmulatorSimulator() {
        return false;
    }

    public List<Fence> getFences() {
        return Collections.unmodifiableList(new ArrayList<Fence>(fences));
    }

    public List<ServicePolicy.TimeFence> getTimeFences() {
        return Collections.unmodifiableList(timeFences);
    }
}
