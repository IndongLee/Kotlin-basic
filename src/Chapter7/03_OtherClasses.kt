package Chapter7

// 내부 클래스의 종류
// 1) 중첩 클래스 (Nested Class) : 외부 클래스를 인스턴스화하지 않고 바로 사용 가능한 내부 클래스
// 2) 이너 클래스 (Inner Class) : 필드나 메서드와 연동하는 내부 클래스
// 3) 지역 클래스 (Local Class) : 클래스의 선언이 블록 안에 있는 지역 클래스
// 4) 익명 객체 (Anonymous Object) : 이름이 없고 주로 일회용 객체를 사용하기 위해 object 키워드를 통해 선언된다.
class A {
//    중첩 클래스 - 코틀린에서는 아무 키워드가 없는 클래스는 중첩 클래스이며(자바에서는 이너 클래스) 정적 클래스처럼 사용된다.
    class B {
//    외부 클래스 A의 프로퍼티, 메서드에 접근할 수 없다.
    }

    inner class C {
//    외부 클래스 A의 필드에 접근 가능하다.
    }
}

class Outer {
    val ov = 5
    companion object {
        const val country = "Korea"
        fun getSomething() = println("Get Something...")
    }
    class Nested {
        val nv = 10
        fun greeting() = "[Nested] Hello ! $nv" // 외부의 ov에는 접근 불가
        fun accessOuter() { // 컴패니언 객체는 접근할 수 있다.
            println(country)
            getSomething()
        }
    }
    fun outside() {
        val msg = Nested().greeting() // 객체 생성 없이 중첩 클래스의 메서드 접근
        println("[Outer]: $msg, ${Nested().nv}")
    }
}

class Smartphone(val model: String) {
    private val cpu = "Exynos"

    inner class ExternalStorage(val size: Int) {
        fun getInfo() = " ${model}: Installed on $cpu with ${size}Gb" // 바깥 클래스의 프로퍼티에 접근할 수 있다.
    }

    fun powerOn(): String {
        class Led(val color: String) {
            fun blink(): String = "Blinking $color on $model"
        }
        val powerStatus = Led("Red")
        return powerStatus.blink()
    }
}


fun main() {
    val output = Outer.Nested().greeting()
    println(output)

//    Outer.outside() -> 외부 클래스의 경우는 객체를 생성해야 한다.
    val outer = Outer()
    outer.outside()
    println()

    val mySdcard = Smartphone("S10").ExternalStorage(128)
    println(mySdcard.getInfo())
    println()
}