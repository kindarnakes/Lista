package model;

import java.util.Comparator;
import java.util.Objects;

/**
 *
 * @author Ángel Serrano García
 * @param <T>
 */
public class Node<T> implements INode<T> {

    private T _value;
    protected Node<T> _next;
    protected Node<T> _prev;

    private Node() {
    }

    ;
    
    public Node(T _value) {
        this._value = _value;
    }

    @Override
    public T getValue() {
        return _value;
    }

    @Override
    public void setValue(T _value) {
        this._value = _value;
    }
    
    @Override
    public boolean equals(Object obj) {
        boolean result=false;
        if(obj!=null){
            if(obj instanceof Node){
                Node otro=(Node)obj;
                if(_value.equals(otro._value)){
                    result=true;
                }
            }
        }
        return result;
    }
    
    @Override
    public int compareTo(INode<T> o) {
        int compare = 1;
        if (o != null) {
            try {
                compare = (Integer) _value.getClass().getMethod("compareTo", o.getValue().getClass()).invoke(_value, o);
            } catch (Exception e) {
                miComparator<T> m=new miComparator<>();
                compare = Objects.compare(this._value, o.getValue(), m);
            }
        }
        return compare;
    }

}
