package com.example.chapter18.config

import org.springframework.core.convert.converter.Converter
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt

class CustomJwtGrantedAuthorityConverter : Converter<Jwt, Collection<GrantedAuthority>> {
    private val AUTHORITIES_CLAIM_NAME = "authorities"

    override fun convert(source: Jwt): Collection<SimpleGrantedAuthority> {
        return source.getClaimAsStringList(AUTHORITIES_CLAIM_NAME)
            .fold(mutableListOf()) { result, current ->
                result.add(SimpleGrantedAuthority(current))
                result
            }
    }
}