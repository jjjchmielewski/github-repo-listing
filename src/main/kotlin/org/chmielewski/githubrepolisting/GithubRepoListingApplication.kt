package org.chmielewski.githubrepolisting

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GithubRepoListingApplication

fun main(args: Array<String>) {
	runApplication<GithubRepoListingApplication>(*args)
}
