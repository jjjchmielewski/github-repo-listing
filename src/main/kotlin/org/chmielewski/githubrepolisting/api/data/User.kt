package org.chmielewski.githubrepolisting.api.data

data class User(
    val login: String,
    val repositories: List<Repository>
)
