object Solution {

    def superDigit(number: String): String = {
        var digits = number
        while(digits.length > 1){
            digits = digits.map(_.toString.toInt).sum.toString
        }
        return digits
    }

    def main(args: Array[String]) {

        val params = io.Source.stdin.getLines().toList(0).split(" ")
        val n = params(0)
        val k = params(1)

        // First round
        var number = n
        number = superDigit(number)

        // Second round
        number = (number.toInt * k.toInt).toString
        number = superDigit(number)

        print(number)

    }
}

