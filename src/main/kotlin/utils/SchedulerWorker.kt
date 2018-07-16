package utils

import org.quartz.JobBuilder.newJob
import org.quartz.Scheduler
import org.quartz.SchedulerException
import org.quartz.impl.StdSchedulerFactory
import org.quartz.SimpleScheduleBuilder.simpleSchedule
import org.quartz.TriggerBuilder.newTrigger


class SchedulerWorker{
    private var scheduler: Scheduler? = null

    init {
        /** Creates a new instance of Quartz Scheduler and starts it. */
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler()

            scheduler!!.start()

            scheduleJob()

        } catch (se: SchedulerException) {
            println("Unable to start scheduler service")
        }

    }

    private fun scheduleJob() {
        // define the job and tie it to our HelloJob class
        val job = newJob(HelloJob::class.java)
                .withIdentity("myJob", "group1")
                .usingJobData("jobSays", "Hello Job!")
                .usingJobData("myFloatValue", 3.141f)
                .build()


        // Trigger the job to run now, and then every 40 seconds
        val trigger = newTrigger()
                .withIdentity("myTrigger", "group1")
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(30)
                        .repeatForever())
                .build()


        // Tell quartz to schedule the job using our trigger
        scheduler!!.scheduleJob(job, trigger)
    }



}