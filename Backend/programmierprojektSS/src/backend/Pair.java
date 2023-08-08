package backend;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Pair class // is mein ersatz f�r make_pair aus c++
class Pair<U, V> {
	public U first; // das erste Feld eines Paares
	public V second; // das zweite Feld eines Paares

	// Erstellt ein neues Paar mit angegebenen Werten
	private Pair(U first, V second) {
		this.first = first;
		this.second = second;
	}

	@Override
	public String toString() {
		return "(" + first + ", " + second + ")";
	}

	// Factory-Methode zum Erstellen einer typisierten unver�nderlichen Pair-Instanz
	public static <U, V> Pair<U, V> of(U a, V b) {
		// ruft privaten Konstruktor auf
		return new Pair<>(a, b);
	}

	// getter und setter just in case

	public U getFirst() {
		return this.first;
	}

	public V getSecond() {
		return this.second;
	}

	public void setFirst(U first) {
		this.first = first;
	}

	public void setSecond(V second) {
		this.second = second;

	}

}
