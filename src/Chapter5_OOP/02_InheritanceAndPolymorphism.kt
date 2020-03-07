package Chapter5_OOP

// 상속받는 하위 클래스는 상위 클래스의 생성자를 최소한 동일하게 정의하거나 확장할 수 있다.
class Lark(name: String, wing: Int, beak: String, color: String) : Bird(name, wing, beak, color) {
    fun singHighTone() = println("Happy Song!")
    override fun sing(vol: Int) {
//        super : 상위 클래스의 프로퍼티나 메소드, 생성자를 이용할 수 있다.
        super.sing(vol)
    }
}

class Parrot : Bird {
    val language: String

    constructor(
        name: String,
        wing: Int,
        beak: String,
        color: String,
        language: String
    ) : super(name, wing, beak, color) {
        this.language = language
    }

    override fun sing(vol: Int) {
        println("I'm a parrot! The volume level is $vol")
        speak()
    }
    fun speak() = println("Speak! $language")
}

open class Person {
    constructor(firstName: String) {
        println("[Person] firstName: $firstName")
    }
    constructor(firstName: String, age: Int) {
        println("[Person] firstName: $firstName, $age")
    }
}

class Developer : Person {
    constructor(firstName: String) : this(firstName, 10) {
        println("[Developer] $firstName")
    }
    constructor(firstName: String, age: Int) : super(firstName, age) {
        println("[Developer] $firstName, $age")
    }
}

// 주 생성자와 부 생성자가 함께 있다면 this를 사용해 주 생성자를 가리킬 수 있다.
open class PersonTwo(firstName: String, out: Unit = println("[Primary Constructor] Parameter")) {
    val fName = println("[Property] Person name : $firstName")

    init {
        println("[init] Person init block")
    }

    constructor(firstName: String, age: Int, out: Unit = println("[Secondary Constructor] Parameter")) : this(firstName) {
        println("[Secondary Constructor] Body: $firstName, $age")
    }
}

open class Base {
    open val x: Int = 1
    open fun f() = println("Base Class f()")
}

class Child : Base() {
    override val x: Int = super.x + 1
    override fun f() = println("Child class f()")

    inner class Inside {
        fun f() = println("Inside class f()")
        fun test() {
            f()
            Child().f()
            super@Child.f()
            println("[Inside] super@child.x = ${super@Child.x}")
        }

    }
}

open class A {
    open fun f() = println("A Class f()")
    fun a() = println("A class ()")
}

interface B {
    fun f() = println("B interface f()")
    fun b() = println("B interface b()")
}


class C : A(), B {
    override fun f() = print("C Class F()")
    fun test() {
        f()
        b()
        super<A>.f()
        super<B>.f()
    }
}
fun main() {
    val coco = Bird("myBird", 2, "short", "blue")
    val lark = Lark("myLark", 2, "Long", "brown")
    val parrot = Parrot("myParrot", 2,"short", "multiple", "korean")

    lark.singHighTone()
    parrot.speak()
    lark.fly()
    parrot.sing(5)

    val indong = Developer("Indong")
    println()

    val p1 = PersonTwo("Indong", 30)
    println()
    val p2 = PersonTwo("Kotlin")
    println()

    val c1 = Child()
    c1.Inside().test()
    println()

    val c = C()
    c.test()
}