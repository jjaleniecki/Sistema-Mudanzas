package TPO.TDA;

public class Dni {
    private String tipoDni;
    private int numDni;
    
    public Dni(String tipoDni, int numDni) {
        this.tipoDni = tipoDni;
        this.numDni = numDni;
    }

    public String getTipoDni() {
        return tipoDni;
    }

    public int getNumDni() {
        return numDni;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((tipoDni == null) ? 0 : tipoDni.hashCode());
        result = prime * result + numDni;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Dni other = (Dni) obj;
        if (tipoDni == null) {
            if (other.tipoDni != null)
                return false;
        } else if (!tipoDni.equals(other.tipoDni))
            return false;
        if (numDni != other.numDni)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return  tipoDni + ":" + numDni ;
    }
    
    
}
