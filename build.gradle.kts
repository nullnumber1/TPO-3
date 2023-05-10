plugins {
    id("java")
    id("jacoco")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

jacoco {
    toolVersion = "0.8.9" // Set the version you want to use
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.3")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.3")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.9.3")
    testImplementation("org.junit.platform:junit-platform-suite-api:1.9.3")
    implementation("org.json:json:20230227")
    implementation("org.seleniumhq.selenium:selenium-java:4.9.0")
    implementation("org.seleniumhq.selenium:selenium-chrome-driver:4.9.0")
    implementation("org.seleniumhq.selenium:selenium-firefox-driver:4.9.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.test {
    finalizedBy("jacocoTestReport")
}

tasks.withType<JacocoReport> {
    reports {
        xml.required.set(true)
        csv.required.set(false)
        html.required.set(true)
    }
}