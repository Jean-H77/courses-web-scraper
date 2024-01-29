package org.john.course;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;

import static org.john.course.CourseConstants.*;

public class CourseSearcher {

    private Document document;

    private final Map<String, List<Course>> courses = new HashMap<>();


    public void loadCourse(String course, String position) {
        Element outerTable = document.getElementById(TABLE_ROW_ID_PREFIX + position);
        if(outerTable == null) {
            return;
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

            courseBuilder.index(position);
            courseList.add(courseBuilder.build());
        }
        System.out.println("Adding course: " + course);
        courses.put(course, courseList);
    }

    public String generateCourseTable(String course) {
        StringBuilder stringBuilder = new StringBuilder();
        List<Course> courseList = courses.get(course);
        for(Course c : courseList) {
            stringBuilder
                    .append(c.section()).append(" ")
                    .append(c.classNumber()).append(" ")
                    .append(c.availableSeats()).append(" ")
                    .append(c.status()).append(" ")
                    .append(c.component()).append(" ")
                    .append(c.location()).append(" ")
                    .append(c.days()).append(" ")
                    .append(c.time()).append(" ")
                    .append(c.instructor()).append(" ")
                    .append(c.consent()).append(" ")
                    .append("\n");
        }
        return stringBuilder.toString().replaceAll("null", "");
    }

    public void loadCourses(String html) {
        courses.clear();
        document = Jsoup.parse(html);
        Elements elements = document.select("span:contains(Units)");
        for(Element e : elements) {
            String position = e.id().substring(e.id().lastIndexOf("$") + 1);
            String courseTitle = e.text().substring(0, e.text().indexOf("-"));
            loadCourse(courseTitle, position);
        }
    }

    public Document getDocument() {
        return document;
    }
}
