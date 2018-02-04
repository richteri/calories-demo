package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * A controller that lets our SPA handle routes that are unknown for the front-end
 */
@Controller
public class RouteController {

  @RequestMapping(value = "/{[path:[^\\.]*}")
  public String redirect() {
    return "forward:/";
  }
}
