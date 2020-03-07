package Chapter3_Function

import java.math.BigInteger

// 익명 함수 : 일반 함수지만 이름이 없는 것. 람다식 함수와는 달리 일반 함수의 이름을 생략하고 사용하는 것이다.
// fun(x: Int, y: Int): Int = x + y와 같이 사용
val addNumbers : (Int, Int) -> Int = fun(x: Int, y: Int): Int = x + y
val addNumbersByLambda : (Int, Int) -> Int = {x: Int, y: Int -> x + y}

// 인라인 함수 : 함수가 호출되는 곳에 함수 본문의 내용을 모두 복사해 붙여넣는 함수.
// 함수의 분기 없이 처리되기 때문에 코드의 성능을 높일 수 있다.
// 인라인 함수는 람다식 매개변수를 가지고 있는 함수에서 동작한다.
inline fun shortFunc(a: Int, out: (Int) -> Unit) {
    println("Before calling out()")
    out(a)
    println("After calling out()")
}

// 인라인 함수의 매개변수로 사용한 람다식의 코드가 너무 길거나 인라인 함수의 본문 자체가 너무 길면 컴파일러에서 성능 경고를 낼 수 있다.
// 또한 인라인 함수가 너무 많이 호출되면 코드 양만 늘어나서 좋지 않을 수도 있다.
// 코드가 복사되어 들어가기 때문에 매개변수 참조도 불가능하다.
inline fun noInlineShortFunc(a: Int, noinline out: (Int) -> Unit) {
    println("Before calling out()")
    out(a)
    println("After calling out()")
}

// 비지역 반환을 금지해야 하는 곳에 crossinline 키워드를 사용한다.
inline fun crossInlineFuc(a: Int, crossinline out: (Int) -> Unit) {
    println("Before calling out()")
    nestedFunc { out(a) }
    println("After calling out()")
}

fun nestedFunc(body: () -> Unit) {
    body()
}

// String class에 확장함수 사용하기
fun String.getLongString(target: String): String =
    if (this.length > target.length) this else target

// 중위 함수 : 중위 표현법(클래스의 멤버를 호출할 때 사용하는 점(.)을 생략하고 함수 이름 뒤에 소괄호를 붙이지 않는 표현법)을 사용하는 함수
//  1) 멤버 메서드 또는 확장 함수여야 한다.
//  2) 하나의 매개변수를 가져야 한다.
//  3) infix 키워드를 사용하여 정의한다.
infix fun Int.multiply(x: Int): Int {
    return this * x
}

// 일반적인 재귀에서는 재귀 함수가 먼저 호출되고 계산되지만 꼬리 재귀 함수에서는 계산을 먼저 하고 재귀 함수를 호출한다.
// 일반적인 팩토리얼 재귀함수. factorial 함수의 문맥을 유지하기 위해 factorial 함수 스택 메모리의 n배만큼 스택 메모리를 사용한다.
fun factorial(n: Int): Long {
    return if (n == 1) n.toLong() else factorial(n-1) * n
}

tailrec fun factorialTail(n: Int, run: Int = 1): Long {
    return if (n == 1) n.toLong() else factorialTail(n-1, run*n)
}

tailrec fun fibonacci(n: Int, a: BigInteger, b: BigInteger): BigInteger {
    println(n)
    println(a)
    println(b)
    println()
    return if (n == 0) a else fibonacci(n-1, b, a+b)
}

fun main() {
    val result = addNumbers(10, 60)
    val resultByLambda = addNumbersByLambda(10, 60)
//    동일한 결과를 낳는다.
//    그럼에도 불구하고 익명 함수를 쓰는 이유는 람다식에서는 return이나 break, continue처럼 제어문을 사용하기 어렵기 때문이다.
    println(result)
    println(resultByLambda)
    println()

    shortFunc(3) { println("First call : $it") }
    shortFunc(5) { println("Second call : $it") }
    println()

    noInlineShortFunc(7) { println("noInline call : $it") }
    println()

//    인라인 함수에서 사용한 람다식에는 return을 사용할 수 있다.
//    out(a)는 인라인되어 대체되기 때문에 return문까지 포함된다. 따라서 After calling out 문장은 실행되지 않는다.
//    이러한 반환을 비지역반환 이라고 부른다.
    shortFunc(3) {
        println("First call : $it")
        return@shortFunc
    }
    println()

    val source = "Hello World!"
    val target = "Kotlin!"
    println(source.getLongString(target))
    println()

    val multi = 3 multiply 10
    println("multi: $multi")
    println()

    println(factorial(5))
    println(fibonacci(10, BigInteger("0"), BigInteger("1")))
}