package org.john.config;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Configuration {

    private static final String FILE_NAME = "config.properties";
    private static final Logger LOGGER = Logger.getLogger(Configuration.class.getName());

    private String url;
    private String subject;
    private String subjectDropdownWebElementId;
    private String searchButtonWebElementId;
    private String iFrameWebElementId;
    private String tableRowWebElementIdPrefix;
    private String tableWebElementClassName;

    public Configuration() {
        loadProperties();
    }

    private void loadProperties() {
        Properties properties = new Properties();

        try {
            properties.load(Configuration.class.getClassLoader().getResourceAsStream(FILE_NAME));

            url = properties.getProperty("url");
            subject = properties.getProperty("subject");
            subjectDropdownWebElementId = properties.getProperty("subjectDropdownWebElementId");
            searchButtonWebElementId = properties.getProperty("searchButtonWebElementId");
            iFrameWebElementId = properties.getProperty("iFrameWebElementId");
            tableRowWebElementIdPrefix = properties.getProperty("tableRowWebElementIdPrefix");
            tableWebElementClassName = properties.getProperty("tableWebElementClassName");

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unable to load properties file", e);
        }
    }

    public String getUrl() {
        return url;
    }

    public String getSubject() {
        return subject;
    }

    public String getSubjectDropdownWebElementId() {
        return subjectDropdownWebElementId;
    }

    public String getSearchButtonWebElementId() {
        return searchButtonWebElementId;
    }

    public String getiFrameWebElementId() {
        return iFrameWebElementId;
    }

    public String getTableRowWebElementIdPrefix() {
        return tableRowWebElementIdPrefix;
    }

    public String getTableWebElementClassName() {
        return tableWebElementClassName;
    }
}
