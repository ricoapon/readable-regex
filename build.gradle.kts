plugins {
    `java-library`
    id("com.github.spotbugs") version "4.5.0"
    id("checkstyle")
    id("jacoco")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
    withSourcesJar()
}

repositories {
    jcenter()
    mavenCentral()
}

dependencies {
    implementation("com.github.spotbugs:spotbugs-annotations:4.1.1")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.2")
}

val test by tasks.getting(Test::class) {
    useJUnitPlatform()
}

// ================
// SpotBugs
// ================
spotbugs {
    // Display final report as HTML.
    // Use different HTML template (stylesheet) that is prettier.
    tasks.spotbugsMain {
        reports.create("html") {
            isEnabled = true
            setStylesheet("fancy-hist.xsl")
        }
    }
    tasks.spotbugsTest {
        reports.create("html") {
            isEnabled = true
            setStylesheet("fancy-hist.xsl")
        }
    }
}
tasks.register("spotbugs") {
    dependsOn(tasks.spotbugsMain)
    dependsOn(tasks.spotbugsTest)
}

// ================
// Checkstyle
// ================
tasks.withType<Checkstyle>().configureEach {
    configFile = File("checkstyle.xml")
}
tasks.register("checkstyle") {
    dependsOn(tasks.checkstyleMain)
    dependsOn(tasks.checkstyleTest)
}

// ================
// JaCoCo
// ================
jacoco {
    // Experimental support for Java 15 has only been added to 0.8.6.
    // Release version 0.8.7 will officially support Java 15.
    toolVersion = "0.8.6"
}
tasks.check {
    // Reports are always generated after running the checks.
    finalizedBy(tasks.jacocoTestReport)
}
tasks.jacocoTestReport {
    // Tests are required before generating the report.
    dependsOn(tasks.test)
}
tasks.jacocoTestReport {
    reports {
        // Codecov.io depends on xml format report.
        xml.isEnabled = true
        // Add HTML report readable by humans.
        html.isEnabled = true
    }
}
tasks.register("jacoco") {
    dependsOn(tasks.jacocoTestCoverageVerification)
    dependsOn(tasks.jacocoTestReport)
}
