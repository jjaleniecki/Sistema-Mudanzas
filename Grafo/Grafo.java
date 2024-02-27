package TPO.Grafo;

import TPO.Lineales.Cola;
import TPO.Lineales.Lista;

public class Grafo {
    private NodoVert inicio = null;

    public Grafo(){
        inicio = null;
    }
    /*
     *insertarVertice(Object): boolean
     *eliminarVertice(Object): boolean
     *existeVertice(Object): boolean
     *insertarArco(Object, Object): boolean
     *eliminarArco(Object, Object): boolean
     *existeArco(Object, Object): boolean
     *vacio(): boolean
    */
    public boolean insertarVertice(Object nuevoVertice){
        boolean exito = false;
        NodoVert aux = this.ubicarVertice(nuevoVertice);
        if(aux == null){
            this.inicio = new NodoVert(nuevoVertice, this.inicio);
            exito = true;
        }
        return exito;
    }

    private NodoVert ubicarVertice(Object buscado){
        NodoVert aux = this.inicio;
        while(aux != null && !aux.getElem().equals(buscado)){
            aux = aux.getSigVertice();
        }
        return aux;
    }

    public boolean eliminarVertice(Object buscado){
        //Buscar el vertice, y eliminar todos los arcos que salen y entran de el
        boolean exito = false;
        NodoVert aux = ubicarVertice(buscado), aux1 = this.inicio, aux2;
        NodoAdy auxAdy1;
        if(aux != null){
            //Eliminamos los arcos que tienen como destino a buscado
            auxAdy1 = aux.getPrimerAdy();
            
            if(auxAdy1 != null){
                while(aux1 != null){
                    auxAdy1 = aux1.getPrimerAdy();
                    while(auxAdy1 != null){
                        aux2 = auxAdy1.getVertice();
                        if(aux2.getElem().equals(buscado)){
                            eliminarArcoAux(aux1, aux2);
                        }
                        auxAdy1 = auxAdy1.getSigAdyacente();
                    }
                    aux1 = aux1.getSigVertice();
                }    
            }
            //Eliminamos el vertice
            if(this.inicio.getElem().equals(buscado)){
                this.inicio = aux.getSigVertice();
            }else if(aux.getSigVertice() != null){
                ubicarVerticePrevio(buscado).setSigVertice(aux.getSigVertice());
            }else{
                //ver 
                ubicarVerticePrevio(buscado).setSigVertice(null);
            }
            //Eliminamos los arcos que tienen como origen a buscado
            aux.setPrimerAdy(null);
        }
        return exito;
    }

    private NodoVert ubicarVerticePrevio(Object buscado){
        NodoVert aux = this.inicio;
        while(aux != null && !aux.getSigVertice().getElem().equals(buscado)){
            aux = aux.getSigVertice();
        }
        return aux;
    }

    public boolean insertarArco(Object origen, Object destino, Object etiq){
        boolean exito = false;
        NodoVert auxO = null, auxD = null, auxI = this.inicio;

        while((auxO == null || auxD == null) && auxI != null){
            if (auxI.getElem().equals(origen)) auxO = auxI;
            if (auxI.getElem().equals(destino)) auxD = auxI;
            auxI = auxI.getSigVertice();
        }
        if(auxO != null && auxD != null){
            //Creamos el arco de origen a destino
            NodoAdy aux = auxO.getPrimerAdy();
            if(aux != null){
                while(aux.getSigAdyacente() != null){
                    aux = aux.getSigAdyacente();
                }
                aux.setSigAdyacente(new NodoAdy(auxD, null, etiq));
            }else{
                auxO.setPrimerAdy(new NodoAdy(auxD, null, etiq));
                
            }
            //Creamos el arco de destino a origen
            aux = auxD.getPrimerAdy();
            if(aux != null){
                while(aux.getSigAdyacente() != null){
                    aux = aux.getSigAdyacente();
                }
                aux.setSigAdyacente(new NodoAdy(auxO, null, etiq));
            }else{
                auxD.setPrimerAdy(new NodoAdy(auxO, null, etiq));
            }
        }
        return exito;
    }

