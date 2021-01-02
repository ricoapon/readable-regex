plugins {
    `java-library`

    `my-checkstyle`
    `my-jacoco`
    `my-spotbugs`
    `my-pitest`
    `my-test-percentage-printer`

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
