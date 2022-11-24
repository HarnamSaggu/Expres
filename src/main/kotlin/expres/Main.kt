package expres

fun main() {
	val sourceCode = """
        > str, "hello world";
        if(1, {println(str)}, 0, {> str, "help, world"});
        
        
        # this is a comment;
    """.trimIndent().also { println(it) }
	val tokens = lex(sourceCode)
	tokens.forEach(::println)
	println()
	val parser = Parser(tokens)
	println()
}
