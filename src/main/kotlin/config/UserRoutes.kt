package config

import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.apibuilder.EndpointGroup
import controllers.UserController

fun userRoutes(userController: UserController) = EndpointGroup {
    path("/api/users") {
        get(userController::getAll)
        post(userController::create)
        path("/{user-id}") {
            get(userController::getById)
            patch(userController::update)
            delete(userController::delete)
        }
    }
}