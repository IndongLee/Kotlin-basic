package Chapter7

// 데이터 클래스 : 데이터 저장만을 위해 사용되는 클래스
// 다음과 같은 메서드가 자동으로 생성된다.
// 1) 프로퍼티를 위한 게터 / 세터
// 2) 비교를 위한 equals()와 키 사용을 위한 hashCode()
// 3) 프로퍼티를 문자열로 변환해 순서대로 보여주는 toString()
// 4) 객체 복사를 위한 Copy()
// 5) 프로퍼티에 상응하는 component1(), component2() 등

// 데이터 클래스는 다음 조건을 만족해야 한다.
// 1) 주 생성자는 최소한 하나의 매개변수를 가진다.
// 2) 주 생성자의 모든 매개변수는 val, var로 지정된 프로퍼티여야 한다.
// 3) 데이터 클래스는 abstract, open, sealed, inner 키워드를 사용할 수 없다.
data class Customer(var name: String, var email: String) {
    var job: String = "Unknown"
    constructor(name: String, email: String, _job: String): this(name, email) {
        job = _job
    }
    init {

    }
}



fun main() {
    val cus1 = Customer("Indong", "kundera@kakao.com")
    val cus2 = Customer("Indong", "kundera@kakao.com")

    println(cus1 == cus2)
    println(cus1.equals(cus2))
    println("${cus1.hashCode()}, ${cus2.hashCode()}")

    val cus3 = cus1.copy(name = "Alice") // name만 변경하도록 할 때
    println(cus1.toString())
    println(cus3.toString())

//    디스트럭쳐링 : 객체가 가지고 있는 프로퍼티를 개별 변수로 분해하여 할당하는 것
    val (name, email) = cus1
    println("name = $name, email = $email")
//    특정 프로퍼티를 가져올 필요가 없는 경우 _(언더스코어)를 사용해 제외할 수 있다.
    val (_, email2) = cus1
    println("name = $name, email = $email2")

//    개별적으로 프로퍼티를 가져오기 위해 componentN() 메서드를 이용할 수 있다.
    val name3 = cus1.component1()
    val email3 = cus1.component2()
    println("name = $name3, email = $email3")
    println()

    val cus4 = Customer("Henderson", "liverpool@naver.com")
    val cus5 = Customer("Firmimo", "Anfield@kakao.com")

    val customers = listOf(cus1, cus2, cus3, cus4, cus5)
    for ((name, email) in customers) {
        println("name = $name, email = $email")
    }
    println()

//    함수로부터 객체가 반환될 때도 사용할 수 있다.
    fun myFunc(): Customer {
        return Customer("Indong", "asghost@naver.com")
    }
    val (myName, myEmail) = myFunc()
    println("name = $myName, email = $myEmail")

//    람다식으로 디스트럭쳐링된 변수 출력하기
    val myLambda = {
        (nameLa, emailLa): Customer ->
        println(nameLa)
        println(emailLa)
    }
    myLambda(cus1)
}
