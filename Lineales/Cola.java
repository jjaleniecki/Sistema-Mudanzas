package TPO.Lineales;

public class Cola {
    private Nodo frente;
    private Nodo fin;
    
    public Cola(){
        this.frente = null;
        this.fin = null;
    }
    
    public boolean poner(Object item){
        Nodo nuevo = new Nodo(item, null);
        
        if(this.frente == null){
            this.frente = nuevo;
        }else{
            this.fin.setEnlace(nuevo);
        }
        this.fin = nuevo;
        return true;
    }
    
    public boolean sacar(){
        boolean exito=false;
        if (this.frente != null){
            this.frente = this.frente.getEnlace();
            if (this.frente == null){
                this.fin = null;
                
            }
            exito = true;
        }
        return exito;
    }
    
    public Object obtenerFrente(){
        Object frente = null;
        if(this.frente != null)
            frente = this.frente.getElem();
        return frente;
    }
    
    public boolean esVacia(){
        boolean vacia = this.frente == null;
        return vacia;
    }
    
    public void vaciar(){
        this.frente = null;
        this.fin = null;
    }
    
    public Cola clone(){
        Cola colin = new Cola();
        Nodo aux1 = this.frente;
        Nodo aux2;

        if (aux1!=null){
            aux2 = new Nodo(aux1.getElem(), null);
            colin.frente = aux2;

            while(aux1.getEnlace() != null){
                aux1 = aux1.getEnlace();
                aux2.setEnlace(new Nodo(aux1.getElem(), null));
                aux2 = aux2.getEnlace();
            }
            colin.fin = aux2;
        }
        return colin;
    }
    
    public String toString(){
        String out="[";
        if(this.frente == null){
            out+="Cola vacia";
        }else{
            Nodo aux = this.frente;
            while(aux != null){
                out += aux.getElem().toString();
                aux = aux.getEnlace();
                if (aux != null)
                    out+= ", ";
            }
        }
        out+="]";
        return out;
    }
}
