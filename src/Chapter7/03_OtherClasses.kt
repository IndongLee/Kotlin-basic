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

interface Switcher {
    fun on(): String
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
        val powerStatus = Led("Red") // 여기서 지역 클래스가 사용됨
        val powerSwitch = object : Switcher { // 익명 객체를 사용해 Switcher의 on()을 구현
            override fun on(): String {
                return powerStatus.blink()
            }
        } // 익명 객체의 블록 끝
        return powerSwitch.on() // 익명 객체의 메서드 사용
    }
}

// 실드 클래스 (Sealed Class) : 미리 만들어 놓은 자료형들을 묶어서 제공하는 클래스. 실드 클래스 그 자체는 추상 클래스와 같기 때문에 객체를 만들 수 없다.
// 또한 생성자도 기본적으로 private이며 private이 아닌 생성자는 허용하지 않는다.
// 실드 클래스는 같은 파일 안에서는 상속이 가능하지만, 다른 파일에서는 상속이 불가능하게 제한된다.
// 블록 안에 선언되는 클래스는 상속이 필요한 경우 open 키워드로 선언될 수 있다.
// 실드 클래스를 선언하는 첫 번째 방법
sealed class Result {
    open class Success(val message: String) : Result()
    class Error(val code: Int, val message: String) : Result()
}

class Status : Result() // 실드 클래스 상속은 같은 파일에서만 가능
class Inside : Result.Success("Status") // 내부 클래스 상속

// 실드 클래스를 선언하는 두 번째 방법
sealed class Result2

open class Success2(val message: String) : Result2()
class Error2(val code: Int, val message: String) : Result2()

class Status2: Result2()
class Inside2: Success2("Status")

// 열거형 클래스 (Enum Class) : 여러 개의 상수를 선언하고 열거된 값을 조건에 따라 선택할 수 있는 특수한 클래스
// 실드 클래스와 비슷하나 실드 클래스처럼 다양한 자료형을 다루지는 못한다.
// 메서드를 포함할 경우 세미콜론을 사용해 열거한 상수 객체를 구분한다.
enum class DayOfWeek(val num: Int) {
    MONDAY(1), TUESDAY(2), WEDNESDAY(3), THURSDAY(4),
    FRIDAY(5), SATURDAY(6), SUNDAY(7); // 세미콜론으로 끝을 알림

//    메서드를 포함할 수 있음
    fun weekend() {
        when(num) {
            1, 2, 3, 4, 5 -> println("Weekday")
            6, 7 -> println("Weekend!")
        }
    }
}

// 열거형 클래스에서 인터페이스의 메서드를 구현할 수도 있다.
interface Score {
    fun getScore(): Int
}

enum class MemberTier(val prio: String) : Score {
    BRONZE("Third") {
        override fun getScore(): Int = 100
    },
    SILVER("Second") {
        override fun getScore(): Int = 500
    },
    GOLD("first") {
        override fun getScore(): Int = 1500
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
    val mySmartphone = Smartphone("Note10")
    println(mySdcard.getInfo())
    println(mySmartphone.powerOn())
    println()

    val result = Result.Success("Good!")
    val msg = eval(result)
    println()

    val day = DayOfWeek.SATURDAY
    DayOfWeek.SATURDAY.weekend()
    println()

    println(MemberTier.BRONZE.getScore())
    println(MemberTier.GOLD)
    println(MemberTier.valueOf("SILVER"))
    println(MemberTier.SILVER.prio)
}

// 실드 클래스는 특정 객체 자료형에 따라 when문과 is에 의해 선택적으로 실행할 수 있다. 모든 경우가 열거되었으므로 else가 필요가 없게 된다.
// 실드 클래스를 사용하면 필요한 경우의 수를 직접 지정할 수 있다.
fun eval(result: Result): String = when(result) {
    is Status -> "in progress"
    is Result.Success -> result.message
    is Result.Error -> result.message
}