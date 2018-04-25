import http.method.HttpMethod
import http.router.Router
import http.server.Server

fun main(args : Array<String>) {
    val router = configureRouter()
    val server = Server(5000, "./", router)
    server.run()
}

fun configureRouter(): Router {
    val router = Router()
    router.addRoute("/move", HttpMethod.POST, GameController())
    return router
}