http://code.google.com/p/dcn-battleship/

10x10 Board

Piece Lengths:
5 4 3 3 2

[Connecting ](.md)
P1 connects to server... waits
P2 connects to server
Server pairs P1 and P2


[P1's Turn]
Server --> P1 [Start Turn ](.md)
P1 --> Server [Attack Square ](.md)
Server --> P2 [Attack Square ](.md)
P2 --> Server [Hit/Miss ](.md)
Server --> P1 [Hit/Miss ](.md)

[Turn](P2.md)
Server --> P2 [Start Turn ](.md)
P2 --> Server [Attack Square](.md)
...

opcodes:

'a' - send attack
'r' - send result