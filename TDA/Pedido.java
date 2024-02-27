package TPO.TDA;

public class Pedido {
    private int origen;
    private int destino;
    private String fecha;
    private Cliente cliente;
    private float volumen;
    private int bultos;
    private String calleSalida;
    private String calleDestino;
    private boolean pagado;

    public Pedido(int origen, int destino, String fecha, Cliente cliente, float volumen, int bultos,
            String calleSalida, String calleDestino, boolean pagado) {
        this.origen = origen;
        this.destino = destino;
        this.fecha = fecha;
        this.cliente = cliente;
        this.volumen = volumen;
        this.bultos = bultos;
        this.calleSalida = calleSalida;
        this.calleDestino = calleDestino;
        this.pagado = pagado;
    }

    public int getOrigen() {
        return origen;
    }

    public int getDestino() {
        return destino;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public float getVolumen() {
        return volumen;
    }

    public void setVolumen(float volumen) {
        this.volumen = volumen;
    }

    public int getBultos() {
        return bultos;
    }

    public void setBultos(int bultos) {
        this.bultos = bultos;
    }

    public String getCalleSalida() {
        return calleSalida;
    }

    public void setCalleSalida(String calleSalida) {
        this.calleSalida = calleSalida;
    }

    public String getCalleDestino() {
        return calleDestino;
    }

    public void setCalleDestino(String calleDestino) {
        this.calleDestino = calleDestino;
    }

    public boolean isPagado() {
        return pagado;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }

    @Override
    public String toString() {
        return "Pedido [O=" + origen + ", D=" + destino + ", F=" + fecha + ", C=" + cliente
                + ", V=" + volumen + ", B=" + bultos + ", CS=" + calleSalida + ", CD="
                + calleDestino + ", P=" + pagado + "]";
    }
    
    
}
