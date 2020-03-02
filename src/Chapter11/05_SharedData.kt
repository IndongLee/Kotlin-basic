package Chapter11

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.w3c.dom.css.Counter
import java.util.concurrent.atomic.AtomicInteger
import kotlin.coroutines.CoroutineContext
import kotlin.system.measureTimeMillis

// 병행 프로그래밍에서는 전역 변수 같은 변경 가능한 공유 자원에 접근할 때 값의 무결성을 보장할 수 있는 방법이 필요하다.

// 자바의 volatile
// 보통 변수는 성능 때문에 데이터를 캐시에 넣어두고 작업하는데 이때 여러 스레드로부터 값을 읽거나 쓰면 데이터가 일치하지 않고 깨진다.
// 이것을 방지하기 위해 데이터를 캐시에 넣지 않도록 volatile 키워드와 함께 변수를 선언할 수 있다.
// volatile 키워드를 사용하면 코드가 최적화되면서 순서가 바뀌는 경우도 방지할 수 있다. 항상 프로그래머가 의도한 순서대로 읽기와 쓰기를 수행한다.
// 하지만 두 스레드에서 공유 변수에 대한 읽기와 쓰기 연산이 있으면 volatile 키워드만으로는 충분하지 않으며,
// synchronized를 통해 변수의 읽기, 쓰기 연산의 원자성을 보장해 줘야 한다.
@Volatile private var running = false
private var count = 0

fun start() {
    running = true
    thread(start = true) {
        while (running) println("${Thread.currentThread()}, count: ${count++}")
    }
}

fun stop() { running = false }

// 원자 변수(Atomic Variable) : 특정 변수의 증가나 감소, 더하기나 빼기가 단일 기계어 명령으로 수행되는 것.
//                             해당 연산이 수행되는 도중에는 누구도 방해하지 못하기 때문에 값의 무결성을 보장할 수 있게 된다.
// 단일 기계어 명령 : CPU가 명령을 처리할 때의 최소 단위. 이 최소 단위는 누구도 방해할 수 없다.
// var counter = 0 // 병행 처리 중 문제가 발생할 수 있는 변수
var counter = AtomicInteger(0) // 원자 변수로 초기화

suspend fun massiveRun(action: suspend () -> Unit) {
    val n = 1000 // 실행할 코루틴의 수
    val k = 1000 // 각 코루틴을 반복할 수
    val time = measureTimeMillis {
        val jobs = List(n) {
            GlobalScope.launch {
                repeat(k) { action() }
            }
        }
        jobs.forEach { it.join() }
    }
    println("Completed ${n * k} actions in $time ms")
}

// 스레드 가두기 : 특정 문맥에서만 작동하도록 단일 스레드에 가두는 방식. 보통 UI 애플리케이션에서 UI 상태는 단일 이벤트에 따라 작동해야 한다.
//                이때 단일 스레드 문맥인 newSingleThreadContext를 사용할 수 있다.
val counterContext = newSingleThreadContext("CounterContext")
var counter2 = 0

suspend fun massiveRun2(context: CoroutineContext, action: suspend () -> Unit) {
    val n = 1000 // 실행할 코루틴의 수
    val k = 1000 // 각 코루틴을 반복할 수
    val time = measureTimeMillis {
        val jobs = List(n) {
            GlobalScope.launch {
                repeat(k) { action() }
            }
        }
        jobs.forEach { it.join() }
    }
    println("Completed ${n * k} actions in $time ms")
}

// 상호 배제(Mutual Exclusion) : 코드가 임계 구역(Critical Section)에 있는 경우 절대로 동시성이 일어나지 않게 하고 하나의 루틴만
//                              접근 가능하는 것을 보장한다. 임계 구역 또는 공유 변수 영역은 병렬 컴퓨팅에서 둘 이상의 스레드가
//                              동시에 접근해서는 안 되는 배타적 공유 자원의 영역으로 정의할 수 있다. 임계 구역은 잘못된 변경이
//                              일어나지 않도록 보호해야 하는 코드가 있는 구역이므로 임계 영역의 처리가 필요한 경우 임계 구역에
//                              들어간 루틴은 다른 루틴이 못 들어오도록 잠가야 한다.
// 상호 배제의 특징으로 소유자 개념이 있는데, 일단 잠근 루틴 만이 잠금을 해제할 수 있다는 뜻이다.
// 코틀린의 코루틴에서는 Mutex의 lock과 unlock을 사용해 임계 구역을 만들 수 있다.
val mutex = Mutex()
var counterMutex = 0

// actor 코루틴 빌더 : 코루틴의 결합으로 만든 actor는 코루틴과 채널에서 통신하거나 상태를 관리한다.
//                    다른 언어의 actor 개념은 들어오고 나가는 메일 박스 기능과 비슷하지만 코틀린에서는 들어오는 메일 박스 기능만 한다.
//                    actor는 한 번에 1개의 메시지만 처리하는 것을 보장한다. 이 코드에서는 특정 루프를 만들고 isClosedForReceive로
//                    닫힌 상태가 아니라면 receive를 사용해 desc를 반복 출력하도록 한다. 만일 채널이 닫히면 ClosedSendChannelException을
//                    만나게 된다.


private fun main() = runBlocking {
//    synchronized 메서드와 블록
//    자바에서 synchronized는 메서드나 블록에 사용할 수 있다. 스레드 간 서로 공유하는 데이터가 있을 때 동기화해서 데이터의 안정성을 보장한다.
//    특정 스레드가 이미 자원을 사용하는 중이면 나머지 스레드의 접근을 막는 것이다.
    @Synchronized fun synchronizedMethod() {
        println("inside: ${Thread.currentThread()}")
    }

    start()
    start()
    Thread.sleep(10)
    stop()

    massiveRun {
//        코드상에서는 counter++라는 한 줄의 코드지만 이것이 컴파일돼서 CPU가 실행할 명령어로 변환되면 여러 개의 명령어로 분할되므로
//        예상치 못한 결과를 초래할 수 있다. 즉, counter의 증가를 시작했지만 CPU의 최소 명령어가 마무리되지 않은 시점에 루틴이
//        중단되어서 다른 루틴이 counter를 건드릴 수 있다. 내부적으로는 값이 증가되지 못하고 다른 루틴이 실행되어버린 셈.
//        원자 변수를 사용하면 이때 counter의 증가 연산 부분을 CPU의 기계어 명령 하나로 컴파일하게 된다.
//        counter++ 증가 연산 시 값의 무결성에 문제가 발생할 수 있음
        
        counter.incrementAndGet()
        
        
    }
//    println("Counter = $counter")
    println("Counter = ${counter.get()}")

    massiveRun {
        withContext(counterContext) {
            counter2++
        }
    }
    println("Counter2 = $counter2")

    massiveRun2(counterContext) {
        counter2++
    }
    println("Counter2 = $counter2")

    massiveRun {
        mutex.withLock {
            counterMutex++ // 임계구역 코드
        }
    }
}