
public class Cardinal {
	int wert;

	public Cardinal() {
		wert = 0;
	}

	public Cardinal(int wert) {
		this.wert = wert;
	}

	public Cardinal(Cardinal copy) {
		this.wert = copy.wert;
	}

	public Cardinal clone() {
		return new Cardinal(this);
	}

	/*
	 * public boolean equals(Cardinal other){ if(other == null) { return false;
	 * } return this.wert == other.wert; }
	 */
	@Override
	public String toString() {
		return String.valueOf(wert);
	}

	/**
	 * Subtracts two Cardinals and returns difference as new Cardinal
	 * 
	 * @param a
	 *            Cardinal to subtract b from
	 * @param b
	 *            Cardinal to subtract from a
	 * @return new Cardinal representing the difference between a and b (a - b)
	 * @throws NullPointerException
	 *             if a or b are null
	 */
	public static Cardinal sub(Cardinal a, Cardinal b) {
		return new Cardinal(a.wert - b.wert);
	}

	/**
	 * Subtracts two Cardinals and returns difference as new Cardinal
	 * 
	 * @param c
	 *            Cardinal to subtract from this Cardinal
	 * @return new Cardinal representing the difference between this and c (this
	 *         - c)
	 * @throws NullPointerException
	 *             if c is null
	 */
	public Cardinal sub(Cardinal c) {
		if (c == null) {
			throw new NullPointerException();
		}
		return Cardinal.sub(this, c);
	}
}
