plugins {
    id("java")
    id("jacoco")
    id("org.sonarqube") version "7.0.1.6134"
}

group = "org.learn"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
val retrofitVersion = "2.11.0"
val jacksonVersion = "2.18.2"
val lombokVersion = "1.18.34"
val junitBomVersion = "5.10.0"
val mockitoVersion = "5.14.2"
val mockWebServerVersion = "4.12.0"
val slf4jVersion = "2.0.16"

dependencies {
    testImplementation(platform("org.junit:junit-bom:$junitBomVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")

    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
    testCompileOnly("org.projectlombok:lombok:$lombokVersion")
    testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion")

    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-jackson:$retrofitVersion")

    testImplementation("com.squareup.okhttp3:mockwebserver:$mockWebServerVersion")
    testImplementation("org.mockito:mockito-core:$mockitoVersion")
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    implementation("org.slf4j:slf4j-simple:$slf4jVersion")
}

tasks.test {
    useJUnitPlatform()
}

jacoco {
    toolVersion = "0.8.13"
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
    classDirectories.setFrom(
        files(classDirectories.files.map {
            fileTree(it) {
                exclude("**/dto/**", "**/app/**")
            }
        })
    )
}

tasks.jacocoTestCoverageVerification {
    dependsOn(tasks.jacocoTestReport)
    classDirectories.setFrom(
        files(classDirectories.files.map {
            fileTree(it) {
                exclude("**/dto/**", "**/app/**")
            }
        })
    )
    violationRules {
        rule {
            limit {
                counter = "LINE"
                value = "COVEREDRATIO"
                minimum = "0.70".toBigDecimal()
            }
            limit {
                counter = "BRANCH"
                value = "COVEREDRATIO"
                minimum = "0.70".toBigDecimal()
            }
        }
    }
}

tasks.check {
    dependsOn(tasks.jacocoTestCoverageVerification)
}

sonar {
    properties {
        property("sonar.projectKey", "rusleak_weatherApi1")
        property("sonar.organization", "rusleak")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.coverage.jacoco.xmlReportPaths", "build/reports/jacoco/test/jacocoTestReport.xml")
    }
}

tasks.named("sonar") {
    dependsOn(tasks.jacocoTestReport)
}