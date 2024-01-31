package org.john;

import org.john.course.Course;
import org.john.course.CourseLoader;
import org.john.course.CourseRepository;

import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        String url = "https://cmsweb.csun.edu/psp/CNRPRD/EMPLOYEE/SA/c/NR_SSS_COMMON_MENU.NR_SSS_SOC_BASIC_C.GBL";
        String subject = "COMP";

        CourseLoader loader = new CourseLoader(url, subject);
        Map<String, List<Course>> courses = loader.loadAndGetCoursesMap();

        CourseRepository courseRepository = new CourseRepository();
        courseRepository.putAll(courses);
    }
}