plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

// External plugins cannot be included with a version inside the standalone scripts. Therefore we need to add them as
// a dependency inside this build script in a specific way.
dependencies {
    implementation(plugin("com.github.spotbugs", "4.5.0"))
}

fun plugin(id: String, version: String) = "$id:$id.gradle.plugin:$version"
