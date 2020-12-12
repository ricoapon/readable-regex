plugins {
    `java-library`
    id("com.github.spotbugs") version "4.5.0"
    id("checkstyle")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
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
