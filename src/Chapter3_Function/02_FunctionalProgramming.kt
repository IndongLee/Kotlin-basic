package Chapter3_Function

// 순수함수 : 1) 같은 인자에 대햐여 항상 같은 값을 반환한다. 2) 함수 외부의 어떤 상태도 바꾸지 않는다.
// 순수함수의 예
fun sumNumbers(a: Int, b: Int) : Int {
    return a + b
}

// 람다 대수 : 이름이 없는 함수로 2개 이상의 입력을 1개의 출력으로 단순화한다는 개념
// 람다식 : 다른 함수의 인자로 넘기는 함수, 함수의 결과값으로 반환하는 함수, 변수에 저장하는 함수
// { x, y -> x + y }와 같은 형태

// 일급 객체
//  1) 일급 객체는 함수의 인자로 전달할 수 있다.
//  2) 일급 객체는 함수의 반환값에 사용할 수 있다.
//  3) 일급 객체는 변수에 담을 수 있다.
//  일급 함수에 이름이 없는 경우 람다식 함수 혹은 람다식이라 부른다.

// 고차 함수 : 다른 함수를 인자로 사용하거나 함수를 결과값으로 반환하는 함수. 일급 객체 혹은 일급 함수를 서로 주고받을 수 있는 함수.
fun highFunc(sumFirst: (Int, Int) -> Int, a: Int, b: Int): Int = sumFirst(a, b)

// 함수의 반환값으로 함수를 사용
fun funcFunc() : Int {
    return sum(2, 5)
}

fun callByValue(b: Boolean): Boolean {
    println("callByValue Function")
    return b
}

// 람다식 자료형으로 선언된 매개변수
fun callByName(b: () -> Boolean): Boolean {
    println("callByName function")
    return b()
}

val lambda: () -> Boolean = {
    println("lambda function")
    true
}

fun funcParam(a: Int, b: Int, c: (Int, Int) -> Int): Int {
    return c(a, b)
}

fun text(a: String, b: String) = "Hi! $a $b"

fun hello(body: (String, String) -> String) {
    println(body("Hello", "World"))
}

// 매개변수가 없는 람다식이 noParam 함수의 매개변수 out으로 지정됨
fun noParam(out: () -> String) = println(out())

fun oneParam(out: (String) -> String) {
    println(out("OneParam"))
}

fun moreParam(out: (String, String) -> String) {
    println(out("OneParam", "TwoParam"))
}

fun withArgs(a: String, b: String, out: (String, String) -> String) {
    println(out(a, b))
}

fun twoLambda(first: (String, String) -> String, second: (String) -> String) {
    println(first("OneParam", "TwoParam"))
    println(second("OneParam"))
}

fun main() {
//    람다식을 인자로 넘긴다.
    println(highFunc({x, y -> x + y}, 10, 5))
    println("funcFunc: ${funcFunc()}")

//    변수에 람다식 할당하기
    var result: Int
//    자료형 선언을 생략 (매개변수에 자료형이 명시되어 있기 때문)
    val multi = {x: Int, y: Int -> x * y}
    val multi2: (Int, Int) -> Int = {x: Int, y: Int -> 
        println("x * y" )
//       마지막 표현식이 반환됨 
        x * y}
//    람다식 매개변수 자료형의 생략
    val multi3: (Int, Int) -> Int = {x ,y -> x * y}
    result = multi(10, 20)
    var result2 = multi2(10, 30)
    println(result)
    
//    매개변수가 없는 람다식
    val greet: () -> Unit = {println("Hello World!")}
    greet() // 함수처럼 사용할 수 있다.
    val new = greet // 람다식이 들어 있는 변수를 다른 변수에 할당
    new()
//    매개변수가 하나인 람다식
    val square: (Int) -> Int = {x -> x * x}
//    람다식 안의 람다식
    val nestedLambda: () -> () -> Unit = {{println("nested")}}

//    값에 의한 호출
    println();
    println("값에 의한 호출입니다.")
    val resultByValue = callByValue(lambda())
    println(resultByValue)

    println()
    println("이름에 의한 호출입니다.")
    val resultByName = callByName(lambda)
    println(resultByName)
    println()

//    sum은 람다식이 아니기 때문에 오류가 발생한다.
//    funcParam(3, 2, sum)
//    하지만 sum()과 funcParam의 매개변수 c의 인자수와 자료형의 개수가 동일하기 때문에
//    ::를 이름 앞에 사용해 소괄호와 인자를 생략하고 사용할 수 있다.
    val res1 = funcParam(3, 2, ::sum)
    println(res1)
    hello(::text)
    println()

    noParam({ "Hello World!" })
    noParam { "Hello World!" } // 소괄호를 생략할 수 있다.
    println()

    oneParam ({ a -> "Hello World! $a" })
    oneParam { a -> "Hello World! $a" }
    oneParam { "Hello World! $it" } // $it으로 대체가 가능하다.
    println()

    moreParam { a, b -> "Hello World! $a $b" } // 매개변수 이름 생략 불가
    moreParam { _, b -> "Hello World! $b" } // 특정 람다식의 매개변수를 사용하고 싶지 않을 때 언더스코어(_)로 대체할 수 있다.
    println()

    withArgs("Arg1", "Arg2", { a, b -> "Hello World! $a $b" })
//    함수의 마지막 인자가 람다식인 경우 소괄호 바깥으로 분리가 가능하다.
    withArgs("Arg1", "Arg2") { a, b -> "Hello World! $a $b" }
    println()

    twoLambda({ a, b -> "First $a $b"}, { "Second $it "})
    twoLambda({ a, b -> "First $a $b"}) { "Second $it "}
}