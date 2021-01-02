/**
 * This file contains the logic to configure pitest.
 */
plugins {
    java
    id("info.solidsoft.pitest")
}

pitest {
    junit5PluginVersion.set("0.12")
    outputFormats.set(listOf("HTML"))
    timestampedReports.set(false)
    threads.set(4)
}

tasks.check {
    dependsOn(tasks.pitest)
}
