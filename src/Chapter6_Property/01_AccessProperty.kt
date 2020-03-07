package Chapter6_Property

class User(val id: Int, var name: String, var age: Int) {}

class User2(_id: Int, _name: String, _age: Int) {
//    field : 프로퍼티를 참조하는 변수. 보조 필드라고도 한다.
    val id: Int = _id
        get() = field

    var name: String = _name
        get() = field
        set(value) {
            println("The name is changed")
            field = value.toUpperCase()
        }

    var age: Int = _age
        get() = field
        private set(value) {
            field = value
        }
}

open class First {
//    기본적으로 프로퍼티는 오버라이딩 할 수 없는 final 형태로 선언됨
    open val x: Int = 0
    get() {
        println("First x")
        return field
    }
    val y: Int = 0
}

class Second : First() {
    override val x : Int = 0
    get() {
        println("Second x")
        return field + 3
    }
    // overide val y: Int = 0 // open 선언하지 않았기 때문에 오버라이드 불가
}

fun main() {
    val user = User(1, "Indong", 30)
    val name = user.name
    user.age = 28
    println("name: $name, ${user.age}")

    val user2 = User2(2, "Indong", 30)
//    user2.age = 20 // 외부에서 private set 금지
    println("name: ${user2.name}, ${user2.age}")

    val second = Second()
    println(second.x)
    println(second.y)
}