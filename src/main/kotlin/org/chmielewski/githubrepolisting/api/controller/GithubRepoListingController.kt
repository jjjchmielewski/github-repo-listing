package org.chmielewski.githubrepolisting.api.controller

import org.chmielewski.githubrepolisting.api.controller.dto.RepositoryDTO
import org.chmielewski.githubrepolisting.api.exceptions.UserNotFoundException
import org.chmielewski.githubrepolisting.api.service.GithubApiService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/list")
internal class GithubRepoListingController(
    private val apiService: GithubApiService
) {
    @GetMapping("/{username}")
    fun getUserRepositories(@PathVariable username: String): Mono<ResponseEntity<*>> {
        return apiService
            .getAllNonForkRepositoriesForGivenUsername(username)
            .collectList()
            .map { repositories ->
                ResponseEntity.ok(repositories.map { repository ->
                    RepositoryDTO(
                        repository.name,
                        repository.owner.login,
                        repository.branches?.map { RepositoryDTO.BranchDTO(it.name, it.commit.sha) }
                    )
                })
            }
            .cast(ResponseEntity::class.java)
            .onErrorResume(UserNotFoundException::class.java) { exception ->
                Mono.just(
                    ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.message ?: "User not found"))
                )
            }
    }

    private data class ErrorResponse(val status: Int, val message: String)
}
