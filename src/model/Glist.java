package model;

import java.lang.reflect.Array;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Ángel Serrano García
 * @param <T>
 */
public class Glist<T> implements IGlist<T> {

    private Node<T> _root;
    private Node<T> _last;
    private int _size;
    int iter;

    public Glist() {
        this._size = 0;
        this._root = null;
        this._last = null;
    }

    @Override
    public int push(T n) {
        Node<T> node = new Node<>(n);

        if (this._root == null) {
            this._root = node;
            node._prev = null;
            node._next = null;

        } else {
            this._last._next = node;
            node._prev = this._last;
        }
        this._last = node;
        this._size++;
        return this._size;
    }

    @Override
    public T pop() {
        Node<T> last = this._last;

        if (last != null) {
            if (last._prev != null) {
                this._last = last._prev;
                this._last._next = null;
                last._prev = null;
            } else {
                this._root = null;
                this._last = null;
            }
            this._size--;
        }

        return last != null ? last.getValue() : null;

    }

    @Override
    public int unshift(T n) {

        Node<T> aux = this._root;
        Node<T> node = new Node<>(n);
        this._root = node;
        node._prev = null;
        node._next = aux;

        if (node._next != null) {
            node._next._prev = node;
        }
        if (this._last == null) {
            this._last = this._root;
        }
        this._size++;

        return this._size;

    }

    @Override
    public T shift() {
        Node<T> aux = this._root;

        if (aux != null) {

            if (this._root._next != null) {
                this._root = this._root._next;
                this._root._prev = null;
            } else {
                this._root = null;
            }
            if (this._size > 0) {
                this._size--;
            }
        }
        return aux != null ? aux.getValue() : null;
    }

    public boolean search(T t) {
        Node<T> current = this._root;
        boolean find = false;

        while (current._next != null && !find) {
            if (t.equals(current.getValue())) {
                find = true;
            } else {
                current = current._next;
            }
        }

        return find;
    }

    public boolean isempty() {
        boolean isempty = false;
        if (this._root == null) {
            isempty = true;
        }
        return isempty;
    }

    public int size() {
        return this._size;
    }

    @Override
    public T get(int pos) {
        Node<T> current;
        int i;

        if (pos < this._size / 2) {
            current = this._root;
            i = 0;
            if (current != null && pos > 0) {
                do {
                    current = current._next;
                    i++;
                } while (current != null && i < pos);
            }
        } else {
            current = this._last;
            i = this._size - 1;
            if (current != null && i != pos) {
                do {
                    current = current._prev;
                    i--;
                } while (current != null && pos < i);
            }
        }

        return ((i == pos) && (current != null)) ? current.getValue() : null;
    }

    @Override
    public Node<T> getElement(int pos) {
        Node<T> current;
        int i;

        if (pos < this._size / 2) {
            current = this._root;
            i = 0;
            if (current != null && pos > 0) {
                do {
                    current = current._next;
                    i++;
                } while (current != null && i < pos);
            }
        } else {
            current = this._last;
            i = this._size - 1;
            if (current != null && i != pos) {
                do {
                    current = current._prev;
                    i--;
                } while (current != null && pos < i);
            }
        }

        return i == pos ? current : null;
    }

    @Override
    public int put(T value, int pos) {

        if (this._size > pos) {
            Node<T> aux = this.getElement(pos);
            Node<T> nuevo = new Node<>(value);
            nuevo._next = aux;
            if (aux._prev != null) {
                nuevo._prev = aux._prev;
                aux._prev._next = nuevo;
            } else {
                this._root = nuevo;
            }
            aux._prev = nuevo;
        } else {
            this.push(value);
        }
        this._size++;
        return this._size;
    }

