package org.john;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import org.john.course.CourseLoader;
import org.john.course.CourseRefreshScheduler;
import org.john.course.CourseRepository;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class MainModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(CourseRepository.class).in(Scopes.SINGLETON);
        bind(WebDriver.class).to(FirefoxDriver.class);
        bind(CourseRefreshScheduler.class);
        bind(CourseLoader.class).in(Scopes.SINGLETON);
    }
}
