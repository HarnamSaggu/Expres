fun main() {
    val sourceCode = """
        > str, "hello world";
        if(1, {println(str)}, 0, {> str, "help, world"})
    """.trimIndent()
    val tokens = lex(sourceCode)
    tokens.forEach(::println)
    println(tokens.size)
    println()
    val parser = Parser(tokens)
}
