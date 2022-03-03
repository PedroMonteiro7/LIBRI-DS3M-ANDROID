package model;

// guarda categorias e os objetos que possuem uma delas
public class Item {

    /* types:
    * 0 - Livro
    * 1 - HQ
    * 2 - Mangá
    * */

    private int type;
    private Object object;

    public Item(int type, Object object) {
        this.type = type;
        this.object = object;
        //classe primitiva primordial que pode receber qualquer coisa
    }

    public int getType() {
        return type;
    }

    public Object getObject() {
        return object;
    }

}
