plugins {
    id("java")
}

group = "org.john"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jsoup:jsoup:1.17.2")
    implementation("org.seleniumhq.selenium:selenium-java:4.17.0")
    implementation("org.quartz-scheduler:quartz:2.3.2")
    implementation("com.google.inject:guice:7.0.0")
    implementation("org.slf4j:slf4j-api:2.0.11")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}