package org.chmielewski.githubrepolisting.api.controller

import com.fasterxml.jackson.databind.ObjectMapper
import io.mockk.every
import io.mockk.mockk
import org.chmielewski.githubrepolisting.api.TestFixtures
import org.chmielewski.githubrepolisting.api.exceptions.UserNotFoundException
import org.chmielewski.githubrepolisting.api.service.GithubApiService
import org.junit.jupiter.api.Test
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import kotlin.test.assertEquals

internal class GithubRepoListingControllerTest {
    private val apiService = mockk<GithubApiService>()
    private val webClient = WebTestClient.bindToController(GithubRepoListingController(apiService)).build()
    private val objectMapper = ObjectMapper()

    @Test
    fun `should list all repositories for user`() {
        every { apiService.getAllNonForkRepositoriesForGivenUsername(TestFixtures.username) } returns Flux.fromIterable(TestFixtures.repositoriesList)
        val expectedJson = objectMapper.readTree(TestFixtures.repositoriesListJson)

        val response = webClient
            .get()
            .uri("/list/${TestFixtures.username}")
            .exchange()
            .expectStatus().isOk
            .expectBody(String::class.java)
            .value { responseBody ->
                val actualJson = objectMapper.readTree(responseBody)
                assertEquals(expectedJson, actualJson)
            }
    }

    @Test
    fun `should return 404 for non existing user`() {
        every { apiService.getAllNonForkRepositoriesForGivenUsername(any()) } returns Flux.error(UserNotFoundException(TestFixtures.username))
        val expectedError = objectMapper.readTree(TestFixtures.errorJson)

        val response = webClient
            .get()
            .uri("/list/${TestFixtures.username}")
            .exchange()
            .expectStatus().isNotFound
            .expectBody(String::class.java)
            .value { responseBody ->
                val actualJson = objectMapper.readTree(responseBody)
                assertEquals(expectedError, actualJson)
            }
    }
}
