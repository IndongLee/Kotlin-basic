package Chapter11_Coroutine

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

// 동기적(synchronous) : 프로그래밍에서 순서대로 작업을 수행하여 1개의 루틴을 완료한 후 다른 루틴을 실행하는 방식
// 비동기적(asynchronous) : 여러 개의 루틴이 선행 작업의 순서나 완료 여부와 상관없이 실행되는 방식
// 병행 수행(Concurrentcy) : 여러 개의 코어에서 태스크가 동시에 수행되는 것

// 하나의 프로그램이 실행되면 프로세스가 시작되는데, 프로세스는 실행되는 메모리, 스택, 열린 파일 등을 모두 포함하기 때문에
// 프로세스 간 문맥 교환(Context-Switching)을 할 때 많은 비용이 든다.
// 반면 스레드는 자신의 스택만 독립적으로 가지고 나머지는 대부분 스레드끼리 공유하므로 문맥 교환 비용이 낮아 많이 사용된다.
// 문맥(Context) : 프로세스가 가지고 있는 코드, 데이터, 열린 파일의 식별자, 동적 할당 영역, 스택 등
//                 스레드는 프로세스의 코드, 데이터, 열린 파일 등을 공유하는 작은 독립된 실행 단위다.
// 문맥 교환 : 하나의 프로세스나 스레드가 CPU를 사용하고 있는 상태에서 다른 프로세스나 스레드가 CPU를 사용하도록 하기 위해
//            이전 프로세스의 상태(문맥)을 보관하고, 새로운 프로세스의 상태를 적재하는 과정을 말한다.

// 코틀린의 코루틴(Coroutine)은 문맥 교환이 없고 최적화된 비동기 함수를 통해 비선점형으로 작동하는 특징이 있어
// 협력형 멀티태스킹(Cooperative Multitasking)을 구현할 수 있게 해준다.

// 스레드 생성하기
// 1) Thread 클래스를 상속받아 구현하기
// Thread를 직접 상속받아 구현한다. 다중 상속이 허용되지 않는다.
class SimpleThread : Thread() {
    override fun run() {
        println("Currnet Threads: ${Thread.currentThread()}")
    }
}

// 2) Runnable 인터페이스로부터 run() 메서드 구현하기
// Runnable 인터페이스를 구현한 것이므로 다른 클래스를 상속할 수 있다.
class SimpleRunnable : Runnable {
    override fun run() {
        println("Currnet Threads: ${Thread.currentThread()}")
    }
}

// 사용자 함수를 통해 스레드 생성하기
fun thread(start: Boolean = true, isDaemon: Boolean = false,
           contextClassLoader: ClassLoader? = null, name: String? = null,
           priority: Int = -1, block: () -> Unit) : Thread {
    val thread = object : Thread() {
        override fun run() {
            block()
        }
    }
    if (isDaemon) thread.isDaemon = true
    if (priority > 0) thread.priority = priority
    if (name != null) thread.name = name
    if (contextClassLoader != null) thread.contextClassLoader = contextClassLoader
    if (start) thread.start()
    return thread
}

fun main() {
    val thread = SimpleThread()
    thread.start()

    val runnable = SimpleRunnable()
    val thread1 = Thread(runnable)
    thread1.start()

//    3) 익명 객체를 통한 스레드 실행
    object : Thread() {
        override fun run() {
            println("Currnet Threads(object): ${Thread.currentThread()}")
        }
    }.start()

//    4) Runnable을 전달하는 람다식
    Thread {
        println("Currnet Threads(lambda): ${Thread.currentThread()}")
    }.start()

    thread(start = true) {
        println("Current Threads(Custom Function): ${Thread.currentThread()}")
        println("Priority: ${Thread.currentThread().priority}")
        println("Name: ${Thread.currentThread().name}")
        println("IsDaemon: ${Thread.currentThread().isDaemon}")
    }

//    스레드 풀 사용하기
//    애플리케이션의 비즈니스 로직을 설계할 때는 스레드가 자주 재사용된다. 따라서 몇 개의 스레드를 먼저 만들어 놓고 필요에 따라 재사용하도록 설계할 수 있다.
//    보통 이런 경우엔 newFixedThreadPool()로 스레드를 인자 수만큼 만들고 작업을 수행할 때 여기에서 재사용 가능한 스레드를 고르게 한다.
    val myService: ExecutorService = Executors.newFixedThreadPool(8)
    var i = 0

/*
    while (i < items.size) { // 아주 큰 데이터를 처리할 때
        val item = items[i]
        myService.submit {
            processItem(item) // 여기서 아주 긴 시간 동안 처리하는 경우
        }
        i++
    }
*/
}