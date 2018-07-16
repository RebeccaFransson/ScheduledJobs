
import controllers.SparkController
import spark.Response
import spark.Spark
import utils.SchedulerWorker


fun main(args: Array<String>) {

    val port = Integer.parseInt("8080")

    // Settings
    Spark.port(port)


    // Template Server
    SparkController()


    // GZIP Everything
    Spark.after("/*") { _, response: Response -> response.header("Content-Encoding", "gzip") }

    // Init/Start server
    Spark.init()

    println("************************************************")
    println("*****  Spark server started on port $port  ******")
    println("************************************************")


    SchedulerWorker()
}

