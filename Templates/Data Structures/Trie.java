
class Trie {

	Node[] root = new Node[26];

	public void insert(String element) {

		int[] indices = toKey(element);

		Node currentNode = root[indices[0]];

		for (int ii = 1; ii < indices.length; ii++) {
			if (currentNode.child[indices[ii]] == null) {
				currentNode.child[indices[ii]] = new Node();
			}
			currentNode = currentNode.child[indices[ii]];
			if (ii == indices.length - 1) {
				currentNode.finishesWord = true;
			}
		}
	}

	public int[] toKey(String input) {
		input.toLowerCase();
		char[] charInput = input.toCharArray();
		int inputLength = charInput.length;

		int[] key = new int[inputLength];

		for (int i = 0; i < inputLength; i++) {
			key[i] = charInput[i] - 97;
		}
		return key;
	}

	public boolean containsPrefix(String element) {
		int[] indices = toKey(element);
		Node currentNode = root[indices[0]];

		for (int ii = 1; ii < indices.length; ii++) {
			if (currentNode.child[indices[ii]] == null) {
				return false;
			}
			currentNode = currentNode.child[indices[ii]];
		}
		return true;
	}

	public boolean containsWord(String element) {
		int[] indices = toKey(element);
		Node currentNode = root[indices[0]];

		for (int ii = 1; ii < indices.length; ii++) {
			if (currentNode.child[indices[ii]] == null) {
				return false;
			}
			currentNode = currentNode.child[indices[ii]];
		}
		return currentNode.finishesWord;
	}

	class Node {
		Node[] child = new Node[26];
		boolean finishesWord;
	}

}
