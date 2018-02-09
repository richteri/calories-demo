package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * A controller that lets our SPA handle routes that are unknown for the back-end
 */
@Controller
public class RouteController {

  /**
   * Forward all requests to the context root
   *
   * @return the forward instruction
   */
  @RequestMapping(value = "/{[path:[^\\.]*}")
  public String redirect() {
    return "forward:/";
  }
}
