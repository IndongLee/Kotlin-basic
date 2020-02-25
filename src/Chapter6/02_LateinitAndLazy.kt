package Chapter6

import kotlin.properties.Delegates

// 특정 객체의 의존성이 있는 경우에 지연 초기화를 해야 할 필요가 있다. 또한 해당 자료형의 프로퍼티를 즉시 사용하지
// 않는데도 미리 생성해서 초기화를 한다면 메모리가 사용되어 낭비될 수도 있다.

class Person {
    //    지연 초기화를 위해 lateinit을 선언한다. 단 실행할 때까지 비어 있는 상태면 오류를 유발할 수 있다.
//    lateinit의 제한 : 1) var로 선언된 프로퍼티만 가능하다. 2) 프로퍼티에 대한 getter와 setter를 사용할 수 없다.
    lateinit var name: String

    fun test() {
//        프로퍼티 참조를 위해 ::를 사용한다.
        if (!::name.isInitialized) {
            println("not initialized")
        } else {
            println("initialized")
        }
    }
}

data class Person2(var name: String, var age: Int)

lateinit var person: Person2 // 객체 생성의 지연 초기화

// lazy를 활용한 지연 초기화
//  1) 호출 시점에 by lazy 정의에 의해 블록 부분의 초기화를 진행한다.
//  2) val에서만 사용 가능하다(읽기 전용).
//  3) val이므로 값을 다시 변경할 수 없다.
class LazyTest {
    init {
        println("init block")
    }

    val subject by lazy {
        println("lazy initialized")
        "Kotlin Programming" // lazy 반환값
    }

    fun flow() {
        println("not initialized")
        println("subject one: $subject")
        println("subject two: $subject")
    }
}

// by를 통한 위임 : 하나의 클래스가 다른 클래스에 위임하도록 선언하여 위임된 클래스가 가지는 멤버를 참조 없이 호출할 수 있게 된다.
// 프로퍼티 위임 : 프로퍼티의 게터와 세터를 특정 객체에 위임하고 그 객체가 값을 읽거나 쓸 때 수행하도록 만드는 것.
interface Car {
    fun go(): String
}

class VanImpl(val power: String) : Car {
    override fun go() = "은 짐을 적재하며 ${power}을 가집니다."
}

class SportImpl(val power: String) : Car {
    override fun go() = "은 경주에 사용되며 ${power}을 가집니다."
}

// 클래스 위임
class CarModel(val model: String, impl: Car) : Car by impl {
    fun carInfo() {
        println("$model ${go()}") // 참조 없이 각 인터페이스 구현 클래스의 go()에 접근
    }
}

// observable : 프로퍼티를 감시하고 있다가 특정 코드의 로직에서 변경이 일어날 때 호출되어 처리된다. 콜백과 비슷한 개념
// vetoable : 반환값에 따라 프로퍼티 변경을 허용 또는 취소할 수 있다.
// 두 위임을 생성하기 위해서는 매개변수에 기본값을 지정해야 한다.
class MyUser {
    var name: String by Delegates.observable("NONAME") { // MyUser 클래스의 name 프로퍼티를 observable() 함수로 위임
        prop, old, new -> // 람다식 매개변수로 프로퍼티, 기존 값, 새로운 값 지정
        println("$old -> $new") // 이벤트가 발생할 때만 실행
    }
}

fun main() {
    val indong = Person()
    indong.test()
    indong.name = "Indong" // 이 시점에서 초기화가 된다.
    indong.test()
    println("name = ${indong.name}")
    println()

    person = Person2("Kotlin", 30) // 생성자 호출 시점에서 초기화 된다.
    println(person.name + " is " + person.age.toString())
    println()

    val test = LazyTest()
    test.flow()
    println()

    var isPersonInstantiated: Boolean = false

//    lazy를 사용한 person 객체의 지연 초기화
    val person: Person2 by lazy {
        isPersonInstantiated = true
        Person2("Kim", 23) // lazy 객체로 반환되는 부분
    }
//    위임 변수를 사용한 초기화
    val personDelegate = lazy { Person2("Hong", 40) }

    println("person init : $isPersonInstantiated")
    println("personDelegate init : ${personDelegate.isInitialized()}")

//    이 시점에서 초기화
    println("person.name: = ${person.name}")
    println("personDelegate.value.name = ${personDelegate.value.name}")

    println("person init : $isPersonInstantiated")
    println("personDelegate init : ${personDelegate.isInitialized()}")
    println()

    val myDamas = CarModel("Damas 2010", VanImpl("100마력"))
    val my350z = CarModel("350Z 2008", SportImpl("350마력"))
    myDamas.carInfo()
    my350z.carInfo()
    println()

    var max: Int by Delegates.vetoable(0) { // 기본값을 0으로 설정
        prop, old, new ->
        new > old // 조건에 맞지 않는다면 거부권을 행사한다.
    }

    println(max)
    max = 10
    println(max)
    max = 5
    println(max) // 기존값이 새 값보다 크므로 false, 5를 재할당하지 않는다.
}