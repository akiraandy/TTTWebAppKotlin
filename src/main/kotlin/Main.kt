import http.method.HttpMethod
import http.router.Router
import http.server.Server
import http.utils.MainArgumentParser

fun main(args : Array<String>) {
    val router = configureRouter()
    val argumentParser = parseArguments(args)
    val server = Server(Integer.parseInt(argumentParser.get("PortNumber")), argumentParser.get("Directory"), router)
    server.run()
}

fun configureRouter(): Router {
    val router = Router()
    router.addRoute("/move", listOf(HttpMethod.POST, HttpMethod.OPTIONS), GameController())
    return router
}

private fun parseArguments(args: Array<String>): Map<String, String> {
    val argumentParser = MainArgumentParser()
            .addFlag("-p", "PortNumber", "5000")
            .addFlag("-d", "Directory", "./")
    return argumentParser.parse(args)
}