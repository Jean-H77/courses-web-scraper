package org.john.course;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;

import static org.john.course.CourseConstants.*;

public class CourseSearcher {

    private Document document;

    private final Map<CourseType, List<Course>> cache = new HashMap<>();

    public CourseSearcher(String html) {
        this.document = Jsoup.parse(html);
    }

    public String getCourseTable(CourseType courseType) {
        if(cache.containsKey(courseType)) {
            return getCoursesAsString(cache.get(courseType));
        }

        Element outerTable = document.getElementById(TABLE_ROW_ID_PREFIX + courseType.getPositionKey());

        if(outerTable == null) {
            return "Table does not exist";
        }

        Element innerTable = outerTable.getElementsByClass(TABLE_CLASS_NAME).getFirst();
        Elements rows = innerTable.select("tr");

        Course.Builder courseBuilder;
        List<Course> courseList = new ArrayList<>();

        for(int i = 1; i < rows.size(); i++) {
            String rowText = rows.get(i).text().replaceAll("OF","").replaceAll("OC","");
            String[] parts = rowText.split("\\s+");
            courseBuilder = new Course.Builder();
            for(int j = 1; j < parts.length; j++) {
                String part = parts[j];
                switch (j) {
                    case 1 -> courseBuilder.section(part);
                    case 2 -> courseBuilder.classNumber(part);
                    case 3 -> courseBuilder.availableSeats(part);
                    case 4 -> courseBuilder.status(part);
                    case 5 -> courseBuilder.component(part);
                    case 6 -> courseBuilder.location(part);
                    case 7 -> courseBuilder.days(part);
                    case 8 -> courseBuilder.time(part);
                    case 9 -> courseBuilder.instructor(part);
                    case 10 -> courseBuilder.consent(part);
                }
            }

            courseList.add(courseBuilder.build());
        }
        cache.put(courseType, courseList);

        return getCoursesAsString(courseList);
    }

    private static String getCoursesAsString(List<Course> courses) {
        StringBuilder stringBuilder = new StringBuilder();
        for(Course course : courses) {
            stringBuilder
                    .append(course.section()).append(" ")
                    .append(course.classNumber()).append(" ")
                    .append(course.availableSeats()).append(" ")
                    .append(course.status()).append(" ")
                    .append(course.component()).append(" ")
                    .append(course.location()).append(" ")
                    .append(course.days()).append(" ")
                    .append(course.time()).append(" ")
                    .append(course.instructor()).append(" ")
                    .append(course.consent()).append(" ")
                    .append("\n");
        }
        return stringBuilder.toString().replaceAll("null", "");
    }

    public void refreshCourses(String html) {
        document = Jsoup.parse(html);
    }
}
