package com.iovation.launchkey.sdk.example.springmvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class DemoController {
    private static final Logger LOG = LoggerFactory.getLogger(DemoController.class);
    private final AuthManager authManager;

    @Autowired
    DemoController(AuthManager authManager) {
        this.authManager = authManager;
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/authorized", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public HttpEntity<Authorized> authorized(HttpSession session) throws AuthManager.AuthException {
        Authorized authorized = new Authorized();
        try {
            authorized.authorized = authManager.isAuthorized();
        } catch (AuthManager.AuthException e) {
            authorized.authorized = false;
        }
        if (!authorized.authorized) {
            session.invalidate();
        }
        return new ResponseEntity<Authorized>(authorized, HttpStatus.OK);
    }

    @RequestMapping(value = "/webhook", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void webhook (WebRequest request, @RequestBody String body) throws AuthManager.AuthException {
        Map<String, List<String>> headers = new HashMap<String, List<String>>();
        Iterator<String> headerNames = request.getHeaderNames();
        while (headerNames.hasNext()) {
            String headerName = headerNames.next();
            headers.put(headerName, Arrays.asList(request.getHeaderValues(headerName)));
        }
        authManager.handleWebhook(headers, body);
    }

    public static class Authorized {
        public boolean authorized;
    }
}