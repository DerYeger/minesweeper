package eu.yeger.minesweeper.model

class DefaultModelBuilder(private val width: Int,
                          private val height: Int,
                          private val bombCount: Int) : ModelBuilder {

    override fun build() = Game(width, height).also {
        initCells(it)
        initBombs(it, bombCount)
        initNumbers(it)
    }

    private fun initCells(game: Game) {
        val cellMap = HashMap<Pair<Int, Int>, Cell>()
        Array(game.height) { y ->
            Array(game.width) {x ->
                Cell(x, y)
                        .setGame(game)
                        .withNeighbors(fromMap(cellMap, x, y))
                        .also { cellMap[x to y] = it }
            }
        }
    }

    private fun fromMap(cellMap: HashMap<Pair<Int, Int>, Cell>, x: Int, y: Int) =
            listOfNotNull(
                    cellMap[x - 1 to y],
                    cellMap[x - 1 to y - 1],
                    cellMap[x to y - 1],
                    cellMap[x + 1 to y - 1]
            )

    private fun initBombs(game: Game, bombCount: Int) {
        game
                .cells
                .shuffled()
                .asSequence()
                .take(bombCount)
                .forEach { it.mine.value = true }
    }

    private fun initNumbers(game: Game) {
        game.cells.forEach { it.number.value = calculateNumber(it) }
    }

    private fun calculateNumber(cell: Cell) = cell.neighbors.filter { it.mine.get() }.count()
}
