package org.chmielewski.githubrepolisting.api.data

data class Branch(
    val name: String,
    val commit: Commit,
) {
    data class Commit(
        val sha: String
    )
}
