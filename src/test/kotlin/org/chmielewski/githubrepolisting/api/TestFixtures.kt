package org.chmielewski.githubrepolisting.api

import org.chmielewski.githubrepolisting.api.data.Branch
import org.chmielewski.githubrepolisting.api.data.Repository

object TestFixtures {
    val username = "example_user"
    val repositoriesListJson = """
        [
          {
            "name": "repo1",
            "owner": "example_user",
            "branches": [
              {
                "name": "master",
                "sha": "1a769e9bc5ddd306659dd40d8282998377e00013"
              }
            ]
          },
          {
            "name": "repo2",
            "owner": "example_user",
            "branches": [
              {
                "name": "master",
                "sha": "1a769e9bc5ddd306659dd40d8282998377e00013"
              }
            ]
          }
        ]
    """.trimIndent()

    val branchesList = mutableListOf(
        Branch("master", Branch.Commit("1a769e9bc5ddd306659dd40d8282998377e00013"))
    )

    val repositoriesList = mutableListOf(
        Repository("repo1", Repository.Owner("example_user"), false, branchesList),
        Repository("repo2", Repository.Owner("example_user"), false, branchesList)
    )

    val errorJson = """
        {
          "status": 404,
          "message": "User with name example_user not found"
        }
    """.trimIndent()
}