    public boolean eliminarArco(Object origen, Object destino){
        boolean exito = false;
        NodoVert auxO = null, auxD = null, aux = this.inicio;
        while((auxO == null || auxD == null) && aux != null){
            if (aux.getElem().equals(origen)) auxO = aux;
            if (aux.getElem().equals(destino)) auxD = aux;
            aux = aux.getSigVertice();
        }
        
        exito = eliminarArcoAux(auxO, auxD);
        return exito;
    }

    private boolean eliminarArcoAux(NodoVert auxO, NodoVert auxD){
        Object origen = auxO.getElem(), destino = auxD.getElem();
        NodoAdy aux1, aux2;
        boolean exito = false;
        if(auxO != null && auxD != null){
            //eliminar arco de destino a origen
            exito = true;
            aux1 = auxD.getPrimerAdy();
            if(aux1 != null && !aux1.getVertice().getElem().equals(origen)){
                //si tiene al menos un adyacente y este no es igual a origen, recorrer la lista
                //de adyacentes hasta encontrar al que sea igual a origen
                aux2 = aux1.getSigAdyacente();
                while(aux2 != null && !aux2.getVertice().getElem().equals(origen)){
                    aux1 = aux1.getSigAdyacente();
                    if(aux1 != null) aux2 = aux1.getSigAdyacente();
                }
                if(aux2.getSigAdyacente() != null){
                    aux1.setSigAdyacente(aux2.getSigAdyacente());
                }else{
                    aux1.setSigAdyacente(null);
                }
                
            }else if(aux1 != null && aux1.getSigAdyacente() != null){
                //si el primer adyacente es igual a origen, y no es el unico de la lista
                //apuntar el primer adyacente al segundo, para eliminar el arco
                auxD.setPrimerAdy(aux1.getSigAdyacente());
            }else{
                //si el primer adyacente es igual a origen y no hay mas adyacentes
                //apuntar el primer adyacente a null
                auxD.setPrimerAdy(null);
            }
            //eliminar arco de origen a destino
            aux1 = auxO.getPrimerAdy();
            if(aux1 != null && !aux1.getVertice().getElem().equals(destino)){
                //si tiene al menos un adyacente y este no es igual a destino, recorrer la lista
                //de adyacentes hasta encontrar al que sea igual a destino
                aux2 = aux1.getSigAdyacente();
                while(aux2 != null && !aux2.getVertice().getElem().equals(destino)){
                    aux1 = aux1.getSigAdyacente();
                    if(aux1 != null) aux2 = aux1.getSigAdyacente();
                }
                if(aux2.getSigAdyacente() != null){
                    aux1.setSigAdyacente(aux2.getSigAdyacente());
                }else{
                    aux1.setSigAdyacente(null);
                }
                
            }else if(aux1 != null && aux1.getSigAdyacente() != null){
                //si el primer adyacente es igual a destino, y no es el unico de la lista
                //apuntar el primer adyacente al segundo, para eliminar el arco
                auxO.setPrimerAdy(aux1.getSigAdyacente());
            }else{
                //si el primer adyacente es igual a destino y no hay mas adyacentes
                //apuntar el primer adyacente a null
                auxO.setPrimerAdy(null);
            }
        }
        return exito;
    }

    public boolean existeVertice(Object buscado){
        boolean exito = false;
        NodoVert auxO = ubicarVertice(buscado);
        if(auxO != null) exito = true;
        return exito;
    }

    public boolean existeArco(Object origen, Object destino){
        boolean exito = false;
        NodoVert auxO = ubicarVertice(origen);
        NodoAdy aux = null;
        if(auxO != null) aux = auxO.getPrimerAdy();
        while(aux != null){
            if(aux.getVertice().getElem().equals(destino)){
                exito = true;
            }
            aux = aux.getSigAdyacente();
        }
        return exito;
    }

    public boolean existeCamino(Object origen, Object destino){
        boolean exito = false;
        NodoVert auxO = null, auxD = null, aux = this.inicio;

        while((auxO == null || auxD == null) && aux != null){
            if (aux.getElem().equals(origen)) auxO = aux;
            if (aux.getElem().equals(destino)) auxD = aux;
            aux = aux.getSigVertice();
        }

        if(auxO != null && auxD != null){
            Lista visitados = new Lista();
            exito = existeCaminoAux(auxO, destino, visitados);
        }
        return exito;
    }

