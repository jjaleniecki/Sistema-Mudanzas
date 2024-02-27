package TPO.Lineales;

public class Nodo {
    private Object elem;
    private Nodo enlace;
    
    public Nodo(Object el, Nodo en){
        this.elem=el;
        this.enlace=en;
    }
    
    public Object getElem(){
        return this.elem;
    }
    
    public void setElem(Object el){
        this.elem = el;
    }
    
    public Nodo getEnlace(){
        return this.enlace;
    }
    
    public void setEnlace(Nodo en){
        this.enlace = en;
    }
}