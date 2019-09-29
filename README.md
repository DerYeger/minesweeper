# Minesweeper 

> A customizable Minesweeper implementation written in Kotlin and using JavaFX.

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Build Status](https://travis-ci.com/DerYeger/minesweeper.svg?token=juB9bV6tFyoA5v7Hx1o4&branch=master)](https://travis-ci.com/DerYeger/minesweeper)
[![codecov](https://codecov.io/gh/DerYeger/minesweeper/branch/master/graph/badge.svg)](https://codecov.io/gh/DerYeger/minesweeper)
[![](https://jitpack.io/v/eu.yeger/minesweeper.svg)](https://jitpack.io/#eu.yeger/minesweeper)

![](pictures/minesweeper.png)

## Gradle

In your build.gradle add
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
and
```
dependencies {
    ...
    implementation 'eu.yeger:minesweeper:0.4'
}
```

## Usage

```
val minesweeper = Minesweeper().apply {
    width = 9
    height = 9
    mineCount = 15
    cellSize = 30.0
    onGameWon = { ... }
    onGameLost = { ... }
}

val view = minesweeper.instance();
```

## Licenses

- Flying flag icon by [Lorc](http://lorcblog.blogspot.com/) under [CC BY 3.0](https://creativecommons.org/licenses/by/3.0/)
- Unlit bomb icon by [Lorc](http://lorcblog.blogspot.com/) under [CC BY 3.0](https://creativecommons.org/licenses/by/3.0/)
