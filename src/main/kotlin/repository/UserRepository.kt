package repository

import model.User
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

class UserRepository {

    private val userStore = ConcurrentHashMap<Int, User>()
    private val idCounter = AtomicInteger(0)

    fun findAll(): List<User> = userStore.values.toList()

    fun findById(id: Int): User? = userStore[id]

    fun findByEmail(email: String): User? {
        return userStore.values.firstOrNull { it.email.equals(email, ignoreCase = true) }
    }

    fun save(user: User): User {
        val id = if (user.id == 0) idCounter.incrementAndGet() else user.id
        val newUser = user.copy(id = id)
        userStore[id] = newUser
        return newUser
    }

    fun update(id: Int, user: User): Boolean {
        if (!userStore.containsKey(id)) return false
        userStore[id] = user.copy(id = id)
        return true
    }

    fun deleteById(id: Int): Boolean {
        return userStore.remove(id) != null
    }
}