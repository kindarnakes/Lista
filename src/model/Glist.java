package model;

import java.util.Iterator;

/**
 *
 * @author Ángel Serrano García
 * @param <T>
 */
public class Glist<T> implements IGlist<T> {

    private Node<T> _root;
    private int _size;
    int iter;
    
    public Glist() {
        this._size = 0;
        this._root = null;
    }

    @Override
    public int push(T n) {
        Node<T> node = new Node<>(n);
        Node<T> current = this._root;

        if (this._root == null) {
            this._root = node;
            node._prev = null;
            node._next = null;
        } else {
            while (current._next != null) {
                current = current._next;
            }
            current._next = node;
            node._prev = current;
            node._next = null;
        }

        this._size++;
        return this._size;
    }

    @Override
    public T pop() {
        Node<T> aux = this._root;
        int newLast = this._size - 2;

        if (aux != null) {
            if (newLast > 0) {
                for (int i = 0; i < newLast; i++) {
                    aux = aux._next;
                }
                Node<T> pop = aux._next;

                aux._next = null;
                if (pop != null) {
                    pop._prev = null;
                }
                aux = pop;
            }

            if (this._size == 1) {
                this._root = null;
                this._size = 0;
            }

            if (this._size > 0) {
                this._size--;
            }
        }

        return aux != null ? aux.getValue() : null;
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
        Node<T> current = this._root;
        int i = 0;

        if (current != null && pos > 1) {
            do {
                current = current._next;
                i++;
            } while (current != null && i < pos);
        }

        return ((i == pos) && (current != null)) ? current.getValue() : null;
    }

    @Override
    public Node<T> getElement(int pos) {
        Node<T> current = this._root;
        int i = 0;

        if (current != null && pos >= 0) {
            do {
                current = current._next;
                i++;
            } while (current != null && i < pos);
        }

        return current;
    }

    @Override
    public int put(T value, int pos) {

        Node<T> current = this._root;
        int i = 0;

        if (current != null && pos > 1) {
            do {
                current = current._next;
                i++;
            } while (current != null && i < pos);
        }

        if (i == pos) {
            Node<T> aux = new Node<>(value);
            aux._next = current;
            this._size++;
            if (current != null) {
                current._prev = aux;
                aux._prev = current._prev;
            } else {
                aux._prev = null;
            }

        }

        return this._size;
    }

    @Override
    public int remove(int pos) {

        Node<T> current = this._root;
        int i = 0;

        if (current != null && pos > 1) {
            do {
                current = current._next;
                i++;
            } while (current != null && i < pos);
        }

        if (i == pos) {
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

        return this._size;
    }

    @Override
    public int removeElement(T v) {

        int pos = this.contains(v);
        return this.remove(pos);
    }

    @Override
    public int removeAllElement(T v) {
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
    }

    @Override
    public void sort() {
        Node<T> current;
        Node<T> aux;

        for (int i = 0; i < this._size; i++) {
            for (int j = 0; j < this._size - i; j++) {
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
                    if (current._prev == null) {
                        this._root = current;
                    }
                }

            }

        }

    }

    @Override
    public Iterator<T> iterator() {
        this.iter = 0;
        Node<T> current = this._root;
        Iterator<T> i = new Iterator<T>(){
            @Override
            public boolean hasNext(){
                return (iter < _size) && (current != null);
            }
            
            @Override
            public T next(){
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

}
