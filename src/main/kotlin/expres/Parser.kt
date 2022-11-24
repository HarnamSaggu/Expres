package expres

import expres.command.Command
import expres.command.CommandType
import expres.command.VariableSetterCommand

class Parser(tokens: List<Token>) {
	private val binaryOperatorGroup = "[${
		sentenceToUTF8(
			mutableListOf(
				Token(TokenType.PLUS),
				Token(TokenType.MINUS),
				Token(TokenType.DIVIDE),
				Token(TokenType.PLUS),
				Token(TokenType.MINUS),
				Token(TokenType.DIVIDE),
				Token(TokenType.MULTIPLY),
				Token(TokenType.MODULO),
				Token(TokenType.POWER),
				Token(TokenType.EQUALS),
				Token(TokenType.LESS_THAN),
				Token(TokenType.GREATER_THAN),
				Token(TokenType.LESS_EQUAL_THAN),
				Token(TokenType.GREATER_EQUAL_THAN),
				Token(TokenType.OR),
				Token(TokenType.AND),
			)
		)
	}]"
	private val unaryOperatorGroup = "[${sentenceToUTF8(mutableListOf(Token(TokenType.NOT)))}]"
	private val commandRegexs: HashMap<CommandType, Regex>

	private val sentences: MutableList<MutableList<Token>> = mutableListOf(mutableListOf())
	private var sentence: MutableList<Token> = mutableListOf()
	val commands: MutableList<Command>

	init {
		commandRegexs = hashMapOf()
		for (value in CommandType.values()) {
			commandRegexs[value] = tokensToRegex(value.form)
		}

		for (token in tokens) {
			if (token.type == TokenType.SEMICOLON) {
				sentences.add(mutableListOf())
			} else {
				sentences.last().add(token)
			}
		}
		printSentences()

		commands = mutableListOf()

		for (sentence in sentences) {
			this.sentence = sentence
			val charSentence = sentenceToUTF8(sentence)
			val command = when {
				charSentence.matches(CommandType.VARIABLE_SETTER.regex()) -> {
					VariableSetterCommand(sentence[1].value ?: "", Parser(sentence.subList(3, sentence.size)).commands)
				}

				charSentence.matches(CommandType.ARRAY_INIT_WITH_SIZE.regex()) -> {

				}

				charSentence.matches(CommandType.ARRAY_INIT.regex()) -> {

				}

				charSentence.matches(CommandType.ARRAY_ELEMENT_SETTER.regex()) -> {

				}

				charSentence.matches(CommandType.FUNCTION_CALL.regex()) -> {

				}

				charSentence.matches(CommandType.VARIABLE_GETTER.regex()) -> {

				}

				charSentence.matches(CommandType.ARRAY_ELEMENT_GETTER.regex()) -> {

				}

				charSentence.matches(CommandType.BINARY_OPERATION.regex()) -> {

				}

				charSentence.matches(CommandType.UNARY_OPERATION.regex()) -> {

				}


				else -> {

				}
			}
		}
	}

	private fun CommandType.regex(): Regex = tokensToRegex(form)

	private fun sentenceToUTF8(sentence: MutableList<Token>): String {
		return sentence.map { token -> token.type.toRegexSafeCode() }.joinToString("")
	}

	private fun tokensToRegex(form: String): Regex {
		var pattern = ""
		var i = 0
		while (i < form.length) {
			if (form[i].isLetter()) {
				var tokenHandle = ""
				while (i < form.length) {
					if (!form[i].isLetter() && form[i] != '_') break
					tokenHandle += form[i++]
				}
				pattern += when (tokenHandle) {
					"binary_operator" -> binaryOperatorGroup
					"unary_operator" -> unaryOperatorGroup
					else -> TokenType.valueOf(tokenHandle.uppercase()).toRegexSafeCode()
				}
			} else if (form[i] == '.') {
				pattern += ".+"
				i += 3
			} else {
				pattern += form[i++]
			}
		}
		return Regex(pattern.replace("\\s".toRegex(), ""))
	}

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
