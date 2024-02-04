package org.john.course;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import org.john.course.CourseScraper;
import org.john.course.CourseRefreshCronJob;
import org.john.course.CourseRepository;
import org.john.discord.DiscordBot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class CourseModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(CourseRepository.class).in(Scopes.SINGLETON);
        bind(WebDriver.class).to(FirefoxDriver.class).asEagerSingleton();
        bind(CourseRefreshCronJob.class);
        bind(CourseScraper.class).in(Scopes.SINGLETON);
    }
}
