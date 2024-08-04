# GitHub Repository Listing Application

This repository contains a Spring Boot application that fetches and displays non-fork repositories for a given GitHub username. The application leverages the GitHub API to retrieve repository information.

## Configuration

To configure the application, you need to set your GitHub personal access token in the application properties. This token is required to authenticate requests to the GitHub API.

1. **Generate a GitHub Personal Access Token**:
    - Just follow the instructions at [GitHub Docs](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens#creating-a-fine-grained-personal-access-token)

2. **Set the Token in Application Properties**:
    - Open the `application.properties` file located in `src/main/resources`.
    - Locate and fill the following property:
      ```properties
      github.token=your_personal_access_token_here
      ```

## Controller Method

The application exposes a REST endpoint to fetch non-fork repositories for a given GitHub username.

### Endpoint

`GET /list/{username}`

### Description

Fetches all non-fork repositories for the specified GitHub user and returns the repository details, including the name, owner, and branches.



