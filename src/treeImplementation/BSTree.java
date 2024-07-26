package treeImplementation;

import java.util.NoSuchElementException;
import java.util.Stack;
import utilities.BSTreeADT;
import utilities.Iterator;

public class BSTree<E extends Comparable<? super E>> implements BSTreeADT<E> {
    private BSTreeNode<E> root;
    private int size;

    public BSTree() {
        this.root = null;
        this.size = 0;
    }

    @Override
    public BSTreeNode<E> getRoot() {
        return root;
    }

    @Override
    public int getHeight() {
        return getHeight(root);
    }

    private int getHeight(BSTreeNode<E> node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(getHeight(node.getLeft()), getHeight(node.getRight()));
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public boolean contains(E entry) throws NullPointerException {
        if (entry == null) {
            throw new NullPointerException("The element cannot be null.");
        }
        return contains(root, entry);
    }

    private boolean contains(BSTreeNode<E> node, E entry) {
        if (node == null) {
            return false;
        }
        int compare = entry.compareTo(node.getElement());
        if (compare < 0) {
            return contains(node.getLeft(), entry);
        } else if (compare > 0) {
            return contains(node.getRight(), entry);
        } else {
            return true;
        }
    }

    @Override
    public BSTreeNode<E> search(E entry) throws NullPointerException {
        if (entry == null) {
            throw new NullPointerException("The element cannot be null.");
        }
        return search(root, entry);
    }

    private BSTreeNode<E> search(BSTreeNode<E> node, E entry) {
        if (node == null) {
            return null;
        }
        int compare = entry.compareTo(node.getElement());
        if (compare < 0) {
            return search(node.getLeft(), entry);
        } else if (compare > 0) {
            return search(node.getRight(), entry);
        } else {
            return node;
        }
    }

    @Override
    public boolean add(E newEntry) throws NullPointerException {
        if (newEntry == null) {
            throw new NullPointerException("The element cannot be null.");
        }
        if (root == null) {
            root = new BSTreeNode<>(newEntry);
            size++;
            return true;
        } else {
            return add(root, newEntry);
        }
    }

    private boolean add(BSTreeNode<E> node, E newEntry) {
        int compare = newEntry.compareTo(node.getElement());
        if (compare < 0) {
            if (node.getLeft() == null) {
                node.setLeft(new BSTreeNode<>(newEntry));
                size++;
                return true;
            } else {
                return add(node.getLeft(), newEntry);
            }
        } else if (compare > 0) {
            if (node.getRight() == null) {
                node.setRight(new BSTreeNode<>(newEntry));
                size++;
                return true;
            } else {
                return add(node.getRight(), newEntry);
            }
        } else {
            return false; 
        }
    }


    @Override
    public BSTreeNode<E> removeMin() {
        if (root == null) {
            return null;
        }
        if (root.getLeft() == null) {
            BSTreeNode<E> minNode = root;
            root = root.getRight();
            size--;
            return minNode;
        }
        return removeMin(root, null);
    }

    private BSTreeNode<E> removeMin(BSTreeNode<E> node, BSTreeNode<E> parent) {
        if (node.getLeft() == null) {
            if (parent != null) {
                parent.setLeft(node.getRight());
            }
            size--;
            return node;
        }
        return removeMin(node.getLeft(), node);
    }

    @Override
    public BSTreeNode<E> removeMax() {
        if (root == null) {
            return null;
        }
        if (root.getRight() == null) {
            BSTreeNode<E> maxNode = root;
            root = root.getLeft();
            size--;
            return maxNode;
        }
        return removeMax(root, null);
    }

    private BSTreeNode<E> removeMax(BSTreeNode<E> node, BSTreeNode<E> parent) {
        if (node.getRight() == null) {
            if (parent != null) {
                parent.setRight(node.getLeft());
            }
            size--;
            return node;
        }
        return removeMax(node.getRight(), node);
    }

    @Override
    public Iterator<E> inorderIterator() {
        return new InOrderIterator();
    }

    @Override
    public Iterator<E> preorderIterator() {
        return new PreOrderIterator();
    }

    @Override
    public Iterator<E> postorderIterator() {
        return new PostOrderIterator();
    }

    private class InOrderIterator implements Iterator<E> {
        private Stack<BSTreeNode<E>> stack = new Stack<>();
        private BSTreeNode<E> current = root;

        public InOrderIterator() {
            pushLeft(current);
        }

        private void pushLeft(BSTreeNode<E> node) {
            while (node != null) {
                stack.push(node);
                node = node.getLeft();
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public E next() throws NoSuchElementException {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            current = stack.pop();
            E result = current.getElement();
            if (current.getRight() != null) {
                pushLeft(current.getRight());
            }
            return result;
        }
    }

    private class PreOrderIterator implements Iterator<E> {
        private Stack<BSTreeNode<E>> stack = new Stack<>();

        public PreOrderIterator() {
            if (root != null) {
                stack.push(root);
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public E next() throws NoSuchElementException {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            BSTreeNode<E> node = stack.pop();
            E result = node.getElement();
            if (node.getRight() != null) {
                stack.push(node.getRight());
            }
            if (node.getLeft() != null) {
                stack.push(node.getLeft());
            }
            return result;
        }
    }

    private class PostOrderIterator implements Iterator<E> {
        private Stack<BSTreeNode<E>> stack = new Stack<>();
        private Stack<BSTreeNode<E>> output = new Stack<>();

        public PostOrderIterator() {
            if (root != null) {
                stack.push(root);
            }
            while (!stack.isEmpty()) {
                BSTreeNode<E> node = stack.pop();
                output.push(node);
                if (node.getLeft() != null) {
                    stack.push(node.getLeft());
                }
                if (node.getRight() != null) {
                    stack.push(node.getRight());
                }
            }
        }

        @Override
        public boolean hasNext() {
            return !output.isEmpty();
        }

        @Override
        public E next() throws NoSuchElementException {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return output.pop().getElement();
        }
    }
}
