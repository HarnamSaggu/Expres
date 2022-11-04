package expres

class Parser(tokens: List<Token>) {
	private val sentences: MutableList<MutableList<Token>> = mutableListOf(mutableListOf())
	private var sentence: MutableList<Token> = mutableListOf()

	init {
		for (token in tokens) {
			if (token.type == TokenType.SEMICOLON) {
				sentences.add(mutableListOf())
			} else {
				sentences.last().add(token)
			}
		}
		printSentences()

		for (sentence in sentences) {
			this.sentence = sentence
			if (matchTokensToPattern("[VARIABLE MUTATOR] [KEYWORD] [COMMA] [...value...]")) {
				/*
				command type - mutator
				name of variable - keyword
				value - commands of [...value...]
				 */


				println("variable assigment with value")
			} else if (matchTokensToPattern("[VARIABLE MUTATOR] [KEYWORD] [OPEN INDEX] [CLOSE INDEX] [COMMA] [...value...]")) {

				/*
				command type - mutator
				name of array - keyword
				size - commands of [...values...]
				 */

				println("array assigment with size")
			} else if (matchTokensToPattern("[VARIABLE MUTATOR] [KEYWORD] [OPEN INDEX] [CLOSE INDEX]")) {
				/*
				command type - mutator
				name of array - keyword
				 */


				println("array assigment")
			} else if (matchTokensToPattern("[VARIABLE MUTATOR] [KEYWORD] [OPEN INDEX] [...value...] [CLOSE INDEX] [COMMA] [...value...]")) {

				/*
				command type - mutator
				name of array - keyword
				index - commands of [...values...][0]
				size - commands of [...values...][1]
				 */

				println("array element assigment with value")
			} else if (matchTokensToPattern("[KEYWORD] [OPEN BRACKET] [...value...] [CLOSE BRACKET]")) {
				/*
				command type - function
				function name - keyword
				arguments - list of commands of [...value...]
				 */


				println("function")
			}
			// TODO matching for variable, array element, operation
		}
	}

	private fun matchTokensToPattern(form: String, sentence: MutableList<Token> = this.sentence): Boolean =
		sentenceToUTF8(sentence).matches(tokensToRegex(form))

	private fun sentenceToUTF8(sentence: MutableList<Token>): String {
		return sentence.map { token -> token.type.toRegexSafeCode() }.joinToString("")
	}

	private fun tokensToRegex(form: String): Regex =
		("^" + form.substring(1, form.length - 1).split("]\\s*\\[".toRegex()).joinToString(separator = "") {
			val type = it.replace(' ', '_')
			if (type == "...value...") {
				".+"
			} else {
				TokenType.valueOf(type).toRegexSafeCode().toString()
			}
		}).toRegex()

	private fun TokenType.toRegexSafeCode(): Char {
		return Char(192 + TokenType.values().indexOf(this))
	}

	private fun printSentences(lists: MutableList<MutableList<Token>> = sentences) {
		for (sentence in lists) {
			sentence.forEach { print("$it ") }
			println()
//			println("$${sentence.size}")
		}
	}
}
