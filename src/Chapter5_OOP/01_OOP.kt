package Chapter5_OOP

// 상속을 허용한다.
open class Bird constructor(_name: String, _wing: Int, _beak: String, _color: String) {
    var name: String = _name
    var wing: Int = _wing
    var beak: String = _beak
    var color: String = _color

    fun fly() = println("Fly wing: $wing")
//    오버라이드를 허용한다.
    open fun sing(vol: Int) = println("Sing vol: $vol")
}

class BirdTwo(_name: String, _wing: Int, _beak: String, _color: String) {
    var name: String = _name
    var wing: Int = _wing
    var beak: String = _beak
    var color: String = _color
}

open class BirdThree(var name: String, var wing: Int, var beak: String, var color: String) {
    init {
        println("초기화 블록 시작")
        println("이름은 $name, 부리는 $beak")
        println("초기화 블록 끝")
    }

    fun fly() = println("Fly wing: $wing")
    fun sing(vol: Int) = println("Sing vol: $vol")
}

fun main() {
    val coco = BirdThree("Coco", 2, "short", "blue")

    coco.color = "yellow"
    println("coco.color: ${coco.color}")
    coco.fly()
}

