package Chapter8_GenericAndArray

fun main() {
    val s = "abcdef"
    var s2 = "ABCDEF"

//    문자열 추출하기
    println(s.substring(0..2))
    
//    문자열 비교하기 : 같으면 0, s1 < s2이면 양수, 반대면 음수를 반환
    println(s.compareTo(s2))
    println(s.compareTo(s2, true)) // 대소문자 무시
    println()

//    StringBuilder : 문자열이 사용할 공간을 좀 더 크게 잡을 수 있기 때문에 요소를 변경할 때 이 부분이 사용되어 특정 단어를 변경할 수 있게 된다.
//    단, 기존의 문자열보다는 처리 속도가 느리고 만일 단어를 변경하지 않고 그대로 사용한다면 임시 공간인 메모리를 조금 더 사용하게 되므로
//    낭비된다는 단점이 있다.
    var s3 = StringBuilder("Hello")
    s3[2] = 'x'
    println(s3)

    s3.append("World")
    println(s3)
    s3.insert(10, "Added")
    println(s3)
    s3.delete(5, 10)
    println(s3)

    var deli = "Welcome to Kotlin"
    var sp = deli.split(" ")
    println(sp)

//    문자열을 정수로 반환하기
    val number = "123".toInt()
    val doubleNumber = "123".toDouble() // double로 변환

    


}