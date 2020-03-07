package Chapter4_ConditionAndLoop

fun inlineLambda (a: Int, b: Int, out: (Int, Int) -> Unit) {
    out(a, b)
}

// 람다식에서 라벨과 함께 returnn 사용하기
fun retFunc() {
    println("start of retFunc")
    inlineLambda(13, 3, lit@{a, b ->
        val result = a + b
        if (result > 10) return@lit
        println("result: $result")
    })
    println("end of redFunc")
}

// 암묵적 라벨
fun retFunc2() {
    println("start of retFunc2")
    inlineLambda(13, 3) { a, b ->
        val result = a + b
        if (result > 10) return@inlineLambda
        println("result: $result")
    }
    println("end of retFunc2")
}

fun retFuncAnony() {
    println("start of retFuncAnony")
    inlineLambda(13, 3, fun (a, b) {
        val result = a + b
        if (result > 10) return
    })
    println("end of recFuncAnony")

}
fun main() {
    for (x in 1..5) {
        println(x)
    }

    var sum = 0;
    for (x in 1..10) sum += x
    println("sum: $sum")

    for (i in 5 downTo 1) print("$i ")
    println()

    for (i in 1..5 step 2) print("$i ")
    println()

    for (i in 5 downTo 1 step 2) print("$i ")
    println()

    var i = 1
    while (i < 5) {
        print("$i ")
        ++i
    }
    println()

    do {
        print("Enter an integer: ")
        val input = readLine()!!.toInt()

        for (i in 0..(input-1)) {
            for (j in 0..(input-1)) print((i + j) % input + 1)
            println()
        }
    } while (input != 0)

    retFunc()
    retFunc2()
    retFuncAnony()

    val getMessagebyLambda = lambda@ {num: Int ->
        if (num !in 1..100) {
            return@lambda "Error" // 라벨을 통한 반환
        }
        "Success" // 마지막 식이 반환
    }

    val getMessage = fun(num: Int): String {
        if (num !in 1..100) {
            return "Error"
        }
        return "Success"
    }

}