    @Override
    public int remove(int pos) {

        Node<T> current = this._root;
        int i = 0;

        if (current != null && pos >= 1) {
            do {
                current = current._next;
                i++;
            } while (current != null && i < pos);
        }

        if (i == pos) {
            if (current == this._root) {
                this.shift();
            } else if (current == this._last) {
                this.pop();
            } else {

                if (current != null) {
                    if (current._prev != null) {
                        current._prev._next = current._next;
                    }
                    if (current._next != null) {
                        current._next._prev = current._prev;
                    }

                    this._size--;
                }
            }
        }
        return this._size;
    }

    @Override
    public int removeElement(T v) {

        int pos = this.contains(v);
        return this.remove(pos);
    }

    @Override
    public int removeAllElement(T v
    ) {
        int pos = this.contains(v);

        while (pos != -1) {
            this.removeElement(v);
            pos = this.contains(v);
        }

        return this._size;
    }

    @Override
    public void reverse() {
        Glist<T> aux = new Glist<>();

        while (this._size >= 1) {
            aux.unshift(this.shift());
        }
        this._root = aux._root;
        this._last = aux._last;
        this._size = aux._size;
    }

    @Override
    public void sort() {
        Node<T> current;
        Node<T> aux;

        for (int i = 0; i < this._size; i++) {
            for (int j = 0; j < (this._size - i); j++) {
                current = this.getElement(j);
                aux = this.getElement(j - 1);
                if (aux != null && aux.compareTo(current) > 0) {

                    if (aux._prev != null) {
                        aux._prev._next = aux._next;
                    }

                    if (current._next != null) {
                        current._next._prev = current._prev;
                    }

                    aux._next = current._next;
                    current._next = aux;
                    current._prev = aux._prev;
                    aux._prev = current;

                    if (aux._prev == null) {
                        this._root = aux;
                    }
                    if (aux._next == null) {
                        this._last = aux;
                    }
                    if (current._prev == null) {
                        this._root = current;
                    }
                    if (current._next == null) {
                        this._last = current;
                    }
                }

            }

        }

    }

    public void sort(Comparator<T> c) {

        if (c != null) {
            Node<T> current;
            Node<T> aux;
            for (int i = 0; i < this._size; i++) {
                for (int j = 0; j < (this._size - i); j++) {
                    current = this.getElement(j);
                    aux = this.getElement(j - 1);
                    if (aux != null && Objects.compare(aux.getValue(), current.getValue(), c) > 0) {

                        if (aux._prev != null) {
                            aux._prev._next = aux._next;
                        }

                        if (current._next != null) {
                            current._next._prev = current._prev;
                        }

                        aux._next = current._next;
                        current._next = aux;
                        current._prev = aux._prev;
                        aux._prev = current;

                        if (aux._prev == null) {
                            this._root = aux;
                        }
                        if (aux._next == null) {
                            this._last = aux;
                        }
                        if (current._prev == null) {
                            this._root = current;
                        }
                        if (current._next == null) {
                            this._last = current;
                        }
                    }

                }

            }
        } else {
            this.sort();
        }

    }

    @Override
    public Iterator<T> iterator() {
        this.iter = 0;
        Node<T> current = this._root;
        Iterator<T> i = new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return (iter < _size) && (current != null);
            }

