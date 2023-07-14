package com.jpa.kotlinjpa.support

import org.springframework.beans.propertyeditors.StringTrimmerEditor
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.InitBinder
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class RequestStringBinder {

    @InitBinder
    fun initStringBinder(binder: WebDataBinder) {
        binder.registerCustomEditor(String::class.java, StringTrimmerEditor(true))
    }
}
