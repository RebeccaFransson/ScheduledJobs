
import controllers.SparkController
import spark.Spark
import utils.SchedulerWorker


fun main(args: Array<String>) {
    val port = Integer.parseInt("8080")

    // Settings
    Spark.port(port)

    // Start a controller
    SparkController()

    // Start server
    Spark.init()

    println("************************************************")
    println("*****  Spark server started on port $port  ******")
    println("************************************************")

    /**
     * Start a worker
     */
    SchedulerWorker()
}

