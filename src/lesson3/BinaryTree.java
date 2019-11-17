package lesson3;

import kotlin.NotImplementedError;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

// Attention: comparable supported but comparator is not
public class BinaryTree<T extends Comparable<T>> extends AbstractSet<T> implements CheckableSortedSet<T> {

    private static class Node<T> {
        final T value;

        Node<T> left = null;

        Node<T> right = null;

        Node(T value) {
            this.value = value;
        }
    }

    private Node<T> root = null;

    private int size = 0;
    private BinaryTree<T> topTree = this;
    private ArrayList<BinaryTree<T>> subTrees = new ArrayList<>();
    private T min = null;
    private T max = null;

    public BinaryTree() {}

    private BinaryTree(BinaryTree<T> tree, T min, T max) {
        this.min = min;
        this.max = max;
        for (T t : tree) {
            try {
                this.add(t);
            }
            catch (IllegalArgumentException e) {

            }
        }
        this.topTree = tree.topTree;

        this.topTree.subTrees.add(this);
    }


    @Override
    public boolean add(T t) {
        if (min != null && t.compareTo(min) < 0 || max != null && t.compareTo(max) >= 0) {
            throw new IllegalArgumentException();
        }
        if (this != topTree && topTree.add(t)) {
            return true;
        }
        Node<T> closest = find(t);
        int comparison = closest == null ? -1 : t.compareTo(closest.value);
        if (comparison == 0) {
            return false;
        }
        Node<T> newNode = new Node<>(t);
        if (closest == null) {
            root = newNode;
        }
        else if (comparison < 0) {
            assert closest.left == null;
            closest.left = newNode;
        }
        else {
            assert closest.right == null;
            closest.right = newNode;
        }
        size++;
        for (BinaryTree<T> sub : subTrees) {
            try {
                sub.add(t);
            }
            catch (IllegalArgumentException e) {

            }
        }
        return true;
    }

    public boolean checkInvariant() {
        return root == null || checkInvariant(root);
    }

    public int height() {
        return height(root);
    }

    private boolean checkInvariant(Node<T> node) {
        Node<T> left = node.left;
        if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
        Node<T> right = node.right;
        return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
    }

    private int height(Node<T> node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    /**
     * Удаление элемента в дереве
     * Средняя
     */
    @Override
    public boolean remove(Object o) {     //временя: O(log n), память: O(1)
        if (root == null || o == null) {
            return false;
        }
        @SuppressWarnings("unchecked")
        T x = (T) o;
        Node<T> node = root;
        Node<T> parent = null;
        while (node != null && !node.value.equals(x)) {
            parent = node;
            if (x.compareTo(node.value) < 0) {
                node = node.left;
            }
            else {
                node = node.right;
            }
        }
        if (node == null) {
            return false;
        }
        if (node.left == null && node.right == null) { // удаление листа
            if (node == root) {
                root = null;
            }
            else if (node == parent.left) {
                parent.left = null;
            }
            else {
                parent.right = null;
            }
        }
        else if (node.left != null && node.right != null) { // удаление элемента с двумя поддеревьями
            Node<T> leftest = node.right;
            Node<T> leftestParent = node;
            while (leftest.left != null) {
                leftestParent = leftest;
                leftest = leftest.left;
            }
            if (leftestParent == node) {
                node.right = leftest.right;
            }
            else {
                leftestParent.left = leftest.right;
            }
            leftest.left = node.left;
            leftest.right = node.right;
            if (parent == null) {
                root = leftest;
            }
            else if (node == parent.left) {
                parent.left = leftest;
            }
            else {
                parent.right = leftest;
            }
        }
        else { // удаление элемента с одним поддеревом
            Node<T> next = node.left != null ? node.left : node.right;
            if (parent == null) {
                root = next;
            }
            else if (node == parent.left) {
                parent.left = next;
            }
            else {
                parent.right = next;
            }
        }
        size--;
        return true;
    }

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        if (min != null && t.compareTo(min) < 0 || max != null && t.compareTo(max) >= 0) {
            return false;
        }
        Node<T> closest = find(t);
        return closest != null && t.compareTo(closest.value) == 0;
    }

    private Node<T> find(T value) {
        if (root == null) return null;
        return find(root, value);
    }

    private Node<T> find(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        }
        else if (comparison < 0) {
            if (start.left == null) return start;
            return find(start.left, value);
        }
        else {
            if (start.right == null) return start;
            return find(start.right, value);
        }
    }

    public class BinaryTreeIterator implements Iterator<T> {
        Stack<Node<T>> stack = new Stack<>();
        Node<T> lastNode = null;
        boolean skipPushing = false;
        private void pushToLeft(Node<T> node) {
            if (node != null) {
                stack.push(node);
                pushToLeft(node.left);
            }
        }
        private BinaryTreeIterator() {
            // Добавьте сюда инициализацию, если она необходима
            pushToLeft(root);
        }

        /**
         * Проверка наличия следующего элемента
         * Средняя
         */
        @Override
        public boolean hasNext() { // время: O(1), память: O(1)
            return !stack.empty();
        }

        /**
         * Поиск следующего элемента
         * Средняя
         */
        @Override
        public T next() {  // время: O(log n), память: O(log n)
            lastNode = stack.pop();
            if (skipPushing) {
                skipPushing = false;
            }
            else {
                pushToLeft(lastNode.right);
            }
            return lastNode.value;
        }

        /**
         * Удаление следующего элемента
         * Сложная
         */
        @Override
        public void remove() {
            // TODO
            throw new NotImplementedError();
        }
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeIterator();
    }

    @Override
    public int size() {
        return size;
    }


    @Nullable
    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    /**
     * Для этой задачи нет тестов (есть только заготовка subSetTest), но её тоже можно решить и их написать
     * Очень сложная
     */
    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        return new BinaryTree<>(this, fromElement, toElement);
    }

    /**
     * Найти множество всех элементов меньше заданного
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {  // время: O(n ^ 2), память: O(n)
        return new BinaryTree<>(this, min, toElement);
    }

    /**
     * Найти множество всех элементов больше или равных заданного
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {  // время: O(n ^ 2), по память: O(n)
        return new BinaryTree<>(this, fromElement, max);
    }

    @Override
    public T first() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    @Override
    public T last() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }
}
