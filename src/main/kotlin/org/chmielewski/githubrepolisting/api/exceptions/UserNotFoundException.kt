package org.chmielewski.githubrepolisting.api.exceptions

internal class UserNotFoundException(name: String) : Exception("User with name $name not found")
