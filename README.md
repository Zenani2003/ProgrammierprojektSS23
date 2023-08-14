# ProgrammierprojektSS23
Programmierprojekt SS23:Beschreibung des Projekts und Anforderungen

Es soll ein Web-basierter Routenplaner implementiert werden.

Die Abgabe zu Phase I muss folgende Anforderungen erfüllen:
Der Graph muss mit der Adjacency Array-Darstellung im Speicher gehalten werden.
Es müssen zwei Versionen des Dijkstra-Algorithmus implementiert werden: one-to-one (von einem Knoten aus die Distanz zu einem bestimmten anderen Knoten berechne),
und one-to-all (von einem Knoten aus die Distanzen zu allen anderen Knoten berechnen, so dass im Anschluss die Distanz zu anderen Knoten nachgeschaut werden kann)
Die Implementierung muss es erlauben, gegeben einer Position innerhalb von Deutschland (Lon./Lat.) in maximal einer Sekunde den nächsten Knoten zu finden
Um den nächsten Knoten zu finden muss man.
Zeitanforderungen mit Deutschland-Graph:
Einlesen darf maximal 2 Minuten dauern (Das Aufbauen der Datenstruktur, welche den nächsten Knoten zu einer gegebenen Position findet, wird hier nicht mit reingerechnet)
Berechnung eines one-to-all Dijkstra darf maximal 15 Sekunden dauern.
Es muss die Möglichkeit geben, eine Datei mit Start-Ziel-Anfragen einzulesen und die Distanzen auf der Konsole auszugeben : die Ausgabe in der Konsole muss dann genau der .sol entsprechen, d.h. ein Diff-Befehl sollte Gleichheit ergeben.
Die Funktionalität der Abgabe in Phase I wird in der Benchmark-Funktion getestet, welche mit entsprechenden Parametern alle geforderten berechneten Distanzen und alle geforderten Laufzeiten ausgibt. 
Abgabe bis 18.06.

Die Abgabe zu Phase II muss folgende Anforderungen erfüllen:
Einfacher Webserver, der eine Seite mit eingebundener Karte bereitstellt.
Muss lokal funktionieren, also soll die Anwendung z.B. unter "localhost:8080" in Firefox aufrufbar sein
Die Seite kommuniziert per AJAX-Requests mit dem Server, um ID und Koordinaten des nächsten Knoten zu finden, und um Routen zwischen zwei Knoten-IDs anzufordern
Falls die Lokalisierung aus Phase I nicht richtig funktioniert lasse ich hier ggf. nochmal die Lokalisierung nachbessern
Start und Ziel müssen per Klick auf die Karte festlegbar sein
Bei Klick wird beim Server angefragt, welcher Knoten dem Klick am nächsten ist
Beim Klick erscheint sofort ein entsprechender Marker auf der Position des nächsten Knotens
Pfade im GeoJSON-Format als Layer auf die Karte legen
Man muss nacheinander verschiedene Routen berechnen lassen können 
Abgabe bis 20.08.
