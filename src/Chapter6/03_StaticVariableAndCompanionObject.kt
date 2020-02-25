package Chapter6

class NewPerson {
    var id: Int = 0
    var name: String = "Indong"
    companion object {
        var language: String = "Korean"
        fun work() {
            println("working...")
        }
    }
}

// 자바에서 코틀린 파일에 접근하게 한다.
class KCustomer {
    companion object {
        const val LEVEL = "INTERMEDIATE"
        @JvmStatic fun login() = println("Login ...")
        @JvmField val JOB = KJob()
    }
}

class KJob {
    var title: String = "Programmer"
}

object OCustomer {
    var name = "Indong"
    fun greeting() = println("Hello World!")
    val hobby = "basketball"
    init {
        println("Init!")
    }
}

open class Superman() {
    fun work() = println("Taking photos")
    fun talk() = println("Talking with people")
    open fun fly() = println("Flying in the air")
}

fun main() {
    println(NewPerson.language)
    NewPerson.language = "English"
    println(NewPerson.language)
    NewPerson.work()
    println()

    OCustomer.greeting()
    OCustomer.name = "Dooly"
    println("name = ${OCustomer.name}")
    println(OCustomer.hobby)
    println()

//    object 표현식은 object 선언과 달리 이름이 없으며 싱글톤이 아니다. 따라서 object 표현식이 사용될 때마다 새로운 인스턴스가 생성된다.
//    결과적으로 이름이 없는 익명 내부 클래스로 불리는 형태를 object 표현식으로 만들 수 있다.
    val pretendedMan = object : Superman() {
        override fun fly() {
            println("I'm not a real superman. I can'y fly")
        }
    }
    pretendedMan.work()
    pretendedMan.talk()
    pretendedMan.fly()
}