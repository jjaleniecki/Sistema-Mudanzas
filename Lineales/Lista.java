package TPO.Lineales;
public class Lista {
    private Nodo cabecera;
    
    public Lista(){
        this.cabecera = null;
    }
    
    public boolean insertar(Object elem, int pos){
        boolean exito = true;
        
        if(pos < 1 || pos > this.longitud() + 1){
            exito = false;
        }else{
            if(pos == 1){
                this.cabecera = new Nodo(elem, this.cabecera);
            }else{
                Nodo aux = this.cabecera;
                int i=1;
                while (i < pos-1){
                    aux = aux.getEnlace();
                    i++;
                }
                Nodo nuevo = new Nodo(elem, aux.getEnlace());
                aux.setEnlace(nuevo);
            }
        }
        return exito;
    }
    
    public boolean eliminar(int pos){
        boolean exito = true;
        
        if(pos < 1 || pos > this.longitud()){
            exito = false;
        }else{
            if(pos == 1){
                this.cabecera = this.cabecera.getEnlace();
            }else{
                Nodo aux = this.cabecera;
                int i=1;
                while (i < pos-1){
                    aux = aux.getEnlace();
                    i++;
                }
                aux.setEnlace(aux.getEnlace().getEnlace());
            }
        }
        return exito;
    }
    
    public Object recuperar(int pos){
        Object elem;
            if(pos >= 1 && pos <= this.longitud()){
                int i=1;
                Nodo aux = this.cabecera;
                while (i < pos){
                    aux = aux.getEnlace();
                    i++;
                }
                elem = aux.getElem();
            }else{
                elem = null;
            }
        return elem;
    }
        
    
    public int localizar(Object elem){
        boolean encontrao = false;
        int i = 1, pos = -1, largo = this.longitud();
        Nodo aux = this.cabecera;
        if(aux != null){
            while(!encontrao && i <= largo){
                if (elem.equals(aux.getElem())){
                    encontrao = true;
                    pos = i;
                }
                aux = aux.getEnlace();
                i++;
            }
        }
        return pos;
    }
    
    public void vaciar(){
        this.cabecera = null;
    }
    
    public boolean esVacia(){
        return this.cabecera == null;
    }
    
    public Lista clone(){
        Lista listin = new Lista();
        Nodo aux1 = this.cabecera;
        Nodo aux2;

        if (aux1!=null){
            aux2 = new Nodo(aux1.getElem(), null);
            listin.cabecera = aux2;

            while(aux1.getEnlace() != null){
                aux1 = aux1.getEnlace();
                aux2.setEnlace(new Nodo(aux1.getElem(), null));
                aux2 = aux2.getEnlace();
            }
        }
        return listin;
    }
    
    public int longitud(){
        int largo=0;
        Nodo aux = this.cabecera;
        if(aux != null){
            largo = 1;
            while(aux.getEnlace() != null){
                aux = aux.getEnlace();
                largo++;
            }
        }
        return largo;
    }
    
    public String toString(){
        String out;
        
        if (this.cabecera == null){
            out = "Lista vacia";
        }else{
            Nodo aux = this.cabecera;
            out = "";

            while(aux != null){
                out+=aux.getElem().toString();
                aux = aux.getEnlace();
                if (aux != null){
                    out += " | ";
                }
            }
            //out+="]";
        }
        return out;
    }
    
    public void invertir(){
        Nodo aux1, aux2, aux3;
        aux1 = this.cabecera;
        
        if(aux1 != null && aux1.getEnlace() != null){
            aux2 = aux1.getEnlace();
            aux1.setEnlace(null);
            
            while(aux2 != null){
                aux3 = aux2.getEnlace();
                aux2.setEnlace(aux1);
                aux1 = aux2;
                aux2 = aux3;
            }
            this.cabecera = aux1;
        }
    }
    
    /*
    public void eliminarApariciones(Object x){
        Nodo aux1, aux2;
        aux1 = this.cabecera;
        
        if(aux1 != null){
            aux2 = aux1.getEnlace();
            if(aux1.getElem() == x && aux2 != null){
                this.cabecera = aux1.getEnlace();
                aux1 = aux1.getEnlace();
                aux2 = aux2.getEnlace();
            }else if(aux1.getElem() == x && aux2 == null){
                this.cabecera = aux1.getEnlace();
                aux1 = aux1.getEnlace();
            }else{
                
            }
        }
    }*/
    
