package com.launchkey.sdk.transport.apachehttp; /**
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

import com.launchkey.sdk.crypto.JCECrypto;
import com.launchkey.sdk.error.CommunicationErrorException;
import com.launchkey.sdk.error.InvalidResponseException;
import com.launchkey.sdk.transport.domain.PublicV3PingGetResponse;
import com.launchkey.sdk.transport.domain.PublicV3PublicKeyGetResponse;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.message.BasicHeader;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ApacheHttpTransportPublicPublicKeyGetTest extends ApacheHttpTransportTestBase {
    private String publicKeyPEM;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        publicKeyPEM = "-----BEGIN PUBLIC KEY-----\n" +
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA8zQos4iDSjmUVrFUAg5G\n" +
                "uhU6GehNKb8MCXFadRWiyLGjtbGZAk8fusQU0Uj9E3o0mne0SYESACkhyK+3M1Er\n" +
                "bHlwYJHN0PZHtpaPWqsRmNzui8PvPmhm9QduF4KBFsWu1sBw0ibBYsLrua67F/wK\n" +
                "PaagZRnUgrbRUhQuYt+53kQNH9nLkwG2aMVPxhxcLJYPzQCat6VjhHOX0bgiNt1i\n" +
                "HRHU2phxBcquOW2HpGSWcpzlYgFEhPPQFAxoDUBYZI3lfRj49gBhGQi32qQ1YiWp\n" +
                "aFxOB8GA0Ny5SfI67u6w9Nz9Z9cBhcZBfJKdq5uRWjZWslHjBN3emTAKBpAUPNET\n" +
                "nwIDAQAB\n" +
                "-----END PUBLIC KEY-----";
        ((BasicHttpEntity) httpResponse.getEntity()).setContent(new ByteArrayInputStream(publicKeyPEM.getBytes()));
        when(httpResponse.getFirstHeader("X-IOV-KEY-ID")).thenReturn(new BasicHeader("X-IOV-KEY-ID", "Key ID"));
    }

    @Test
    public void publicPublicKeyGetCallsHttpClientWithProperHttpRequestMethod() throws Exception {
        transport.publicV3PublicKeyGet("fingerprint");
        ArgumentCaptor<HttpUriRequest> actual = ArgumentCaptor.forClass(HttpUriRequest.class);
        verify(httpClient).execute(actual.capture());
        assertEquals("GET", actual.getValue().getMethod());
    }

    @Test
    public void publicPublicKeyGetCallsHttpClientWithProperHttpRequestUriWithoutFingerprint() throws Exception {
        URI expected = URI.create(baseUrl.concat("/public/v3/public-key"));
        transport.publicV3PublicKeyGet(null);
        ArgumentCaptor<HttpUriRequest> actual = ArgumentCaptor.forClass(HttpUriRequest.class);
        verify(httpClient).execute(actual.capture());
        assertEquals(expected, actual.getValue().getURI());
    }

    @Test
    public void publicPublicKeyGetCallsHttpClientWithProperHttpRequestUriWithFingerprint() throws Exception {
        URI expected = URI.create(baseUrl.concat("/public/v3/public-key/expected-fingerprint"));
        transport.publicV3PublicKeyGet("expected-fingerprint");
        ArgumentCaptor<HttpUriRequest> actual = ArgumentCaptor.forClass(HttpUriRequest.class);
        verify(httpClient).execute(actual.capture());
        assertEquals(expected, actual.getValue().getURI());
    }

    @Test
    public void publicPublicKeyGetReturnsExpectedPublicKeyValue() throws Exception {
        String actual = transport.publicV3PublicKeyGet("fingerprint").getPublicKey();
        assertEquals(publicKeyPEM, actual);
    }

    @Test
    public void publicPublicKeyGetReturnsExpectedFingerprint() throws Exception {
        String expected = "Expected Fingerprint";
        when(httpResponse.getFirstHeader("X-IOV-KEY-ID")).thenReturn(new BasicHeader("X-IOV-KEY-ID", expected));
        String actual = transport.publicV3PublicKeyGet("fingerprint").getPublicKeyFingerprint();
        assertEquals(expected, actual);
    }

    @Test(expected = CommunicationErrorException.class)
    public void publicPublicKeyGetThrowsInvalidResponseExceptionWhenEntityGetContentThrowsIOError() throws Exception {
        HttpEntity entity = mock(HttpEntity.class);
        when(entity.getContent()).thenThrow(new IOException());
        when(httpResponse.getEntity()).thenReturn(entity);
        transport.publicV3PublicKeyGet("fingerprint");
    }

    @Test(expected = CommunicationErrorException.class)
    public void publicPublicKeyGetThrowsInvalidResponseExceptionWhenHttpClientThrowsIOError() throws Exception {
        when(httpClient.execute(any(HttpUriRequest.class))).thenThrow(new IOException());
        transport.publicV3PublicKeyGet("fingerprint");
    }

    @Test(expected = InvalidResponseException.class)
    public void publicPublicKeyThrowsInvalidResponseExceptionWhenNoFingerprintHeaderExists() throws Exception {
        when(httpResponse.getFirstHeader("X-IOV-KEY-ID")).thenReturn(null);
        transport.publicV3PublicKeyGet("fingerprint");
    }
}