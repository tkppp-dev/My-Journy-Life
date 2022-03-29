package com.tkppp.myjournylife.config.vue

import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class ViewErrorController : ErrorController {

    @GetMapping("/error")
    fun redirectRoot(): String {
        return "index.html"
    }

    @Override
    fun getErrorPath(): String {
        return "/error"
    }
}