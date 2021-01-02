/**
 * This file contains the logic to configure spotbugs.
 */
plugins {
    java
    id("com.github.spotbugs")
}

dependencies {
    // The spotbugs annotation dependency may not occur as dependency. It is only needed at compile time.
    // Annotations are currently not used in production code. If this should be the case in the future, add the dependency
    // as compileOnly.
    testCompileOnly("com.github.spotbugs:spotbugs-annotations:4.1.1")
}

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

// Spotbugs automatically adds the spotbugsMain and spotbugsTest to the check task.
// We don't need to do this ourselves.
