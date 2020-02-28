package Chapter10

// DSL : 범용 언어와는 반대로, 특정 애플리케이션의 도메인을 위해 특화된 언어기 때문에 사용되는 목적에 따라 달라진다.
// 데이터베이스에 접근하기 위한 SQL이 대표적인 DSL
data class Person(var name: String? = null, var age: Int? = null, var job: Job? = null)

data class Job(var category: String? = null, var position: String? = null, var extension: Int? = null)

fun person(block: (Person) -> Unit): Person {
    val p = Person()
    block(p)
    return p
}

fun person2(block: Person.() -> Unit): Person {
    val p = Person()
    p.block()
    return p
}

fun person3(block: Person.() -> Unit): Person = Person().apply(block)

fun Person.job(block: Job.() -> Unit) {
    job = Job().apply(block)
}

fun main() {
    var person = person {
        it.name = "Indong"
        it.age = 30
    }

    var person2 = person2 {
        name = "Indong"
        age = 30
    }

    var finalPerson = person3 {
        name = "Indong"
        age = 30
        job {
            category = "IT"
            position = "Android Developer"
            extension = 1234
        }
    }
    println(finalPerson)
}