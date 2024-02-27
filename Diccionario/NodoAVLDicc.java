package TPO.Diccionario;

public class NodoAVLDicc {
    private Comparable clave;
    private Object dato;
    private int altura;
    private NodoAVLDicc izq;
    private NodoAVLDicc der;

    public NodoAVLDicc(Comparable el, Object dat, NodoAVLDicc iz, NodoAVLDicc de){
        this.clave = el;
        this.dato = dat;
        this.izq = iz;
        this.der = de;
    }

    public Comparable getClave() {
        return this.clave;
    }

    public void setClave(Comparable elem) {
        this.clave = elem;
    }

    public Object getDato(){
        return this.dato;
    }

    public void setDato(Object dat){
        this.dato = dat;
    }

    public int getAltura() {
        return this.altura;
    }

    public void recalcularAltura() {
        int alturaIzq=0, alturaDer=0, altura;
        if(this.izq != null){
            this.izq.recalcularAltura();
            alturaIzq = this.izq.getAltura();
        } 
        if(this.der != null){
            this.der.recalcularAltura();
            alturaDer = this.der.getAltura();
        } 
        altura = Math.max(alturaIzq, alturaDer)+1;
        this.altura = altura;
    }

    public NodoAVLDicc getIzq() {
        return this.izq;
    }

    public void setIzq(NodoAVLDicc izq) {
        this.izq = izq;
    }

    public NodoAVLDicc getDer() {
        return this.der;
    }

    public void setDer(NodoAVLDicc der) {
        this.der = der;
    }
}