    public void cambiarPosicion(int pos1, int pos2){
        if(this.cabecera != null){
            if(pos1>0 && pos2>0 && pos1!= pos2){
                Nodo aux = this.cabecera;
                int pos = 1;
                if(pos1 < pos2){
                    while(pos != pos1){
                        aux = aux.getEnlace();
                        pos++;
                    }
                    Object el = aux.getElem();
                    while(pos!=pos2){
                        aux.setElem(aux.getEnlace().getElem());
                        aux=aux.getEnlace();
                        pos++;
                    }
                    aux.setElem(el);
                }else{
                    while(pos!=pos2){
                        aux=aux.getEnlace();
                        pos++;
                    }
                    Object el=aux.getElem(), e2=el;
                    Nodo aux2=aux;
                    while(pos!=pos1){
                        aux=aux.getEnlace();
                        e2=aux.getElem();
                        aux.setElem(el);
                        el=e2;
                        pos++;
                    }
                    aux2.setElem(e2);
                }
            }
        }
    }
    
    public boolean cambiarPosicion2(int pos1, int pos2) {
	/*elimina el elemento de pos1 y lo inserta en pos2*/
	boolean insertado = false;
	int cont = 2;
	Nodo aux = this.cabecera, anteriorInsertar = null, anteriorEliminar = null;
	
	if(aux != null && pos1 > 0 && pos2 > 0) {
		while(aux.getEnlace() != null && (cont <= pos1 || cont <= pos2)) {
			if(cont == pos1) {
				anteriorEliminar = aux;
			}
			if(cont == pos2) {
				anteriorInsertar = aux;
			}
			
			cont++;
			aux = aux.getEnlace();	
		}
		if(cont > pos1 && cont > pos2) {
			//se encontraron ambas posiciones
                        if(pos1 < pos2){
                            anteriorInsertar = anteriorInsertar.getEnlace();
                        }
			if(pos1 == 1) {
				//el elemento a eliminar es la cabecera
				aux = this.cabecera;
				this.cabecera = aux.getEnlace();
			} else {
				aux = anteriorEliminar.getEnlace();
				anteriorEliminar.setEnlace(aux.getEnlace());
			}
			
			if(pos2 == 1) {
				//debemos agregar al elemento como la nueva cabecera
				aux.setEnlace(this.cabecera);
				this.cabecera = aux;
			} else {
				aux.setEnlace(anteriorInsertar.getEnlace());
				anteriorInsertar.setEnlace(aux);
			}
			
			insertado = true;
		}
	}
	return insertado;
    }
    
    
    public void agregarElem(Object nuevo, int x){
        int cont = 0;
        Nodo aux = this.cabecera;
        if(aux!=null){
            while(aux != null){
                if(cont == 0){
                    this.cabecera = new Nodo(nuevo, aux);
                    cont++;
                }else if(cont != 0 && cont%(x+1)==0){
                    aux.setElem(nuevo);
                }
                aux = aux.getEnlace();
                cont++;
            }
        }
    }
    
    public Lista intercalar(Lista otro){
        Lista ret = new Lista();
        int cont = 0;
        Nodo aux1 = ret.cabecera;
        Nodo aux2 = otro.cabecera;
        Nodo aux3 = this.cabecera;
        
        
        while(aux2 != null && aux3 != null){
            if(cont==0){
                ret.cabecera = new Nodo(this.cabecera.getElem(), null);
                aux1 = ret.cabecera;
                aux3 = aux3.getEnlace();
            }else if(cont%2!=0){
                aux1.setEnlace(new Nodo(aux2.getElem(), null));
                aux1 = aux1.getEnlace();
                aux2 = aux2.getEnlace();
            }else{
                aux1.setEnlace(new Nodo(aux3.getElem(), null));
                aux1 = aux1.getEnlace();
                aux3 = aux3.getEnlace();
            }
            cont++;
        }
        while(aux2 != null){
            aux1.setEnlace(new Nodo(aux2.getElem(), null));
            aux1 = aux1.getEnlace();
            aux2 = aux2.getEnlace();
        }
        while(aux3 != null){
            aux1.setEnlace(new Nodo(aux3.getElem(), null));
            aux1 = aux1.getEnlace();
            aux3 = aux3.getEnlace();
        }
        
        return ret;
    }


    public int contarIt(Object el){
        int cant = 0;
        Nodo aux = this.cabecera;
        while(aux!=null){
            if(aux.getElem().equals(el)){
                cant++;
            }
            aux = aux.getEnlace();
        }
        return cant;
    }

    public int contarRec(Object el){
        int cant = 0;
        if(this.cabecera != null){
            cant = contarRecAux(this.cabecera, el, 0);
        }
        return cant;
    }

    private int contarRecAux(Nodo n, Object el, int cant){
        if(n != null){
            if(n.getElem().equals(el)){
                cant++;
                cant = contarRecAux(n.getEnlace(), el, cant);
            }else{
                cant = contarRecAux(n.getEnlace(), el, cant);
            }
        }
        return cant;
    }

    public boolean esCapicua(){
        
        return false;
    }
}
