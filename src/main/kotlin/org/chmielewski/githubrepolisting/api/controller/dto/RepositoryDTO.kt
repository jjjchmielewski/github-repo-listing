package org.chmielewski.githubrepolisting.api.controller.dto

internal data class RepositoryDTO(
    val name: String,
    val owner: String,
    val branches: List<BranchDTO>?
) {
    data class BranchDTO(
        val name: String,
        val sha: String
    )
}
