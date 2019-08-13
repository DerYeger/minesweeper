# Minesweeper 

> A customizable minesweeper implementation using JavaFX.

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT) [![Build Status](https://travis-ci.com/DerYeger/minesweeper.svg?token=juB9bV6tFyoA5v7Hx1o4&branch=master)](https://travis-ci.com/DerYeger/minesweeper) [![codecov](https://codecov.io/gh/DerYeger/minesweeper/branch/master/graph/badge.svg)](https://codecov.io/gh/DerYeger/minesweeper)

Minesweeper provides an implementation of the classic game with customizable graphics and game settings.

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
    implementation 'com.github.deryeger:minesweeper:0.1'
}
```

## Usage

With the builder you can quickly create Minesweeper configurations. Every time Minesweeper::instance is called a new game istance using the previously set configuration is created.
```
Minesweeper minesweeper = Minesweeper
                .builder()
                .width(20)
                .height(10)
                .bombCount(40)
                .cellSize(30)
                .onGameWon(() -> someMethod())
                .onGameLost(() -> someOtherMethod())
                .build();

Node view = minesweeper.instance();
```

## Release History

- 0.1
    - WIP Alpha Release
