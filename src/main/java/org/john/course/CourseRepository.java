package org.john.course;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseRepository {

    private final Map<String, List<Course>> courses = new HashMap<>();

    public void putAll(Map<String, List<Course>> courses) {
        this.courses.putAll(courses);
    }

    private List<Course> getByTitle(String title) {
        return courses.getOrDefault(title, new ArrayList<>());
    }

    private List<Course> getAllByInstructorName(String name) {
        return courses.values()
                .stream()
                .flatMap(List::stream)
                .filter(course -> course.instructor().equalsIgnoreCase(name))
                .toList();
    }
}
