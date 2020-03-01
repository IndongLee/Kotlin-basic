package Chapter11

import kotlinx.coroutines.*

// 코루틴은 항상 특정한 문맥에서 실행되는데, 어떤 문맥에서 실행할지는 디스패처가 결정한다.

private fun main() = runBlocking {
    val job = arrayListOf<Job>()
//    1) Unconfined 문맥 : 호출자 스레드에서 코루틴을 시작하지만 첫 번째 지연점까지만 실행한다. 특정 스레드 풀에 가두지 않고,
//                         첫 번째 일시 중단 후 호출된 지연 함수에 의해 재개된다. 권장하지 않는 옵션
    job += launch(Dispatchers.Unconfined) {
        println("Unconfined: \t\t ${Thread.currentThread().name}")
    }

//    2) 부모의 문맥, 여기서는 runBlocking의 문맥
    job += launch(coroutineContext) {
        println("coroutineContext: \t ${Thread.currentThread().name}")
    }

//    3) 기본 문맥 : Dispatchers.Default는 기본 문맥인 CommonPool에서 실행되고 GlobalScope로도 표현된다.
//                  따라서 launch(Dispatchers.Default)와 GlobalScope.launch는 같은 표현이다.
//                  이것은 공유된 백그라운드 스레드의 CommonPool에서 코루틴을 실행하도록 한다. 스레드를 생성하지 않고 기존에 있는 것을 이용한다.
//                  연산 중심의 코드에 적합
    job += launch(Dispatchers.Default) {
        println("Default: \t\t ${Thread.currentThread().name}")
    }

//    4) 입출력 중심의 문맥 : 입출력 위주의 동작을 하는 코드에 적합한 공유된 풀. 따라서 블로킹 동작이 많은 파일이나 소켓 I/O 처리에 좋다.
    job += launch(Dispatchers.IO) {
        println("IO: \t\t ${Thread.currentThread().name}")
    }

//    5) 아무런 인자가 없을 때
    job += launch {
        println("main runBlocking: ${Thread.currentThread().name}")
    }

//    6) 새 스레드를 생성하는 문맥 : 새 스레드를 만들기 때문에 비용이 많이 들고 더 이상 필요하지 않으면 해제하거나 종료시켜야 한다.
//                                 코루틴 안에 또 다른 코루틴을 정의하면 자식 코루틴이 된다. 부모가 취소되는 경우 자식은 재귀적으로 취소된다.
//                                 따라서 필요한 경우 명시적으로 join() 함수를 사용해 명시적으로 처리를 기다리도록 만들 수 있다.
    job += launch(newSingleThreadContext("MyThread")) { // 새 스레드를 생성
        println("MyThread: \t\t ${Thread.currentThread().name}")
    }

//    repeat() 함수를 통한 반복 동작하기
//    지속적으로 반복하는 코드를 작성하기 위해 repeat() 함수를 이용할 수 있다.
//    이렇게 백그라운드에서 실행하는 일종의 데몬(daemon) 스레드를 구성할 수 있다.
    GlobalScope.launch { // GlobalScope로 생명주기를 한정했기 때문에 메인 스레드가 종료되면 더 이상 진행되지 않는다.
        repeat(5) { i->
            println("I'm sleeping $i ...")
            delay(500L)
        }
    }
    delay(1300L)

//    코루틴 작업 취소하기
//    만일 join() 함수만 사용하면, main() 함수가 job의 완료를 기다리기 때문에 repeat() 함수의 1,000번 반복 실행이 모두 진행된다.
//    cancel() 함수를 사용하면 job2는 1.3초 뒤 작업을 취소하고 main() 함수가 종료된다.
    val job2 = launch {
        delay(2000L)
        repeat(1000) { i->
            println("I'm sleeping $i ...")
            delay(500L)
        }
    }
    delay(3300L)
//    job2.join()
    job2.cancel()

//    finally의 살행 보장
//    try ~ finally 구문을 사용해 finally 블록에서 코루틴의 종료 과정을 처리하도록 할 수 있다.
    val job3 = launch {
        try {
            delay(3500L)
            repeat(1000) { i ->
                println("I'm sleeping $i ...")
                delay(500L)
            }
//            만일 finally 블록에서 지연 함수를 사용하려고 하면 코루틴이 취소되므로 지연 함수를 사용할 수 없다.
//            그 외에 파일을 닫거나 통신 채널을 닫는 등의 작업은 넌블로킹 형태로 작동하며 지연 함수를 포함하고 있지 않기 때문에 문제가 없다.
//            만일 finally 블록에 시간이 걸리는 작업이나 지연 함수가 사용될 경우 실행을 보장하기 위해 NonCancellable 문맥에서 작동하도록 해야 한다.
        } finally {
//            println("Bye")
            withContext(NonCancellable) {
                println("I'm running finally")
                delay(1000L)
                println("Non-Cancellable")
            }
        }

    }
    delay(4800L)
    job3.cancelAndJoin() // 작업을 취소하고 완료될 때까지 기다림
    println("main: Quit!")

//    코드를 중단하기 위해 코루틴에 조건식을 넣으려고 할 때, 연산이 마무리되기 전까지는 조건식에 의해 코루틴이 중단되지 않는다.
    val job4 = GlobalScope.launch {
        delay(5000L)
        var nextPrintTime = System.currentTimeMillis()
        var i = 0
//        while (i < 5) {
//        취소 시그널을 받아 루프를 중단하려면 isActive를 사용한다.
        while (isActive) {
            if (System.currentTimeMillis() >= nextPrintTime) {
                println("I'm sleeping ${i++}")
                nextPrintTime += 500L
            }
        }
    }
    delay(6300L)
    println("main: I'm tired of waiting!")
    job4.cancelAndJoin()
    println("main: Now I'm can quit")

//    withTimeout은 시간이 만료되면 block을 취소시키고 TimeoutCalcellationException 오류를 발생시킨다.
//    예외를 발생시키지 않고 null로 처리하려면 withTimeoutOrNull을 사용한다.
    try {
        withTimeout(1300L) {
            repeat(1000) { i ->
                println("I'm sleeping $i ...")
                delay(500L)
            }
        }
    } catch (e: TimeoutCancellationException) {
        println("Time out with $e")
    }
}