package TPO.Grafo;

public class NodoAdy {
    private NodoVert vertice;
    private NodoAdy sigAdyacente;
    private Object etiqueta;
    
    public NodoAdy(NodoVert vert, NodoAdy sig, Object etiq) {
        sigAdyacente = sig;
        vertice = vert;
        etiqueta = etiq;
    }

    public NodoVert getVertice() {
        return vertice;
    }
    public void setVertice(NodoVert vertice) {
        this.vertice = vertice;
    }
    public NodoAdy getSigAdyacente() {
        return sigAdyacente;
    }
    public void setSigAdyacente(NodoAdy sigAdyacente) {
        this.sigAdyacente = sigAdyacente;
    }
    public Object getEtiqueta() {
        return etiqueta;
    }
    public void setEtiqueta(Object etiqueta) {
        this.etiqueta = etiqueta;
    }
    
}
