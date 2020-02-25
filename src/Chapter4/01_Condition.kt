package Chapter4

fun main() {
    val a = 12
    val b = 7

    var max: Int

    if (a > b)
        max = a
    else
        max = b

    max = if (a > b) a else b

    max = if (a > b) {
        println("a 선택")
        a  // 마지막 식인 a가 반환되어 return에 할당
    } else {
        println("b 선택")
        b
    }

    val number = 0
    val result = if (number > 0)
        "양수"
    else if (number < 0)
        "음수"
    else
        "0"

    val score = readLine()!!.toDouble()
    var grade: Char = 'F'

    if (score >= 90) {
        grade = 'A'
    } else if (score >= 80.0 && score <= 89.9) {
        grade = 'B'
    } else if (score >= 70.0 && score <= 79.9) {
        grade = 'C'
    }
    println("Score: $score, Grade: $grade")

    if (score >= 90) {
        grade = 'A'
    } else if (score in 80.0..89.9) {
        grade = 'B'
    } else if (score in 70.0..79.9) {
        grade = 'C'
    }
    println("Score: $score, Grade: $grade")

    when (score) {
        in 90.0..100.0 -> grade = 'A'
        in 80.0..89.9 -> grade = 'B'
        in 70.0..79.9 -> grade = 'C'
        !in 70.0..100.0 -> grade = 'F'
    }
    println("Score: $score, Grade: $grade")

    fun cases(x: Any) {
        val x = if (x is Double) x.toInt() else x
        when (x) {
            is String -> "문자열입니다."
            1 -> println("x == 1")
            2, 3 -> println("x == 2 or x == 3") // 2 또는 3
            in 10..20 -> println("x는 10 이상 20 이하입니다.")
            !in 100..999 -> println("x는 세 자리의 자연수가 아닙니다.")
            else -> println(false)
        }
    }
    cases(score)

}