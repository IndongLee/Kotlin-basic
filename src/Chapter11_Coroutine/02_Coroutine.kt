package Chapter11_Coroutine

import kotlinx.coroutines.*
import java.util.concurrent.Executors

suspend fun doWork1(): String {
    delay(1000L)
    return "Work1"
}
suspend fun doWork2(): String {
    delay(3000L)
    return "Work2"
}

private fun worksInSerial() {
    GlobalScope.launch {
//        이 두 개의 함수는 내부적으로 비동기 코드로서 동시에 작동할 수 있지만 코드만 봤을 때는 순차적으로 실행되는 것처럼 보여서 프로그래밍의 복잡도를 낮춘다.
        val one = doWork1()
        val two = doWork2()
        println("Kotlin One: $one")
        println("Kotlin Two: $two")
    }
}

// async 코루틴 빌더 생성하기
// async는 launch와 달리 Deferred<T>를 통해 결괏값을 반환한다. 이때 지연된 결괏값을 받기 위해 await를 사용할 수 있다.
private fun worksInParallel() {
//    doWork1과 doWork2는 async에 의해 감싸져 있으므로 완전히 병행 수행할 수 있다.
//    보다 복잡한 루틴을 작성하는 경우에는 많은 태스크들과 같이 병행 수행되므로 어떤 루틴이 먼저 종료될지 알기 어렵다.
//    따라서 태스크가 종료되는 시점을 기다렸다가 결과를 받을 수 있도록 await를 사용해 현재 스레드의 블로킹 없이 먼저 종료되면 결과를 가져올 수 있다.
    val one = GlobalScope.async {
        doWork1()
    }
    val two = GlobalScope.async {
        doWork2()
    }

    GlobalScope.launch {
        val combined = one.await() + "_" + two.await()
        println("Kotlin Combined: $combined")
    }
}

