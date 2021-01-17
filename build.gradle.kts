plugins {
    `java-library`
    `my-checkstyle`
    `my-jacoco`
    `my-spotbugs`
    `my-pitest`
    `my-test-percentage-printer`
    `my-artifact-publisher` apply false // We can only apply the plugin after the version has been determined.
}

group = "io.github.ricoapon"
version = when {
    project.hasProperty("customVersion") -> project.property("customVersion").toString()
    else -> "head-SNAPSHOT"
}
println("Using version $version")
plugins.apply("my-artifact-publisher")

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
    api("javax.inject:javax.inject:1")
    implementation("com.thoughtworks.paranamer:paranamer:2.8");

    testImplementation("org.hamcrest:hamcrest:2.2")

    val junitVersion = "5.6.2"
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}

val test by tasks.getting(Test::class) {
    useJUnitPlatform()
}
