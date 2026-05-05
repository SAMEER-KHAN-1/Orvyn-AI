fun parseTimeString(s: String): Pair<Int, Int>? {
    val cleaned = s.trim().lowercase()
    val match = Regex("(\\d{1,2})(?::(\\d{2}))?\\s*(am|pm)?").find(cleaned) ?: return null
    var hour = match.groupValues[1].toIntOrNull() ?: return null
    val minute = match.groupValues[2].toIntOrNull() ?: 0
    val ampm = match.groupValues[3]
    if (ampm == "pm" && hour < 12) hour += 12
    if (ampm == "am" && hour == 12) hour = 0
    if (hour > 23 || minute > 59) return null
    return hour to minute
}

fun tryParseReminderCommand(input: String): Pair<String, Long>? {
    val main = Regex("(?i)^\\s*(?:remind me to|set a reminder to|set reminder to|set a reminder for|set reminder for)\\s+(.+?)\\s*\\.?\\s*$")
        .find(input.trim())?.groupValues?.get(1) ?: return null
    println("Main matched: $main")

    val timeRegex = Regex("(?i)\\bat\\s+(\\d{1,2}(?::\\d{2})?\\s*(?:am|pm)?)\\b")
    val timeMatch = timeRegex.find(main)
    if (timeMatch == null) {
        println("Time match failed for: $main")
        return null
    }
    println("Time matched: ${timeMatch.value}")
    val (h, min) = parseTimeString(timeMatch.groupValues[1]) ?: return null
    return "success" to 0L
}

println("TEST 1:")
println(tryParseReminderCommand("Set a reminder to go to the store at 5 pm"))
println("\nTEST 2:")
println(tryParseReminderCommand("Set a reminder to find my keys at 5 pm"))
println("\nTEST 3:")
println(tryParseReminderCommand("set a reminder to whatever at 5 pm tomorrow"))
