/**
 * Copyright 2017 iovation, Inc.
 * <p>
 * Licensed under the MIT License.
 * You may not use this file except in compliance with the License.
 * A copy of the License is located in the "LICENSE.txt" file accompanying
 * this file. This file is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.iovation.launchkey.sdk.integration.steps;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.iovation.launchkey.sdk.domain.policy.*;
import com.iovation.launchkey.sdk.integration.Utils;
import com.iovation.launchkey.sdk.integration.cucumber.converters.LocationListConverter;
import com.iovation.launchkey.sdk.integration.cucumber.converters.TimeFenceListConverter;
import com.iovation.launchkey.sdk.integration.managers.DirectoryServicePolicyManager;
import com.iovation.launchkey.sdk.integration.managers.MutablePolicy;
import com.iovation.launchkey.sdk.integration.managers.PolicyContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Arrays;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@Singleton
public class DirectoryServicePolicySteps {
    private DirectoryServicePolicyManager directoryServicePolicyManager;
    private GenericSteps genericSteps;
    private PolicyContext policyContext;

    @Inject
    public DirectoryServicePolicySteps(
            DirectoryServicePolicyManager directoryServicePolicyManager,
            GenericSteps genericSteps,
            PolicyContext policyContext) {
        this.directoryServicePolicyManager = directoryServicePolicyManager;
        this.genericSteps = genericSteps;
        this.policyContext = policyContext;
    }

    @When("^I retrieve the Policy for the Current Directory Service$")
    public void iRetrieveTheDirectoryServicePolicy() throws Throwable {
        directoryServicePolicyManager.retrievePolicyForCurrentService();
    }

    @When("^I retrieve the Advanced Policy for the Current Directory Service$")
    public void iRetrieveTheDirectoryServiceAdvancedPolicy() throws Throwable {
        policyContext.setCurrentPolicy(directoryServicePolicyManager.getCurrentlySetDirectoryServiceAdvancedPolicy());
        policyContext.setCurrentPolicy(directoryServicePolicyManager.getCurrentlySetDirectoryServiceAdvancedPolicy());
    }

    @When("^I attempt to remove the Policy for the Directory Service with the ID \"([^\"]*)\"$")
    public void iAttemptToRemoveThePolicyForTheDirectoryServiceWithTheID(String uuid) throws Throwable {
        UUID serviceId = UUID.fromString(uuid);
        try {
            directoryServicePolicyManager.removePolicyForService(serviceId);
        } catch (Exception e) {
            genericSteps.setCurrentException(e);
        }
    }

    @When("^I remove the Policy for the Directory Service$")
    public void iRemoveThePolicyForTheDirectoryService() throws Throwable {
        directoryServicePolicyManager.removePolicyForCurrentService();
    }

    @When("^I attempt to retrieve the Policy for the Directory Service with the ID \"([^\"]*)\"$")
    public void iAttemptToRetrieveThePolicyForTheDirectoryServiceWithTheID(String uuid) throws Throwable {
        UUID serviceId = UUID.fromString(uuid);
        try {
            directoryServicePolicyManager.retrievePolicyForService(serviceId);
        } catch (Exception e) {
            genericSteps.setCurrentException(e);
        }
    }

    @When("^I attempt to set the Policy for the Directory Service with the ID \"([^\"]*)\"$")
    public void iAttemptToSetThePolicyForTheDirectoryServiceWithTheID(String uuid) throws Throwable {
        UUID serviceId = UUID.fromString(uuid);
        try {
            directoryServicePolicyManager.setPolicyForService(serviceId);
        } catch (Exception e) {
            genericSteps.setCurrentException(e);
        }
    }

    @When("^I set the Policy for the Directory Service$")
    public void iSetThePolicyForTheDirectoryService() throws Throwable {
        directoryServicePolicyManager.setAdvancedPolicyForCurrentService();
    }

    @When("^the Directory Service Policy is set to require (\\d+) factors?$")
    public void theDirectoryServicePolicyRequiresFactors(int requiredFactors) throws Throwable {
        directoryServicePolicyManager.getCurrentServicePolicyEntity().setRequiredFactors(requiredFactors);
    }

    @When("^I set the Policy for the Current Directory Service$")
    public void iSetThePolicyForTheCurrentDirectoryService() throws Throwable {
        directoryServicePolicyManager.setAdvancedPolicyForCurrentService();
    }

    @When("^the Directory Service Policy is (not set|set) to require inherence$")
    public void theDirectoryServicePolicyIsSetToRequireInherence(String switchString) throws Throwable {
        directoryServicePolicyManager.getCurrentServicePolicyEntity()
                .setRequireInherenceFactor(Utils.getBooleanFromBooleanTextSwitch(switchString));
    }

    @When("^the Directory Service Policy is (not set|set) to require knowledge$")
    public void theDirectoryServicePolicyIsSetToRequireKnowledge(String switchString) throws Throwable {
        directoryServicePolicyManager.getCurrentServicePolicyEntity()
                .setRequireKnowledgeFactor(Utils.getBooleanFromBooleanTextSwitch(switchString));
    }

    @When("^the Directory Service Policy is (not set|set) to require possession$")
    public void theDirectoryServicePolicyIsSetToRequirePossession(String switchString) throws Throwable {
        directoryServicePolicyManager.getCurrentServicePolicyEntity()
                .setRequirePossessionFactor(Utils.getBooleanFromBooleanTextSwitch(switchString));
    }

    @When("^the Directory Service Policy is (not set|set) to require jail break protection$")
    public void theDirectoryServicePolicyIsSetToRequireJailBreakProtection(String switchString) throws Throwable {
        directoryServicePolicyManager.getCurrentServicePolicyEntity()
                .setJailBreakProtectionEnabled(Utils.getBooleanFromBooleanTextSwitch(switchString));
    }

    @Then("^the Directory Service Policy has no requirement for number of factors$")
    public void theDirectoryServicePolicyRequiresFactory() throws Throwable {
        assertThat(directoryServicePolicyManager.getCurrentServicePolicyEntity().getRequiredFactors(), is(nullValue()));
    }

    @Then("^the Directory Service Policy requires (\\d+) factors$")
    public void theDirectoryServicePolicyRequiresFactory(int numberOfFactors) throws Throwable {
        assertThat(directoryServicePolicyManager.getCurrentServicePolicyEntity().getRequiredFactors(),
                is(equalTo(numberOfFactors)));
    }

    @Then("^the Directory Service Policy (does not|does|has no) require(?:ment for)? inherence$")
    public void theDirectoryServicePolicyDoesNotRequireInherence(String switchString) throws Throwable {
        assertThat(directoryServicePolicyManager.getCurrentServicePolicyEntity().getRequireInherenceFactor(),
                is(Utils.getBooleanFromBooleanTextSwitch(switchString)));
    }

    @Then("^the Directory Service Policy (does not|does|has no) require(?:ment for)? knowledge$")
    public void theDirectoryServicePolicyDoesNotRequireKnowledge(String switchString) throws Throwable {
        assertThat(directoryServicePolicyManager.getCurrentServicePolicyEntity().getRequireKnowledgeFactor(),
                is(Utils.getBooleanFromBooleanTextSwitch(switchString)));
    }

    @Then("^the Directory Service Policy (does not|does|has no) require(?:ment for)? possession$")
    public void theDirectoryServicePolicyDoesNotRequirePossession(String switchString) throws Throwable {
        assertThat(directoryServicePolicyManager.getCurrentServicePolicyEntity().getRequirePossessionFactor(),
                is(Utils.getBooleanFromBooleanTextSwitch(switchString)));
    }

    @Then("^the Directory Service Policy has (\\d+) locations$")
    public void theDirectoryServicePolicyHasLocations(int numberOfLocations) throws Throwable {
        assertThat(directoryServicePolicyManager.getCurrentServicePolicyEntity().getLocations(),
                hasSize(numberOfLocations));
    }

    @Then("^the Directory Service Policy has (\\d+) time fences$")
    public void theDirectoryServicePolicyHasTimeFences(int numberOfFences) throws Throwable {
        assertThat(directoryServicePolicyManager.getCurrentServicePolicyEntity().getTimeFences(),
                hasSize(numberOfFences));
    }

    @Then("^the Directory Service Policy (does not|does|has no) require(?:ment for)? jail break protection$")
    public void theDirectoryServicePolicyDoesNotRequireJailBreakProtection(String switchString) throws Throwable {
        assertThat(directoryServicePolicyManager.getCurrentServicePolicyEntity().getJailBreakProtectionEnabled(),
                is(Utils.getBooleanFromBooleanTextSwitch(switchString)));
    }

    @Given("^the Directory Service Policy is set to have the following Time Fences:$")
    public void theDirectoryServicePolicyIsSetToHaveTheFollowingTimeFences(DataTable dataTable) throws Throwable {

        directoryServicePolicyManager.getCurrentServicePolicyEntity().getTimeFences().addAll(TimeFenceListConverter.fromDataTable(dataTable));
    }

    @Given("^the Directory Service Policy has the following Time Fences:$")
    public void theDirectoryServicePolicyHasTheFollowingTimeFences(DataTable dataTable) throws Throwable {
        assertThat(directoryServicePolicyManager.getCurrentServicePolicyEntity().getTimeFences(), is(equalTo(TimeFenceListConverter.fromDataTable(dataTable))));
    }

    @Given("^the Directory Service Policy is set to have the following Geofence locations:$")
    public void theDirectoryServicePolicyIsSetToHaveTheFollowingGeofenceLocations(DataTable dataTable) throws Throwable {
        directoryServicePolicyManager.getCurrentServicePolicyEntity().getLocations().addAll(LocationListConverter.fromDataTable(dataTable));
    }

    @Given("^the Directory Service Policy has the following Geofence locations:$")
    public void theDirectoryServicePolicyHasTheFollowingGeofenceLocations(DataTable dataTable) throws Throwable {
        assertThat(directoryServicePolicyManager.getCurrentServicePolicyEntity().getLocations(), is(equalTo(LocationListConverter.fromDataTable(dataTable))));
    }

    @When("^I set the Advanced Policy for the Current Directory Service to the new policy$")
    public void iSetPolicyForCurrentDirectory() throws Throwable {
        directoryServicePolicyManager.setAdvancedPolicyForCurrentService(policyContext.currentPolicy.toImmutablePolicy());
    }

    @Then("^the Directory Service Policy has \"(\\d+)\" fence(?:s)?$")
    public void directoryServicePolicyHasAmountFences(int amount) throws Throwable {
        Policy policy = directoryServicePolicyManager.getCurrentlySetDirectoryServiceAdvancedPolicy();
        assertThat(policy.getFences().size(), is(equalTo(amount)));
    }

    @Then("^the Directory Service Policy contains the GeoCircleFence \"([^\"]*)\"$")
    public void directoryServicePolicyHasGeoCircleFenceNamed(String fenceName) throws Throwable {
        Policy policy = directoryServicePolicyManager.getCurrentlySetDirectoryServiceAdvancedPolicy();
        boolean fenceNameMatches = false;
        for (Fence fence : policy.getFences()) {
            if (fence.getName().equals(fenceName)) {
                fenceNameMatches = true;
                policyContext.fenceCache = fence;
            }
        }
        assertThat(fenceNameMatches, is(equalTo(true)));
    }

    @Then("^the Directory Service Policy contains the TerritoryFence \"([^\"]*)\"$")
    public void directoryServicePolicyHasTerritoryFenceNamed(String fenceName) throws Throwable {
        Policy policy = directoryServicePolicyManager.getCurrentlySetDirectoryServiceAdvancedPolicy();
        boolean fenceNameMatches = false;
        for (Fence fence : policy.getFences()) {
            if (fence.getName().equals(fenceName)) {
                fenceNameMatches = true;
                policyContext.fenceCache = fence;
                break;
            }
        }
        assertThat(fenceNameMatches, is(equalTo(true)));
    }

    @Given("^the Directory Service is set to any Conditional Geofence Policy$")
    public void directoryServiceIsSetToAnyConditionalGeofencePolicy() throws Throwable {
        policyContext.currentPolicy = new MutablePolicy(new ConditionalGeoFencePolicy(
                false, false,
                Arrays.asList(new TerritoryFence("US", "US", null, null)),
                new MethodAmountPolicy(false, false, null, 0),
                new MethodAmountPolicy(false, false, null, 0)));
        directoryServicePolicyManager.setAdvancedPolicyForCurrentService(policyContext.currentPolicy.toImmutablePolicy());
    }
}
