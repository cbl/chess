### Einführung

Das Programm stellt eine virtuelles Schachbrett dar. Im folgenden wird der Funktionsumfang des Programms, die Verwendung des GUI und Test fälle beschrieben. Es werden keine Regeln beschrieben, diese können z.B. auf Wikipedia nachgeschlagen werden. https://en.wikipedia.org/wiki/Rules_of_chess

### Funktionsumfang

Es können Spiele gespielt werden. Beim Ziehen der Figuren werden nach dem Klicken auf eine Figur die legalen Zügen eingefärbt. Es können beliebige Boardpositionen in Form des FEN's geladen werden. Außerdem ist es Möglich das bestehende Spiel in Form eines FEN's zu exportieren. Nur legale Züge sind möglich.

### Spiel Anleitung

Wenn auf den "New Game" Button geklickt wird, wird die Standard Belegung eines Schachbrettes geladen. Durch das klicken auf "Import Game" und das anschließende einfügen des FEN's in das Input Feld rechts neben dem Schachbrett können auch andere Spiele geladen werden.

Das Ziehen einer Figur erfolgt, indem die Figur durch einen Klick ausgewählt wird und anschließend auf das Quadrat geklickt wird auf das die Figur gezogen werden soll.

Während des Spiels kann der FEN zu jedem beliebigen Zeitpunkt aus dem Spiel exportiert werden in dem dieser aus dem Input Feld rechts neben dem Feld kopiert wird.

Während des Spiel kann der Spieler der an der Reihe ist, aufgeben, in dem er auf den "Resign" Button klickt. Dies kann allerdings erst getan werden, nach der erste Zug ausgeführt wurde.

### Tests

Um die Durchführung legaler Züge zu testen, werden im Folgenden einige Fälle beschrieben, die die richtigkeit der implementation Zeigen sollen.

#### Schachmatt

Eine Einfacher Version eines Schachmatts lässt sich durch das sogenante "Schäfermatt" testen. Dafür müssen, ausgegangen von der Standart Board Belegung, die folgenden Züge ausgeführt werden:

1. e2e3 e7e5
2. f1c4 d7d6
3. d1h5 g8f6
4. h5f7

#### Rochade

Die Rochade kann getestet werden durch das importieren des Folgenden FEN's:

r3k2r/pppppppp/8/8/8/8/PPPPPPPP/R3K2R w KQkq - 0 0

#### En Passant

Der En Passant kann mit dem Folgenden FEN getestet werden:

rnbqkbnr/ppppppp1/8/6Pp/8/8/PPPPPP1P/RNBQKBNR w KQkq h6 0 0

#### En Passant Spezialfall

Der folgende FEN zeigt einen Speziallfall, bei dem durch das ausführen eines En Passants, der eigene König im Schach wäre. Dieser Zug ist illegal.

Im vorigen Zug wurde der weiße Bauer von d2 nach d4 bewegt, somit könnte der schwarze Bauer auf c4 den weißen Bauern recht neben Ihm schlagen, allerdings wäre dann der schwarze König durch den weißen Turm im Schach.

8/6bb/8/8/R1pP2k1/4P3/P7/K7 b - d3 0 0

Der folgende FEN zeigt, dass der Zug möglich ist, wenn es keinen Turm auf a4 gibt:

8/6bb/8/8/2pP2k1/4P3/P7/K7 b - d3 0 0

#### Ausweichen Und Blockieren

Wenn der eigene König im Schach ist, können nur Züge ausgeführt werden, die entweder den Angreifer blockieren, oder den eigenen König aus dem Schach setzen. Im folgenden FEN ist der schwarze König durch den Turm im Schach, somit. Dieser kann entweder mit einem der Läufer blockiert werden, oder selber auf die Zeile 3 bzw. 6 ausweiche.

8/6bb/8/8/R5k1/4P3/P7/K7 b KQkq d3 0 0

Ein weiterer FEN zeig, dass der Angreifer (in diesem Fall von der Dame) geschlagen werden kann wenn.

rnb1kbnr/ppp2ppp/8/1B1qp3/8/5P2/PPPP2PP/RNBQK1NR b KQkq - 1 0

#### Unentschieden Durch Unzureichenden Material

Ein Unentschieden durch unzureichenden Material tritt in den folgenden Fällen ein:

1. Wenn es nur zwei Könige gibt.
2. Wenn ein Spieler nur einen Springer hat UND:
   2.1. Der Spieler keine anderen Figuren hat.
   2.2. Der Gegenspieler keine Figuren außer dem König hat.
3. Wenn ein Spieler nur noch Läufer einer Farbe hat UND:
   3.1. Der Gegenspieler keine Figuren außer dem König hat.

Nach importieren der folgenden FEN's, kann die Figure mit dem schwarzen König geschlagen werden um ein Unentschieden zu erzwingen:

1. kP6/8/8/8/8/8/8/7K b - - 0 0
2. kN6/8/8/8/4N3/8/8/7K b - - 0 0
