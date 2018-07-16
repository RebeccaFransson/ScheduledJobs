package controllers

import spark.Request
import spark.Response
import spark.Spark

class SparkController {
    init {
        Spark.get("/test", ::test)
    }


    private fun test(request: Request, response: Response): String{
        return "Fungerar!"
    }
}