rootProject.name = "advent-of-code"
include("advent-of-code-utils")
include("2024:aoc24-java")
findProject(":2024:aoc24-java")?.name = "aoc24-java"
