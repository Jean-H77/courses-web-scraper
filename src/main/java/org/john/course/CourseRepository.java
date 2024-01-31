package org.john.course;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class CourseRepository {

    private final Map<String, List<Course>> courses = new ConcurrentHashMap<>();

    public void putAll(Map<String, List<Course>> courses) {
        this.courses.putAll(courses);
    }

    public List<Course> getByTitle(String title) {
        return courses.getOrDefault(title, new ArrayList<>());
    }

    public List<Course> getAllByInstructorName(String name) {
        return courses.values()
                .stream()
                .flatMap(List::stream)
                .filter(Objects::nonNull)
                .filter(course -> course.instructor() != null)
                .filter(course -> course.instructor().equalsIgnoreCase(name))
                .toList();
    }
}
