package Chapter7

// 추상 클래스, 주 생성자에는 비추상 프로퍼티 선언의 매개변수 3개가 있음
abstract class Vehicle(val name: String, val color: String, val weight: Double) {

    //    추상 프로퍼티 (반드시 하위 클래스에서 재정의해 초기화해야 함)
//    만일 클래스에서 추상 프로퍼티나 추상 메서드가 하나라도 있다면 해당 클래스는 추상 클래스가 되어야 한다.
    abstract var maxSpeed: Double

    //    일반 프로퍼티 (상태를 저장할 수 있음)
    var year = "2018"

    abstract fun start()
    abstract fun stop()

    fun displaySpec() {
        println("Name: $name, Color: $color, Weight: $weight, Year: $year, Max Speed: $maxSpeed")
    }
}

class Car(name: String, color: String, weight: Double, override var maxSpeed: Double) : Vehicle(name, color, weight) {
    override fun start() {
        println("Car Started")
    }

    override fun stop() {
        println("Car Stopped")
    }
}

class Motocycle(name: String, color: String, weight: Double, override var maxSpeed: Double) :
    Vehicle(name, color, weight) {
    override fun start() {
        println("Bike Started")
    }

    override fun stop() {
        println("Bike Stopped")
    }
}

abstract class Printer {
    abstract fun print()
}

val myPrinter = object: Printer() {
    override fun print() {
        println("출력합니다.")
    }
}

// 인터페이스에는 abstract로 정의된 추상 메서드나 일반 메서드가 포함된다.
// 다른 객체 지향 언어와는 다르게 메서드에 구현 내용이 포함될 수 있다.
// 하지만 추상 클래스처럼 프로퍼티를 통해 상태를 저장할 수 없다.
interface Pet {
//    인터페이스는 abstract가 없어도 기본이 추상 프로퍼티, 추상 메서드이다.
    var category: String
//    인터페이스에서는 프로퍼티에 값을 저장할 수 없으나 val로 선언된 프로퍼티에는 게터를 통해 필요한 내용을 구현할 수 있다.
    val msgTags: String
    get() = "I'm your lovely pet!"
    var species: String

    fun feeding()
//    일반 메서드 - 구현부를 포함하면 일반적인 메서드로 기본이 된다.
    fun patting() {
        println("Keep patting!")
    }
}

open class Animal(name: String)

class Cat(name: String, override var category: String) : Animal(name), Pet {
    override var species: String = "cat"
    override fun feeding() {
        println("Feed the cat a tuna can!")
    }
}

class Dog(name: String, override var category: String) : Animal(name), Pet {
    override var species: String = "dog"
    override fun feeding() {
        println("Feed the dog a bone")
    }
}

class Master {
    fun playWithMyPet(pet: Pet) {
        println("Enjoy with my ${pet.species}")
    }
}

interface A {
    fun functionA() {}
}

interface B {
    fun functionB() {}
}

// functionA와 functionB에 직접 접근하기 위해 A와 B 변수 사용
class C(val a: A, val b: B) {
    fun functionC() {
        a.functionA()
        b.functionB()
    }
}

// 각각 a와 b 인터페이스를 A와 B에 위임함으로써 해당 메서드를 사용할 때 점(.) 표기법 접근 없이 사용할 수 있게 된다.
class Delegated(a: A, b: B) : A by a, B by b {
    fun functionC() {
        functionA()
        functionB()
    }
}

interface Heater {
    fun on()
    fun off()
    fun isHot() : Boolean
}

class ElectricHeater(var heating: Boolean = false) : Heater {
    override fun on() {
        println("[ElectricHeater] heating...")
        heating = true
    }

    override fun off() {
        heating = false
    }

    override fun isHot(): Boolean = heating
}

interface Pump {
    fun pump()
}

class Thermosiphon(heater: Heater) : Pump, Heater by heater {
    override fun pump() {
        if (isHot()) {
            println("[Thermosiphon] pumping...")
        }
    }
}

interface CoffeeModule {
    fun getThermosiphon() : Thermosiphon
}

class MyDripCoffeeModule : CoffeeModule {
    companion object {
        val electricHeater : ElectricHeater by lazy {
            ElectricHeater()
        }
    }
    private val _thermosiphon : Thermosiphon by lazy {
        Thermosiphon(electricHeater)
    }

    override fun getThermosiphon(): Thermosiphon =_thermosiphon
}

class CoffeeMaker(val coffeeModule: CoffeeModule) {
    fun brew() {
        val theSiphon: Thermosiphon = coffeeModule.getThermosiphon()
        theSiphon.on()
        theSiphon.pump()
        println("Here is your coffee!")
        theSiphon.off()
    }
}

fun main() {
    val car = Car("SuperMatiz", "yellow", 1110.0, 270.0)
    val motor = Motocycle("DreamBike", "Black", 173.0, 100.0)

    car.year = "2013"

    car.displaySpec()
    car.start()
    motor.displaySpec()
    motor.start()
    println()

    myPrinter.print()
    println()

    val obj = Cat("navi","small")
    println("Pet Category: ${obj.category}")
//    val로 선언된 msgTags는 초기화할 수 없지만 게터를 통해 반환값을 지정할 수 있다.
    println("Pet Message Tags: ${obj.msgTags}")
    obj.feeding()
    obj.patting()
    println()

    val master = Master()
    val dog = Dog("Toto", "Small")
    val cat = Cat("Coco", "Small")
    master.playWithMyPet(cat)
    println()

    val coffeeMaker = CoffeeMaker(MyDripCoffeeModule())
    coffeeMaker.brew()
}