fun main() { // 메인 스레드의 문맥
    GlobalScope.launch { // 새로운 코루틴을 백그라운드에 실행
        delay(1000L) // 1초의 넌블로킹 지연(시간의 기본 단위는 ms)
        println("World!") // 지연 후 출력
    }
    println("Hello, ") // 메인 스레드의 코루틴이 지연되는 동안 계속 실행
    Thread.sleep(2000L) // 메인 스레드가 JVM에서 바로 종료되지 않게 2초 기다림

//    코루틴에서 사용되는 함수는 suspend()로 선언된 지연 함수여야 코루틴 기능을 사용할 수 있다.
//    suspend로 표기함으로서 이 함수는 실행이 일시 중단(suspended)될 수 있으며 필요한 곳에 재개(resume)할 수 있게 된다.
//    delay() 함수 역시 suspend가 선언된 지연 함수다.

    //    이러한 지연 함수는 코루틴 빌더인 launch와 async에서 사용할 수 있지만 메인 스레드에서는 사용할 수 없다.
//    지연 함수는 또 다른 지연 함수 내에서 사용하거나 코루틴 블록 안에서만 사용해야 한다.
    suspend fun doSomething() {
        println("Do something!")
    }

//    launch를 통해 코루틴 블록을 만들어 내는 것을 코루틴 빌더의 생성이라고 한다.
//    launch는 스레드를 차단하지 않고 새로운 코루틴을 실행할 수 있게 하며 특정 결괏값 없이 Job 객체를 반환한다.
//    코루틴을 실행하기 위해서는 내부적으로 스레드를 통해서 실행될 수 있다. 단 실행 루틴이 많지 않은 경우에는 내부적으로 하나의 스레드에서
//    여러 루틴을 실행할 수 있기 때문에 1개의 스레드면 충분하다.

//    Job : 백그라운드에서 실행되는 작업. 개념적으로는 간단한 생명주기를 가지고 있고 부모-자식 관계가 형성되면 부모가 작업이 취소될 때까지
//          하위 자식의 작업이 모두 취소된다. 보통 Job() 팩토리 함수나 launch에 의해 job 객체가 생성된다.
//          Job의 상태를 판별하기 위해 job에는 isActive, isCompleted, isCancelled 변수가 있다.
//          보통 Job이 생성되면 활성화 상태인 Active를 가진다. 하지만 Job() 팩토리 함수에 인자로 Coroutine.LAZY를 설정하면 아직은
//          Job이 활성화되지 않고 New 상태로 만들어진다. Job을 Active 상태로 만들기 위해서는 start()나 join() 함수를 호출하면 된다.
//          Job을 취소하려면 cancel() 함수를 사용할 수 있다. 그러면 Job은 Cancelling 상태로 즉시 바뀌고 이후 Cancelled 상태로 바뀐다.
    val job = GlobalScope.launch {
        delay(1000L)
        println("World!")
    }
    println("Hello, ")
    println("job.isActive: ${job.isActive}, completed: ${job.isCompleted}")
    Thread.sleep(2000L)
    println("job.isActive: ${job.isActive}, completed: ${job.isCompleted}")

    worksInSerial()
    Thread.sleep(4000L)
    println()
    worksInParallel()
    Thread.sleep(4000L)

//    코루틴의 문맥
//    코루틴이 실행될 때 여러 가지 문맥은 CoroutineContext에 의해 정의된다.
//    launch{}와 같이 인자가 없는 경우에는 CoroutineScope에서 상위의 문맥이 상속되어 결정되고,
//    launch(Dispatchers.Default) {}와 같이 사용되면 GlobalScope에서 실행되는 문맥과 동일하게 사용된다.
//    GlobalScope는 메인 스레드의 생명주기가 끝나면 같이 종료된다.
//    내부적으로 보통 CommonPool이 지정되어 코루틴이 사용할 스레드의 공동 풀(pool)을 사용하게 된다.
//    이것은 이미 초기화되어 있는 스레드 중 하나 혹은 그 이상이 선택되어 초기화하기 때문에 스레드를 생성하는 오버헤드가 없어 빠른 기법
//    그리고 하나의 스레드에 다수의 코루틴이 동작할 수 있다.

//    특정 스레드 개수를 직접 지정하기
    val threadPool = Executors.newFixedThreadPool(4)
    val MyContext = threadPool.asCoroutineDispatcher()
    /*
    async(MyContext) {
        ...
    }
    */

//    필요한 경우 launch나 async에 인자를 지정해 코루틴에 필요한 속성을 줄 수 있다.
    /*
    public fun launch(
        context : CoroutineContext,
        start: CoroutineStart,
        parent: Job?,
        onCompletion: CompletionHandler?,
        block: suspend CoroutineScope.() -> Unit): Job {
        ...
    }
    */
//    CoroutineStart는 다음과 같은 시작 방법을 정의할 수 있다.
//    1) DEFAULT : 즉시 시작
//    2) LAZY : 코루틴을 느리게 시작(처음에는 중단된 상태이며 start()나 await() 등으로 시작됨)
//    3) ATOMIC : 최적화된 방법으로 시작
//    4) UNDISPATCHED : 분산 처리 방법으로 시작
    val job2 = GlobalScope.async(start = CoroutineStart.LAZY) { doWork1() }
    job2.start() // 실제 시작 시점으로 또는 job2.await()로 시작됨

//    runblocking : 새로운 코루틴을 실행하고 완료되기 전까지 현재 스레드를 블로킹한다. runblocking에서는 지연 함수를 사용할 수 있다.
    runBlocking { delay(2000) }

//    메인 스레드 자체를 잡아두기 위해 다음과 같이 main() 함수를 블로킹 모드에서 실행할 수 있다.
    /*
    fun main() = runBlocking<Unit> { // main() 함수가 코루틴 환경에서 실행
        launch { // 백그라운드로 코루틴 실행
            delay(1000L)
            println("World!")
        }
        println("Hello") // 즉시 이어서 실행됨, delay() 함수를 사용하지 않아도 코루틴을 기다림
    }
    suspend fun main() = coroutineScope {} 와 같이 main() 함수에 suspend를 지정할 수도 있다.
    */

    //    클래스의 멤버 메서드에서도 사용할 수 있다.
    class MyTest {
        fun mySuspendMethod() = runBlocking<Unit> {  }
    }

//    코루틴과 시퀀스
//    sequence() 함수는 Sequence<T>를 반환하는데, Sequence() 함수 내부에서 지연 함수를 사용할 수 있고 코루틴과 함께 최종 형태를 나중에
//    결정할 수 있는 늦은(lazy) 시퀀스를 만들 수도 있다.
//    늦은 시퀀스 : 특정 요소가 완전히 구성되기 전에 사용 범위와 시점을 결정할 수 있는 시퀀스
    val fibonacciSeq = sequence {
        var a = 0
        var b = 1
        yield(1) // 지연 함수가 사용됨

        while (true) {
            yield(a + b)
            val temp = a + b
            a = b
            b = temp
        }
    }
// yield 함수는 각 표현식을 계속 진행하기 전에 실행을 잠시 멈추고 요소를 반환한다.
    println(fibonacciSeq.take(8).toList())

    val seq = sequence {
        val start = 0

        yield(start) // 단일 값 산출
        yieldAll(1..5 step 2) // 반복 값 산출
        yieldAll(generateSequence(8) { it * 3 }) // 무한한 시퀀스에서 산출
    }
    println(seq.take(7).toList())

//    모든 요소는 일회성이기 때문에 각 요소에 대한 다음 요소를 직접 지정하려면 iterator()를 통해 next() 메서드를 사용해야 한다.
    val saved = fibonacciSeq.iterator()
    println("${saved.next()}, ${saved.next()}, ${saved.next()}")

}


