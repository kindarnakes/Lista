package Lista;

import java.util.ArrayList;

/**
 *
 * @author Ángel Serrano García
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        model.Glist<Integer> lista = new model.Glist<>();
        int maxlist = 100;
        /*for (int i = 0; i < maxlist; i++) {
            lista.push(i);
        }
        System.out.println("¿Cuántos elementos tenemos? " + lista.size());
        for (int i = 0; i < maxlist; i++) {
            //System.out.println(lista.shift());
            System.out.println(lista.pop());
        }

        for (int i = 0; i < maxlist; i++) {
            System.out.println(""+i+lista.shift());
        }

        System.out.println("¿Esta vacía ahora? " + lista.isempty());
        System.out.println("¿Cuántos elementos tenemos? " + lista.size());
        System.out.println("--------------------------");

        */for (int i = 0; i < maxlist; i++) {
            lista.unshift(i);
            //System.out.println(i);
        }
        //System.out.println("¿Esta vacía ahora? " + lista.isempty());
        int i = 50;
        /*System.out.println("¿Está " + i + " dentro? " + lista.search(i));
        System.out.println("--------------------------");
        lista.removeElement(5);*/
        lista.push(-1);
        lista.unshift(45);
        lista.unshift(45);
        lista.push(-45);
        lista.unshift(80);
        System.out.println("---> " +lista.getElement(24)+ "     " + lista.size());
        //lista.sort();
        //lista.reverse();
        lista.shuffle();
        //lista.clear();
        lista.qcksort();
        
        Integer[] inte = lista.ToArray();
        
        for (Integer inte1 : inte) {
            System.out.println(inte1);
        }
        /*maxlist = lista.size();
        for (i = 0; i < maxlist; i++) {
        System.out.println(lista.shift());
        }
        /*lista.push(1);
        lista.push(2);
        lista.push(3);
        lista.swapp(lista.getElement(0), lista.getElement(2));
        System.out.println(lista.shift() + " " + lista.shift() +" " +lista.shift());
         */
    }

}
