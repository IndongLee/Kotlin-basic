package Chapter5

open class Car protected constructor(_year: Int, _model: String, _power: String, _wheel: String) {
    private var year: Int = _year
    var model: String = _model
    protected open var power = _power
    internal var wheel = _wheel

    protected fun start(key: Boolean) {
        if (key) println("Start the Engine!")
    }

    class Driver(_name: String, _licence: String) {
        private var name = _name
        var licence = _licence

        internal fun driving() = println("[Driver] Driving() - $name")
    }
}

class Tico(_year: Int, _model: String, _power: String, _wheel: String, var name: String, private var key: Boolean)
    : Car(_year, _model, _power, _wheel) {
    override var power: String = "50hp"
    val driver = Driver(name, "first class")

    constructor(_name: String, _key: Boolean) : this(2014, "basic", "100hp", "normal", _name, _key) {
        name = _name
        key = _key
    }

    fun access(password: String) {
        if (password == "gotico") {
            println("----[Tico] access()----")
            // super.year // private 접근 불가
            println("super.model = ${super.model}")
            println("super.power = ${super.power}")
            println("super.wheel = ${super.wheel}")
            super.start(key)

            // driver.name // private 접근 불가
            println("Driver().licence = ${driver.licence}")
            driver.driving()
        } else {
            println("You're a burglar")
        }
    }
}

class Burglar() {
    fun steal(anyCar: Any) {
        if (anyCar is Tico) {
            println("----[Burglar] steal()----")
            // println(anyCar.year) // private 접근 불가
            // println(anyCar.power) // protected 접근 불가
            println("anyCar.name = ${anyCar.name}")
            println("anyCar.wheel = ${anyCar.wheel}")
            println("anyCar.model = ${anyCar.model}")

            println(anyCar.driver.licence)
            anyCar.driver.driving()
            // println(Car.start()) // protected 접근 불가
            anyCar.access("dontknow")
        } else {
            println("Nothing to steal")
        }
    }
}

fun main() {
    // val car = Car() // protected 생성 불가
    val tico = Tico("Kildong", true)
    tico.access("gotico")

    val burglar = Burglar()
    burglar.steal(tico)
}