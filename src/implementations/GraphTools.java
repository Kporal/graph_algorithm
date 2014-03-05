package implementations;

import interfaces.IDirectedGraph;
import interfaces.IGraph;
import interfaces.IUndirectedGraph;
import interfaces.IWeightedGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GraphTools {

	public GraphTools() {
	}

	// ***********************************************
	// GENERATION DE GRAPHE
	// ***********************************************

	/**
	 * Permet de générer une matrice d'ajacence
	 * 
	 * @param n
	 *            nb de noeud
	 * @param m
	 *            nb d'arrete
	 * @param s
	 *            symétrique?
	 * @return int[]
	 */
	public static int[][] generateGraphData(int n, int m, boolean s) {
		int[][] matrix = new int[n][n];

		// tant que j'ai des arcs à ajouter
		while (m > 0) {
			int x = generatedInt(n);
			int y = generatedInt(n);
			// on ecrit pas sur la diagonale et sur une nouvelle case
			if (x != y && matrix[x][y] == 0) {
				// génére un graphe simétrique
				if (s) {
					matrix[y][x] = 1;
				}
				matrix[x][y] = 1;
				m--;
			}
		}

		return matrix;
	}

	/**
	 * Génére un nombre aléatoire entre 0 et max
	 * 
	 * @param max
	 *            le nombre généré ne pourra ne sera pas supérieur à max
	 * @return int
	 */
	private static int generatedInt(int max) {
		Random random = new Random();
		// random.setSeed(0);
		return random.nextInt(max);
	}

	// ***********************************************
	// PARCOURS EN PROFONDEUR
	// ***********************************************

	/**
	 * Compteur du numero d'ordre pour le parcours (debut et fin)
	 */
	private static int cpt;
	/**
	 * tableau de debut. Contient le numero d'ordre du parcours.
	 */
	private static int[] start;

	public static int[] getStartTab() {
		return start;
	}

	/**
	 * tableau de fin. Contient le numero d'ordre du parcours.
	 */
	private static int[] end;

	public static int[] getEndTab() {
		return end;
	}

	/**
	 * Parcours de la structure de graphe en profondeur
	 * 
	 * @param graph
	 *            graphe à explorer
	 * @return int[] sommet atteint
	 */
	public static int[] exploreDepthGraph(IGraph graph) {
		// initialisation
		start = new int[graph.getNbNodes()];
		end = new int[graph.getNbNodes()];
		cpt = 0;
		int[] atteint = initArray(graph.getNbNodes());
		// parcours des sommets
		for (int sCourant = 0; sCourant < graph.getNbNodes(); sCourant++) {
			// noeud n'est pas encore atteint
			if (!contains(sCourant, atteint)) {
				int[] parcours = initArray(graph.getNbNodes());
				exploreDepthNode(graph, sCourant, parcours);
				arrayCopy(parcours, atteint);
			}
		}
		return atteint;
	}

	/**
	 * Parcours de la structure de graphe en profondeur
	 * 
	 * @param graph
	 *            graphe à explorer
	 * @param ordre
	 *            ordre de parcours du graphe
	 * @return int[][] composantes fortements connexes
	 */
	public static int[][] exploreDepthGraphWithConnexe(IGraph graph, int[] ordre) {
		// initialisation
		start = new int[graph.getNbNodes()];
		end = new int[graph.getNbNodes()];
		cpt = 0;
		int[] atteint = initArray(graph.getNbNodes());
		int cptComposantes = 0;
		List<ArrayList<Integer>> composantes = new ArrayList<ArrayList<Integer>>();
		// parcours des sommets
		for (int sCourant : ordre) {
			// noeud n'est pas encore atteint
			if (!contains(sCourant, atteint)) {
				exploreDepthNode(graph, sCourant, atteint);
				// je recopie les composantes connexes
				ArrayList<Integer> newComposante = new ArrayList<Integer>();
				for (int i = cptComposantes; i < atteint.length; i++) {
					if (atteint[i] != -1) {
						cptComposantes++;
						newComposante.add(atteint[i]);
					} else {
						break;
					}
				}
				composantes.add(newComposante);
			}
		}
		// formatte les composantes connexes dans un tableau
		int[][] fortementsConnexe = new int[composantes.size()][];
		int i = 0;
		for (ArrayList<Integer> c : composantes) {
			int[] composante = new int[c.size()];
			int j = 0;
			for (int s : c) {
				composante[j] = s;
				j++;
			}
			fortementsConnexe[i] = composante;
			i++;
		}
		return fortementsConnexe;
	}

	/**
	 * Parcours de la structure de graphe en profondeur
	 * 
	 * @param graph
	 *            graphe à explorer
	 * @param node
	 *            le noeud que l'on explorer
	 * @return int[] sommet atteint
	 */
	public static int[] exploreDepthGraph(IGraph graph, int node) {
		// initialisation
		start = new int[graph.getNbNodes()];
		end = new int[graph.getNbNodes()];
		cpt = 0;
		int[] atteint = initArray(graph.getNbNodes());
		int[] parcours = initArray(graph.getNbNodes());
		exploreDepthNode(graph, node, parcours);
		arrayCopy(parcours, atteint);
		return atteint;
	}

	/**
	 * Méthode permettant d'explorer un sommet en profondeur
	 * 
	 * @param graph
	 *            le graphe a parcourir
	 * @param i
	 *            sommet courant
	 * @param parcours
	 *            l'etat du parcours, contient les differents sommets parcourus
	 */
	private static void exploreDepthNode(IGraph graph, int i, int[] parcours) {
		cpt += 1;
		start[i] = cpt;
		parcours[nextIndex(parcours)] = i;

		int[] voisins;
		// On cherche sur qu'elle type de graphe on travail
		if (graph instanceof IUndirectedGraph) {
			voisins = ((IUndirectedGraph) graph).getNeighbors(i);
		} else {
			voisins = ((IDirectedGraph) graph).getSuccessors(i);
		}

		for (int sommet : voisins) {
			if (!contains(sommet, parcours)) {
				exploreDepthNode(graph, sommet, parcours);
			}
		}
		cpt += 1;
		end[i] = cpt;
	}

	// ***********************************************
	// PARCOURS EN LARGEUR
	// ***********************************************

	/**
	 * Parcours de la structure de graphe en largeur
	 * 
	 * @param graph
	 *            graphe à explorer
	 * @return int[] sommet atteint
	 */
	public static int[] exploreBreadthGraph(IGraph graph) {
		return exploreBreadthGraph(graph, 0);
	}

	public static int[] exploreBreadthGraph(IGraph graph, int nodeStart) {
		int[] atteint = initArray(graph.getNbNodes());
		int[] parcours = new int[1];
		atteint[nextIndex(atteint)] = nodeStart;

		// tq mon tableau de parcours n'est pas vide
		while (parcours.length > 0) {
			// on recupere le dernier element ajouté
			int sommet = parcours[parcours.length - 1];
			parcours = pop(parcours);

			int[] voisins;
			// On cherche sur qu'elle type de graphe on travail
			if (graph instanceof IUndirectedGraph) {
				voisins = ((IUndirectedGraph) graph).getNeighbors(sommet);
			} else {
				voisins = ((IDirectedGraph) graph).getSuccessors(sommet);
			}
			// parcours des noeuds au même niveau
			for (int voisin : voisins) {
				if (!contains(voisin, atteint)) {
					parcours = push(parcours, voisin);
					atteint[nextIndex(atteint)] = voisin;
				}
			}
		}
		return atteint;
	}

	public static boolean[] kchromatique(IGraph graph, int nodeStart) {
		// tableau de couleurs
		boolean[] colors = new boolean[graph.getNbNodes()];
		boolean currentColor = false;
		int[] atteint = initArray(graph.getNbNodes());
		int[] parcours = new int[1];
		atteint[nextIndex(atteint)] = nodeStart;
		colors[nodeStart] = currentColor;
		currentColor = true;

		// tq mon tableau de parcours n'est pas vide
		while (parcours.length > 0) {
			// on recupere le dernier element ajouté
			int sommet = parcours[parcours.length - 1];
			parcours = pop(parcours);

			int[] voisins;
			// On cherche sur qu'elle type de graphe on travail
			if (graph instanceof IUndirectedGraph) {
				voisins = ((IUndirectedGraph) graph).getNeighbors(sommet);
			} else {
				voisins = ((IDirectedGraph) graph).getSuccessors(sommet);
			}
			// parcours des noeuds au même niveau
			for (int voisin : voisins) {
				if (!contains(voisin, atteint)) {
					parcours = push(parcours, voisin);
					atteint[nextIndex(atteint)] = voisin;
					// associe la couleur au sommet courant
					colors[voisin] = currentColor;
				}
			}
			currentColor = !currentColor; // changement de couleur
		}
		return colors;
	}

	public static int[][] partitionBiparti(IGraph graph, int nodeStart) {
		boolean[] colors = kchromatique(graph, nodeStart);
		// retourne la partition (les deux sous ensembles)
		int[][] partition = new int[2][]; // graphe biparti
		List<Integer> ensemble0 = new ArrayList<Integer>();
		List<Integer> ensemble1 = new ArrayList<Integer>();
		for (int i = 0; i < graph.getNbNodes(); i++) {
			if (colors[i]) {
				ensemble0.add(i);
			} else {
				ensemble1.add(i);
			}
		}
		int[] part0 = new int[ensemble0.size()];
		for (int i = 0; i < ensemble0.size(); i++) {
			part0[i] = ensemble0.get(i);
		}
		partition[0] = part0;

		int[] part1 = new int[ensemble1.size()];
		for (int i = 0; i < ensemble1.size(); i++) {
			part1[i] = ensemble1.get(i);
		}
		partition[1] = part1;
		return partition;
	}

	// ***********************************************
	// COMPOSANTES CONNEXE
	// ***********************************************

	/**
	 * Retourne l'ordre decroissant des sommets a partir du tableau de fin
	 * 
	 * @param tab
	 *            tableau de fin
	 * @return int[] ordre d'apparition des sommets decroissant
	 */
	private static int[] getOrderFromEnd(int[] tab) {
		int n = tab.length;
		int[] res = new int[n];
		// tab contient des valeurs >0, on cherche la plus grande, et on le met
		// a 0; on fait ca 'n' fois.
		int idxMax = -1, valMax = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (valMax < tab[j]) {
					idxMax = j;
					valMax = tab[j];
				}
			}
			res[i] = idxMax;
			tab[idxMax] = 0;
			valMax = 0;
		}
		return res;
	}

	/**
	 * Retourne les composantes fortement connexe d'un graphe oriente.
	 * Complexite : O (n^2 log n)
	 * 
	 * @param graph
	 *            un graphe oriente
	 * @return int[][] les composantes fortements connexe
	 */
	public static int[][] getComposanteFortementConnexe(IDirectedGraph graph) {
		exploreDepthGraph(graph);
		IDirectedGraph g2 = graph.computeInverse();
		return exploreDepthGraphWithConnexe(g2, getOrderFromEnd(getEndTab()));
	}

	public static int[][] getComposanteFortementConnexeOld(IDirectedGraph graph) {

		int[][] connexeInit = new int[graph.getNbNodes()][];
		for (int i = 0; i < graph.getNbNodes(); i++) {
			connexeInit[i] = exploreDepthGraph(graph, i);
		}

		IDirectedGraph graphInverse = graph.computeInverse();
		int[][] connexeInverse = new int[graphInverse.getNbNodes()][];
		for (int i = 0; i < graphInverse.getNbNodes(); i++) {
			connexeInverse[i] = exploreDepthGraph(graphInverse, i);
		}

		List<ArrayList<Integer>> mesComposantes = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < graph.getNbNodes(); i++) {
			ArrayList<Integer> courant = new ArrayList<Integer>();
			for (int j = 0; j < connexeInit[i].length; j++) {
				if (contains(connexeInit[i][j], connexeInverse[i])) {
					courant.add(connexeInit[i][j]);
				}
			}
			Collections.sort(courant);
			if (!mesComposantes.contains(courant)) {
				mesComposantes.add(courant);
			}
		}

		int[][] composantes = new int[mesComposantes.size()][];
		for (int i = 0; i < mesComposantes.size(); i++) {
			composantes[i] = new int[mesComposantes.get(i).size()];
			for (int j = 0; j < mesComposantes.get(i).size(); j++) {
				composantes[i][j] = mesComposantes.get(i).get(j);
			}
		}
		return composantes;
	}

	// ***********************************************
	// ARBRE DE POIDS MINIMAL
	// ***********************************************

	public static int[] prim(IWeightedGraph graph) {
		// je tir au hasard le premier sommet
		Random rdm = new Random();
		return prim(graph, rdm.nextInt(graph.getNbNodes()));
	}

	/**
	 * Algorithme de prim. Calcule l'arbre de poid minimal dans un graphe
	 * connexe. Complexite : O(n.m) n: nb sommets et m: nb arretes
	 * 
	 * @param graph
	 *            graphe pondere
	 * @param start
	 *            noeud sur lequel on démarre
	 * @return int[] chemin du poid minimal
	 */
	public static int[] prim(IWeightedGraph graph, int start) {
		boolean[] visited = new boolean[graph.getNbNodes()];
		int[] predecessors = new int[graph.getNbNodes()];

		int nextNode = start;
		predecessors[0] = nextNode;
		visited[nextNode] = true;

		// je boucle tq pas fini
		int nbArcs = 1;
		while (nbArcs < graph.getNbNodes()) {
			nextNode = graph.getNextNode(predecessors, visited);
			visited[nextNode] = true;
			predecessors[nbArcs] = nextNode;
			nbArcs++;
		}

		return predecessors;
	}

	// ***********************************************
	// PLUS COURT CHEMIN
	// ***********************************************

	/**
	 * Algorithme de bellman. Calcule le plus court chemin d'un sommet a tous
	 * les autres sommets. Complexite : O(n.m)
	 * 
	 * @param graph
	 *            graphe pondere
	 * @param start
	 *            sommet de debut
	 * @return int[] liste des plus court chemins
	 */
	public static int[] bellman(IWeightedGraph graph, int start) {
		// initialisation et iteration 0
		int[] pred = new int[graph.getNbNodes()];
		int[] actual = new int[graph.getNbNodes()];
		for (int i = 0; i < graph.getNbNodes(); i++) {
			actual[i] = graph.getWeigth(start, i);
		}
		actual[start] = 0;

		// iteration 1 a nbNodes - 1
		for (int i = 1; i < graph.getNbNodes(); i++) {
			pred = actual.clone();
			// pour chaque noeud du graphe
			for (int j = 0; j < graph.getNbNodes(); j++) {
				// recuperation des predecesseurs du sommet
				int[] neighbors = new int[0];
				if (graph instanceof IUndirectedGraph) {
					neighbors = ((IUndirectedGraph) graph).getNeighbors(j);
				} else {
					neighbors = ((IDirectedGraph) graph).getPredecessors(j);
				}

				// pour chaque predecesseurs
				for (int k = 0; k < neighbors.length; k++) {
					// si la valeur pred est l'infini pas d'actualisation
					if (!(pred[neighbors[k]] >= Integer.MAX_VALUE)) {
						// nouvelle distance entre les deux sommets
						int distance = pred[neighbors[k]]
								+ graph.getWeigth(neighbors[k], j);
						// maj de la distance
						if (actual[j] > distance) {
							actual[j] = distance;
						}
					}
				}
			}
			// si aucune valeur n'a changé on arrete
			if (pred.equals(actual)) {
				return actual;
			}
		}

		return actual;
	}

	/**
	 * Algorithme de dijkstra. Calcule le plus court chemin d'un sommet a tous
	 * les autres. Complexite : O(m + n log n)
	 * 
	 * @param graph
	 *            graphe pondere
	 * @return int[] liste des plus court chemins
	 */
	public static int[] dijkstra(IWeightedGraph graph) {
		// initialisation
		int n = graph.getNbNodes();
		int[] v = new int[n]; // tableau de couts
		for (int i = 1; i < n; i++) {
			v[i] = Integer.MAX_VALUE;
		}
		int[] p = new int[n];
		p[0] = 1;
		boolean[] b = new boolean[n];
		// Recherche du sommet x non atteint de cout minimal
		int x = 0;
		// tant qu’il existe un sommet non marque
		int indexSommet = 0;
		while (indexSommet < n && !b[indexSommet]) {
			int min = Integer.MAX_VALUE;
			// trouve le prochain min
			for (int y = 0; y < n; y++) {
				if (!b[y] && v[y] < min) {
					x = y;
					min = v[y];
					break;
				}
			}
			// Mise à jour des successeurs non fixés de x
			if (min < Integer.MAX_VALUE) {
				b[x] = true;
				for (int y = 0; y < n; y++) {
					int cout = v[x] + graph.getWeigth(x, y);
					// si le cout est inferieur, je MAJ
					if (!b[y] && graph.getWeigth(x, y) < Integer.MAX_VALUE
							&& cout < v[y]) {
						v[y] = cout;
						p[y] += 1;
					}
				}
			}
			indexSommet++;
		}

		return v;
	}

	// ***********************************************
	// METHODES POUR MANIPULER LES TABLEAUX
	// ***********************************************

	/**
	 * Test si la valeur search existe dans le tableau
	 * 
	 * @param search
	 *            valeur à chercher
	 * @param array
	 *            tableau de valeur
	 * @return boolean
	 */
	private static boolean contains(int search, int[] array) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == search) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Test si la valeur search existe dans le tableau
	 * 
	 * @param search
	 *            valeur à chercher
	 * @param array
	 *            tableau de valeur
	 * @return boolean
	 */
	private static boolean contains(int[] search, int[][] array) {
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				if (!contains(search[j], array[i])) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Permet de retirer le dernier element d'un tableau
	 * 
	 * @param array
	 * @return int[] nouveau tableau avec nbElement - 1
	 */
	private static int[] pop(int[] array) {
		int[] newArray = new int[array.length - 1];
		arrayCopy(array, newArray);
		return newArray;
	}

	/**
	 * Permet d'ajouter un element à l'index + 1 du tableau
	 * 
	 * @param array
	 * @param entier
	 * @return int[] nouveau tableau avec nbElement + 1
	 */
	private static int[] push(int[] array, int entier) {
		int[] newArray = new int[array.length + 1];
		arrayCopy(array, newArray);
		newArray[array.length] = entier;
		return newArray;
	}

	/**
	 * Initialise un array de taille size et rempli de -1
	 * 
	 * @param size
	 *            la taille du tableau à initialiser
	 * @return int[]
	 */
	private static int[] initArray(int size) {
		int[] array = new int[size];
		for (int i = 0; i < size; i++) {
			array[i] = -1;
		}
		return array;
	}

	/**
	 * Copie les valeurs contenu dans l'array from dans l'array to
	 * 
	 * @param from
	 * @param to
	 */
	private static void arrayCopy(int[] from, int[] to) {
		int i = 0;
		// tq on a des valeurs à recopier
		while (i < from.length && i < to.length && from[i] != -1) {
			to[i] = from[i];
			i++;
		}
	}

	/**
	 * Retourne le prochaine index disponible dans le tableau. La premiere case
	 * avec -1 comme valeur.
	 * 
	 * @param array
	 * @return int
	 */
	private static int nextIndex(int[] array) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == -1) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Retourne la taille du tableau, nombre de case differentes de -1
	 * 
	 * @param array
	 * @return int size
	 */
	private static int size(int[] array) {
		int i = 0;
		while (i < array.length && array[i] != -1) {
			i++;
		}
		return i;
	}

	/**
	 * Permet de transformer un tableau à deux dim en string
	 * 
	 * @param graph
	 * @return string
	 */
	public static String toString(int[][] graph) {
		String res = "";
		for (int i = 0; i < graph.length; i++) {
			res += Arrays.toString(graph[i]) + "\n";
		}
		return res;
	}

	/**
	 * Permet de transformer un tableau à une dim en string
	 * 
	 * @param graph
	 * @return string
	 */
	public static String toString(int[] graph) {
		return Arrays.toString(graph);
	}

	/**
	 * Permet d'afficher en console un tableau à deux dim en string
	 * 
	 * @param graph
	 * @return string
	 */
	public static void toPrint(int[][] graph) {
		System.out.println(toString(graph));
	}

	/**
	 * Permet d'afficher en console un tableau à une dim en string
	 * 
	 * @param graph
	 */
	public static void toPrint(int[] graph) {
		System.out.println(toString(graph));
	}
}