    private boolean existeCaminoAux(NodoVert n, Object dest, Lista vis){
        boolean exito = false;
        if(n != null){
            if(n.getElem().equals(dest)) {
                //Hay camino pq llegamos de n a dest
                exito = true;
            }else{
                //Todavia no se llego a dest, sigo recorriendo
                vis.insertar(n.getElem(), vis.longitud()+1);
                NodoAdy ady = n.getPrimerAdy();
                while(!exito && ady != null){
                    if(vis.localizar(ady.getVertice().getElem())<0){
                        exito = existeCaminoAux(ady.getVertice(), dest, vis);
                    }
                    ady = ady.getSigAdyacente();
                }
            }
        }
        return exito;
    }

    public Object obtenerEtiqueta(Object origen, Object destino){
        Object etiq = null;
        NodoVert auxO = ubicarVertice(origen);
        NodoAdy aux = null;
        if(auxO != null) aux = auxO.getPrimerAdy();
        while(aux != null){
            if(aux.getVertice().getElem().equals(destino)){
                etiq = aux.getEtiqueta();
            }
            aux = aux.getSigAdyacente();
        }
        return etiq;
    }
    public Lista listarEnProfundidad(){
        Lista visitados = new Lista();
        NodoVert aux = this.inicio;
        while(aux != null){
            if(visitados.localizar(aux.getElem()) < 0){
                listarEnProfundidadAux(aux, visitados);
            }
            aux = aux.getSigVertice();
        }
        return visitados;
    }

    private void listarEnProfundidadAux(NodoVert n, Lista vis){
        if(n != null){
            vis.insertar(n.getElem(), vis.longitud()+1);
            NodoAdy ady = n.getPrimerAdy();
            while(ady != null){
                if(vis.localizar(ady.getVertice().getElem()) < 0){
                    listarEnProfundidadAux(ady.getVertice(), vis);
                }
                ady = ady.getSigAdyacente();
            }
        }
    }

    public Lista listarEnAnchura(){
        Lista visitados = new Lista();
        NodoVert aux = this.inicio;
        while(aux != null){
            if(visitados.localizar(aux.getElem()) < 0){
                //System.out.println(aux.getElem());
                listarEnAnchuraAux(aux, visitados);
            }
            aux = aux.getSigVertice();
        }
        return visitados;
    }

    private void listarEnAnchuraAux(NodoVert n, Lista vis){
        Cola cola = new Cola();
        NodoVert frente;
        NodoAdy ady;

        vis.insertar(n.getElem(), vis.longitud()+1);
        cola.poner(n.getElem());
        
        while(!cola.esVacia()){
            frente = ubicarVertice(cola.obtenerFrente());
            ady = frente.getPrimerAdy();
            cola.sacar();
            while(ady != null){
                if(vis.localizar(ady.getVertice().getElem()) < 0){
                    vis.insertar(ady.getVertice().getElem(), vis.longitud()+1);
                    cola.poner(ady.getVertice().getElem());
                }
                ady = ady.getSigAdyacente();
            }
        }
    }

    public Lista caminoMasCortoEtiq(Object origen, Object destino){
        Lista camino = new Lista();
        Lista visitados = new Lista();
        
        NodoVert auxO = null, auxD = null, auxI = this.inicio;

        while((auxO == null || auxD == null) && auxI != null){
            if (auxI.getElem().equals(origen)) auxO = auxI;
            if (auxI.getElem().equals(destino)) auxD = auxI;
            auxI = auxI.getSigVertice();
        }

        if(auxO != null && auxD != null){
            camino = caminoMasCortoEtiqAux(auxO, destino, camino, visitados);
        }
        return camino;
    }

