package org.jeecgframework.core.timer;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Job
{
    @Scheduled(cron = "0 */4 * * * *")
    public void timerJob() throws Exception
    {

    }
}