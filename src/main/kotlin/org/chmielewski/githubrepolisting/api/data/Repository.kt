package org.chmielewski.githubrepolisting.api.data

data class Repository(
    val name: String,
    val owner: Owner,
    val fork: Boolean,
    var branches: List<Branch>?,
) {
    data class Owner(
        val login: String,
    )
}
