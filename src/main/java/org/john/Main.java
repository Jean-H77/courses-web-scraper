package org.john;

import org.john.course.CourseSearcher;
import org.john.course.CourseType;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Scanner;

import static org.john.course.CourseConstants.COURSE_URL;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Client client = new Client(new FirefoxDriver(), COURSE_URL);
        client.load();

        CourseSearcher courseSearcher = new CourseSearcher(client.getDriver().getPageSource());

        while(true) {
            var scanner = new Scanner(System.in);
            System.out.println("Enter course > ");
            String input = scanner.nextLine();
            CourseType courseType = CourseType.getCourse(input);
            if(courseType == null) {
                System.out.println("Null");
                continue;
            }
            System.out.println(courseSearcher.getCourseTable(courseType));
        }
    }
}