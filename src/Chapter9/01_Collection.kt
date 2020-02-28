package Chapter9

import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet
import kotlin.collections.LinkedHashSet

// 코틀린의 컬렉션은 자바 컬렉션의 구조를 확장 구현한 것이다. 컬렉션의 종류로는 List, Set, Map 등이 있다.
// 자바와는 다르게 코틀린의 컬렉션은 불변형(immutable)과 가변형(mutable)로 나뉜다.
// List - listOf / mutableListOf, arrayListOf
// Set - setOf / mutableSetOf, hashSetOf, linkedSetOf, sortedSetOf
// Map - mapOf / mutableMapOf, hashMapOf, linkedMapOf, sortedMapOf

fun main() {
//    불변형 List의 사용
    var numbers: List<Int> = listOf(1, 2, 3, 4, 5)
    var names: List<String> = listOf("one", "two", "three")
    var fruits = listOf("apple", "banana", "kiwi")
    for (name in names) {
        println(name)
    }
    numbers.forEach { print(it) }
    println()
    for (i in fruits.indices) { // 인덱스로 접근하기
        println(fruits[i])
    }
    println()

//    비어 있는 List 생성
    val emptyList: List<String> = emptyList<String>()
    
//    null을 제외한 요소만 반환해 List를 구성
    val nonNullsList: List<Int> = listOfNotNull(2, 45, null, 100, null, 5, null)
    println(nonNullsList)

//    List의 메서드들
    println(names.size)
    println(names.get(0))
    println(names.indexOf("three"))
    println(names.contains("two"))
    println()

//    가변형 arrayList
    var stringList: ArrayList<String> = arrayListOf<String>("Hello", "Kotlin", "!!!")
    stringList.add("Java")
    stringList.remove("Hello")
    stringList.removeAt(2)
    stringList[1] = "and"
    println(stringList)
    println()

    val mutableList = names.toMutableList() // 새로운 가변형 리스트를 만들어냄
    mutableList.add("four")
    println(mutableList)

//    Set - 정해진 순서가 없는 요소들의 집합을 나타내는 컬렉션
//    불변형 Set
    val mixedTypeSet = setOf("Hello", 5, "World", 3.14, 'c') // 자료형 혼합 초기화
    val intSet: Set<Int> = setOf(1, 5, 5)
    println(mixedTypeSet)
    println(intSet)

    val animals = mutableSetOf("Lion", "Dog", "Cat", "Python", "Hippo")
    println(animals)
    animals.add("Dog")
    println(animals)
    animals.remove("Python")
    println(animals)

//    해시 테이블에 요소를 저장하는 HashSet
    val intsHashSet: HashSet<Int> = hashSetOf(6, 3, 4, 5, 6)
    intsHashSet.add(2)
    intsHashSet.remove(6)
    println(intsHashSet)

//    sortedTreeSet 함수는 자바의 TreeSet 컬렉션을 정렬된 상태로 반환한다.
//    TreeSet은 저장된 데이터의 값에 따라 정렬되는데, 일종의 개선된 이진 탐색 트리인 레드 블랙 트리 알고리즘을 사용해 자료구조를 구성한다.
//    HashSet보다 성능이 떨어지고 데이터를 추가하거나 삭제하는 데 시간이 걸리지만 검색과 정렬이 뛰어나다는 장점이 있다.
    val intsSortedSet: TreeSet<Int> = sortedSetOf(4, 1, 7, 2)
    intsSortedSet.add(6)
    intsSortedSet.remove(1)
    println(intsSortedSet)

//    linkedSet 함수는 링크드 리스트를 사용해 구현된 해시 테이블에 요소를 저장한다.
//    저장된 순서에 따라 값이 정렬되며 HashSet, TreeSet보다 느리다.
//    다만 자료구조상 다음 데이터를 가리키는 포인터 연결을 통해 메모리 저장 공간을 좀 더 효율적으로 사용할 수 있다.
    val intsLinkedHashSet: LinkedHashSet<Int> = linkedSetOf(35, 21, 76, 26, 75)
    intsLinkedHashSet.add(4)
    intsLinkedHashSet.remove(21)
    println(intsLinkedHashSet)
    println()

//    Map의 key와 value는 key to value 형태로 나타낸다.
    val langMap: Map<Int, String> = mapOf(11 to "Java", 22 to "Kotlin", 33 to "Python")
    for ((key, value) in langMap) {
        println("key=$key, value=$value")
    }
    println("langMap[22] = ${langMap[22]}")
    println("langMap.keys = ${langMap.keys}")
    println("langMap.values = ${langMap.values}")

//    가변형 mutableMapOf 함수
    val capitalCityMap: MutableMap<String, String> = mutableMapOf("Korea" to "Seoul", "France" to "Paris", "Croatia" to "Zagreb", "Czech" to "Prague")
    println(capitalCityMap.keys)
    println(capitalCityMap.values)

    capitalCityMap.put("UK", "London")
    capitalCityMap.remove("France")
    println(capitalCityMap)

    val addData = mutableMapOf("Italy" to "Rome")
    capitalCityMap.putAll(addData) // 두 Map 객체를 통합한다.
    println(capitalCityMap)
}