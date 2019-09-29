package eu.yeger.minesweeper.model

interface ModelBuilder {
    fun build(
            width: Int,
            height: Int,
            mineCount: Int
    ): Game
}
