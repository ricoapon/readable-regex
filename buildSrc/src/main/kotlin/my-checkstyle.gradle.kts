/**
 * This file contains the logic to configure checkstyle.
 */
plugins {
    java
    checkstyle
}

tasks.withType<Checkstyle>().configureEach {
    configFile = File("checkstyle.xml")
}
tasks.register("checkstyle") {
    dependsOn(tasks.checkstyleMain)
    dependsOn(tasks.checkstyleTest)
}

// Checkstyle automatically adds the checkstyleMain and checkstyleTest to the check task.
// We don't need to do this ourselves.
