package expres.command

open class Command(open val name: String, open val type: CommandType)

data class VariableSetterCommand(
	override val name: String,
	val value: List<Command>
	): Command(name, CommandType.VARIABLE_SETTER)

data class ArrayInitCommand(
	override val name: String
	): Command(name, CommandType.ARRAY_INIT)

data class SizedArrayInitCommand(
	override val name: String,
	val size: List<Command>
	): Command(name, CommandType.ARRAY_INIT_WITH_SIZE)

data class ArrayElementSetterCommand(
	override val name: String,
	val value: List<Command>
	): Command(name, CommandType.ARRAY_ELEMENT_SETTER)

data class FunctionCallCommand(
	override val name: String,
	val arguments: List<List<Command>>
	): Command(name, CommandType.FUNCTION_CALL)

data class VariableGetterCommand(
	override val name: String
	): Command(name, CommandType.VARIABLE_GETTER)

data class ArrayElementGetterCommand(
	override val name: String
	): Command(name, CommandType.ARRAY_ELEMENT_GETTER)

data class BinaryOperator(
	override val name: String,
	val first: List<Command>, val second: List<Command>, val operator: Operator
	): Command(name, CommandType.BINARY_OPERATION)

data class UnaryOperator(
	override val name: String,
	val operand: List<Command>, val operator: Operator
): Command(name, CommandType.UNARY_OPERATION)

enum class CommandType(val form: String) {
	VARIABLE_SETTER         ("setter keyword comma ..."),
	ARRAY_INIT_WITH_SIZE    ("setter keyword open_index close_index comma ..."),
	ARRAY_INIT              ("setter keyword open_index close_index"),
	ARRAY_ELEMENT_SETTER    ("setter keyword open_index ... close_index comma ..."),
	FUNCTION_CALL           ("keyword open_bracket ... close_bracket"),
	VARIABLE_GETTER         ("keyword"),
	ARRAY_ELEMENT_GETTER    ("keyword open_index ... close_index"),
	BINARY_OPERATION        ("[number keyword (open_bracket ... close_bracket)] binary_operator [number keyword (open_bracket ... close_bracket)]"),
	UNARY_OPERATION         ("unary_operator [number keyword (open_bracket ... close_bracket)]")
}

enum class Operator {
	PLUS,
	MINUS,
	DIVIDE,
	MULTIPLY,
	MODULO,
	POWER,
	EQUALS,
	LESS_THAN,
	GREATER_THAN,
	LESS_EQUAL_THAN,
	GREATER_EQUAL_THAN,
	NOT,
	OR,
	AND
}
