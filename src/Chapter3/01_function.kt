package Chapter3

// 매개변수가 있는 함수
fun sum(a: Int, b: Int): Int {
//    var : 변경 가능, val : 변경 불가
    var sum = a + b
    return sum
}

// 인자와 반환값의 타입이 같으면 생략 가능
// 중괄호 안의 코드가 한 줄이면 return문 생략 가능
fun sum2(a: Int, b: Int) = a + b

// 매개변수가 없는 함수
fun printSum(a: Int, b: Int): Unit {
    println("sum of $a and $b is ${a + b}")
}

// 매개변수에 기본값 지정하기
fun userInfo(name: String, email: String = "default") {
    println("${name}님의 이메일은 ${email}입니다.")
}

// 매개변수의 개수가 고정되어 있지 않은 함수
fun normalVarargs(vararg counts: Int) {
    for (num in counts) {
        print("$num ")
    }
    println()
}

fun main() {
    println(sum(5, 10))
    println(sum2(5, 10))
    printSum(5, 10)
    userInfo("이인동", "kundera@kakao.com")
    userInfo("코틀린")
    normalVarargs(1, 3, 5, 7, 9)
}