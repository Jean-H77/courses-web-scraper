package org.john;

import org.john.course.CourseSearcher;

import java.util.Scanner;

import static org.john.course.CourseConstants.COURSE_URL;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Client client = new Client(COURSE_URL);

        CourseSearcher courseSearcher = new CourseSearcher();
        courseSearcher.loadCourses(client.getPageSource());

        while(true) {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            String str = courseSearcher.generateCourseTable(input);
            System.out.println(str);
        }
    }
}