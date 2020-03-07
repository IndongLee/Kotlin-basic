package Chapter10_StandardFunctionAndIO

import java.io.*
import java.lang.Exception
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption


fun main() {
    print("Enter: ")
    val input = readLine()!!
    println("You entered: $input")

//    간단한 데이터 - readBytes, readLines, readText
//    대량의 데이터 - copyTo, forEachBlock, forEachLine
//    InputStream, Reader, Writer를 쓸 때는 호출 후 사용이 완료되면 반드시 닫아야 한다.
//    Java.io - 스트림 방식 / 넌버퍼(Non-Buffer) / 비동기 지원 안 함(블로킹 방식)
//    Java.nio - 채널 방식 / 버퍼 / 비동기 지원함(넌블로킹 지원)
//    스트림 방식(Stream) : 발생한 데이터를 물 흐르듯 바로 전송
//    채널 방식(Channel) : 여러 개의 수로를 사용해 병목 현상을 줄이는 방식. 양방향으로 입출력이 모두 가능
//    버퍼(Buffer) : 송/수신 사이에 임시적으로 사용할 공간을 줌. 버퍼 방식은 좀 더 유연한 처리가 가능함
//    프로그램에서 만일 쓰려고 하는데 쓸 공간이 없으면 공간이 비워질 때까지 기다리게 된다. 읽으려고 할 때 읽을 내용이 없을 때도 마찬가지
//    블로킹(Blocking) : 위의 경우에서 공간이 비워지거나 채워지기 전까지는 쓰고 읽을 수 없기 때문에 호출한 코드에서 계속 멈춰 있는 것
//    넌블로킹(Non-Blocking) : 메인 코드의 흐름을 방해하지 않도록 입출력 작업 시 스레드나 비동기 루틴에 맡겨 별개의 흐름으로 작업하게 되는 것

//    파일에 쓰기 : File 클래스의 write()를 이용해 경로에 지정된 파일을 생성하고 내용을 쓴다.
//    StandardOpenOption : 파일을 생성할 때의 옵션
//    1) READ - 파일을 읽기용으로 연다. 2) WRITE - 파일을 쓰기용으로 연다.
//    3) APPEND - 파일이 존재하면 마지막에 추가한다. 4) CREATE - 파일이 없으면 새 파일을 생성한다.
    val path = "파일 경로"
    val text = "안녕하세요!"
    try {
        Files.write(Paths.get(path), text.toByteArray(), StandardOpenOption.CREATE)
    } catch (e: IOException) {

    }

//    PrintWriter, BufferedWriter 사용하기
//    null인 내용을 파일에 쓰는 경우 printWriter는 null을 파일에 쓸 수 있지만 bufferedWriter는 NPE를 발생시킬 수 있다.
    val outString = "안녕하세요!"
    val file = File(path).printWriter().use { it.println(outString) }
    val fileBuffer = File(path).bufferedWriter().use { it.write(outString) }

//    writeText : 코틀린에서 확장해 감싼 메서드로 제공
//    null을 사용하는 경우 자료형 불일치 오류가 발생한다.
    val fileWrite = File(path)
    fileWrite.writeText(outString)
    fileWrite.appendText("Hello!")

//    FileWriter
    val writer = FileWriter(path, true)
    try {
        writer.write(outString)
    } catch (e: Exception) {

    } finally {
        writer.close()
    }
    val writer2 = FileWriter(path, true).use { it.write(outString) }

//    파일 읽기
    try {
        val read = FileReader(path)
        println(read.readText())
    } catch (e: Exception) {
        println(e.message)
    }
}