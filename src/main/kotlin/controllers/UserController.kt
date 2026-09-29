package controllers

import io.javalin.http.Context
import io.javalin.http.HttpStatus
import io.javalin.http.NotFoundResponse
import io.javalin.http.bodyAsClass
import io.javalin.http.pathParamAsClass
import model.User
import service.UserService

class UserController(private val userService: UserService) {

    fun getAll(ctx: Context) {
        ctx.json(userService.getAllUsers())
        // to get all the user
    }

    fun getById(ctx: Context) {
        val id = ctx.pathParamAsClass<Int>("user-id").get()
        try {
            val user = userService.getUserById(id)
            ctx.json(user)
        } catch (e: NoSuchElementException) {
            throw NotFoundResponse(e.message ?: "User not found")
        }
    }

    fun getByEmail(ctx: Context) {
        val email = ctx.pathParam("email")
        try {
            val user = userService.getUserByEmail(email)
            ctx.json(user)
        } catch (e: NoSuchElementException) {
            throw NotFoundResponse(e.message ?: "User not found")
        }
    }

    fun create(ctx: Context) {
        val user = ctx.bodyAsClass<User>()
        val createdUser = userService.createUser(user)
        ctx.status(HttpStatus.CREATED).json(createdUser)
    }

    fun update(ctx: Context) {
        val id = ctx.pathParamAsClass<Int>("user-id").get()
        val user = ctx.bodyAsClass<User>()
        try {
            userService.updateUser(id, user)
            ctx.status(HttpStatus.NO_CONTENT)
        } catch (e: NoSuchElementException) {
            throw NotFoundResponse(e.message ?: "User not found")
        }
    }

    fun delete(ctx: Context) {
        val id = ctx.pathParamAsClass<Int>("user-id").get()
        try {
            userService.deleteUser(id)
            ctx.status(HttpStatus.NO_CONTENT)
        } catch (e: NoSuchElementException) {
            throw NotFoundResponse(e.message ?: "User not found")
        }
    }
}