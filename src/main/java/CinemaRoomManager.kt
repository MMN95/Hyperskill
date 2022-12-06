fun main() {
    println("Enter the number of rows:")
    val rows = readLine()!!.toInt()
    println("Enter the number of seats in each row:")
    val seatsInRow = readLine()!!.toInt()
    val cinema = Cinema(rows, seatsInRow)
    cinema.showActionMenu()
}

class Cinema(val rows: Int, val seatsInRow: Int) {

    val seatList = initCinema()

    var purchasedTickets = 0
    var ticketsPercentage = 0.0
    var currentIncome = 0
    var totalIncome = 0

    val totalSeats = rows * seatsInRow
    val firstHalf = rows / 2 * seatsInRow
    val secondHalf = totalSeats - firstHalf

    fun initCinema(): MutableList<MutableList<String>> {
        val list = MutableList(0) { MutableList(0) { " " } }
        for (i in 1..rows) list.add(MutableList(seatsInRow) { "S" })
        return list
    }

    fun showActionMenu() {
        do {
            println("1. Show the seats\n2. Buy a ticket\n3. Statistics\n0. Exit")
            val option = readLine()!!
            when (option) {
                "1" -> showSeats()
                "2" -> buyTicket()
                "3" -> showStatistics()
                else -> println("Unknown option. Please, try again.")
            }
        } while (option != "0")
    }

    fun showSeats() {
        print("Cinema:\n  ")
        for (i in 0 until seatList[0].size) print("${i + 1} ")
        println()
        for (i in 0 until seatList.size) println("${i + 1} ${seatList[i].joinToString(" ")}")
    }

    fun buyTicket() {
        var isValidSeat = false
        while (!isValidSeat) {
            println("Enter a row number:")
            val rowNumber = readLine()!!.toInt()
            println("Enter a seat number in that row:")
            val seatNumberInRow = readLine()!!.toInt()
            val seatNumber = (rowNumber - 1) * rows + seatNumberInRow
            if (seatNumber !in 1..totalSeats || rowNumber > rows || seatNumberInRow > seatsInRow) {
                println("Wrong input!")
            } else if (seatList[rowNumber - 1][seatNumberInRow - 1] == "B") {
                println("That ticket has already been purchased!")
            } else if (seatNumber in 1..totalSeats && seatList[rowNumber - 1][seatNumberInRow - 1] != "B") {
                isValidSeat = true
                currentIncome += when {
                    totalSeats <= 60 -> {
                        println("Ticket price: $10")
                        10
                    }
                    else -> {
                        if (seatNumber in 1..firstHalf) {
                            println("Ticket price: $10")
                            10
                        } else {
                            println("Ticket price: $8")
                            8
                        }
                    }
                }
                seatList[rowNumber - 1][seatNumberInRow - 1] = "B"
                purchasedTickets++
            }
        }
    }

    fun showStatistics() {
        println("Number of purchased tickets: $purchasedTickets")
        ticketsPercentage = (purchasedTickets * 100.0) / totalSeats
        println("Percentage: ${"%.2f".format((purchasedTickets * 100.0) / totalSeats)}%")
        println("Current income: $$currentIncome")
        totalIncome = if (totalSeats <= 60) totalSeats * 10 else firstHalf * 10 + secondHalf * 8
        println("Total income: $$totalIncome")
    }
}