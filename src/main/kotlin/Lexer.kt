class Lexer(sourceCode: String) {
	private val code: String
	private var pointer = 0
	private val tokens = mutableListOf<Token>()
	private var value: String? = null
	private var type = TokenType.UNDEFINED

	init {
		code = sourceCode.replace("\n", "")

		while (pointer < code.length) {
			value = null
			type = TokenType.UNDEFINED

			if (char() == ' ' || char() == '\t') {
				inc()
				continue
			}

			if (char()?.isLetter() == true) {
				value = ""
				while (char()?.isLetter() == true) {
					value += char()
					inc()
				}
				appendToken(tokenType = TokenType.KEYWORD)
				continue
			}

			if (char() == '>') {
				if (proChar() == '=') {
					type = TokenType.GREATER_EQUAL_THAN
					inc(2)
					appendToken()
					continue
				} else {
					type = if (preChar() == ';') {
						TokenType.VARIABLE_MUTATOR
					} else {
						TokenType.GREATER_THAN
					}
				}
			}

			/*

			match(TokenType.COMMA)

			match(TokenType.SEMICOLON)

			match(TokenType.SNIPPET_START)

			match(TokenType.SNIPPET_END)

			if (char().isDigit()) {
				value = ""
				var decimal = false
				while (char().isDigit() || (!decimal && char() == '.')) {
					if (char() == '.') {
						decimal = true
					}
					value += char()
					inc()
				}
				appendToken(tokenType = TokenType.NUMBER)
				continue
			}

			match(TokenType.PLUS)

			match(TokenType.MINUS)

			match(TokenType.DIVIDE)

			match(TokenType.MULTIPLY)

			match(TokenType.MODULO)

			match(TokenType.POWER)

			match(TokenType.EQUALS)

			if (char() == '<') {
				if (proChar() == '=') {
					inc(2)
					appendToken(tokenType = TokenType.LESS_EQUAL_THAN)
				} else {
					type = TokenType.LESS_THAN
				}
			}
			
			// GREATER (EQUAL) THAN DONE ABOVE

			match(TokenType.NOT)

			match(TokenType.OR)

			match(TokenType.AND)

			match(TokenType.OPEN_BRACKET)

			match(TokenType.CLOSE_BRACKET)

			match(TokenType.OPEN_INDEX)

			match(TokenType.CLOSE_INDEX)

			match(TokenType.TYPE_INDICATOR)

			if (char() == '"') {
				value = ""
				inc()
				while (char() != '"') {
					if (char() == '\\') {
						if (proChar()?.let { "\\\"".contains(it) } == true) {
							inc()
						} else if (proChar() == 'n') {
							value += '\n'
							inc(2)
							continue
						} else if (proChar() == 't') {
							value += '\t'
							inc(2)
							continue
						}
					}
					value += char()
					inc()
				}
				inc()
				appendToken(tokenType = TokenType.STRING_LITERAL)
			}

			*/

			inc()
			appendToken()
		}
	}

	fun tokens(): List<Token> = tokens.toList()

	private fun appendToken(tokenType: TokenType = type, tokenValue: String? = value) =
		tokens.add(Token(tokenType, tokenValue))

	private fun match(checkType: TokenType) {
		type = if (char() == checkType.char) {
			checkType
		} else {
			type
		}
	}

	private fun char(index: Int = pointer): Char? = if (index < code.length) code[index] else null

	private fun preChar(): Char? = if (pointer == 0) null else code[pointer - 1]

	private fun proChar(): Char? = if (pointer + 1 < code.length) code[pointer + 1] else null

	fun inc(x: Int = 1) {
		pointer += x
	}

	fun dec() {
		if (pointer > 0) {
			pointer--
		}
	}
}

data class Token(val type: TokenType, val value: String? = null) {
	override fun toString(): String =
		"[${type.name.replace('_', ' ')}${if (value != null) ", '$value'" else ""}]"
}

enum class TokenType(val char: Char?) {
	UNDEFINED(null),
	KEYWORD(null),
	VARIABLE_MUTATOR('>'),
	COMMA(','),
	SEMICOLON(';'),
	SNIPPET_START('{'),
	SNIPPET_END('}'),
	NUMBER(null),
	PLUS('+'),
	MINUS('-'),
	DIVIDE('/'),
	MULTIPLY('*'),
	MODULO('%'),
	POWER('^'),
	EQUALS('='),
	LESS_THAN('<'),
	GREATER_THAN('>'),
	LESS_EQUAL_THAN(null),
	GREATER_EQUAL_THAN(null),
	NOT('!'),
	OR('|'),
	AND('&'),
	OPEN_BRACKET('('),
	CLOSE_BRACKET(')'),
	OPEN_INDEX('['),
	CLOSE_INDEX(']'),
	TYPE_INDICATOR(':'),
	STRING_LITERAL(null)
}
