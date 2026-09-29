
import controllers.UserController
import io.javalin.Javalin
import io.javalin.http.HttpStatus
import config.userRoutes
import repository.UserRepository
import service.UserService

fun main() {

    // 1. Instantiate Repository (DAO)
    val userRepository = UserRepository()

    // 2. Inject Repository into Service
    val userService = UserService(userRepository)

    // 3. Inject Service into Controller
    val userController = UserController(userService)

    val app = Javalin.create { config ->
        config.bundledPlugins.enableCors { cors ->
            cors.addRule { rule ->
                rule.anyHost()
                // Ensure front-end client can send Authorization header
                rule.allowCredentials = true
            }
        }

        config.router.apiBuilder {
            userRoutes(userController).addEndpoints()
        }
    }.apply {
        exception(Exception::class.java) { e, _ -> e.printStackTrace() }
        error(HttpStatus.UNAUTHORIZED) { ctx -> ctx.json(mapOf("error" to "Unauthorized")) }
    }.start(7070)
}