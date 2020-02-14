package model;

/**
 *
 * @author Ángel Serrano García
 * @param <T>
 */
import java.io.Serializable;


public interface INode<T> extends Serializable,Comparable<INode<T>>{
    T getValue();
    void setValue(T value);
    
}
