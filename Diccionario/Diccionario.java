package TPO.Diccionario;

import TPO.TDA.Ciudad;
import TPO.Lineales.Lista;

public class Diccionario {
    private NodoAVLDicc raiz;
    
    public Diccionario(){
        this.raiz = null;
    }
    
    public boolean insertar(Comparable clave, Object dato){
        boolean exito[] = {true};
        
        if(this.raiz == null){
            this.raiz = new NodoAVLDicc(clave, dato,null, null);
        }else{
            this.raiz = insertarAux(this.raiz, clave, exito, dato);
        }
        return exito[0];
    }

    private NodoAVLDicc insertarAux(NodoAVLDicc n, Comparable clave, boolean[] ex, Object dat){
        NodoAVLDicc ret;
        if(n==null){
            ret = new NodoAVLDicc(clave, dat, null, null);
        }else{
            if((clave.compareTo(n.getClave()) == 0)){
                //elemento repetido, error
                ret = n;
                ex[0] = false;
            }else if (clave.compareTo(n.getClave()) < 0){
                // clave es menor que n.getClave(), si tiene HI baja, sino agrega
                n.setIzq(insertarAux(n.getIzq(), clave, ex, dat));
            }else{
                //clave es mayor que n.getClave(), si tiene HD baja, sino agrega
                n.setDer(insertarAux(n.getDer(), clave, ex, dat));
            }
            n.recalcularAltura();
            ret = aplicarRotaciones(n);
            ret.recalcularAltura();
        }
        return ret;
    }
    
    private NodoAVLDicc aplicarRotaciones(NodoAVLDicc n){
        int balance = balance(n);
        if(balance > 1){
            //System.out.print("Rotacion ");
            if(balance(n.getIzq()) < 0){
                n.setIzq(rotarIzq(n.getIzq()));
                //System.out.print("izquierda-");
            }
            //System.out.println("derecha");
            return rotarDer(n);
        }else if(balance < -1){
            //System.out.print("Rotacion ");
            if(balance(n.getDer()) > 0){
                n.setDer(rotarDer(n.getDer()));
                //System.out.print("derecha-");
            }
            //System.out.println("izquierda");
            return rotarIzq(n);
        }
        
        return n;
    }
    
    private int balance(NodoAVLDicc n){
        int balance = 0, alturaIzq, alturaDer;
        if(n != null){
            alturaIzq = calcAltura(n.getIzq());
            alturaDer = calcAltura(n.getDer());
            balance = alturaIzq - alturaDer;
        }
        return balance;
    }

    private int calcAltura(NodoAVLDicc n){
        int alt = 0;
        if(n != null) alt = n.getAltura();
        return alt;
    }

    public boolean eliminar(Comparable el){
        boolean exito = false;
        if(existeClaveAux(this.raiz, el)){
            this.raiz = eliminarAux(this.raiz, el);
            exito = true;
        }
        return exito;
    }

    private NodoAVLDicc eliminarAux(NodoAVLDicc n, Comparable el){
        NodoAVLDicc ret = null;
        if (n != null){
            int cmp = el.compareTo(n.getClave());
            if(cmp < 0){
                //el es menor que getElem, vamos a la izq
                n.setIzq(eliminarAux(n.getIzq(), el));
                ret = n;
            }else if(cmp > 0){
                //el es mayor que getElem, vamos a la der
                n.setDer(eliminarAux(n.getDer(), el));
                ret = n;
            }else{
                //el es igual a getElem, borramos
                if(n.getIzq() == null && n.getDer() == null){
                    //si el no tiene HI ni HD borramos
                    n = null;
                }else if (n.getIzq() == null){
                    // si el no tiene HI
                    ret = n.getDer();
                    n = null;
                }else if (n.getDer() == null){
                    // si el no tiene HD
                    ret = n.getIzq();
                    n = null;
                }else{
                    // si el tiene HI y HD
                    Comparable tmp = masIzq(n.getDer());
                    //TODO clave y no dato
                    n.setClave(tmp);
                    n.setDer(eliminarAux(n.getDer(), tmp));
                    ret = n;
                }
            }
            if(n != null){
                n.recalcularAltura();
                ret = aplicarRotaciones(n);
                ret.recalcularAltura();
            }
        }
        return ret;
    }

    private Comparable masIzq(NodoAVLDicc n){
        //
        Comparable el = null;
        if(n != null){
            while(n.getIzq() != null){
                n = n.getIzq();
            }
            el = n.getClave();
        }
        return el;
    }

    public boolean existeClave(Comparable el){
        boolean pert = false;
        if(this.raiz != null){
            pert = existeClaveAux(this.raiz, el);
        }
        return pert;
    }

    private boolean existeClaveAux(NodoAVLDicc n, Comparable el){
        boolean pert = false;
        if(n.getClave() != null){
            if(el.compareTo(n.getClave()) == 0){
                //el es igual a n.getElem()
                pert = true;
            }else if(el.compareTo(n.getClave()) > 0){
                //el es mayor que getElem, bajo por derecha
                if(n.getDer() != null){
                    pert = existeClaveAux(n.getDer(), el);
                }else{
                    pert = false;
                }
            }else{
                //el es menor que getElem, bajo por izquierda
                if (n.getIzq() != null) {
                    pert = existeClaveAux(n.getIzq(), el);
                }else{
                    pert = false;
                }
            }
        }
        return pert;
    }

