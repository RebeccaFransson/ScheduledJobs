package utils

import org.quartz.JobExecutionException
import org.quartz.JobExecutionContext
import org.quartz.Job


class HelloJob : Job {

    @Throws(JobExecutionException::class)
    override fun execute(context: JobExecutionContext) {
        val key = context.jobDetail.key

        val dataMap = context.jobDetail.jobDataMap

        val jobSays = dataMap.getString("jobSays")

        println("************** Scheduled job *******************")
        println("Hello Job!  Key '$key' is executing.")
        println("Job says: $jobSays")
        println("************************************************")

    }
}