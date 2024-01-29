package org.john;

import org.john.course.CourseSearcher;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.john.course.CourseConstants.COURSE_URL;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Client client = new Client(new FirefoxDriver(), COURSE_URL);
        client.load();

        CourseSearcher courseSearcher = new CourseSearcher(client.getDriver().getPageSource());
        courseSearcher.loadAllCourses();
    }
}