package Chapter10_StandardFunctionAndIO

class Calc {
    fun addNum(a: Int, b: Int, add: (Int, Int) -> Unit): Unit {
        add(a, b)
    }
}

fun main() {
//    클로저(Closure) : 람다식으로 표현된 내부 함수에서 외부 범위에 선언된 변수에 접근할 수 있는 개념을 말한다.
//    이때 람다식 안에 있는 외부 변수는 값을 유지하기 위해 람다식이 포획(Capture)한 변수라고 부른다.
//    기본적으로 함수 안에 정의된 변수는 지역 변수로 스택에 저장되어 있다가 함수가 끝나면 같이 사라진다.
//    하지만 클로저 개념에서 포획한 변수는 참조가 유지되어 함수가 종료되어도 사라지지 않고 함수의 변수에 접근하거나 수정할 수 있게 해준다.
//    클로저의 조건
//    1) final 변수를 포획한 경우 변수 값을 람다식과 함께 저장한다.
//    2) final이 아닌 변수를 포획한 경우 변수를 특정 래퍼(wrapper)로 감싸서 나중에 변경하거나 읽을 수 있게 한다.
//       이때 래퍼에 대한 참조를 람다식과 함께 저장한다.
    val calc = Calc()
    var result = 0 // 외부의 변수
    calc.addNum(2, 3) { x, y -> result = x + y } // 클로저
    println(result) // 값을 유지하며 5 출력

    fun filteredNames(length: Int) {
        val names = arrayListOf<String>("Kotlin", "Java", "Python", "C++", "Javascript")
        val filterResult = names.filter {
            it.length == length // 바깥의 변수 length에 접근
        }
        println(filterResult)
    }
    filteredNames(6)

//    확장 함수의 람다식 접근 방법
//    함수 이름 / 람다식 접근 방법 / 변환 방법
//    T.let / it / block 결과
//    T.also / it / T caller (it)
//    T.apply / this / T caller (this)
//    T.run 또는 run / this / block 결과
//    with / this / Unit

//    let : 함수를 호출하는 객체 T를 이어지는 block에 넘기고 block의 결괏값을 반환한다.
    val score: Int? = 32

    //    일반적인 null 검사
    fun checkScore() {
        if (score != null) {
            println("Score: $score")
        }
    }

    //    let을 사용한 null 검사
    fun checkScoreLet() {
        score?.let { println("Score: $it") }
        var str = score.let { it.toString() }
        println(str)
    }
    checkScore()
    checkScoreLet()

//    메서드 체이닝을 사용할 때 let() 함수 활용하기
    var a = 1
    var b = 2
    a = a.let { it + 2 }.let {
        val i = it + b
        i // 마지막 식이 반환된다.
    }
    println(a)
    println()

//    also : 함수를 호출하는 객체 T를 이어지는 block에 전달하고 객체 T 자체를 반환한다.
    var m = 1
    m = m.also { it + 3 }
    println(m) // 블록 안의 코드 수행 결과와 상관없이 T인 객체 this를 반환하게 된다.

    data class Person(var name: String, var skills: String)
    var person = Person("Indong" ,"Kotlin")
    val resLet = person.let {
        it.skills = "Android"
        "success" // 마지막 문장을 결과로 반환
    }
    println(person)
    println("resLet: $resLet")
    val resAlso = person.also {
        it.skills = "Java"
        "success" // 마지막 문장은 사용되지 않음
    }
    println(person)
    println("resAlso: $resAlso")
    println()

//    apply : also 함수와 마찬가지로 호출하는 객체 T를 이어지는 block으로 전달하고 객체 자체인 this를 반환한다.
//    apply 함수는 특정 객체를 생성하면서 함께 호출해야 하는 초기화 코드가 있는 경우 사용할 수 있다.
//    apply 함수와 also 함수의 차이점은 T.()와 같은 표현에서 람다식이 확장함수로 처리된다는 것이다.
    person.apply { this.skills = "Python" } // 여기서 this는 person 객체를 가리킴
    println(person)

//    apply는 확장 함수로서 person을 this로 받아오는데 클로저를 사용하는 방식과 같다.
//    따라서 객체의 프로퍼티를 변경하면 원본 객체에 반영되고 또한 이 객체는 this로 반환된다.
//    also 함수에서는 it을 사용해 멤버에 접근한다. it은 생략할 수 없지만 apply 함수에서는 this를 생략할 수 있다.
    val returnObj = person.apply {
        name = "Indong" // this는 생략할 수 있음
        skills = "Vue.js" // this 없이 객체의 멤버에 여러 번 접근
    }
    println(person)
    println(returnObj)
    println()

//    run : run 함수는 인자가 없는 익명 함수처럼 동작하는 형태와 객체에서 호출하는 형태 2가지로 사용할 수 있다.
//    이어지는 block 내에서 처리할 작업을 넣어줄 수 있으며, 일반 함수와 마찬가지로 값을 반환하지 않거나 특정 값을 반환할 수도 있다.
    var skills = "Kotlin"
    println(skills)

    val num = 10
    skills = run {
        val level = "Kotlin Level: " + a
        level // 마지막 표현식이 반환됨
    }
    println(skills)

    val returnObj2 = person.run {
        this.name = "Klopp"
        this.skills = "Manage"
        "success" // apply 함수는 this에 해당하는 객체를 반환한 반면에, run 함수는 마지막 표현식인 "success"를 반환한다.
    }
    println(person)
    println("returnObj2: $returnObj2")
    println()

//    with : 인자로 받는 객체를 이어지는 block의 receiver로 전달하며 결괏값을 반환한다.
//    with 함수는 run 함수와 거의 동일한데, run 함수의 경우 receiver가 없지만 with 함수에서는 receiver로 전달할 객체를 처리하므로 객체의 위치가 달라진다.
//    with 함수는 확장 함수 형태가 아니고 단독으로 사용되는 함수이다. 또한 세이프콜을 지원하지 않기 때문에 let 함수와 같이 사용되기도 한다.
    data class User(val name: String, var skills: String, var email: String? = null)
    val user: User = User("Indong", "default")

    val resWith = with (user) {
        skills = "Kotlin"
        email = "kundera@kakao.com"
        "success" // 기본적으로 Unit이 반환되지만 필요한 경우 마지막 표현식을 반환할 수 있다.
    }
    println(user)
    println("resWith: $resWith")
    println()

//    use : 보통 특정 객체가 사용된 후 닫아야 하는 경우가 생기는데 이때 use() 함수를 사용하면 객체를 사용한 후 close() 함수를 자동적으로 호출해 닫아줄 수 있다.

//    takeIf : 람다식이 true면 결과를 반환한다.
//    takeUnless : 람다식이 false면 결과를 반환한다.
    val input = "Kotlin"
    val keyword = "in"

    input.indexOf(keyword).takeIf { it >= 0 } ?: error("keyword not found")
    input.indexOf(keyword).takeUnless { it >= 0 } ?: error("keyword not found")
}