package main;

import java.util.*;

public class avlTree<T extends Comparable<T>> implements SortedSet<T> {

    private Node<T> root = null;
    private int size = 0;

    private class Node<T> {
        int balance;
        T val;
        Node<T> leftChild = null;
        Node<T> rightChild = null;

        Node(T val, int balance) {
            this.balance = balance;
            this.val = val;
        }
    }

    @Override
    public boolean add(T t) {
        Node<T> foundNode = findNode(t);
        Node<T> tempNode;
        int compare = foundNode == null ? -1 : t.compareTo(foundNode.val);
        if (compare == 0) {
            return false;
        }
        Node<T> newNode = new Node<>(t, 0);

        if (foundNode == null) {
            root = newNode;
        } else {
            if (compare < 0 && foundNode.leftChild == null) {
                foundNode.leftChild = newNode;
            } else {
                foundNode.rightChild = newNode;
            }
            tempNode = newNode;
            balanceTree(tempNode,newNode);
        }
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        Node<T> currentNode = findNode(root, (T) o);
        if (!o.equals(currentNode.val)) return false;
        Node<T> parentNode = getParent(currentNode);
        boolean checkTop = false;
        if (parentNode == null) checkTop = true;

        if (currentNode.rightChild == currentNode.leftChild && checkTop) {
            root = null;
        } else if (currentNode.rightChild == currentNode.leftChild) {
            if (parentNode.val.compareTo(currentNode.val) > 0)
                parentNode.leftChild = null;
            else
                parentNode.rightChild = null;
        } else {
            if (currentNode.rightChild == null && checkTop || currentNode.leftChild == null && checkTop) {
                root = currentNode.rightChild == null ? currentNode.leftChild : currentNode.rightChild;
            } else if (currentNode.rightChild == null) {
                if (parentNode.val.compareTo(currentNode.val) > 0)
                    parentNode.leftChild = currentNode.leftChild;
                else
                    parentNode.rightChild = currentNode.leftChild;
            } else if (currentNode.leftChild == null) {
                if (parentNode.val.compareTo(currentNode.val) > 0)
                    parentNode.leftChild = currentNode.rightChild;
                else
                    parentNode.rightChild = currentNode.rightChild;
            } else {
                Node<T> node = getParent(getMinNode(currentNode.rightChild));
                Node<T> minLeft = getMinNode(currentNode.rightChild);
                if (node.val.compareTo(minLeft.val) > 0)
                    node.leftChild = minLeft.rightChild;
                else
                    node.rightChild = minLeft.rightChild;
                currentNode.val = minLeft.val;
            }
        }
        Node<T> current = parentNode;
        balanceTreeRemove(current);
        size--;
        return true;
    }

    private void balanceTree(Node<T> tempNode, Node<T> newNode) {
        while (tempNode != null) {
            setBalance(tempNode);
            if (Math.abs(tempNode.balance) > 1) {
                restructureTree(tempNode, newNode);
            }
            tempNode = getParent(tempNode);
        }
    }

    private void balanceTreeRemove(Node<T> current) {
        while (current != null) {
            setBalance(current);
            if (Math.abs(current.balance) > 1) {
                removeRestructureTree(current);
            }
            current = getParent(current);
        }
    }

    private void restructureTree(Node<T> node, Node<T> newNode) {
        Node<T> left = node.leftChild;
        Node<T> right = node.rightChild;

        if (node.val.compareTo(newNode.val) < 0) {
            if (right.val.compareTo(newNode.val) < 0) rotateLeft(node);
            else rotateRightLeft(node);
        } else {
            if (left.val.compareTo(newNode.val) < 0) rotateLeftRight(node);
            else rotateRight(node);
        }
    }


    private void removeRestructureTree(Node<T> node) {
        Node<T> left = node.leftChild;
        Node<T> right = node.rightChild;

        if (node.balance == 2) {
            if (right.balance == 0 || right.balance == 1) rotateLeft(node);
            else rotateRightLeft(node);
        } else {
            if (node.balance == -2) {
                if (left.balance == 0 || left.balance == -1) rotateRight(node);
                else rotateLeftRight(node);
            }
        }
    }


    private void rotateRight(Node<T> node) {
        Node<T> leftNode = node.leftChild;
        Node<T> parentNode = getParent(node);

        node.leftChild = leftNode.rightChild;
        leftNode.rightChild = node;

        if (parentNode != null) {
            if (parentNode.val.compareTo(leftNode.val) > 0) parentNode.leftChild = leftNode;
            else parentNode.rightChild = leftNode;
        } else root = leftNode;

        setBalance(node);
        setBalance(leftNode);
    }

    private void rotateLeft(Node<T> node) {
        Node<T> rightNode = node.rightChild;
        Node<T> parentNode = getParent(node);

        node.rightChild = rightNode.leftChild;
        rightNode.leftChild = node;

        if (parentNode != null) {
            if (parentNode.val.compareTo(rightNode.val) > 0) parentNode.leftChild = rightNode;
            else parentNode.rightChild = rightNode;
        } else root = rightNode;

        setBalance(node);
        setBalance(rightNode);
    }

    private void rotateLeftRight(Node<T> node) {
        Node<T> parentNode = getParent(node);
        rotateLeft(node.leftChild);
        rotateRight(node);

        if (parentNode != null) {
            if (parentNode.val.compareTo(Objects.requireNonNull(getParent(node)).val) > 0) parentNode.leftChild = getParent(node);
            else parentNode.rightChild = getParent(node);
        }
    }

