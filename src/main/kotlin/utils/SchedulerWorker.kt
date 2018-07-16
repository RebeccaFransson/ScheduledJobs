package utils

import org.quartz.CronScheduleBuilder.cronSchedule
import org.quartz.CronScheduleBuilder.dailyAtHourAndMinute
import org.quartz.JobBuilder.newJob
import org.quartz.Scheduler
import org.quartz.impl.StdSchedulerFactory
import org.quartz.SimpleScheduleBuilder.simpleSchedule
import org.quartz.Trigger
import org.quartz.TriggerBuilder.newTrigger
import org.quartz.impl.calendar.HolidayCalendar
import java.sql.Date.*
import java.time.LocalDate


class SchedulerWorker {
    private var scheduler: Scheduler? = null
    // define the job and tie it to our HelloJob class
    private val job = newJob(HelloJob::class.java)
            .withIdentity("myJob", "group1")
            .usingJobData("jobSays", "Hello Job!")
            .build()

    init {
        scheduler = StdSchedulerFactory.getDefaultScheduler()

        scheduler!!.start()

        createJobInterval()

        createJobEveryThirtyMinutesBetween17and23()

        createJobEveryThirtySecondsWeekdays()

        createJobEveryDayAtTime()

        createJobEveryDayAtTimeButNotOnSpecificHolidays()

    }

    /**
     * Create and schedule a job that is executed in en interval for 30 seconds forever
     */
    private fun createJobInterval() {
        val trigger = newTrigger()
                .withIdentity("myTrigger", "group1")
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(30)
                        .repeatForever())
                .build()

        scheduleJob(trigger)
    }

    /**
     * Create and schedule a job that is executed every 30 minutes between 17:00 and 23:00
     */
    private fun createJobEveryThirtyMinutesBetween17and23() {
        val trigger = newTrigger()
                .withIdentity("myTrigger", "group1")
                .startNow()
                .withSchedule(cronSchedule("0 0/30 17-23 * * ?"))
                .build()

        scheduleJob(trigger)
    }

    /**
     * Create and schedule a job that is executed every 30 seconds on Weekdays (Monday through Friday)
     */
    private fun createJobEveryThirtySecondsWeekdays() {
        val trigger = newTrigger()
                .withIdentity("myTrigger", "group1")
                .startNow()
                .withSchedule(cronSchedule("0,30 * * ? * MON-FRI"))
                .build()

        scheduleJob(trigger)
    }

    /**
     * Create and schedule a job that is executed every day at a specific time
     */
    private fun createJobEveryDayAtTime() {
        val trigger = newTrigger()
                .withIdentity("myTrigger", "group1")
                .startNow()
                .withSchedule(dailyAtHourAndMinute(9, 30)) // execute job daily at 9:30
                .build()

        scheduleJob(trigger)
    }

    /**
     * Create and schedule a job that is executed every day at a specific time but not tomorrow or the day after
     */
    private fun createJobEveryDayAtTimeButNotOnSpecificHolidays() {
        val cal = HolidayCalendar()
        // Add tomorrow and the day after tomorrow to the holiday calender
        cal.addExcludedDate(valueOf(LocalDate.now().plusDays(1)))
        cal.addExcludedDate(valueOf(LocalDate.now().plusDays(2)))

        scheduler!!.addCalendar("myHolidays", cal, false, false)

        val trigger = newTrigger()
                .withIdentity("myTrigger", "group1")
                .startNow()
                .withSchedule(dailyAtHourAndMinute(9, 30)) // execute job daily at 9:30
                .modifiedByCalendar("myHolidays") // but not on holidays
                .build()

        scheduleJob(trigger)
    }

    /**
     * Schedule job for the quartz Scheduler
     * @param trigger - when should the job be executed
     */
    private fun scheduleJob(trigger: Trigger) {
        // Tell quartz to schedule the job using our trigger
        scheduler!!.scheduleJob(job, trigger)
    }


}