            @Override
            public T next() {
                return get(iter++);
            }
        };

        return i;
    }

    @Override
    public int contains(T v) {

        Node<T> current = this._root;
        boolean find = false;
        int i = 0;

        if (current != null) {

            if (current.getValue().equals(v)) {
                find = true;
            }
            while (current._next != null && !find) {
                if (v.equals(current.getValue())) {
                    find = true;
                } else {
                    i++;
                    current = current._next;
                }
            }
        }
        return find ? i : -1;
    }

    public void shuffle() {

        int max = this._size * 2;

        for (int i = 0; i < max; i++) {
            /*int pos = ThreadLocalRandom.current().nextInt(0, this._size);
            T aux = this.get(pos);
            this.remove(pos);
            pos = ThreadLocalRandom.current().nextInt(0, this._size);
            this.put(aux, pos);*/
            int pos1 = ThreadLocalRandom.current().nextInt(0, this._size);
            int pos2 = ThreadLocalRandom.current().nextInt(0, this._size);
            this.swapp(this.getElement(pos1), this.getElement(pos2));
        }
    }

    public void qcksort() {
        this.shuffle(); //perdemos tiempo en todos los casos, pero evitamos los peores casos de orden inverso, para los que quickSort tiene O(N^2)
        qcksort(0, this._size - 1);
    }

    private void qcksort(int low, int high) {
        if (low < high) {
            int partitionIndex = partition(low, high);

            qcksort(low, partitionIndex - 1);
            qcksort(partitionIndex + 1, high);
        }
    }

    private int partition(int begin, int end) {
        Node<T> pivot = this.getElement(end);
        int i = (begin - 1);

        for (int j = begin; j < end; j++) {
            Node<T> nj = this.getElement(j);
            if (nj != null && nj.compareTo(pivot) <= 0) {
                i++;
                Node<T> ni = this.getElement(i);
                this.swapp(nj, ni);
            }
        }

        Node<T> aux = this.getElement(i + 1);
        this.swapp(aux, pivot);

        return i + 1;
    }

    private void swapp(Node<T> n1, Node<T> n2) {

        if (n1 != null && n2 != null && n1 != n2) {
            if (n1._next == n2) {
                if (n1._prev != null) {
                    n1._prev._next = n1._next;
                }

                if (n2._next != null) {
                    n2._next._prev = n2._prev;
                }

                n1._next = n2._next;
                n2._next = n1;
                n2._prev = n1._prev;
                n1._prev = n2;

                if (n1._prev == null) {
                    this._root = n1;
                }
                if (n1._next == null) {
                    this._last = n1;
                }
                if (n2._prev == null) {
                    this._root = n2;
                }
                if (n2._next == null) {
                    this._last = n2;
                }

            } else if (n1._prev == n2) {
                if (n2._prev != null) {
                    n2._prev._next = n2._next;
                }

                if (n1._next != null) {
                    n1._next._prev = n1._prev;
                }

                n2._next = n1._next;
                n1._next = n2;
                n1._prev = n2._prev;
                n2._prev = n1;

                if (n2._prev == null) {
                    this._root = n2;
                }
                if (n2._next == null) {
                    this._last = n2;
                }
                if (n1._prev == null) {
                    this._root = n1;
                }
                if (n1._next == null) {
                    this._last = n1;
                }

            } else {
                Node<T> aux = new Node<>(null);
                Node<T> aux2 = new Node<>(null);

                aux._next = n1._next;
                aux._prev = n1._prev;
                aux2._next = n2._next;
                aux2._prev = n2._prev;

                if (n1._next != null) {
                    n1._next._prev = n2;
                }
                if (n1._prev != null) {
                    n1._prev._next = n2;
                }
                if (n2._next != null) {
                    n2._next._prev = n1;
                }
                if (n2._prev != null) {
                    n2._prev._next = n1;
                }

                n1._next = n2._next;
                n1._prev = n2._prev;
                n2._next = aux._next;
                n2._prev = aux._prev;

                if (n1 == this._root) {
                    this._root = n2;
                } else if (n2 == this._root) {
                    this._root = n1;
                }

                if (n1 == this._last) {
                    this._last = n2;
                } else if (n2 == this._last) {
                    this._last = n1;
                }
            }

        }

    }

    public T[] ToArray() {
        
        if (this._size > 0) {
            T[] aux = (T[]) Array.newInstance(this._root.getValue().getClass(), _size);
            Node<T> current = this._root;
            int i = 0;
            while (current != null) {
                aux[i] = current.getValue();
                i++;
                current = current._next;
            }
            return aux;
        }
        return null;
    }

    public void clear() {

        Node<T> current = this._root;

        while (current._next != null) {
            current = current._next;
            current._prev._prev = null;
            current._prev._next = null;
            current._prev = null;
        }

        this._root = null;
        this._last = null;
        this._size = 0;
    }
}