    private void rotateRightLeft(Node<T> node) {
        Node<T> parentNode = getParent(node);
        rotateRight(node.rightChild);
        rotateLeft(node);

        if (parentNode != null) {
            if (parentNode.val.compareTo(Objects.requireNonNull(getParent(node)).val) > 0) parentNode.leftChild = getParent(node);
            else parentNode.rightChild = getParent(node);
        }
    }

    private Node<T> getParent(Node<T> child) {
        if (child == root) return null;
        Node<T> parentNode = new Node<>(null,0);
        Node<T> tempNode = root;

        while(tempNode != null) {
            if (tempNode.rightChild == child) {
                parentNode = tempNode;
                break;
            }
            if (tempNode.leftChild == child) {
                parentNode = tempNode;
                break;
            }

            if (child.val.compareTo(tempNode.val) > 0) {
                tempNode = tempNode.rightChild;
            } else {
                if (child.val.compareTo(tempNode.val) < 0) {
                    tempNode = tempNode.leftChild;
                }
            }
        }
        return parentNode;
    }

    private int getHeight(Node<T> node) {
        int height = 0;
        int leftHeight;
        int rightHeight;
        if (node != null) {
            leftHeight = getHeight(node.leftChild);
            rightHeight = getHeight(node.rightChild);
            if (rightHeight > leftHeight) height = rightHeight + 1;
            else height = leftHeight + 1;
        }
        return height;
    }

    private void setBalance(Node<T> node) {
        int leftHeight = getHeight(node.leftChild);
        int rightHeight = getHeight(node.rightChild);
        node.balance = rightHeight - leftHeight;
    }

    private Node<T> findNode(T val) {
        if (root == null) return null;
        return findNode(root, val);
    }

    private Node<T> findNode(Node<T> startNode, T val) {
        int compare = val.compareTo(startNode.val);
        if (compare == 0) {
            return startNode;
        } else if (compare < 0) {
            if (startNode.leftChild == null) return startNode;
            return findNode(startNode.leftChild, val);
        } else {
            if (startNode.rightChild == null) return startNode;
            return findNode(startNode.rightChild, val);
        }
    }

    private Node<T> getMinNode(Node<T> node) {
        if (node.leftChild == null) return node;
        else return getMinNode(node.leftChild);
    }

    public boolean checkAVL() {
        return root != null && isTree(root) && checkBalanced(root);
    }

    private boolean checkBalanced(Node<T> node) {
        Stack<Node<T>> stack = new Stack<>();
        Node<T> currentNode = node;
        Node<T> tempNode;
        stack.push(currentNode);

        while (!stack.isEmpty()) {
            if (currentNode.leftChild != null) {
                currentNode = currentNode.leftChild;
                stack.push(currentNode);
            } else {
                tempNode = stack.pop();
                if (Math.abs(tempNode.balance) > 1) return false;
                if (tempNode.rightChild != null) stack.push(tempNode.rightChild);
            }
        }
        return true;
    }

    private boolean isTree(Node<T> node) {
        Node<T> left = node.leftChild;
        Node<T> right = node.rightChild;
        return (left == null || (left.val.compareTo(node.val) < 0 && isTree(left))) && (right == null || right.val.compareTo(node.val) > 0 && isTree(right));
    }

    @Override
    public Iterator<T> iterator() {
        return new IteratorAVL(root);
    }

    public class IteratorAVL implements Iterator<T> {
        Stack<Node<T>> stack;

        IteratorAVL(Node<T> node) {
            stack = new Stack<>();
            while (node != null) {
                stack.push(node);
                node = node.leftChild;
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public T next() {
            Node<T> node = stack.pop();
            T result = node.val;
            if (node.rightChild != null) {
                node = node.rightChild;
                while (node != null) {
                    stack.push(node);
                    node = node.leftChild;
                }
            }
            return result;
        }
    }

    @Override
    public T first() {
        Node<T> node = root;
        if (node == null) throw new NoSuchElementException();
        while (node.leftChild != null)
            node = node.leftChild;
        return node.val;
    }

    @Override
    public T last() {
        Node<T> node = root;
        if (node == null) throw new NoSuchElementException();
        while (node.rightChild != null)
            node = node.rightChild;
        return node.val;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public boolean contains(Object o) {
        T t = (T) o;
        Node<T> findNode = findNode(t);
        return findNode != null && t.compareTo(findNode.val) == 0;
    }

    @Override
    public Object[] toArray() {
        Object[] arr = new Object[size];
        int i = 0;
        for (T t : this) {
            arr[i] = t;
            i++;
        }
        return arr;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (c.isEmpty()) return false;
        for (Object object : c) {
            if (!contains(object)) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (c.isEmpty()) return false;
        else {
            for (T t : c) {
                add(t);
            }
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (c.isEmpty()) return false;
        List<T> list = new ArrayList<>();
        for (Object object : c) {
            if (contains(object)) {
                list.add((T) object);
            }
        }
        return addAll(list);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (c.isEmpty()) return false;
        for (Object object : c) {
            if (contains(object)) {
                remove(object);
            } else return false;
        }
        return true;
    }

    @Override
    public void clear() {

    }

    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        return null;
    }

    @Override
    public SortedSet<T> headSet(T toElement) {
        return null;
    }

    @Override
    public SortedSet<T> tailSet(T fromElement) {
        return null;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }
}
