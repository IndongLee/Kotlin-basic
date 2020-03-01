package Chapter11

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.selects.select
import java.util.*

// 채널(Channel) : 자료를 서로 주고 받기 위해 약속된 일종의 통로. 코루틴의 채널은 넌블로킹 전송 개념으로 사용되고 있다.
// 채널을 구현할 때는 SendChannel과 ReceiveChannel 인터페이스를 이용해 값들의 스트림을 전송하는 방법을 제공한다.
// 실제 전송에는 send()와 receive() 함수를 사용한다.

// 송신자는 SendChannel에서 채널이 꽉 차 있는지, 즉 isFull 값이 true인지 살펴보고 꽉 차 있으면 일시 지연된다.
// 만일 close()에 의해 닫으면 isCloseedForSend가 true로 지정되어 isFull은 false를 반환할 수 있다.
// 수신자는 isEmpty가 true라면 비어 있으므로 가져갈 게 없는 루틴은 일시 지연된다.
// 마찬가지로 닫을 경우 isClosedForReceive에 의해 false를 반환받을 수 있다.
// 그 밖의 SendChannel과 ReceiveChannel에는 다음과 같은 메서드를 사용할 수 있다.
// SendChannel.offer(element: E): Boolean  가능하면 요소를 채널에 추가. 채널이 꽉 찬 경우 false를 반환
// ReceiveChannel.poll(): E  요소를 반환. 채널이 비어 있으면 null을 반환

private fun main() = runBlocking {
    val channel = Channel<Int>()
    launch {
//        여기에 다량의 CPU 연산 작업이나 비동기 로직을 둘 수 있음.
        for (x in 1..5) channel.send(x * x)
//        일반 큐와는 다르게 더 이상 전달 요소가 없으면 채널을 닫을 수 있다.
//        보통 for문을 구성해 채널을 받고 close()를 사용하면 바로 채널을 닫는 것이 아니라 닫겠다는 특수한 토큰을 보낸다.
        channel.close()
    }
    repeat(5) { println(channel.receive()) }
    println("Done!")

//    produce 생산자 소비자 패턴
//    produce는 채널이 붙어 있는 코루틴으로 생산자 측면의 코드를 쉽게 구성할 수 있다.
//    채널에 값을 보내면 생산자로 볼 수 있고, 소비자는 consumeEach 함수를 확장해 for문을 대신 해서 저장된 요소를 소비한다.
//    생산자를 위한 함수 생성
    fun CoroutineScope.producer(): ReceiveChannel<Int> = produce {
        var total: Int = 0
        for (x in 1..5) {
            total += x
            send(total)
        }
    }
    val result = producer() // 값의 생산
    result.consumeEach { print("$it ") } // 소비자 루틴 구성
    println()

//    버퍼를 가진 채널
//    채널에는 기본 버퍼가 없으므로 send() 함수가 먼저 호출되면 receive() 함수가 호출되기 전까지 send() 함수는 일시 지연된다.
//    반대의 경우도 receive() 함수가 호출되면 send() 함수가 호출되기 전까지 receive() 함수는 지연된다.

//    하지만 채널에 버퍼 크기를 주면 지연 없이 여러 개의 요소를 보낼 수 있게 된다.
//    Channel() 생성자에는 capacity 매개변수가 있으며 이것이 버퍼의 크기를 결정한다.
    val bufferedChannel = Channel<Int>(3) // 버퍼 capacity 값을 줌
    val sender = launch(coroutineContext) { 
        repeat(10) {
            println("Sending $it")
            bufferedChannel.send(it) // 지속적으로 보내다가 꽉 차면 일시 지연
        }
    }
    delay(1000) // 아무것도 받지 않고 1초 기다린 후
    sender.cancel() // 송신자의 작업을 취소
    bufferedChannel.close()

//    select 표현식
//    다양한 채널에서 무언가 응답해야 한다면 각 채널의 실행 시간에 따라 결과가 달라질 수 있는데 이때 select를 사용하면 표현식을 통해 결과를 받을 수 있다.
    val routine1 = GlobalScope.produce {
        delay(Random().nextInt(1000).toLong())
        send("A")
    }
    val routine2 = GlobalScope.produce {
        delay(Random().nextInt(1000).toLong())
        send("B")
    }
    val selectResult = select<String> { // 먼저 수행된 것을 받게 된다.
        routine1.onReceive { result -> result }
        routine2.onReceive { result -> result }
    }
    println("Result was $selectResult")

}