fun main() {
    Lexer("""
        Hello world this is a test for the while loop;
        > varName;
        greater than next > >= > > ;>=
    """.trimIndent()).tokens().also { println(it.size) }.forEach(::println)
}
