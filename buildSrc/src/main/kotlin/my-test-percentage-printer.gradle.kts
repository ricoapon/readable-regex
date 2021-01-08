/**
 * This file adds the logic for printing test percentages from reports to the console. It is assumed that the jacoco
 * and pitest plugins are applied.
 */
plugins {
    java
}

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
    var areAllTests100Percent = true;

    var resultString = "Coverage Summary:\n"
    readTestPercentageFromJacocoReport().forEach {
        resultString += createLine(it)
        if (it.value.first != it.value.second) {
            areAllTests100Percent = false
        }
    }
    readTestPercentagesFromPitestReport().forEach {
        resultString += createLine(it)
        if (it.value.first != it.value.second) {
            areAllTests100Percent = false;
        }
    }

    print(resultString)

    if (!areAllTests100Percent) {
        throw GradleException("The test coverage is not 100%!")
    }
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
