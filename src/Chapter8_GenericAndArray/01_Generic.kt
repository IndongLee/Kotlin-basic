package Chapter8_GenericAndArray

class Box<T>(t: T) {
    var name = t
}

// 제네릭 클래스 : 형식 매개변수를 1개 이상 받는 클래스. 클래스를 선언할 때 자료형을 특정하지 않고 인스턴스를 생성하는 시점에서 자료형을 결정한다.
class MyClass<T> {
//    만일 형식 매개변수를 클래스의 프로퍼티에 사용하는 경우 클래스 내부에서는 사용할 수 없다.
//    자료형이 특정되지 못하므로 인스턴스를 생성할 수 없기 때문이다.
//    var myProp: T // 프로퍼티는 초기화되거나 abstract로 선언되어야 한다.
    fun myMethod(a: T) {
        println(a)
    }
}

// 다음과 같이 주 생성자나 부 생성자에 형식 매개변수를 지정해 사용할 수 있다.
class MyClass2<T>(val myProp: T) {}

class MyClass3<T> {
    val myProp: T
    constructor(myProp: T) {
        this.myProp = myProp
    }
}

// 일반적으로 상위 클래스와 하위 클래스의 선언 형태에 따라 클래스의 자료형을 변환할 수 있지만 제네릭 클래스는 가변성을 지정하지 않으면 형식 매개변수에
// 상하위 클래스가 지정되어도 서로 자료형이 반환되지 않는다.
open class Parent

class Child : Parent()

class Cup<T>

// 형식 매개변수 T는 기본적으로 null이 허용된다.
class GenericNull<T> {
    fun equalityFunc(arg1: T, arg2: T) {
        println(arg1?.equals(arg2))
    }
}

// 형식 매개변수에 특정 자료형을 지정하면 null을 허용하지 않게 된다.
class GenericNotNull<T: Any> {

}

// 제네릭 함수 또는 메서드 : 형식 매개변수를 받는 함수나 메서드. 해당 함수나 메서드 앞쪽에 <T>와 같이 형식 매개변수를 지정한다.
fun <T> find(a: Array<T>, Target: T): Int {
//    indices는 배열의 유효 범위를 반환한다.
    for (i in a.indices) {
        if (a[i] == Target) return i
    }
    return -1
}

// 형식 매개변수로 선언된 함수의 매개변수를 연산할 경우에는 자료형을 결정할 수 없기 때문에 오류가 발생한다.
fun <T> add(a: T, b: T): T {
//    return a + b // 자료형을 결정할 수 없어 오류가 발생
    return a
}

// 람다식을 매개변수로 받으면 자료형을 결정하지 않아도 실행 시 람다식 본문을 넘겨줄 때 결정되므로 문제를 해결할 수 있다.
fun <T> add2(a: T, b: T, op: (T, T) -> T): T {
    return op(a, b)
}

// 여러 개의 조건에 맞춰 형식 매개변수의 자료형을 제한할 때는 형식 매개변수의 사용 범위를 지정하는 where 키워드를 사용할 수 있다.
interface InterfaceA
interface InterfaceB

class HandlerA: InterfaceA, InterfaceB
class HandlerB: InterfaceA

class ClassA<T> where T: InterfaceA, T: InterfaceB // 2개의 인터페이스를 구현하는 클래스로 제한한다.

fun <T> myMax(a: T, b: T): T where T: Number, T: Comparable<T> {
    return if (a > b) a else b
}

fun main() {
    val box1: Box<Int> = Box<Int>(1)
    val box2: Box<String> = Box<String>("Hello")
//    객체 생성 시 생성자에서 유추될 수 있는 자료형이 있다면 선언된 자료형인 <String>이나 <Int>는 생략 가능하다.
    val box3 = Box(10)
    val box4 = Box("Kotlin")
    println(box1.name)
    println(box2.name)
    println(box3.name)
    println(box4.name)
    println()

    var a = MyClass2<Int>(12)
    println(a.myProp)
    println(a.javaClass)
    println()

    val obj1: Parent = Child() // Parent 형식의 obj1은 Child의 자료형으로 변환될 수 있음.
//    val obj2: Child = Parent() // 자료형 불일치로 오류가 일어난다.

//    val obj3: Cup<Parent> = Cup<Child>() // 제네릭 클래스에서는 형식 매개변수인 T에 상위와 하위 클래스를 지정하더라도 서로 관련이 없어 오류가 발생한다.
//    val obj4: Cup<Child> = Cup<Parent>()

    val obj5 = Cup<Child>() // obj5는 Cup<Child>의 자료형이 된다.
    val obj6: Cup<Child> = obj5 // 자료형이 일치하므로 OK

    val obj = GenericNull<String>() // non-null로 선언됨.
    obj.equalityFunc("Hello", "World") // null이 허용되지 않음.

    val secondObj = GenericNull<Int?>() // null이 가능한 형식으로 선언됨.
    secondObj.equalityFunc(null, 10) // null이 사용 가능함.
    println()

    val arr1: Array<String> = arrayOf("Apple", "Pineapple", "Banana", "Kiwi")
    val arr2: Array<Int> = arrayOf(1, 2, 3, 4)

    println("arr.indices ${arr1.indices}")
    println(find<String>(arr1, "Kiwi"))
    println(find(arr2, 3)) // 선언에서 <Int>처럼 자료형이 특정되어 있는 경우에는 생략할 수 있다.
    println()

    val result = add2(10, 20, {a, b -> a + b})
    var sumInt = { a: Int, b: Int -> a + b }
    println(result)
    println(add2(2, 3, sumInt))
    println()

    val whereObj = ClassA<HandlerA>()
//    val whereObj2 = ClassA<HandlerB>() // 범위에 없으므로 오류가 발생한다.
}