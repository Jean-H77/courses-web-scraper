package org.john.course;

import com.google.inject.name.Named;
import jakarta.inject.Inject;
import org.checkerframework.checker.units.qual.N;
import org.john.config.Configuration;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class CourseScraper {

    private static final Logger LOGGER = Logger.getLogger(CourseScraper.class.getName());

    private final WebDriver driver;
    private final CourseRepository courseRepository;
    private final String url;
    private final String subject;
    private final String subjectDropdownWebElementId;
    private final String searchButtonWebElementId;
    private final String iFrameWebElementId;
    private final String tableRowWebElementIdPrefix;
    private final String tableWebElementClassName;

    @Inject
    public CourseScraper(WebDriver webDriver, CourseRepository courseRepository, Configuration configuration) {
        this.driver = webDriver;
        this.courseRepository = courseRepository;
        this.url = configuration.getUrl();
        this.subject = configuration.getSubject();
        this.subjectDropdownWebElementId = configuration.getSubjectDropdownWebElementId();
        this.searchButtonWebElementId = configuration.getSearchButtonWebElementId();
        this.iFrameWebElementId = configuration.getiFrameWebElementId();
        this.tableRowWebElementIdPrefix = configuration.getTableRowWebElementIdPrefix();
        this.tableWebElementClassName = configuration.getTableWebElementClassName();
    }

    public void scrape() {
        Map<String, List<Course>> coursesMap = new HashMap<>();
        System.setProperty("webdriver.gecko.driver", "C:/geckodriver.exe");

        driver.get(url);

        WebElement iframe = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(By.id(iFrameWebElementId)));
        driver.switchTo().frame(iframe);

        WebElement subjectElement = driver.findElement(By.id(subjectDropdownWebElementId));
        Select subjectDropdown = new Select(subjectElement);
        subjectDropdown.selectByValue(subject);

        driver.findElement(By.id(searchButtonWebElementId)).click();
        new WebDriverWait(driver, Duration.ofSeconds(45)).until(ExpectedConditions.visibilityOfElementLocated(By.id(tableRowWebElementIdPrefix+"0")));

        Document document = Jsoup.parse(driver.getPageSource());

        Elements elements = document.select("span:contains(Units)");

        LOGGER.info("Loading courses");
        int numberCoursesLoaded = 0;

        for (Element e : elements) {
            String position = e.id().substring(e.id().lastIndexOf("$") + 1);
            String courseTitle = e.text().substring(0, e.text().indexOf("-"));
            List<Course> courseList = loadCourse(document, courseTitle, position);
            if (courseList != null) {
                coursesMap.put(courseTitle.replaceAll("\\s", ""), courseList);
                numberCoursesLoaded += courseList.size();
            }
        }

        LOGGER.info("Finished loading: " + numberCoursesLoaded + " courses");
        courseRepository.putAll(coursesMap);
    }

    private List<Course> loadCourse(Document document, String course, String position) {
        Element outerTable = document.getElementById(tableRowWebElementIdPrefix + position);

        if (outerTable == null) {
            return null;
        }

        Element innerTable = outerTable.getElementsByClass(tableWebElementClassName).getFirst();
        Elements rows = innerTable.select("tr");

        Course.Builder courseBuilder;
        List<Course> courseList = new ArrayList<>();

        for (int i = 1; i < rows.size(); i++) {
            String rowText = rows.get(i).text().replaceAll("OF", "").replaceAll("OC", "");
            String[] parts = rowText.split("\\s+");
            courseBuilder = new Course.Builder();

            courseBuilder.title(course);

            for (int j = 1; j < parts.length; j++) {
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
                }
            }

            courseBuilder.index(position);
            courseList.add(courseBuilder.build());
        }

        return courseList;
    }
}
