package com.example.chapter18.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.core.convert.converter.Converter
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt

class CustomJwtGrantedAuthorityConverter : Converter<Jwt, Collection<GrantedAuthority>> {
    private val AUTHORITIES_CLAIM_NAME = "authorities"

    override fun convert(source: Jwt): Collection<SimpleGrantedAuthority> {
        return source.getClaimAsMap("realm_access").flatMap { item ->
            item.value as? List<String> ?: listOf()
        }.fold(mutableListOf()) {result, current ->
            result.add(SimpleGrantedAuthority(current))
            result
        }
    }
}