package pl.pjatk.s16604.proj1

fun initUpgrades(): MutableList<Upgrade> {
    return mutableListOf(
        Upgrade("cursor", "Cursor", 0, 1, 50, 1.05),
        Upgrade("granny", "Granny", 0, 5, 500, 1.1),
        Upgrade("farm", "Farm", 0, 30, 1000, 1.15),
        Upgrade("mine", "Mine", 0, 100, 10000, 1.2),
        Upgrade("factory", "Factory", 0, 100000, 50, 1.25),
        Upgrade("lab", "Lab", 0, 1000000, 1000000, 1.3)

    )
}

fun countFormatter(number: Double): String {
    if (number < 1000000) {
        return number.toString()
    }
    if (number < 1000000000) {
        val prettier = number / 1000000
        return "$prettier MLN"
    }
    if (number < 1000000000000) {
        val prettier = number / 1000000000
        return "$prettier BLN"
    }
    return "Damn... $number"
}

fun timeFormatter(number: Long): String {
    var seconds = number
    var minutes = 0L
    var hours = 0L

    //wiecej niz minuta
    if (seconds > 60) {
        //wiecej niz godzina
        if (seconds > 60 * 60) {
            hours = (number / (60 * 60))
            seconds = number.rem(60 * 60)
            //gdy reszta poza godzina jest wieksza od minuty
            if (seconds > 60) {
                minutes = (seconds / 60)
                seconds = seconds.rem(60)
                return "$hours :$minutes : $seconds"
            }
            return " $hours h"
        }
        return " $hours h"
    }
    return seconds.toString()
}