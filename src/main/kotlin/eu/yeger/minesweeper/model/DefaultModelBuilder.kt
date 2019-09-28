package eu.yeger.minesweeper.model

object DefaultModelBuilder : ModelBuilder {

    override fun build(
            width: Int,
            height: Int,
            mineCount: Int
    ) = Game(width, height).also {
        initCells(it)
        initBombs(it, mineCount)
        initNumbers(it)
    }

    private fun initCells(game: Game) {
        val cellMap = HashMap<Pair<Int, Int>, Cell>()
        Array(game.height) { y ->
            Array(game.width) {x ->
                Cell(game, x, y).apply {
                    withNeighbors(fromMap(cellMap, x, y))
                    cellMap[x to y] = this
                }
            }
        }
    }

    private fun fromMap(
            cellMap: HashMap<Pair<Int, Int>, Cell>,
            x: Int,
            y: Int
    ) = listOfNotNull(
            cellMap[x - 1 to y    ], //left
            cellMap[x - 1 to y - 1], //top-left
            cellMap[x     to y - 1], //top
            cellMap[x + 1 to y - 1]  //top-right
    )

    private fun initBombs(game: Game, bombCount: Int) {
        game.cells
                .shuffled()
                .asSequence()
                .take(bombCount)
                .forEach { it.hasMine = true }
    }

    private fun initNumbers(game: Game) {
        game.cells.parallelStream().forEach { it.number = calculateNumber(it) }
    }

    private fun calculateNumber(cell: Cell) = cell.neighbors.filter { it.hasMine }.count()
}
