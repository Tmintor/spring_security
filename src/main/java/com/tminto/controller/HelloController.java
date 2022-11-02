package com.tminto.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 吴员外
 * @date 2022/10/24 18:52
 */
@RestController
public class HelloController {

    //@PreAuthorize("hasAuthority('/admin')")
    @GetMapping("hello")
    public String hello() {
        return "hello";
    }
}
