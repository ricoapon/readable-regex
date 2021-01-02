plugins {
    `java-library`
    id("info.solidsoft.pitest") version "1.5.1"

    `my-checkstyle`
    `my-jacoco`
    `my-spotbugs`

    `maven-publish`
    signing
}

group = "io.github.ricoapon"
version = when {
    project.hasProperty("customVersion") -> project.property("customVersion").toString()
    else -> "head-SNAPSHOT"
}
println("Using version $version")

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
    withJavadocJar()
    withSourcesJar()
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.hamcrest:hamcrest:2.2")

    val junitVersion = "5.6.2"
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}

val test by tasks.getting(Test::class) {
    useJUnitPlatform()
}

// ================
// Pitest
// ================
pitest {
    junit5PluginVersion.set("0.12")
    outputFormats.set(listOf("HTML"))
    timestampedReports.set(false)
    threads.set(4)
}
tasks.check {
    finalizedBy(tasks.pitest)
}

// ================
// Print test percentages
// ================
tasks.register("printTestPercentages") {
    // Don't add the input files to the task. The percentages should always show, also if the task has already been executed.
    doLast {
        printTestPercentages()
    }
    dependsOn("jacoco", "pitest")
}
tasks.check {
    finalizedBy("printTestPercentages")
}

fun printTestPercentages() {
    var resultString = "Coverage Summary:\n"
    readTestPercentageFromJacocoReport().forEach { resultString += createLine(it) }
    readTestPercentagesFromPitestReport().forEach { resultString += createLine(it) }
    println(resultString)
}

fun createLine(result: Map.Entry<String, Pair<Int, Int>>): String =
        "${result.key.padEnd(18)}: ${Math.floorDiv(result.value.first * 100, result.value.second).toString().padStart(3)}% " +
                "(${result.value.first.toString().padStart(4)} / ${result.value.second.toString().padStart(4)})\n"

fun readTestPercentageFromJacocoReport(): Map<String, Pair<Int, Int>> {
    val reportFileContent = File(project.buildDir.resolve("reports/jacoco/test/jacocoTestReport.xml").toURI()).readText()

    val pattern = Regex("<\\/package><counter type=\"INSTRUCTION\" missed=\"(\\d+)\" covered=\"(\\d+)\"\\/>" +
            "<counter type=\"BRANCH\" missed=\"(\\d+)\" covered=\"(\\d+)\"\\/>" +
            "<counter type=\"LINE\" missed=\"(\\d+)\" covered=\"(\\d+)\"\\/>")
    val (instructionMissed, instructionTotal, branchMissed, branchTotal, lineMissed, lineTotal) = pattern.find(reportFileContent)!!.destructured

    return mapOf("JACOCO_INSTRUCTION" to Pair(Integer.parseInt(instructionTotal) - Integer.parseInt(instructionMissed), Integer.parseInt(instructionTotal)),
            "JACOCO_BRANCH" to Pair(Integer.parseInt(branchTotal) - Integer.parseInt(branchMissed), Integer.parseInt(branchTotal)),
            "JACOCO_LINE" to Pair(Integer.parseInt(lineTotal) - Integer.parseInt(lineMissed), Integer.parseInt(lineTotal)))
}

fun readTestPercentagesFromPitestReport(): Map<String, Pair<Int, Int>> {
    val reportFileContent = File(project.buildDir.resolve("reports/pitest/index.html").toURI()).readText()

    val pattern = Regex("<td>\\d+% <div class=\"coverage_bar\"><div class=\"coverage_complete width-\\d+\"></div><div class=\"coverage_legend\">(\\d+)/(\\d+)</div></div></td>.*\\r?\\n?" +
            "\\s+<td>\\d+% <div class=\"coverage_bar\"><div class=\"coverage_complete width-\\d+\"></div><div class=\"coverage_legend\">(\\d+)/(\\d+)</div></div></td>")
    val (lineCoverageHit, lineCoverageTotal, mutationCoverageHit, mutationCoverageTotal) = pattern.find(reportFileContent)!!.destructured

    return mapOf("PITEST_LINE" to Pair(Integer.parseInt(lineCoverageHit), Integer.parseInt(lineCoverageTotal)),
            "PITEST_MUTATION" to Pair(Integer.parseInt(mutationCoverageHit), Integer.parseInt(mutationCoverageTotal)))
}

// ================
// Publishing artifacts to Maven Central
// ================
publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = "readable-regex"
            from(components["java"])
            pom {
                name.set("Readable Regex")
                description.set("Regular expressions made readable in Java")
                url.set("https://github.com/ricoapon/readable-regex")
                inceptionYear.set("2020")

                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://github.com/ricoapon/readable-regex/blob/master/LICENSE")
                    }
                }

                developers {
                    developer {
                        id.set("ricoapon")
                        name.set("Rico Apon")
                    }
                }

                scm {
                    url.set("https://github.com/ricoapon/readable-regex")
                    connection.set("scm:https://github.com/ricoapon/readable-regex.git")
                    developerConnection.set("scm:git@github.com:ricoapon/readable-regex.git")
                }
            }
        }
    }

    repositories {
        maven {
            val releasesRepoUrl = "https://oss.sonatype.org/service/local/staging/deploy/maven2"
            val snapshotsRepoUrl = "https://oss.sonatype.org/content/repositories/snapshots"
            url = uri(if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl)

            credentials {
                username = project.findProperty("ossrhUsername") as String?
                password = project.findProperty("ossrhPassword") as String?
            }
        }
    }
}
signing {
    sign(publishing.publications["mavenJava"])
}
tasks.javadoc {
    if (JavaVersion.current().isJava9Compatible) {
        (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
    }
}

// Make sure that we can only publish if all checks have been completed successfully.
// Don't add this to the task "publish", because this can go wrong with parallel execution.
tasks.withType<PublishToMavenRepository> {
    dependsOn("check")
}
