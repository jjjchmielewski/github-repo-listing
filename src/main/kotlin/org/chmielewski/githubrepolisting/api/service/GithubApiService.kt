package org.chmielewski.githubrepolisting.api.service

import org.chmielewski.githubrepolisting.api.GithubApiProperties
import org.chmielewski.githubrepolisting.api.data.Branch
import org.chmielewski.githubrepolisting.api.data.Repository
import org.chmielewski.githubrepolisting.api.exceptions.UserNotFoundException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
internal class GithubApiService(private val properties: GithubApiProperties) {
    private val webClient = WebClient.builder()
        .baseUrl("https://api.github.com")
        .defaultHeaders { headers ->
            headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer ${properties.token}")
        }
        .build()

    fun getAllNonForkRepositoriesForGivenUsername(username: String): Flux<Repository> {
        return webClient
            .get()
            .uri(USER_REPOS_URL.format(username))
            .retrieve()
            .onStatus({ status -> status == HttpStatus.NOT_FOUND }) {
                Mono.error(UserNotFoundException(username))
            }
            .bodyToFlux(Repository::class.java)
            .filter { !it.fork }
            .flatMap { repo ->
                getBranchesForRepository(repo.owner.login, repo.name)
                    .collectList()
                    .map { branches ->
                        repo.branches = branches
                        repo
                    }
            }
    }

    fun getBranchesForRepository(owner: String, repoName: String): Flux<Branch> {
        return webClient
            .get()
            .uri(BRANCHES_URL.format(owner, repoName))
            .retrieve()
            .bodyToFlux(Branch::class.java)
    }

    companion object {
        private const val USER_REPOS_URL = "/users/%s/repos"
        private const val BRANCHES_URL = "/repos/%s/%s/branches"
    }
}
