package org.chmielewski.githubrepolisting.config

import org.chmielewski.githubrepolisting.api.GithubApiProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(GithubApiProperties::class)
internal class GithubRepoListingConfiguration {

    @Autowired
    private lateinit var githubApiProperties: GithubApiProperties
}
