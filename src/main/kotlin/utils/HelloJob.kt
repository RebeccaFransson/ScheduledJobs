package utils

import org.quartz.JobExecutionException
import org.quartz.JobExecutionContext
import org.quartz.Job

/**
 * Purpose of this class is to Print out some text about a job
 */
class HelloJob : Job {

    /**
     * Override execute function.
     *
     * This is the job that this class will execute when a job with this class is triggered
     */
    @Throws(JobExecutionException::class)
    override fun execute(context: JobExecutionContext) {
        // Name of the job
        val key = context.jobDetail.key

        // Fetch data from job
        val dataMap = context.jobDetail.jobDataMap
        val jobSays = dataMap.getString("jobSays")

        println("************** Scheduled job *******************")
        println("Hello Job!  Key '$key' is executing.")
        println("Job says: $jobSays")
        println("************************************************")
    }
}