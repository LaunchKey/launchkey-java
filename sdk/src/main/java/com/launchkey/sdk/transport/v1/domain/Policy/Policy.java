/**
 * Copyright 2016 LaunchKey, Inc.  All rights reserved.
 * <p>
 * Licensed under the MIT License.
 * You may not use this file except in compliance with the License.
 * A copy of the License is located in the "LICENSE.txt" file accompanying
 * this file. This file is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.launchkey.sdk.transport.v1.domain.Policy;

import java.util.List;

public class Policy {
    private MinimumRequirements minimumRequirements;
    private List<Factor> factors;

    public Policy(MinimumRequirements minimumRequirements, List<Factor> factors) {
        this.minimumRequirements = minimumRequirements;
        this.factors = factors;
    }
}