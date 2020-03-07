package Chapter8_GenericAndArray

// 가변성(Variance)이란 형식 매개변수가 클래스 계층에 영향을 주는 것을 말한다.
// 예를 들어 형식 A의 값이 필요한 모든 클래스에 형식 B의 값을 넣어도 아무 문제가 없다면 B는 A의 하위 형식(Subtype)이 된다.
// 기본적으로 제네릭에서는 클래스 간에 상위와 하위의 개념이 없어 서로 무관하다. 따라서 상위와 하위에 따른 형식을 주려면 가변성의 3가지 특징을 이해해야 한다.
// 가변성의 3가지 유형
// 1) 공변성(Covariance) : T'가 T의 하위 자료형이면, C<T'>는 C<T>의 하위 자료형이다. 생산자 입장의 out 성질.
// 2) 반공변성(Contravariance) : T'가 T의 하위 자료형이면, C<T>는 C<T'>의 하위 자료형이다. 소비자 입장의 in 성질.
// 3) 무변성(Invariance) : C<T>와 C<T'>는 아무 관계가 없다. 생산자 + 소비자

// 무변성 선언
class BoxInva<T>(val size: Int)

// 공변성 선언 : 형식 매개변수의 상하 자료형 관계가 일치하고, 그 관계가 그대로 인스턴스 자료형 관계로 이어지는 경우
class BoxCova<out T>(val size: Int)

// 반공변성 선언 : 자료의 상하 관계가 반대가 되어 인스턴스의 자료형이 상위 자료형이 된다.
class BoxContrava<in T>(val size: Int)

open class Animal(val size: Int) {
    fun feed() = println("Feeding...")
}

class Cat(val jump: Int) : Animal(50)
class Spider(val poison: Boolean) : Animal(1)

// 형식 매개변수를 Animal로 제한
class AnimalBox<out T: Animal>(val element: T) { // out을 사용하는 경우에 형식 매개변수를 갖는 프로퍼티는 var로 지정될 수 없고 val만 허용한다.
    fun getAnimal(): T = element // out은 반환자료형에만 사용할 수 있다.
//    fun set(new: T) { // T는 in 위치에 사용될 수 없다.
//        element = new
//    }
}

fun main() {
//    val anys: BoxInva<Any> = BoxInva<Int>(10) // 자료형이 불일치해 오류가 발생
//    val nothings: BoxInva<Nothing> = BoxInva<Int>(10)

    val anys: BoxCova<Any> = BoxCova<Int>(10)
//    val nothings: BoxCova<Nothing> = BoxCova<Int>(10)

//    val anys: BoxContrava<Any> = BoxContrava<Int>(10)
    val nothings: BoxContrava<Nothing> = BoxContrava<Int>(10)

    val c1: Cat = Cat(10)
    val s1: Spider = Spider(true)

    var a1: Animal = c1 // 클래스의 상위 자료형으로 변하는 것은 문제가 없다.
    a1 = c1 // a1은 Spider의 객체가 된다.
    println("a1.size = ${a1.size}")

    val c2: AnimalBox<Animal> = AnimalBox<Cat>(Cat(10)) // 공변성 - Cat은 Animal의 하위 자료형
    println("c2.element.size = ${c2.element.size}")

//    val c3: AnimalBox<Cat> = AnimalBox<Animal>(10) // 반대의 경우에는 인스턴스화되지 않아 오류가 발생한다.
//    val c4: AnimalBox<Any> = AnimalBox<Int>(10) // 자료형을 제한하여 Animal과 하위 클래스 이외에는 사용할 수 없다.
}