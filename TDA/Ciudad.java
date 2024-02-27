package TPO.TDA;

public class Ciudad {
    private int CP;
    private String ciudad;
    private String provincia;

    public Ciudad(int CP, String ciudad, String provincia) {
        this.CP = CP;
        this.ciudad = ciudad;
        this.provincia = provincia;
    }

    public int getCP() {
        return CP;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    @Override
    public String toString() {
        return "[CP:" + CP + ", " + ciudad + ", " + provincia + "]";
    }
    
}
