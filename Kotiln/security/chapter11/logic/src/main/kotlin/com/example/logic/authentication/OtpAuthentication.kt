package com.example.logic.authentication

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

class OtpAuthentication : UsernamePasswordAuthenticationToken {
    constructor(principal: Any?, credentials: Any?) : super(principal, credentials)
}