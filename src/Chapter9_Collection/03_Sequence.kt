package Chapter9_Collection

// 시퀀스(Sequence) : 코틀린의 시퀀스는 순차적인 컬렉션으로 요소의 크기를 특정하지 않고, 나중에 결정할 수 있는 특수한 컬렉션이다.
// 시퀀스 처리 중에는 계산하고 있지 않다가 toList()나 count()와 같은 최종 연산에 의해 결정된다.
// 단, 작은 컬렉션에는 시퀀스를 사용하지 않는 것이 좋다.
// 왜냐하면 filter() 등은 인라인 함수로 설계되어 있는데 시퀀스를 사용하면 람다식을 저장하는 객체로 표현되기 때문에 인라인되지 않아 작은 컬렉션에는 오히려 좋지 않다.
// 또한 한 번 계산된 내용은 메모리에 저장되기 때문에 시퀀스 자체를 인자로 넘기는 형태는 사용하지 않는 것이 좋다.

fun main() {
//    시드 값 1을 시작으로 1씩 증가하는 시퀀스 정의
    val nums: Sequence<Int> = generateSequence(1) { it + 1 }
//    take()를 사용해 원하는 요소 개수만큼 획득하고 toList()를 사용해 List 컬렉션으로 반환
    println(nums.take(10).toList())

//    주어진 식에 따라 새로운 컬렉션을 반환하는 map이나 filter같은 연산을 사용할 수 있다.
    val squares = generateSequence(1) { it + 1 }.map { it * it }
    println(squares.take(10).toList())
    val oddSquares = squares.filter { it % 2 != 0 }
    println(oddSquares.take(10).toList())
    println()

//    asSequence : 중간 연산 결과 없이 한 번에 끝까지 연산한 후 결과를 반환.
//    메서드 체이닝을 사용할 경우 순차적 연산이기 때문에 시간이 많이 걸릴 수 있지만 asSequence를 사용하면 병렬처리되기 때문에 성능이 좋아진다.
    val list1 = listOf(1, 2, 3, 4, 5)
//    단순한 메서드 체이닝 예시
    val listDefault = list1
        .map { println("map($it)"); it * it }
        .filter { println("filter($it)"); it % 2 == 0 }
    println(listDefault)
    println()

//    asSequence()를 활용한 예시
    val listSeq = list1.asSequence()
        .map { print("map($it) "); it * it }
        .filter { println("filter($it)"); it % 2 == 0 }
        .toList()
    println(listSeq)

}