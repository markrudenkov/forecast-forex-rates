package spaApp.utils.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


public class AuthController {

    @RequestMapping(method = RequestMethod.DELETE, path = "/api/logout")
    public void logout() {

    }
}
