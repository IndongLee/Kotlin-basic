package Chapter8

// 가변성을 지정하는 2가지 방법
// 1) 선언 지점 변성(declaration-site variance): 클래스를 선언하면서 클래스 자체에 가변성을 지정하는 방식.
// 클래스의 공변성을 전체적으로 지정하는 것이 되기 때문에 클래스를 사용하는 장소에서는 따로 자료형을 지정해 줄 필요가 없어 편리하다.
class DSBox<in T: Animal>(var item: Int) {}

// 2) 사용 지점 변성(use-site- variance)
// item을 얻기 위해서 Box에는 out이 지정되어야 하며 item을 설정하기 위해서는 in으로 지정되어야 한다.
// Box를 사용하는 시점에서 이것을 나타낼 수 있다.
class USBox<T>(var item: T)

// box의 자료형이 out의 제약을 둔 상태로 사용하고 있다.
// 이렇게 사용하고자 하는 요소의 특정 자료형에 in 또는 out을 지정해 제한하는 것을 자료형 프로젝션(Type Projection)이라고 한다.
// 따라서 이 경우 box는 형식 매개변수 T를 in 위치에 사용하는 경우를 제한한다.
// 이렇게 사용할 수 있는 위치를 제한하는 이유는 자료형 안정성을 보장하기 위해서이다.
// 이 함수에서는 out에 의한 게터만 허용하고 in에 의한 세터는 금지하겠다는 것이다.
fun <T> printObj(box: USBox<out Animal>) {
    val obj: Animal = box.item
//    box.item = Animal() // 설정(set)하려고 할 때는 in이 지정되어야 함
    println(obj)
}

class InOutTest<in T, out U>(t: T, u: U) {
//    val propT: T = t // T는 in 위치이기 때문에, out 위치에 사용 불가
    val propU: U = u
    
//    fun func1(u: U) // U는 out 위치이기 때문에 in 위치에 사용 불가
    fun func2(t: T) {
        println(t)
    }
}

// Box<*>는 어떤 자료형이라도 들어올 수 있으나 구체적으로 자료형이 결정되고 난 후에는 그 자료형과 하위 자료형의 요소만 담을 수 있도록 제한할 수 있다.
// 이렇게 in과 out을 정하지 않고 스타(*)를 통해 지정하는 방법을 스타 프로젝션이라고 한다.
// in으로 정의되어 있는 형식 매개변수를 *로 받으면 in Nothing인 것으로 간주하고, out으로 정의되어 있는 형식 매개변수를 *로 받으면 out Any?로 간주한다.
fun starTestFunc(v: InOutTest<*, *>) {
//    v.func2(1) // Nothing으로 간주되어 오류가 발생
    println(v.propU)
}

// 다음과 같은 일반적인 제네릭 함수에서 T 자료형은 자바처럼 실행 시간에 삭제되기 때문에 T 자체에 그대로 접근할 수 없다.
// <Int>처럼 결정된 제네릭 자료형이 아닌, <T>처럼 결정되지 않은 제네릭 자료형은 컴파일 시간에는 접근 가능하나 함수 내부에서 사용하려면
// 아래 코드처럼 함수의 매개변수를 넣어 c: Class<T>처럼 지정해야만 실행 시간에 사라지지 않고 접근할 수 있다.
fun <T> myGenericFunc(c: Class<T>) {}

// 만약 reified로 형식 매개변수 T를 지정하면 실행 시간에 접근할 수 있게 된다. 매개변수에 c를 선언해 Class<T> 형태로 넘기지 않아도 된다.
// 다음과 같이 지정하면 T는 일반 클래스처럼 사용할 수 있게 된다.
// reified 자료형은 인라인 함수에서만 사용할 수 있다.
// reified T 자료형은 컴파일러가 복사해 넣을 때 실제 자료형을 알 수 있기 때문에 실행 시간에도 사용할 수 있게 된다.
inline fun <reified T> myGenericFunc2() {}


fun main() {

}