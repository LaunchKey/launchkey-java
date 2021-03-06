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

package com.iovation.launchkey.sdk.external;

import com.iovation.launchkey.sdk.client.OrganizationFactory;
import com.iovation.launchkey.sdk.transport.Transport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.mockito.Mockito.mock;

/**
 * These tests are for regression and only ensure that the factory methods are accessible outside the package
 */
public class OrganizationFactoryExternalAccessVerificationTest {

    private OrganizationFactory factory = null;

    @Before
    public void setUp() throws Exception {
        factory = new OrganizationFactory(mock(Transport.class), UUID.fromString("49af9c38-31b3-11e7-93ae-92361f002671"));
    }

    @After
    public void tearDown() throws Exception {
        factory = null;
    }

    @Test
    public void testMakeDirectoryClient() throws Exception {
        factory.makeDirectoryClient("49af9c38-31b3-11e7-93ae-92361f002671");
    }

    @Test
    public void testMakeServiceClient() throws Exception {
        factory.makeServiceClient("49af9c38-31b3-11e7-93ae-92361f002671");
    }
}
