object Solution {

    def main(args: Array[String]) {
        val stdin = scala.io.StdIn

        val q = stdin.readLine.trim.toInt

        var expressions: List[String] = List()
        for (nItr <- 1 to q) {
            val expression = stdin.readLine.trim
            expressions = expressions :+ expression
        }

        var factorizations: List[Map[Int, Int]] = List()
        for (expression <- expressions){
            val primeAndPowers = expression.split(" ").map(_.toInt)
            val evenIndexes = Range(0, primeAndPowers.length).filter(_ % 2 == 0)
            val oddIndexes = Range(0, primeAndPowers.length).filter(_ % 2 == 1)

            val primes = evenIndexes.map(primeAndPowers)
            val powers = oddIndexes.map(primeAndPowers)

            val factorization = (primes, powers).zipped.toMap[Int, Int]
            factorizations = factorizations :+ factorization
        }

        val allPrimes = factorizations.flatMap(_.keys).toSet.toSeq.sorted
        val allPowers = allPrimes.map(p => factorizations.map(_.get(p).getOrElse(0)).min)

        val gcdExpression = (allPrimes, allPowers)
            .zipped.filter((p, n) => n > 0)
            .zipped.flatMap((p, n) => List(p, n))
            .mkString(" ")

        print(gcdExpression)
    }
}