    private Lista caminoMasCortoEtiqAux(NodoVert n, Object fin, Lista masCorto, Lista visitados) {
        NodoAdy ady;
        if (n != null) {
            visitados.insertar(n.getElem(), visitados.longitud() + 1);
            if (n.getElem().equals(fin)) {
                if (masCorto.esVacia() || etiqCamino(visitados) < etiqCamino(masCorto)) {
                    masCorto = visitados.clone();
                }
            } else {
                if(masCorto.esVacia() || (!masCorto.esVacia() && etiqCamino(visitados) < etiqCamino(masCorto))){
                    ady = n.getPrimerAdy();
                    while (ady != null) {
                        if(masCorto.esVacia() || (!masCorto.esVacia() && etiqCamino(visitados) < etiqCamino(masCorto))){
                            if (visitados.localizar(ady.getVertice().getElem()) < 0) {
                                masCorto = caminoMasCortoEtiqAux(ady.getVertice(), fin, masCorto, visitados);
                            }
                            ady = ady.getSigAdyacente();
                        }
                    }
                }
            }
            visitados.eliminar(visitados.longitud()); 
            // Deshacer el último cambio en la lista visitados antes de salir de la función.
        }
        return masCorto;
    }

    private float etiqCamino(Lista camino) {
        float etiquetaTotal = 0;
        int cont = 1;
        Object prox, el = camino.recuperar(1);
        while(el != null){
            prox = camino.recuperar(cont+1);
            if(el!= null && prox!=null) etiquetaTotal += (float) this.obtenerEtiqueta(el, prox);
            cont++;
            el = camino.recuperar(cont);
        }
        
        return etiquetaTotal;
    }

    public Lista caminoMasCortoNodos(Object origen, Object destino){
        Lista camino = new Lista();
        Lista visitados = new Lista();
        NodoVert auxO = null, auxD = null, auxI = this.inicio;

        while((auxO == null || auxD == null) && auxI != null){
            if (auxI.getElem().equals(origen)) auxO = auxI;
            if (auxI.getElem().equals(destino)) auxD = auxI;
            auxI = auxI.getSigVertice();
        }
        if(auxO != null && auxD != null){
            camino = caminoMasCortoNodosAux(auxO, destino, camino, visitados);
        }
        return camino;
    }

    private Lista caminoMasCortoNodosAux(NodoVert n, Object fin, Lista masCorto, Lista visitados){
        NodoAdy ady;
        if (n != null) {
            visitados.insertar(n.getElem(), visitados.longitud() + 1);
            if (n.getElem().equals(fin)) {
                if (masCorto.esVacia() || visitados.longitud() < masCorto.longitud()) {
                    masCorto = visitados.clone();
                }
            } else {
                ady = n.getPrimerAdy();
                while (ady != null) {
                    if (visitados.localizar(ady.getVertice().getElem()) < 0) {
                        masCorto = caminoMasCortoNodosAux(ady.getVertice(), fin, masCorto, visitados);
                    }
                    ady = ady.getSigAdyacente();
                }
            }
            visitados.eliminar(visitados.longitud());
        }
        return masCorto;
    }

    public boolean esVacia(){
        return this.inicio==null;
    }

    public String toString(){
        String out ="";
        NodoVert aux = this.inicio;
        NodoAdy aux2 = null;
        if(aux != null) aux2 = aux.getPrimerAdy();
        while(aux != null){
            aux2 = aux.getPrimerAdy();
            if(aux.getPrimerAdy() != null){
                while(aux2 != null){
                    out += "El vertice " + aux.getElem() + " apunta a ";
                    out += aux2.getVertice().getElem();
                    out += " con peso: " + aux2.getEtiqueta() + "\n";
                    aux2 = aux2.getSigAdyacente();
                }
            }else{
                out += "El vertice " + aux.getElem() + " no apunta a nadie";
            }
            out += "\n";
            aux = aux.getSigVertice();
        }
        return out;
    }

    public boolean esCamino(Lista ls){
        boolean esCamino = false;
        if(this.inicio != null){
            NodoVert n = ubicarVertice(ls.recuperar(1));
            esCamino = esCaminoAux(n, ls.recuperar(ls.longitud()), ls, 2);
        }
        return esCamino;
    }

    private boolean esCaminoAux(NodoVert n, Object fin, Lista camino, int it){
        boolean esCamino = false;
        NodoAdy aux = null;
        if(n != null) aux = n.getPrimerAdy();
        while(aux != null){
            if(aux.getVertice().getElem().equals(camino.recuperar(it))){
                esCamino = esCaminoAux(aux.getVertice(), fin, camino, it+1);
                if(aux.getVertice().getElem().equals(fin)){
                    esCamino = true;
                }
            }
            aux = aux.getSigAdyacente();
        }
        return esCamino;
    }
}
