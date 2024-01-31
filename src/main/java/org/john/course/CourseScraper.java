package org.john.course;

import jakarta.inject.Inject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseScraper {

    private final WebDriver driver = new FirefoxDriver();
    private final String URL;
    private final String subject;
    private final CourseRepository courseRepository;

    @Inject
    public CourseScraper(CourseRepository courseRepository) {
        // TODO: 1/31/2024 courses to load from config file
        URL = "https://cmsweb.csun.edu/psp/CNRPRD/EMPLOYEE/SA/c/NR_SSS_COMMON_MENU.NR_SSS_SOC_BASIC_C.GBL";
        this.subject = "COMP";
        this.courseRepository = courseRepository;
    }

    public void scrape() {
        Map<String, List<Course>> coursesMap = new HashMap<>();

        System.setProperty("webdriver.gecko.driver", "C:/geckodriver.exe");

        driver.get(URL);

        WebElement iframe = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("ptifrmtgtframe")));
        driver.switchTo().frame(iframe);

        WebElement subjectElement = driver.findElement(By.id("NR_SSS_SOC_NWRK_SUBJECT"));
        Select subjectDropdown = new Select(subjectElement);
        subjectDropdown.selectByValue(subject);

        driver.findElement(By.id("NR_SSS_SOC_NWRK_BASIC_SEARCH_PB")).click();
        new WebDriverWait(driver, Duration.ofSeconds(45)).until(ExpectedConditions.visibilityOfElementLocated(By.id("NR_SSS_SOC_NSEC$scroll$0")));

        Document document = Jsoup.parse(driver.getPageSource());

        Elements elements = document.select("span:contains(Units)");

        for (Element e : elements) {
            String position = e.id().substring(e.id().lastIndexOf("$") + 1);
            String courseTitle = e.text().substring(0, e.text().indexOf("-"));
            List<Course> courseList = loadCourse(document, courseTitle, position);

            if (courseList != null) {
                coursesMap.put(courseTitle.replaceAll("\\s", ""), courseList);
            }
        }

        courseRepository.putAll(coursesMap);
    }

    private List<Course> loadCourse(Document document, String course, String position) {
        String rowIdPrefix = "NR_SSS_SOC_NSEC$scroll$";
        Element outerTable = document.getElementById(rowIdPrefix + position);

        if (outerTable == null) {
            return null;
        }

        String tableName = "PSLEVEL3GRIDWBO";
        Element innerTable = outerTable.getElementsByClass(tableName).getFirst();
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
                    case 10 -> courseBuilder.consent(part);
                }
            }

            courseBuilder.index(position);
            courseList.add(courseBuilder.build());
        }

        return courseList;
    }
}
