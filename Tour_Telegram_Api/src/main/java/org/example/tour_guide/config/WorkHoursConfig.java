package org.example.tour_guide.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.time.LocalTime;

@Configuration
public class WorkHoursConfig {
    private final Environment environment;

    @Autowired
    public WorkHoursConfig(Environment environment) {
        this.environment = environment;
        initializeWorkHours();
    }

    private LocalTime beginTime;
    private LocalTime endTime;
    private LocalTime deadline;

    private void initializeWorkHours() {
        String beginTimeString = environment.getProperty("worktime.begin");
        String endTimeString = environment.getProperty("worktime.end");
        String deadlineString = environment.getProperty("worktime.deadline");

        if (beginTimeString != null && endTimeString != null && deadlineString != null) {
            this.beginTime = LocalTime.parse(beginTimeString);
            this.endTime = LocalTime.parse(endTimeString);
            this.deadline = LocalTime.parse(deadlineString);
        } else {
            throw new IllegalArgumentException("Missing or invalid work time properties in application.properties");
        }
    }

    public LocalTime getBeginTime() {
        return beginTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public LocalTime getDeadline() {
        return deadline;
    }

    public boolean isWorkingHours(LocalTime currentTime) {
        return currentTime.isAfter(beginTime) && currentTime.isBefore(endTime);
    }

    public boolean isBeforeDeadline(LocalTime currentTime) {
        return currentTime.isBefore(deadline);
    }
}
