package com.iovation.launchkey.sdk.client;

import com.iovation.launchkey.sdk.domain.webhook.AdvancedAuthorizationResponseWebhookPackage;
import com.iovation.launchkey.sdk.domain.webhook.AuthorizationResponseWebhookPackage;
import com.iovation.launchkey.sdk.domain.webhook.ServiceUserSessionEndWebhookPackage;
import com.iovation.launchkey.sdk.domain.webhook.WebhookPackage;
import com.iovation.launchkey.sdk.error.InvalidRequestException;
import com.iovation.launchkey.sdk.transport.Transport;
import com.iovation.launchkey.sdk.transport.domain.ServerSentEvent;
import com.iovation.launchkey.sdk.transport.domain.ServerSentEventAuthorizationResponse;
import com.iovation.launchkey.sdk.transport.domain.ServerSentEventUserServiceSessionEnd;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BasicServiceClientHandleAdvancedWebhookTest extends TestCase {

    @Mock
    public Transport transport;

    @Mock
    private ServerSentEventAuthorizationResponse serverSentEventAuthorizationResponse;

    @Mock
    private ServerSentEventUserServiceSessionEnd serverSentEventUserServiceSessionEnd;

    private final static UUID serviceId = UUID.randomUUID();
    private BasicServiceClient client;


    @Before
    public void setUp() throws Exception {
        client = new BasicServiceClient(serviceId, transport);
        when(serverSentEventAuthorizationResponse.getAuthorizationRequestId()).thenReturn(UUID.randomUUID());
        when(serverSentEventAuthorizationResponse.getServicePins()).thenReturn(new String[]{});
    }

    @Test
    public void handleAdvancedWebhookPassesPathToTransport() throws Exception {
        client.handleAdvancedWebhook(new HashMap<String, List<String>>(), "body", "method", "path");
        verify(transport).handleServerSentEvent(ArgumentMatchers.<String, List<String>>anyMap(), anyString(), eq("path"), anyString());
    }

    @Test
    public void handleAdvancedWebhookPassesMethodToTransport() throws Exception {
        client.handleAdvancedWebhook(new HashMap<String, List<String>>(), "body", "method", "path");
        verify(transport).handleServerSentEvent(ArgumentMatchers.<String, List<String>>anyMap(), eq("method"), anyString(), anyString());
    }

    @Test
    public void handleAdvancedWebhookPassesBodyToTransport() throws Exception {
        client.handleAdvancedWebhook(new HashMap<String, List<String>>(), "body", "method", "path");
        verify(transport).handleServerSentEvent(ArgumentMatchers.<String, List<String>>anyMap(), anyString(), anyString(), eq("body"));
    }

    @Test
    public void handleAdvancedWebhookPassesHeaders() throws Exception {
        Map<String, List<String>> expected = new HashMap<>();
        expected.put("key", Arrays.asList("value", "value"));
        client.handleAdvancedWebhook(expected, "body", "method", "path");
        verify(transport).handleServerSentEvent(eq(expected), anyString(), eq("path"), anyString());
    }

    @Test
    public void handleAdvancedWebhookWhenNullReturnedByTransportNullIsReturned() throws Exception{
        WebhookPackage actual = client.handleAdvancedWebhook(null, null, null, null);
        assertNull(actual);
    }

    @Test(expected = InvalidRequestException.class)
    public void handleAdvancedWebhookWhenAnUnknownWebPackageIsReturnedInvalidRequestIsThrown() throws Exception {
        ServerSentEvent webhookPackage = new ServerSentEvent() {};
        when(transport.handleServerSentEvent(ArgumentMatchers.<String, List<String>>anyMap(), anyString(), anyString(), anyString())).thenReturn(webhookPackage);
        client.handleAdvancedWebhook(new HashMap<String, List<String>>(), "body", "method", "path");
    }

    @Test
    public void handleAdvancedWebhookPassesAuthorizationRequestIdWhenAuthResponseWebhook() throws Exception {
        when(transport.handleServerSentEvent(null, null, null, null)).thenReturn(serverSentEventAuthorizationResponse);
        UUID expected = UUID.randomUUID();
        when(serverSentEventAuthorizationResponse.getAuthorizationRequestId()).thenReturn(expected);
        AdvancedAuthorizationResponseWebhookPackage actual = (AdvancedAuthorizationResponseWebhookPackage) client.handleAdvancedWebhook(null, null, null, null);
        assertEquals(expected.toString(), actual.getAuthorizationResponse().getAuthorizationRequestId());
    }

    @Test
    public void handleAdvancedWebhookPassesAuthorizationResponseWhenAuthResponseWebhook() throws Exception {
        when(transport.handleServerSentEvent(null, null, null, null)).thenReturn(serverSentEventAuthorizationResponse);
        when(serverSentEventAuthorizationResponse.getResponse()).thenReturn(true);
        AdvancedAuthorizationResponseWebhookPackage actual = (AdvancedAuthorizationResponseWebhookPackage) client.handleAdvancedWebhook(null, null, null, null);
        assertTrue(actual.getAuthorizationResponse().isAuthorized());
    }

    @Test
    public void handleAdvancedWebhookPassesServiceUserHashWhenAuthResponseWebhook() throws Exception {
        when(transport.handleServerSentEvent(null, null, null, null)).thenReturn(serverSentEventAuthorizationResponse);
        String expected = "Service User Hash";
        when(serverSentEventAuthorizationResponse.getServiceUserHash()).thenReturn(expected);
        AdvancedAuthorizationResponseWebhookPackage actual = (AdvancedAuthorizationResponseWebhookPackage) client.handleAdvancedWebhook(null, null, null, null);
        assertEquals(expected, actual.getAuthorizationResponse().getServiceUserHash());
    }

    @Test
    public void handleAdvancedWebhookPassesOrganizationUserHashWhenAuthResponseWebhook() throws Exception {
        when(transport.handleServerSentEvent(null, null, null, null)).thenReturn(serverSentEventAuthorizationResponse);
        String expected = "Organization User Hash";
        when(serverSentEventAuthorizationResponse.getOrganizationUserHash()).thenReturn(expected);
        AdvancedAuthorizationResponseWebhookPackage actual = (AdvancedAuthorizationResponseWebhookPackage) client.handleAdvancedWebhook(null, null, null, null);
        assertEquals(expected, actual.getAuthorizationResponse().getOrganizationUserHash());
    }

    @Test
    public void handleAdvancedWebhookPassesUserPushIdWhenAuthResponseWebhook() throws Exception {
        when(transport.handleServerSentEvent(null, null, null, null)).thenReturn(serverSentEventAuthorizationResponse);
        String expected = "User Push ID";
        when(serverSentEventAuthorizationResponse.getUserPushId()).thenReturn(expected);
        AdvancedAuthorizationResponseWebhookPackage actual = (AdvancedAuthorizationResponseWebhookPackage) client.handleAdvancedWebhook(null, null, null, null);
        assertEquals(expected, actual.getAuthorizationResponse().getUserPushId());
    }

    @Test
    public void handleAdvancedWebhookPassesDeviceIdWhenAuthResponseWebhook() throws Exception {
        when(transport.handleServerSentEvent(null, null, null, null)).thenReturn(serverSentEventAuthorizationResponse);
        String expected = "User Push ID";
        when(serverSentEventAuthorizationResponse.getDeviceId()).thenReturn(expected);
        AdvancedAuthorizationResponseWebhookPackage actual = (AdvancedAuthorizationResponseWebhookPackage) client.handleAdvancedWebhook(null, null, null, null);
        assertEquals(expected, actual.getAuthorizationResponse().getDeviceId());
    }

    @Test
    public void handleAdvancedWebhookPassesServicePinsWhenAuthResponseWebhook() throws Exception {
        when(transport.handleServerSentEvent(null, null, null, null)).thenReturn(serverSentEventAuthorizationResponse);
        String[] pins = {"pin1", "pin2"};
        List<String> expected = Arrays.asList(pins);
        when(serverSentEventAuthorizationResponse.getServicePins()).thenReturn(pins);
        AdvancedAuthorizationResponseWebhookPackage actual = (AdvancedAuthorizationResponseWebhookPackage) client.handleAdvancedWebhook(null, null, null, null);
        assertEquals(expected, actual.getAuthorizationResponse().getServicePins());
    }

    @Test
    public void handleAdvancedWebhookPassesApiTimeWhenEndSessionWebhook() throws Exception {
        when(transport.handleServerSentEvent(null, null, null, null)).thenReturn(serverSentEventUserServiceSessionEnd);
        Date expected = new Date();
        when(serverSentEventUserServiceSessionEnd.getApiTime()).thenReturn(expected);
        ServiceUserSessionEndWebhookPackage actual = (ServiceUserSessionEndWebhookPackage) client.handleAdvancedWebhook(null, null, null, null);
        assertEquals(expected, actual.getLogoutRequested());
    }

    @Test
    public void handleAdvancedWebhookPassesUserHashWhenEndSessionWebhook() throws Exception {
        when(transport.handleServerSentEvent(null, null, null, null)).thenReturn(serverSentEventUserServiceSessionEnd);
        String expected = "User hash";
        when(serverSentEventUserServiceSessionEnd.getUserHash()).thenReturn(expected);
        ServiceUserSessionEndWebhookPackage actual = (ServiceUserSessionEndWebhookPackage) client.handleAdvancedWebhook(null, null, null, null);
        assertEquals(expected, actual.getServiceUserHash());
    }
}