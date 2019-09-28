package eu.yeger.minesweeper.model

import java.util.ArrayList

class DefaultModelBuilder(private val width: Int,
                          private val height: Int,
                          private val bombCount: Int) : ModelBuilder {

    override fun build() = Game(width, height).also {
        initCells(it)
        initBombs(it, bombCount)
        initNumbers(it)
    }

    private fun initCells(game: Game) {
        val cells = Array<Array<Cell>>(game.height) { arrayOfNulls(game.width) }
        for (y in 0 until game.height) {
            for (x in 0 until game.width) {
                cells[y][x] = Cell(x, y)
                        .setGame(game)
                        .withNeighbors(initNeighbors(cells, x, y))
            }
        }
    }

    private fun initNeighbors(cells: Array<Array<Cell>>, x: Int, y: Int): List<Cell> {
        val neighbors = ArrayList<Cell?>()
        neighbors.add(getCellAtCoordinates(cells, x - 1, y)) //left
        neighbors.add(getCellAtCoordinates(cells, x - 1, y - 1)) //top left
        neighbors.add(getCellAtCoordinates(cells, x, y - 1)) //top
        neighbors.add(getCellAtCoordinates(cells, x + 1, y - 1)) //top right
        return neighbors.filterNotNull()
    }

    private fun getCellAtCoordinates(cells: Array<Array<Cell>>, x: Int, y: Int): Cell? {
        return if (y < 0 || cells.size <= y || x < 0 || cells[y].size <= x) null else cells[y][x]
    }

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
