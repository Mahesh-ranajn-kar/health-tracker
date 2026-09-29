package model


data class UserDto(
    val name: String = "",
    val email: String = ""
)

data class User(
    val id: Int = 0,
    val name: String = "",
    val email: String = ""
)