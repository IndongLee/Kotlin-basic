package Chapter9_Collection

fun main() {
//    컬렉션의 연산
    val list1: List<String> = listOf("one", "two", "three")
    val list2: List<Int> = listOf(1, 3, 4)
    val map1 = mapOf("Hi" to 1, "Hello" to 2, "Good Bye" to 3)

    println(list1 + "four") // 연산자를 활용한 문자열 요소 추가
    println(list2 + 1) // 연산자를 사용한 정수형 요소 추가
    println(list2 + listOf(5, 6, 7)) // 두 List의 병합
    println(list2 - 1) // 요소의 제거
    println(list2 - listOf(3, 4, 5)) // 일치하는 요소의 제거
    println(map1 + Pair("Bye", 4)) // Pair()를 사용한 Map 요소 추가
    println(map1 - "Hello") // 일치하는 값의 제거
    println(map1 + mapOf("Good Morning" to 5, "Good Evening" to 6)) // 두 Map의 병합
    println(map1 - listOf("Hi", "Hello")) // List에 일치하는 값을 Map에서 제거

//    요소의 처리와 집계
    val list = listOf(1, 2, 3, 4, 5, 6)
    val listPair = listOf(Pair("A", 300), Pair("B", 200), Pair("C", 100))
    val map = mapOf(11 to "Kotlin", 22 to "Java", 33 to "Python")

//    forEach와 forEachIndexed : 각 요소에 대한 순환 처리를 위해 사용
    list.forEach { print(" $it") }
    println()
    list.forEachIndexed { index, value -> println("index[$index]: $value") }

//    onEach : forEach는 각 요소를 람다식으로 처리한 후 컬렉션을 반환하지 않지만 onEach를 사용하면 각 컬렉션을 반환받을 수 있다.
    val returnedList = list.onEach { print(it) }
    println()
    val returnedMap = map.onEach { println("key: ${it.key}, value: ${it.value}") }
    println("returnedList = $returnedList")
    println("returnedMap = $returnedMap")
    println()

//    count: 특정 조건에 일치하는 요소의 개수를 반환
    println(list.count { it % 2 == 0 })
    println()

//    max, min, maxBy, minBy : 최댓값과 최솟값 반환
    println(list.max())
    println(list.min())
    println(map.maxBy { it.key })
    println(map.minBy { it.key })
    println()

//    fold, reduce : 각 요소에 정해진 식 적용하기. fold는 초깃값과 정해진 식에 따라 처음 요소부터 끝 요소에 적용해 반환. reduce는 초깃값이 없다.
    println(list.fold(4) { total, next -> total + next })
    println(list.fold(1) { total, next -> total * next })

//    foldRight : fold와 같고 마지막 요소에서 처음 요소로 반대로 적용
    println(list.foldRight(4) { total, next -> total + next })
    println(list.foldRight(1) { total, next -> total * next })

    println(list.reduce { total, next -> total + next })
    println(list.reduceRight { total, next -> total + next })
    println()

//    sumBy : 식에서 도출된 모든 요소를 합한 결과를 반환
    println(listPair.sumBy { it.second })

//    all : 람다식에서 모든 요소가 일치할 때 true를 반환, any : 최소한 하나 혹은 그 이상의 특정 요소가 일치하면 true를 반환
    println(list.all { it < 10 })
    println(list.any { it % 2 == 0 })
    println()

//    contains : 컬렉션에 특정 요소가 포함되어 있는지를 검사
    println("contains: " + list.contains(2))
    println(2 in list)
    println(map.contains(11))
    println(11 in map)

//    containsAll : 모든 요소가 포함되어 있으면 true를 반환
    println("containsAll: " + list.containsAll(listOf(1, 2, 3)))
    println()

//    none : 요소가 없으면 true, 있으면 false를 반환
    println("none: " + list.none())
    println("none: " + list.none { it > 6 })

//    isEmpty, isNotEmpty : 컬렉션이 비어 있는지 아닌지 검사
    println(list.isEmpty())
    println(list.isNotEmpty())
    println()

    val listMixed = listOf(1, "Hello", 3, "World", 5, 'A')
    val listWithNull = listOf(1, null, 3, null, 5, 6)
    val listRepeated = listOf(2, 2, 3, 4, 5, 5, 6)

//    filter : 특정 요소를 골라내기
    println(list.filter { it % 2 == 0 })
    println(list.filterNot { it % 2 == 0 }) // 식 이외의 요소 골라내기
    println(listWithNull.filterNotNull()) // null을 제외
    println("filterIndexed: " + list.filterIndexed { idx, value -> idx != 1 && value % 2 == 0 }) // 인덱스와 함께 추출
    val mulList  = list.filterIndexedTo(mutableListOf()) { idx, value -> idx != 1 && value % 2 == 0 } // 추출 후 가변형 컬렉션으로 변환
    println("filterIndexedTo: $mulList")
    println("filterKeys: " + map.filterKeys { it != 11 })
    println("filterValues " + map.filterValues { it == "Java" })
    println("filterIsInstance: " + listMixed.filterIsInstance<String>()) // 여러 자료형의 요소 중 String 자료형만 골라냄
    println()

//    slice : 특정 범위를 잘라내거나 반환하기
    println("slice: " + list.slice(listOf(0, 1, 2))) // 인덱스 0~2번에 해당하는 값을 추출
    println()

//    take : n개의 요소를 가진 List를 반환
    println(list.take(2)) // 앞 두 요소 반환
    println(list.takeLast(2)) // 마지막 두 요소 반환
    println(list.takeWhile { it < 3 }) // 조건식에 따른 반환
    println()

//    drop : n개의 요소를 제외하고 List를 반환
    println(list.drop(3))
    println(list.dropWhile { it < 3 })
    println(list.dropLastWhile { it > 3 })
    println()

//    componentN : N번에 해당하는 요소를 반환(인덱스 번호가 아님!)
    println("component1(): " + list.component1())
    println()

//    distinct : 중복 요소가 있는 경우 1개로 취급해 다시 List로 반환
    println("distinct: " + listRepeated.distinct())

//    intersect : 교집합의 원리로, 겹치는 요소만 골라내 List를 반환
    println("intersect: " + list.intersect(listOf(5, 6, 7, 8)))
    println()
    
//  .map : 컬렉션의 요소에 일괄적으로 map()에 있는 식을 적용해 새로운 컬렉션을 만듦
//    forEach와 비슷해 보이나 주어진 컬렉션을 전혀 건드리지 않는다는 점에서 좀 더 안전함.
    println(list.map { it * 2 })
    val mapIndexed = list.mapIndexed {index, it -> index * it } // 컬렉션에 인덱스를 포함하고 주어진 식을 적용해 새로운 컬렉션 반환
    println(mapIndexed)

    println(listWithNull.mapNotNull { it?.times(2) }) // null을 제외하고 식을 적용해 새로운 컬렉션 반환
    println()

//    flatMap : 각 요소에 식을 적용한 후 이것을 다시 하나로 합쳐 새로운 컬렉션을 반환한다.
    println(list.flatMap { listOf(it, 'A') })
    println(listOf("abc", "12").flatMap { it.toList() })
    println()

//    groupBy : 주어진 식에 따라 요소를 그룹화하고 이것을 다시 Map으로 반환한다.
    val grpMap = list.groupBy { if (it % 2 == 0) "even" else "odd" }
    println(grpMap)

//    element : 보통 인덱스와 함께 해당 요소의 값을 반환한다.
//    elementAt : 주어진 인덱스에 해당하는 요소를 반환. 인덱스 범위를 벗어나면 IndexOutOfBoundsException 오류가 발생
//    elementAtOrElse : 인덱스 범위를 벗어나도 식에 따라 결과를 반환
//    elementAtOrNull : 인덱스 범위를 벗어나면 null을 반환
    println("elementAt: " + list.elementAt(1))
    println("elementAtOrElse: " + list.elementAtOrElse(10, { 2 * it }))
    println("elementAtOrNull: " + list.elementAtOrNull(10))
    println()
    
//    first : 식에 일치하는 첫 요소 반환
//    firstOrNull : 식에 일치하지 않는 경우 null 반환
//    last : 식에 일치하는 마지막 요소 반환
//    lastOfNull : 식에 일치하지 않는 경우 null 반환
    println("first: " + listPair.first { it.second == 200 })
    println("firstOrNull: " + listPair.firstOrNull { it.first == "E" })
    println("last: " + listPair.last { it.second == 200 })
    println("lastOrNull: " + listPair.lastOrNull { it.first == "E" })
    println()

//    indexOf : 주어진 요소에 일치하는 첫 번째 인덱스 반환
//    indexOfFirst : 람다식에 일치하는 첫 요소의 인덱스 반환, 없으면 -1
//    lastIndexOf : 주어진 요소에 일치하는 가장 마지막 인덱스 반환
//    indexOfLast : 람다식에 일치하는 가장 마지막 요소의 인덱스 반환, 없으면 -1
    println("indexOf: " + list.indexOf(4))
    println("indexOfFirst: " + list.indexOfFirst { it % 2 == 0 })
    println("lastIndexOf: " + list.lastIndexOf(5))
    println("indexOfLast: " + list.indexOfLast { it % 2 == 0 })
    println()

//    single : 해당 조건식에 일치하는 요소를 하나 반환. 일치하는 요소가 하나 이상이면 예외 발생
//    singleOrNull : 조건식에 일치하는 요소가 없거나 하나 이상이면 null 반환
    println("single: " + listPair.single { it.second == 100 })
    println("singleOrNull: " + listPair.singleOrNull { it.second == 500 })
    println()

//    binarySearch : 인자로 주어진 요소에 대해 이진 탐색 후 요소를 반환
//    find : 조건식을 만족하는 첫 번째 검색된 요소를 반환. 없으면 null
    println("binarySearch: " + list.binarySearch(3))
    println("find: " + list.find { it > 3 })
    println()

//    union : 두 List 컬렉션을 병합하고 중복된 요소 값은 하나만 유지한다.
//    plus 또는 + : 중복 요소를 포함해 합친다.
//    partition : 주어진 조건식의 결과에 따라 List 컬렉션을 2개로 분리한다.
//    zip : 2개의 컬렉션에서 동일한 인덱스끼리 Pair를 만들어 반환한다. 요소의 개수가 가장 적은 컬렉션에 맞춰 Pair가 구성된다.
    println(list.union(listRepeated))
    println(list.plus(listRepeated))
    println(list + listRepeated)
    println(list.partition { it % 2 == 0 })
    println(list.zip(listOf(7, 8)))
    println()

//    reversed : 뒤집힌 순서로 컬렉션 반환
//    sorted : 요소를 정렬한 후 정렬된 컬렉션 반환
//    sortedDescending : 내림차순 정렬
//    sortedBy : 특정 비교식에 의해 정렬된 컬렉션 반환
    val unsortedList = listOf(3, 2, 7, 5)
    println(unsortedList.reversed())
    println(unsortedList.sorted())
    println(unsortedList.sortedDescending())
    println(unsortedList.sortedBy { it % 3 })
}