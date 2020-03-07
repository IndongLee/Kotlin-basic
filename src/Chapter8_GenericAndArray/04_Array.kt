package Chapter8_GenericAndArray

import java.util.*

fun main() {
//    일차원 배열 만들기
    val numbers = arrayOf(4, 5, 6, 7)
    for (element in numbers) {
        println(element)
    }

//    다차원 배열 만들기
    val array1 = arrayOf(1, 2, 3)
    val array2 = arrayOf(4, 5, 6)
    val array3 = arrayOf(7, 8, 9)

    val arr2d = arrayOf(array1, array2, array3)
    for (e1 in arr2d) {
        for (e2 in e1) {
            print(e2)
        }
        println()
    }
    println()

//    다양한 자료형을 혼합할 수 있다.
    val mixArr = arrayOf(4, 5, 7, 3, "Chike", false)
//    특정 자료형으로 제한하기
    val intOnlyArr1 = arrayOf<Int>(4, 5, 7, 3)
    val intOnlyArr2 = intArrayOf(4, 5, 7, 3)

    println("arr: ${Arrays.toString(intOnlyArr1)}")
    println("size: ${intOnlyArr1.size}")
    println("sum(): ${intOnlyArr1.sum()}")
    println(intOnlyArr1[2])
    println(intOnlyArr1.get(2))
    println()

    for (i in 0..intOnlyArr1.size-1) {
        println("arr[$i] = ${intOnlyArr1[i]}")
    }

    println(Arrays.deepToString(arr2d))
    println()

//    표현식에 의한 배열 생성
    val arr3 = Array(5, { i -> i * 2 })
    println("arr3: ${Arrays.toString(arr3)}")
    println()

    var nullArr = arrayOfNulls<Int>(1000) // 1000개의 null로 채워진 정수 배열
    val zeroArr = Array(1000, { 0 }) // 0으로 채워진 배열

//    배열이 정의되면 크기가 고정되기 때문에 다음과 같이 새로 할당하는 방법으로 요소를 추가하거나 잘라낼 수 있다.
    val originalArr = Array(5, { i -> i + 1 })
    val plusArr = originalArr.plus(6)
    val slicedArr = originalArr.sliceArray(0..2)
    println("originalArr: ${Arrays.toString(originalArr)}")
    println("plusArr: ${Arrays.toString(plusArr)}")
    println("slicedArr: ${Arrays.toString(slicedArr)}")
    println()

    println(originalArr.first()) // 배열의 첫 번째 요소
    println(originalArr.last()) // 배열의 마지막 요소
    println("indexOf(3): ${originalArr.indexOf(3)}") // 요소 3의 인덱스 번호
    println("average: ${originalArr.average()}") // 배열의 평균 값
    println("count: ${originalArr.count()}") // 요소 개수 세기
    println(originalArr.contains(4)) // 배열에 요소 4가 포함되어 있는지 확인
    println(4 in originalArr) // 위와 동일
    println()

//    일단 자료형이 지정된 배열은 다른 자료형으로 변환할 수 없다. 단, Any 자료형으로 만들어진 배열은 기존 자료형을 다른 자료형으로 지정할 수 있다.
    val b = Array<Any>(10, {0})
    b[0] = "Hello World"
    b[1] = 1.1
    b[2] = 1
    println(b[0])
    println(b[1])
    println(b[2])
    println()

//    forEach()는 요소 개수만큼 지정한 구문을 반복해서 실행하며, forEachIndexed()는 순환하며 인덱스까지 출력한다.
    originalArr.forEach { element -> print("$element") }
    println()
    originalArr.forEachIndexed({ i, e -> println("arr[$i] = $e") })
//    반복을 위한 요소를 처리하는 iterator()를 사용할 수도 있다.
    val iter: Iterator<Int> = originalArr.iterator()
    while (iter.hasNext()) {
        print("${iter.next()}")
    }
    println()
    println()

    val arr = arrayOf(8, 4, 3, 2, 5, 9, 1)
//    오름차순, 내림차순으로 정렬된 일반 배열로 반환
    val sortedNums = arr.sortedArray()
    val sortedNumsDesc = arr.sortedArrayDescending()
    println("ORI: " + Arrays.toString(arr))
    println("ASC: " + Arrays.toString(sortedNums))
    println("DESC: " + Arrays.toString(sortedNumsDesc))
    println()

//    원본 배열에 대한 정렬
    arr.sort(1, 3) // 특정 인덱스 구간만 정렬
    println("ORI: " + Arrays.toString(arr))
    arr.sortDescending()
    println("ORI: " + Arrays.toString(arr))
    println()

//    List로 반환
    val listSorted: List<Int> = arr.sorted()
    val listDesc: List<Int> = arr.sortedDescending()
    println("LIST: " + listSorted)
    println("LIST: " + listDesc)
    println()

//    SortBy를 이용한 특정 표현식에 따른 정렬
    var items = arrayOf<String>("Dog", "Cat", "Lion", "Tiger", "Parrot")
    items.sortBy { item -> item.length }
    println(Arrays.toString(items))

    data class Product(val name: String, val price: Double)
    val products = arrayOf(
        Product("Smartphone", 1000.0),
        Product("Laptop", 2000.0),
        Product("Desktop", 2500.0),
        Product("Mouse", 120.0),
        Product("Keyboard", 150.0),
        Product("Speaker", 200.0),
        Product("Monitor", 500.0)
    )
    products.sortBy { it.price }
    products.forEach { println(it) }
    println()

//    SortWith 비교자로 정렬하기
    products.sortWith(
        kotlin.Comparator<Product> { p1, p2 ->
            when {
                p1.price > p2.price -> -1
                p1.price == p2.price -> 0
                else -> 1

            }
        }
    )
    products.forEach { println(it) }
    println()

//    compareBy를 사용해 여러 기준으로 정렬하기
    val newProducts = products.plus(Product("Mouse", 300.0))
    newProducts.sortWith(compareBy({it.name}, {it.price}))
    newProducts.forEach { println(it) }
    println()

//    배열 필터링하기
    products.filter { e -> e.price > 1500.0 }.forEach { println(it)}

//    요소 변경하기
    products.filter { it.price > 900 }.map { it.price * 0.9 }.forEach { println(it) }
    println()

//    가장 큰 값과 작은 값 골라내기
    println(products.minBy { it.price })
    println(products.maxBy { it.price })
    println()

//    배열 평탄화하기
    val nums = arrayOf(1, 2, 3)
    val strs = arrayOf("one", "two", "three")
    val simpleArray = arrayOf(nums, strs)
    simpleArray.forEach { println(it) }

    val flattenSimpleArray = simpleArray.flatten()
    println(flattenSimpleArray)

}