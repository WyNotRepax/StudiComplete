#!/bin/sh

# Beim Starten des Hamstersimulators unter Linux soll sichergestellt sein,
# dass wir uns im aktuellen Ordner befinden.
# Dadurch wird die lokale hamster.properties Datei gefunden und
# der Ordner ./Programme als workspace verwendet.

# Auslesen des Dateinamen dieses Scriptes,
# davon das Verzeichnis verwenden und in dieses wechseln

cd `dirname $(readlink -f ${0})`

java -jar ./hamstersimulator.jar

