from texttable import Texttable

class Board:
    def __init__(self):
        self._data = [[0] * 7, [0] * 7, [0] * 7, [0] * 7, [0] * 7, [0] * 7]
        self._moves = 0
        self._freeSpace=[6]*7

    def get(self,x,y):
        return self._data[x][y]

    def _isWon(self):
        '''
        Determines if the game was won by one of the players
        :return:
            - True if the game has been won
            - Does nothing if the game was not won
        '''
        for row in range(3):
            for column in range(7):
                if self._data[row][column] != 0:
                    if self._data[row][column]==self._data[row+1][column] and self._data[row+1][column]==self._data[row+2][column] and self._data[row+2][column]==self._data[row+3][column]:
                        return True
        for column in range(4):
            for row in range(6):
                if self._data[row][column] != 0:
                    if self._data[row][column]==self._data[row][column+1] and self._data[row][column+1]==self._data[row][column+2] and self._data[row][column+2]==self._data[row][column+3]:
                        return True
        for row in range(3):
            for column in range(4):
                if self._data[row][column] != 0:
                    if self._data[row][column] == self._data[row + 1][column + 1] and self._data[row + 1][column + 1] == self._data[row + 2][column + 2]:
                        if self._data[row + 2][column + 2] == self._data[row + 3][column + 3]:
                            return True
        for row in range(4, 6):
            for column in range(4):
                if self._data[row][column] != 0:
                    if self._data[row][column] == self._data[row - 1][column + 1]:
                        if self._data[row - 1][column + 1] == self._data[row - 2][column + 2]:
                            if self._data[row - 2][column + 2] == self._data[row - 3][column + 3]:
                                return True

    def _isTie(self):
        '''
        Determines if the game was a tie
        :return:
            - True if the game reached the 42nd move with no winner
            - Does nothing otherwise
        '''
        if self._isWon()==False and self._moves==42:
            return True

    def get_freeSpace(self,y):
        '''
        Returns the free space available on the specified column
        :param
            - y - column
        :return:
            - the free spaces available
        '''
        return self._freeSpace[y]

    def move(self,y,symbol):
        '''
        Makes a move on the board
        :param
            - y - column to make a move on
            - symbol - the player to make the move, it is X(human) or O(computer)
        - raises GameWonException if someone won
        - raises GameDrawException if it is a tie
        - raises Value error for bad column, no space available on the column or for incorrect symbol
        '''
        #ensure y in board and x is possible
        if y not in [0,1,2,3,4,5,6]:
            raise ValueError("Move outside the board!")
        if self.get_freeSpace(y)==0:
            raise ValueError("Invalid column for move!")
        if symbol not in ['X','O']:
            raise ValueError("Incorrect symbol!")
        if self.get_freeSpace(y)>0:
            if self._data[self.get_freeSpace(y)-1][y]==0:
                d = {'X': 1, 'O': 2}
                self._data[self.get_freeSpace(y)-1][y] = d[symbol]
                self._moves += 1
                self._freeSpace[y]-=1
        if self._isWon()==True:
            raise GameWonException()
        if self._isTie()==True:
            raise GameDrawException()

    

    def __str__(self):
        t = Texttable()
        d = {0: ' ', 1: 'X', 2: 'O'}
        for i in range(6):
            row = self._data[i][:]
            for j in range(7):
                row[j] = d[row[j]]
            t.add_row(row)
        return t.draw()

    def __copy__(self):
        return Board.__copy__(self)

class GameWonException(Exception):
    pass

class GameDrawException(Exception):
    pass