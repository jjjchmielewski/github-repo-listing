package org.chmielewski.githubrepolisting.api

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "github")
data class GithubApiProperties(
    val token: String
)
