package spaApp.utils.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import spaApp.utils.service.SecurityService;

@RestController
public class AuthController {

    @Autowired
    SecurityService securityService;

    @RequestMapping(method = RequestMethod.DELETE, path = "/api/logout")
    public void logout() {
        securityService.removeToken();
    }
}
