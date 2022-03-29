package com.tkppp.myjournylife.config.vue

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class ViewController {

    @GetMapping("/")
    fun returnHomeView(): String{
        return "index.html"
    }
}