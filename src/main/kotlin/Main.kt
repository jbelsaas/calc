fun main(args: Array<String>) {
    val scanner = Tokenizer()
    val tokens = scanner.scan("3+6*((3+6)*3)/2")
    if(tokens == null && scanner.lastError != "") println(scanner.lastError)
    else if(tokens == null) println("unknown error")
    else {
        val parser = Parser(tokens)
        println("Result: " + parser.parseExpression())
    }

}