    public boolean esVacio(){
        return this.raiz == null;
    }

    public Lista listarClaves(){
        Lista l = new Lista();
        if(!esVacio()){
            listarClavesAux(this.raiz, 1, l);
        }
        return l;
    }
    
    private Lista listarClavesAux(NodoAVLDicc n, int pos, Lista l){
        if(n != null){
            l.insertar(n.getClave(), l.longitud()+1);
            listarClavesAux(n.getIzq(), pos+1, l);
            listarClavesAux(n.getDer(), pos+1, l);
        }
        return l;
    }

    public Lista listarDatos(){
        Lista l = new Lista();
        if(!esVacio()){
            listarDatosAux(this.raiz, 1, l);
        }
        return l;
    }

    public Ciudad recuperarDato(Comparable clave){
        Ciudad dato = null;
        if(existeClaveAux(this.raiz, clave)){
            dato = recuperarDatoAux(this.raiz, clave);
        }
        return dato;
    }

    private Ciudad recuperarDatoAux(NodoAVLDicc n, Comparable clave){
        Ciudad data = null;
        if(n != null){
            int cmp = n.getClave().compareTo(clave);
            if(cmp < 0){
                data = recuperarDatoAux(n.getDer(), clave);
            }else if(cmp > 0){
                data = recuperarDatoAux(n.getIzq(), clave);
            }else{
                data = (Ciudad) n.getDato();
            }
        }
        return data;
    }

    public Lista datosSegunPrefijo(int prefijoIni, Lista ls){
        String prefijoStr = String.valueOf(prefijoIni);
        int prefijoFin = prefijoIni;
        switch(prefijoStr.length()){
            case 1: prefijoFin = prefijoIni*1000 + 999; 
                    prefijoIni *= 1000;
                    break;
            case 2: prefijoFin = prefijoIni*100 + 99;
                    prefijoIni *= 100;
                    break;
            case 3: prefijoFin = prefijoIni*10 + 9;
                    prefijoIni *= 10;
                    break;
            default:;break;
        }
        datosSegunPrefijoAux(this.raiz, prefijoIni, prefijoFin, ls);
        return ls;
    }

    private void datosSegunPrefijoAux(NodoAVLDicc n, int prefijoInicial, int prefijoFinal, Lista empiezan){
        if(n != null){
            int cmp1 = n.getClave().compareTo(prefijoInicial);
            int cmp2 = n.getClave().compareTo(prefijoFinal);
            if(cmp1 >= 0){
                datosSegunPrefijoAux(n.getIzq(), prefijoInicial, prefijoFinal, empiezan);
                if(cmp2 < 0){
                    datosSegunPrefijoAux(n.getDer(), prefijoInicial, prefijoFinal, empiezan);
                }
            }else if(cmp1 < 0){
                datosSegunPrefijoAux(n.getDer(), prefijoInicial, prefijoFinal, empiezan);
            }
            if(cmp2 <= 0 && cmp1 >= 0){
                empiezan.insertar(n.getDato(), empiezan.longitud()+1);
            }
        }
    }

    private void listarDatosAux(NodoAVLDicc n,int pos, Lista l){
        if(n != null){
            l.insertar(n.getDato(), l.longitud()+1);
            listarDatosAux(n.getIzq(), pos+1, l);
            listarDatosAux(n.getDer(), pos+1, l);
        }
    }

    public String toString(){
        return toStringAux(this.raiz);
    }

    private String toStringAux(NodoAVLDicc n){
        String out="";
        
        if(n != null){
            if(n.getClave()==this.raiz.getClave()){
                out += "Raiz:" + n.getClave();
            }else{
                out += "Nodo:" + n.getClave();
            }
            
            if(n.getIzq() != null){
                out += "    HI:" + n.getIzq().getClave();
            }else{
                out += "    HI:-";
            }
            if(n.getDer() != null){
                out += "    HD:" + n.getDer().getClave();
            }else{
                out += "    HD:-";
            }
            out += "\n";
            out += toStringAux(n.getIzq());
            out += toStringAux(n.getDer());
        }
        return out;
    }
    
    private NodoAVLDicc rotarIzq(NodoAVLDicc n){
        NodoAVLDicc nuevaRaiz = n.getDer();
        NodoAVLDicc temp = nuevaRaiz.getIzq();
        nuevaRaiz.setIzq(n);
        n.setDer(temp);
        nuevaRaiz.recalcularAltura();
        n.recalcularAltura();
        return nuevaRaiz;
    }
    
    private NodoAVLDicc rotarDer(NodoAVLDicc n){
        NodoAVLDicc nuevaRaiz = n.getIzq();
        NodoAVLDicc temp = nuevaRaiz.getDer();
        nuevaRaiz.setDer(n);
        n.setIzq(temp);
        nuevaRaiz.recalcularAltura();
        n.recalcularAltura();
        return nuevaRaiz;
    }
    
}
