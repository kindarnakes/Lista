package model;

import java.util.Comparator;

class miComparator<T> implements Comparator<T> 
{ 
    // Used for sorting in ascending order of 
    // roll number 
    @Override
    public int compare(T a, T b) 
    { 
        return (Integer)a - (Integer)b; 
    } 
} 