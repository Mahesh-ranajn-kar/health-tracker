package service

import model.User
import repository.UserRepository

class UserService(private val userRepository: UserRepository) {

    init {
        seedMockData()
    }

    private fun seedMockData() {
        createUser(User(name = "Alice Smith", email = "alice@example.com"))
        createUser(User(name = "Bob Jones", email = "bob@example.com"))
        createUser(User(name = "Charlie Brown", email = "charlie@example.com"))
    }

    fun getAllUsers(): List<User> {
        return userRepository.findAll()
    }

    fun getUserById(id: Int): User {
        return userRepository.findById(id)
            ?: throw NoSuchElementException("User not found with ID: $id")
    }

    fun getUserByEmail(email: String): User {
        return userRepository.findByEmail(email)
            ?: throw NoSuchElementException("User not found with email: $email")
    }

    fun createUser(user: User): User {
        require(user.email.isNotBlank()) { "User email cannot be empty" }

        // Example business validation: Check duplicate email
        val existingUser = userRepository.findByEmail(user.email)
        if (existingUser != null) {
            throw IllegalArgumentException("User with email ${user.email} already exists")
        }

        return userRepository.save(user)
    }

    fun updateUser(id: Int, user: User) {
        val updated = userRepository.update(id, user)
        if (!updated) {
            throw NoSuchElementException("Cannot update. User not found with ID: $id")
        }
    }

    fun deleteUser(id: Int) {
        val deleted = userRepository.deleteById(id)
        if (!deleted) {
            throw NoSuchElementException("Cannot delete. User not found with ID: $id")
        }
    }
}