enum class TokenOperatorType {
    Plus,
    Minus,
    Multiply,
    Divide
}

enum class TokenParenthesisType {
    Open,
    Close
}

abstract class Token()

class OperatorToken(val type: TokenOperatorType) : Token()
class ParenthesisToken(val type: TokenParenthesisType) : Token()

class NumberToken(val value: Double) : Token()