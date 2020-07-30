package com.example.ktapp

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.lang.Integer.parseInt

//Kotlin 应用程序的入口点是 main 函数。
//val 定义只读局部变量使用关键字 val 定义。只能为其赋值一次。
//可重新赋值的变量使用 var 关键字：
//Unit 返回类型可以省略：
//在 Kotlin 中，if 也可以用作表达式 fun maxOf(a: Int, b: Int) = if (a > b) a else b
//当某个变量的值可以为 null 的时候，必须在声明处的类型后添加 ? 来标识该引用可为空。
//is 运算符检测一个表达式是否某类型的一个实例。
//倒序downTo     =====>>>9 downTo 0
//使用 lambda 表达式来过滤（filter）与映射（map）集合
//for (i in 1..100) { …… }  // 闭区间：包含 100
//for (i in 1 until 100) { …… } // 半开区间：不包含 100
//for (x in 2..10 step 2) { …… }
//for (x in 10 downTo 1) { …… }
//if (x in 1..10) { …… }
//遍历 map/pair型list
//  for ((k, v) in map) {
//    println("$k -> $v")
//  }
//创建单例
//object Resource {
//    val name = "Name"
//}

//可以用 + 操作符连接字符串。这也适用于连接字符串与其他类型的值， 《必须表达式中的第一个元素是字符串》
//请注意，在大多数情况下，优先使用字符串模板或原始字符串而不是字符串连接
//suspend方法只能在协程里面调用, 不能在协程外面调用.
//suspend方法本质上, 与普通方法有较大的区别, suspend方法的本质是异步返回(注意: 不是异步回调). 后面我们会解释这句话的含义.


fun main() {


//    println(sayHello("刘涛")) // 协程已在等待时主线程还在继续?

//    job.join()

//    Thread.sleep(2000L) // 阻塞主线程 2 秒钟来保证 JVM 存活
//    val files = File("Test").listFiles()
//
//    println(files?.size ?: "empty")
//
//    fllll();
//   for (a in 9 downTo 0 step 2){
//       print(a);
//   }
//   println("你好啊!")
//   println("a的值是：${addNumber(4, 9)}")
//
//   val a: Int = 1
//   val b = 2
//   val c: Int
//   c = 3
//   println("你好啊!")
//    val items = listOf("aap1","afsd","dsffffggfdd");
//   for (item in items.indices){
//       println(items[item])
//   }
//   inRange()
//    println(describe(1))
//    println(describe("Hello"))
//    println(describe(1000L))
//    println(describe(2))
//    println(describe("other"))
      addAll(100000)
}

fun addAll(num: Int): Int {
    println("运行次数${num}")
    if (num == 1) {
        return 1
    } else {
        return num + addAll(num - 1);
    }
}

fun sayHello(name: String): String {
    return "${name}:你好啊"
}

fun fooo(): Int {     // 不良
    return 1
}

fun foo() = 1        // 良好

fun f(x: String) =
    x.length

fun fill() {
    val list = listOf(1, 2, 3, 4, 12, -5)
    val positives = list.filter { it > 0 }.forEach {
        println(it)
    }
    val fruits = listOf("banana", "avocado", "apple", "kiwifruit")
    fruits.filter { it.startsWith("a") }
        .sortedBy { it }//倒序
        .forEach { println(it) }
//    fruits.filter { it.startsWith("a") }
//        .sortedBy { it }
//        .map { it.toUpperCase() }
//        .forEach { println(it) }
}

fun addNumber(a: Int, b: Int): Int {
    return a + b;
}

fun printProduct(arg1: String, arg2: String) {
    val x = parseInt(arg1)
    val y = parseInt(arg2)

    // 直接使用 `x * y` 会导致编译错误，因为它们可能为 null
    if (x != null && y != null) {
        // 在空检测后，x 与 y 会自动转换为非空值（non-nullable）
        println(x * y)
    } else {
        println("'$arg1' or '$arg2' is not a number")
    }

}

fun inRange() {
    val x = 10
    val y = 9
    if (x in 1..y + 2) {
        println("fits in range")
    }
}

fun describe(obj: Any): String = when (obj) {
    1 -> "One"
    "Hello" -> "Greeting"
    is Long -> "Long"
    !is String -> "Not a string"
    else -> "Unknown"
}

//返回 when 表达式
fun transform(color: String): Int {
    return when (color) {
        "Red" -> 0
        "Green" -> 1
        "Blue" -> 2
        else -> throw IllegalArgumentException("Invalid color param value")
    }
}

//“try/catch”表达式
fun test() {
    val result = try {
//        count()
    } catch (e: ArithmeticException) {
        throw IllegalStateException(e)
    }

    // 使用 result
}

//“if”表达式
fun foo(param: Int) {
    val result = if (param == 1) {
        "one"
    } else if (param == 2) {
        "two"
    } else {
        "three"
    }
}

//返回类型为 Unit 的方法的 Builder 风格用法
fun arrayOfMinusOnes(size: Int): IntArray {
    return IntArray(size).apply { fill(-1) }
}
//单表达式函数
//fun theAnswer() = 42
//等价于

fun theAnswer(): Int {
    return 42
}