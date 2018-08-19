# My Crash Minesweeper

## Usage instructions:
The game is developed in Java 8, the JDK version i used was open JDK 8u171

My crash minesweeper can be used in the following two forms:
* It can be compiled from source i.e `javac src/*.java`, then run the Main class i.e `java src/Main`
* Alternatively a Docker image can be builded making `cd my_crash_minesweeper` and running `docker build . -t mycrashMinesweeper` and started `docker run -it mycrashMinesweeper`, in this way the container run as a binary

## Description:

```
Note: the * was replaced by + for mines representation, merely for aesthetic purposes
```
The game is a Java CLI version of the classic MS Windows mainsweeper.
As initial input the game will ask you for the number of rows(height), the number of columns(width) and the number of Mines. Users should enter the asked values in this way:

```
Enter the game settings in the following order:
number of <rows> <cols> <mines>
>>
```
Then you should type something like this (or the values you want):
>8 15 10

The above line means you want to create a game with 8 rows, 15 columns and 10 mines. If the user makes a typo or added some extra space between numbers the game will ask again for the values until the input match the pattern.

Passed the first input the game will start, and an empty board will be shown in the screen, followed by a prompt asking you for the next move you want to do.

```
       1    2    3    4    5
    -------------------------
1 |    .    .    .    .    .
2 |    .    .    .    .    .
3 |    .    .    .    .    .
4 |    .    .    .    .    .
5 |    .    .    .    .    .
Next move >>
```

The user can choose between three moves:
> U - for uncover the cell

> M - for mark the cell with a flag like (in this way 'P', c'mon it's a flag, use your imagination)

> R - for remove a flag from a marked cell

The move should be entered in this way:

```
Next move >> 5 2 U
```
With the above input the user tells the game he/she wants to UNCOVER the cell in the row 5 col 2. If any typo is maked or any extra space is added the game will ignore the input and will ask again for a valid move. The same applies for the other to moves `M` and `R`

The game finish when the user uncovers a mine or if he/she uncovers all safe cells without uncover a mine, you should remember you can't win if there is safe cells marked with a flag. When the game ends the program will exit, so for a new game the user should run the program again or start the docker container again.