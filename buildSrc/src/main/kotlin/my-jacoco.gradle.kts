/**
 * This file contains the logic to configure jacoco.
 */
plugins {
    java
    jacoco
}

jacoco {
    // Experimental support for Java 15 has only been added to 0.8.6. Release version 0.8.7 will officially support Java 15.
    // This needs to be set, otherwise this task won't run properly if you use Java 15+ locally.
    toolVersion = "0.8.6"
}

tasks.jacocoTestReport {
    // Tests are required before generating the report. For some reason, you need to do this yourself.
    dependsOn(tasks.test)

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

tasks.check {
    // Reports are always generated after running the checks.
    finalizedBy(tasks.jacocoTestReport)
}
