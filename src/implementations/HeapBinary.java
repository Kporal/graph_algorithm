package implementations;

public class HeapBinary {

	private final int[] matrix;
	private int nbNodes;

	public HeapBinary(int size) {
		this.nbNodes = 0;
		this.matrix = new int[size];
	}

	@Override
	public String toString() {
		String str = "[";
		for (int i = 0; i < this.nbNodes; i++) {
			if (i == this.nbNodes - 1) {
				str += this.matrix[i] + "";
			} else {
				str += this.matrix[i] + ", ";
			}
		}
		return str + "]";
	}

	/**
	 * Insertion dans le tas binaire assurant que x soit correctement positionne
	 * 
	 * @param valeur
	 *            nouvel element a ajouter
	 */
	public void addNode(int valeur) {
		this.matrix[this.nbNodes] = valeur;
		int current = this.nbNodes;
		int parent = getIdxParent(current);
		this.nbNodes++;
		while (current != 0 && this.matrix[parent] > this.matrix[current]) {
			permut(parent, current);
			current = parent;
			parent = getIdxParent(current);
		}
	}

	/**
	 * Suppression du plus petit element dans le tas binaire (la tete)
	 */
	public void removeHead() {
		int indexCourant = 0;
		permut(indexCourant, this.nbNodes - 1);
		this.matrix[this.nbNodes - 1] = 0;
		nbNodes--;
		while (!checkNode(indexCourant)) {
			int filsGauche = getIdxFilsGauche(indexCourant);
			int filsDroit = getIdxFilsDroit(indexCourant);
			if ((!hasLeftChild(indexCourant) || (hasLeftChild(indexCourant) && this.matrix[filsGauche] < this.matrix[filsDroit]))) {
				permut(indexCourant, filsGauche);
				indexCourant = filsGauche;
			} else {
				permut(indexCourant, filsDroit);
				indexCourant = filsDroit;
			}
		}
	}

	/**
	 * Permet de tester si le noeud à l'index teste est bien place (plus grand
	 * que ces deux fils)
	 * 
	 * @param index
	 *            index du noeud a tester
	 * @return boolean vrai ou faux
	 */
	private boolean checkNode(int index) {
		return (!hasLeftChild(index) || (hasLeftChild(index) && this.matrix[index] <= this.matrix[getIdxFilsGauche(index)]))
				&& (!hasRightChild(index) || (hasRightChild(index) && this.matrix[index] <= this.matrix[getIdxFilsDroit(index)]));
	}

	/**
	 * Permet de savoir si le noeud situe a l'index teste possede un enfant a
	 * gauche
	 * 
	 * @param index
	 *            index du noeud a tester
	 * @return boolean vrai ou faux
	 */
	private boolean hasLeftChild(int index) {
		return this.getIdxFilsGauche(index) < nbNodes;
	}

	/**
	 * Permet de savoir si le noeud situe a l'index teste possede un enfant a
	 * droite
	 * 
	 * @param index
	 *            index du noeud a tester
	 * @return boolean vrai ou faux
	 */
	private boolean hasRightChild(int index) {
		return this.getIdxFilsDroit(index) < nbNodes;
	}

	/**
	 * Permet d'echanger des valeurs entre deux index
	 * 
	 * @param i
	 *            from
	 * @param j
	 *            to
	 */
	private void permut(int i, int j) {
		int tmp = this.matrix[i];
		this.matrix[i] = this.matrix[j];
		this.matrix[j] = tmp;
	}

	/**
	 * Permet de retrouver l'index du noeud parent a partir de l'index d'un
	 * noeud fils
	 * 
	 * @param x
	 *            index d'un noeud fils
	 * @return int index du noeud du parent
	 */
	private int getIdxParent(int x) {
		if (x == 0) {
			return 0;
		}
		if (x % 2 == 0) {
			return (x - 2) / 2;
		} else {
			return (x - 1) / 2;
		}
	}

	/**
	 * Retourne l'index du noeud fils gauche depuis l'index parent
	 * 
	 * @param x
	 *            index du noeud parent
	 * @return int index du fils gauche
	 */
	private int getIdxFilsGauche(int x) {
		return x * 2 + 1;
	}

	/**
	 * Retourne l'index du noeud fils droit depuis l'index parent
	 * 
	 * @param x
	 *            index du noeud parent
	 * @return int index du fils droit
	 */
	private int getIdxFilsDroit(int x) {
		return x * 2 + 2;
	}

}
