package TPO;

//Imports
import java.util.Scanner; //Para ingreso de datos
import java.io.BufferedReader; //Para lectura de archivos txt
import java.io.FileReader;
import java.io.FileWriter; //Para escritura de archivos txt y manejar excepciones
import java.io.FileNotFoundException; //Para manejar excepciones
import java.io.IOException;
import TPO.Diccionario.Diccionario; //Ciudades TDA Diccionario / Tabla de busqueda
import TPO.Grafo.Grafo; //Mapa de rutas con grafo etiquetado
import TPO.Lineales.Lista;
import java.util.HashMap; //Clientes con Hash abierto / HashMap
import java.util.Iterator;

import TPO.TDA.Ciudad; //Importo los TDA creados
import TPO.TDA.Dni;
import TPO.TDA.Pedido;
import TPO.TDA.Cliente;

public class MudanzasCompartidas {
    public static void main(String[] args) {
        /*
         * Main de la clase MudanzasCompartidas, donde se implementara el menu de opciones
         * con las siguientes tareas:
         * 1) Carga inicial del sistema
         * 2) ABM de ciudades
         * 3) ABM de la red de rutas
         * 4) ABM de clientes
         * 5) ABM de pedidos
         * 6) Dada una clave de cliente, mostrar toda la informacion del mismo
         * 7) Dado un codigo postal de una ciudad, mostrar toda su info
         *    Dado un prefijo, devolver todas las ciudades cuyo CP comienza con este
         * 8) Obtener el camino de A a B que pase por menos ciudades
         *    Obtener el camino de A a B que tenga menor distancia en km
         * 9) Dada una ciudad A y una ciudad B, mostrar todos los pedidos y cuando espacio
         *    hace falta en el camion
         *    Dada una lista de ciudades y una cantidad de m^3 (cap camion) verificar si es
         *    un camino perfecto
         *10) Mostrar sistema
         * 
         * Trabajo practico realizado por Jeremias Jaleniecki DNI 44103259 Legajo FAI-3837
         */
        Scanner sc = new Scanner(System.in);
        Scanner st = new Scanner(System.in);
        limpiarLog(); //Metodo utilizado para vaciar el archivo de logs
        //Declaracion de estructuras para almacenar los datos del sistema
        HashMap <String, String> mapaClientes = new HashMap<>();
        HashMap <Integer, Lista> hashInterno = new HashMap<>();
        HashMap <Integer, HashMap> pedidos = new HashMap<>();
        Diccionario ciudades = new Diccionario();
        Grafo rutas = new Grafo();

        System.out.println("Sistema de mudanzas compartidas iniciado.");
        System.out.print("\t * 1) Carga inicial del sistema\r\n" + 
                "         * 2) ABM de ciudades\r\n" + 
                "         * 3) ABM de la red de rutas\r\n" + 
                "         * 4) ABM de clientes\r\n" + 
                "         * 5) ABM de pedidos\r\n" + 
                "         * 6) Dada una clave de cliente, mostrar toda la informacion del mismo\r\n" + 
                "         * 7) Dado un codigo postal de una ciudad, mostrar toda su info\r\n" + 
                "         *    Dado un prefijo, devolver todas las ciudades cuyo CP comienza con este\r\n" + 
                "         * 8) Obtener el camino de A a B que pase por menos ciudades\r\n" + 
                "         *    Obtener el camino de A a B que tenga menor distancia en km\r\n" + 
                "         * 9) Dada una ciudad A y una ciudad B, mostrar todos los pedidos y cuando espacio\r\n" + 
                "         *    hace falta en el camion\r\n" + 
                "         *    Dada una lista de ciudades y una cantidad de m^3 (cap camion) verificar si es\r\n" + 
                "         *    un camino perfecto\r\n" + 
                "         *10) Mostrar sistema" + "\nIngrese una opcion: ");

        String input = st.nextLine(), var;
        int sw = 0, varInt;
        boolean cargado = false; //Flag para saber si ya se realizo la carga inicial

        //verificamos si el ingreso del usuario es un numero, en caso de que lo sea, lo almacenamos en la variable sw,
        //si no lo es, dejamos su valor de 0 y cerramos el programa. verificamos utilizando la expresion regular [0-9]+
        boolean esNumerico = (input != null && input.matches("[0-9]+"));
        if(esNumerico)sw = Integer.parseInt(input);

        while(sw > 0 && sw <= 10){
            switch(sw){
                case 1: if(!cargado){
                            System.out.println("Carga inicial del sistema realizada");
                            cargaInicial(ciudades, mapaClientes, rutas, pedidos, hashInterno);
                            log("ESTADO INICIAL: \nCiudades: \n" + ciudades.toString() + "\nRutas:\n" + rutas.toString()
                            + "\nPedidos:\n" + pedidos.toString() + "\n\nClientes:\n" + mapaClientes.toString() + "\n------------------------------");
                            cargado = true;
                        }else{
                            System.out.println("La carga inicial ya fue realizada");
                        }
                        break;
                case 2: System.out.println("ABM ciudades");
                        ciudadesABM(ciudades, rutas);
                        break;
                case 3: System.out.println("ABM rutas");
                        rutasABM(rutas);
                        break;
                case 4: System.out.println("ABM clientes");
                        clientesABM(mapaClientes);
                        break;
                case 5: System.out.println("ABM pedidos");
                        pedidosABM(pedidos, hashInterno, mapaClientes);
                        break;
                case 6: System.out.print("Mostrar la informacion de un cliente\nTipo documento: ");
                        var = st.nextLine();
                        System.out.print("Numero documento: ");
                        varInt = sc.nextInt();
                        Dni dni = new Dni(var, varInt);
                        if(!mapaClientes.isEmpty()){
                            Cliente cli = buscarCliente(mapaClientes, dni);
                            if(cli != null) System.out.println(cli.toString());
                            if(cli == null) System.out.println("No existe el cliente ingresado");
                        }else{
                            System.out.println("No hay ningun cliente cargado");
                        }
                        break;
                case 7: System.out.print("Desea\n1)Ver la informacion de una ciudad segun su CP" +
                        "\n2)Ver la informacion de todas las ciudades segun el prefijo ingresado" +
                        "\nOpcion elegida: ");
                        var = st.nextLine();
                        if(var.equals("1")){
                            System.out.print("Ingrese el CP: ");
                            varInt = sc.nextInt();
                            if(!ciudades.esVacio()){
                                Ciudad ciudad = buscarCiudad(ciudades, varInt);
                                if(ciudad!=null){
                                    System.out.println(ciudad);
                                }else{
                                    System.out.println("No se encontro la ciudad");
                                }
                            }else{
                                System.out.println("No hay ninguna ciudad cargada");
                            }
                        }else if(var.equals("2")){
                            System.out.print("Ingrese el prefijo: ");
                            varInt = sc.nextInt();
                            if(!ciudades.esVacio()){
                                System.out.println(buscarPrefijo(ciudades, varInt));
                            }else{
                                System.out.println("No hay ninguna ciudad cargada");
                            }
                        }else{
                            System.out.println("Opcion invalida");
                        }
                        break;
                case 8: System.out.print("Desea\n1)Obtener el camino A->B que pase por menos ciudades" +
                        "\n2)Obtener el camino A->B que tenga menor distancia\nOpcion elegida: ");
                        var = st.nextLine();
                        if(var.equals("1")){
                            System.out.print("Ingrese el CP A: ");
                            varInt = sc.nextInt();
                            System.out.print("Ingrese el CP B: ");
                            int temp = sc.nextInt();
                            
                            if(!rutas.esVacia()){
                                System.out.println(caminoMenosCiudades(varInt, temp, rutas));
                            }else{
                                System.out.println("No hay ninguna ruta cargada");
                            }
                        }else if(var.equals("2")){
                            System.out.print("Ingrese el CP A: ");
                            varInt = sc.nextInt();
                            System.out.print("Ingrese el CP B: ");
                            int temp = sc.nextInt();
                            if(!rutas.esVacia()){
                                System.out.println(caminoMenosKm(varInt, temp, rutas).toString());
                            }else{
                                System.out.println("No hay ninguna ruta cargada");
                            }
                        }else{
                            System.out.println("Opcion invalida");
                        }
                        break;
                case 9: System.out.print("Desea\n1)Dada las ciudades A y B, ver todos los pedidos y cuanto espacio " +
                        "hace falta en el camion\n2)Dada una lista de ciudades y una cantidad de m3, verificar si es un " + 
                        "camino perfecto\nOpcion elegida: ");
                        var = st.nextLine();
                        if(var.equals("1")){
                            System.out.print("Ingrese el CP A: ");
                            varInt = sc.nextInt();
                            System.out.print("Ingrese el CP B: ");
                            int temp = sc.nextInt();
                            Lista lTemp = new Lista();
                            if(!pedidos.isEmpty()){
                                lTemp = pedidosEntre(varInt, temp, pedidos);
                            }else{
                                System.out.println("No hay ningun pedido cargado");
                            }
                            if(lTemp != null){
                                if(!lTemp.esVacia()){
                                    System.out.println("Pedidos: " + lTemp);
                                    System.out.println("Espacio necesario en m^3: " + espacio(lTemp));
                                }else if(!pedidos.isEmpty()){
                                    System.out.println("No se encontraron pedidos entre esas 2 ciudades");
                                }
                            }else{
                                System.out.println("No se encontraron pedidos entre esas 2 ciudades");
                            }
                            
                        }else if(var.equals("2")){
                            System.out.print("Ingrese la capacidad del camion: ");
                            float capacidad = sc.nextFloat();
                            Lista camino = ingresarCamino(ciudades);
                            System.out.println("Es camino perfecto: " + caminoPerfecto(camino, rutas, ciudades, pedidos, capacidad));
                        }else{
                            System.out.println("Opcion invalida");
                        }
                        break;
                case 10:if(cargado) {
                            System.out.println("Mostrar sistema");
                            System.out.println("Ciudades: \n" + ciudades.toString() + "\nRutas:\n" + rutas.toString()
                            + "\nPedidos:\n" + pedidos.toString() + "\n\nClientes:\n" + mapaClientes.toString());
                        }else{
                            System.out.println("Todavia no se realizo la carga inicial");
                        }
                        break;
                default:break;
            }
            System.out.print("Ingrese una nueva opcion, o un numero no valido en caso de querer finalizar: ");
            input = st.nextLine();
            esNumerico = (input != null && input.matches("[0-9]+"));
            sw = 0;
            if(esNumerico)sw = Integer.parseInt(input);
        }
        System.out.println("Sistema finalizando. Almacenando estado final en archivo log");
        log("\n------------------------------\nESTADO FINAL: \nCiudades: \n" + ciudades.toString() + "\nRutas:\n" + rutas.toString()
        + "\nPedidos:\n" + pedidos.toString() + "\n\nClientes:\n" + mapaClientes.toString() + "\n------------------------------");
    }

    public static void limpiarLog(){
        //Metodo que limpia el archivo log antes de la ejecucion del programa.
        String nombreArchivoLog = "C:\\Users\\Jere\\Documents\\NetBeansProjects\\EDat\\TPO\\Log.txt";
        try(FileWriter escritorArchivo = new FileWriter(nombreArchivoLog)){
            escritorArchivo.write("");
            escritorArchivo.close();
        }catch (IOException ex) {
            System.err.println("Error leyendo o escribiendo en algun archivo.");
        }
    }

    public static void log(String st){
        //Metodo para guardar los cambios realizados en el sistema
        String nombreArchivoLog = "C:\\Users\\Jere\\Documents\\NetBeansProjects\\EDat\\TPO\\Log.txt";
        try{
            FileWriter escritorArchivo = new FileWriter(nombreArchivoLog, true);
            escritorArchivo.write(st + "\n");
            escritorArchivo.flush();
            escritorArchivo.close();
        } catch (IOException ex) {
            System.err.println("Error leyendo o escribiendo en algun archivo.");
        }
    }

    public static void cargaInicial(Diccionario ciud, HashMap client, Grafo rutas, HashMap pedidos, HashMap interno){
        //Punto n°1
        cargarCiudades(ciud);
        cargarRutas(rutas);
        cargarClientes(client);
        cargarPedidos(pedidos, interno, client);
    }
    
    //Metodos para cargar desde un .txt y para crear arreglo nuevo y ordenarlo
    public static void cargarCiudades(Diccionario ciudades) {
        String nombreArchivoCiudades = "C:\\Users\\Jere\\Documents\\NetBeansProjects\\EDat\\TPO\\Ciudades.txt";
        String linea = null;
        try {
            FileReader lectorArchivo = new FileReader(nombreArchivoCiudades);
            BufferedReader bufferLectura = new BufferedReader(lectorArchivo);
            while ((linea = bufferLectura.readLine()) != null) {
                String[] arr = linea.split(";"); 
                int cp = Integer.parseInt(arr[1]);
                ciudades.insertar(cp, new Ciudad(cp, arr[2], arr[3]));
            }
            bufferLectura.close();
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage() + "\nSignifica que el archivo del que queriamos leer no existe.");
        } catch (IOException ex) {
            System.err.println("Error leyendo o escribiendo en algun archivo.");
        }
    }

    public static void cargarClientes(HashMap clientes) {
        String nombreArchivoCiudades = "C:\\Users\\Jere\\Documents\\NetBeansProjects\\EDat\\TPO\\Clientes.txt";
        String linea = null;
        try {
            FileReader lectorArchivo = new FileReader(nombreArchivoCiudades);
            BufferedReader bufferLectura = new BufferedReader(lectorArchivo);
            while ((linea = bufferLectura.readLine()) != null) {
                String[] arr = linea.split(";"); 
                int numDni = Integer.parseInt(arr[2]);
                Dni dni = new Dni(arr[1], numDni);
                Cliente c = new Cliente(dni, arr[3], arr[4], arr[5], arr[6]);
                clientes.put(dni, c);
                //clientes.put(arr[1] +":"+ arr[2], arr[3] + " " + arr[4] + " " + arr[5] + " " + arr[6]);
            }
            bufferLectura.close();
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage() + "\nSignifica que el archivo del que queriamos leer no existe.");
        } catch (IOException ex) {
            System.err.println("Error leyendo o escribiendo en algun archivo.");
        }
    }

    public static void cargarRutas(Grafo rutas) {
        String nombreArchivoCiudades = "C:\\Users\\Jere\\Documents\\NetBeansProjects\\EDat\\TPO\\Rutas.txt";
        String linea = null;
        try {
            FileReader lectorArchivo = new FileReader(nombreArchivoCiudades);
            BufferedReader bufferLectura = new BufferedReader(lectorArchivo);
            while ((linea = bufferLectura.readLine()) != null) {
                String[] arr = linea.split(";"); 
                int origen = Integer.parseInt(arr[1]);
                int destino = Integer.parseInt(arr[2]);
                float distancia = Float.parseFloat(arr[3]);
                rutas.insertarVertice(origen);
                rutas.insertarVertice(destino);
                rutas.insertarArco(origen, destino, distancia);
            }
            bufferLectura.close();
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage() + "\nSignifica que el archivo del que queriamos leer no existe.");
        } catch (IOException ex) {
            System.err.println("Error leyendo o escribiendo en algun archivo.");
        }
    }

    private static void cargarPedidos(HashMap pedidos, HashMap interno, HashMap clientes) {
        //pedidos<Integer, HashMap>
        //interno<Integer, Lista>
        String nombreArchivoPedidos = "C:\\Users\\Jere\\Documents\\NetBeansProjects\\EDat\\TPO\\Pedidos.txt";
        String linea = null;
        Lista ls = new Lista();
        try {
            FileReader lectorArchivo = new FileReader(nombreArchivoPedidos);
            BufferedReader bufferLectura = new BufferedReader(lectorArchivo);
            while ((linea = bufferLectura.readLine()) != null) {
                String[] arr = linea.split(";"); 
                int origen = Integer.parseInt(arr[1]);
                int destino = Integer.parseInt(arr[2]);
                
                Dni dni = new Dni(arr[4], Integer.parseInt(arr[5]));
                if(clientes.containsKey(dni)){
                    Cliente cliente = (Cliente) clientes.get(dni);
                    Pedido pedido = new Pedido(origen, destino, arr[3], cliente, Float.parseFloat(arr[6]), 
                                Integer.parseInt(arr[7]), arr[8], arr[9], Boolean.parseBoolean(arr[10]));
                    if(pedidos.containsKey(origen)){
                        interno = (HashMap) pedidos.get(origen);
                        if(interno.containsKey(destino)){
                            /* Si ya se encuentra tanto el origen como el destino, y tenemos que insertar un nuevo viaje,
                            * insertamos todos los previos valores asociados al destino del hash interno para asi mantenerlos
                            * y poder tener multiples viajes con un mismo origen y destino
                            */
                            ((Lista) interno.get(destino)).insertar(pedido, 1);
                        }else{
                            Lista lTemp = new Lista();
                            lTemp.insertar(pedido, 1);
                            interno.put(destino, lTemp);
                        }
                    }else{
                        HashMap <Integer, Lista> internoNuevo = new HashMap<>();
                        Lista lTemp = new Lista();
                        lTemp.insertar(pedido, 1);
                        internoNuevo.put(destino, lTemp);
                        pedidos.put(origen, internoNuevo);
                    }
                }
            }
            bufferLectura.close();
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage() + "\nSignifica que el archivo del que queriamos leer no existe.");
        } catch (IOException ex) {
            System.err.println("Error leyendo o escribiendo en algun archivo.");
        }
    }

    public static void ciudadesABM(Diccionario ciudades, Grafo rutas){
        //Metodo para realizar una alta, baja o modificacion en el TDA diccionario ciudades
        //Ejercicio n°2
        Scanner sc = new Scanner(System.in);
        Scanner st = new Scanner(System.in);
        int cp, tmp;
        String nom, prov;
        System.out.println("Que accion desea realizar con ciudades?" + 
        "\n 1) Alta \t 2) Baja \t 3) Modificacion\nOpcion elegida: ");
        int var = sc.nextInt();
        switch(var){
            case 1: System.out.print("Ingrese los datos de la ciudad nueva:"
                    + "\nCodigo postal: ");
                    cp = sc.nextInt();
                    System.out.print("Nombre ciudad: ");
                    nom = st.nextLine();
                    System.out.print("Nombre provincia: ");
                    prov = st.nextLine();
                    if(cp > 1000 && cp<10000){
                        log("Creada la ciudad " + nom + ", " + prov + " cp: " + cp + ": " 
                        + ciudades.insertar(cp, new Ciudad(cp, nom, prov)));
                    }else{
                        System.out.println("El CP ingresado no es valido");
                    }
                    break;
            case 2: System.out.print("Ingrese el CP de la ciudad a borrar: ");
                    cp = sc.nextInt();
                    if(cp > 0 && cp < 99000000){
                        boolean temp = ciudades.eliminar(cp);
                        if(temp){
                            rutas.eliminarVertice(cp);
                        }
                        log("Borrada la ciudad con CP: " + cp + " : " + temp);
                    }else{
                        System.out.println("El CP ingresado no es valido");
                    }
                    break;
            case 3: System.out.print("Ingrese el CP de la ciudad a modificar: ");
                    cp = sc.nextInt();
                    Ciudad temp = ciudades.recuperarDato(cp);
                    nom = temp.getCiudad();
                    prov = temp.getProvincia();
                    System.out.println("Desea modificar: \n1)Ciudad\t2)Provincia");
                    tmp = sc.nextInt();
                    if(tmp == 1){
                        System.out.print("Nombre ciudad nuevo: ");
                        nom = st.nextLine();
                        log("Modificada la ciudad con cp: " + cp + ", ciudad modificada " + nom + ", " + prov );
                    }else if(tmp == 2){
                        System.out.print("Nombre provincia nuevo: ");
                        prov = st.nextLine();
                        log("Modificada la ciudad con cp: " + cp + ", provincia modificada " + nom + ", " + prov );
                    }else{
                        System.out.println("No ingreso un numero valido");
                    }
                    temp.setCiudad(nom);
                    temp.setProvincia(prov);
                    break;
            default: System.out.println("No ingreso un valor valido. Operacion finalizada.");
                    break;
        }
    }

    public static void rutasABM(Grafo rutas){
        //Metodo para realizar una alta, baja o modificacion en el grafo rutas
        //Ejercicio n°3
        Scanner sc = new Scanner(System.in);
        int origen, destino, var, temp;
        float dist;
        System.out.println("Que accion desea realizar con rutas?" + 
        "\n 1) Alta \t 2) Baja \t 3) Modificacion");
        var = sc.nextInt();
        switch(var){
            case 1: System.out.print("Ingrese los datos de la ruta nueva:"
                    + "\nOrigen: ");
                    origen = sc.nextInt();
                    System.out.print("Destino: ");
                    destino = sc.nextInt();
                    System.out.print("Distancia: ");
                    dist = sc.nextFloat();
                    boolean v1 = rutas.insertarVertice(origen); 
                    boolean v2 = rutas.insertarVertice(destino);
                    if(dist > 0 && rutas.insertarArco(origen, destino, dist)){
                        log("Ruta desde " + origen + " a " + destino + " creada exitosamente, con distancia " + dist);
                    }else{
                        log("Ruta desde " + origen + " a " + destino + " no fue creada exitosamente");
                        if(v1) rutas.eliminarVertice(origen);
                        if(v2) rutas.eliminarVertice(destino);
                    }
                    break;
            case 2: System.out.print("Ingrese la ruta a borrar. Origen: ");
                    origen = sc.nextInt();
                    System.out.print("Destino: ");
                    destino = sc.nextInt();
                    if(rutas.existeArco(origen, destino)){
                        rutas.eliminarArco(origen, destino);
                        log("Ruta desde " + origen + " a " + destino + " borrada");
                    }else{
                        System.out.println("No existe un arco entre el origen y destino ingresado.");
                        log("Ruta desde " + origen + " a " + destino + " no borrada");
                    }
                    break;
            case 3: System.out.print("Ingrese la ruta a modificar. Origen: ");
                    origen = sc.nextInt();
                    System.out.print("Destino: ");
                    destino = sc.nextInt();
                    dist = (float) rutas.obtenerEtiqueta(origen, destino);
                    if(rutas.existeArco(origen, destino)){
                        System.out.println("Que desea modificar?\n1) Origen\t2) Destino\t 3) Distancia");
                        var = sc.nextInt();
                        switch(var){
                            case 1: System.out.print("Ingrese el nuevo origen:");
                                    temp = origen;
                                    origen = sc.nextInt(); //el nuevo origen
                                    if(origen > 0 && origen <9999){
                                        rutas.eliminarArco(temp, destino);
                                        log("Ruta desde " + origen + " hasta " + temp + " modificada");
                                        rutas.insertarVertice(origen);
                                        rutas.insertarArco(origen, destino, dist);
                                        log("Ahora es desde " + origen + " a " + destino);
                                    }
                                    break;
                            case 2: System.out.print("Ingrese el nuevo destino:");
                                    temp = destino;
                                    destino = sc.nextInt(); //el nuevo destino
                                    if(destino > 0 && destino <9999){
                                        rutas.eliminarArco(origen, temp);
                                        rutas.eliminarVertice(temp);
                                        log("Ruta desde " + origen + " hasta " + temp + " modificada");
                                        rutas.insertarVertice(destino);
                                        rutas.insertarArco(origen, destino, dist);
                                        log("Ahora es desde " + origen + " a " + destino);
                                    }
                                    break;
                            case 3: System.out.print("Ingrese la nueva distancia:");
                                    dist = sc.nextInt(); //la nueva distancia        
                                    if(dist > 0){
                                        rutas.eliminarArco(origen, destino);
                                        rutas.eliminarVertice(origen);
                                        
                                        rutas.insertarVertice(origen);
                                        rutas.insertarArco(origen, destino, dist);
                                    }
                                    log("La ruta desde " + origen + " a " + destino + " ahora tiene distancia " + dist);
                                    break;
                            default:System.out.println("No ingreso un valor valido");
                                    break;
                        }
                    }
                    break;
            default: System.out.println("No ingreso un valor valido. Operacion finalizada.");
                    break;
        }
    }

    public static void clientesABM(HashMap clientes){
        //Metodo para realizar una alta, baja o modificacion en el HashMap clientes
        //Ejercicio n°4
        Scanner sc = new Scanner(System.in);
        Scanner st = new Scanner(System.in);
        System.out.print("Que accion desea realizar con clientes?" + 
        "\n 1) Alta \t 2) Baja \t 3) Modificacion\nOpcion elegida: ");
        int numDni, sw = sc.nextInt(), swInterno;
        String tipoDni, nombres, apellidos, numTelefono, mail;
        boolean parametrosAceptables;
        switch(sw){
            case 1: System.out.print("Ingrese los datos del cliente nuevo \nTipo dni: ");
                    tipoDni = st.nextLine();        
                    System.out.print("Ingrese el numero de dni: ");
                    numDni = sc.nextInt();
                    System.out.print("Ingrese los nombres: ");
                    nombres = st.nextLine();
                    System.out.print("Ingrese los apellidos: ");
                    apellidos = st.nextLine();
                    System.out.print("Ingrese el telefono: ");
                    numTelefono = st.nextLine();
                    System.out.print("Ingrese el e-mail: ");
                    mail = st.nextLine();
                    parametrosAceptables = (numDni>0 && numDni < 99000000) && (tipoDni.equals("DNI") || 
                    tipoDni.equals("PAS")) && (mail.contains("@") && (mail.endsWith(".com") || 
                    (mail.endsWith(".ar")))) && (String.valueOf(numTelefono).length()==11);
                    if(parametrosAceptables){
                        Dni dni = new Dni(tipoDni, numDni);
                        log("Creado cliente con dni " + dni + " = " + 
                        clientes.put(dni, new Cliente(dni, apellidos, nombres, numTelefono, mail)));
                    }else{
                        System.out.println("Cliente inexistente o parametros erroneos");
                    }
                    break;
            case 2: System.out.print("Ingrese los datos del cliente a dar de baja\nTipo dni: ");
                    tipoDni = st.nextLine();
                    System.out.print("Numero dni: ");
                    numDni = sc.nextInt();
                    if((numDni>0 && numDni < 99000000) && (tipoDni.equals("DNI") || tipoDni.equals("PAS"))){
                        Dni dni = new Dni(tipoDni, numDni);
                        log("Borrado cliente con dni " + dni + " = " + clientes.remove(dni));
                    }else{
                        System.out.println("Cliente inexistente o parametros erroneos");
                    }
                    break;
            case 3: System.out.print("Ingrese los datos del cliente a modificar\nTipo dni: ");
                    tipoDni = st.nextLine();
                    System.out.print("Tipo dni: ");
                    numDni = sc.nextInt();
                    if((numDni>0 && numDni < 99000000) && (tipoDni.equals("DNI") || tipoDni.equals("PAS"))){
                        Dni dni = new Dni(tipoDni, numDni);
                        Cliente cliente = (Cliente) clientes.get(dni);
                        System.out.print("Que dato desea modificar?\n\t1)Nombres\t2)Apellidos\n\t3)Telefono\t4)Email\nOpcion: ");
                        swInterno = sc.nextInt();
                        switch(swInterno){
                            case 1: System.out.print("Ingrese nuevos nombres: ");
                                    nombres = st.nextLine();
                                    cliente.setNombres(nombres);
                                    log("Modificado cliente con dni " + dni + ", nuevos nombres: " + nombres);
                                    break;
                            case 2: System.out.print("Ingrese nuevos apellidos: ");
                                    apellidos = st.nextLine();
                                    cliente.setApellidos(apellidos);
                                    log("Modificado cliente con dni " + dni + ", nuevos apellidos: " + apellidos);
                                    break;
                            case 3: System.out.print("Ingrese nuevo numero de telefono: ");
                                    numTelefono = st.nextLine();
                                    cliente.setTelefono(numTelefono);
                                    log("Modificado cliente con dni " + dni + ", nuevo telefono: " + numTelefono);
                                    break;
                            case 4: System.out.println("Ingrese nuevo mail: ");
                                    mail = st.nextLine();
                                    cliente.setEmail(mail);
                                    log("Modificado cliente con dni " + dni + ", nuevo mail: " + mail);
                                    break;
                            default:System.out.println("Opcion invalida.");
                                    break;
                        }
                    }else{
                        System.out.println("Cliente inexistente");
                    }
                    break;
            default:System.out.println("No ingreso un valor valido. Operacion finalizada.");
                    break;
        }
    }

    public static void pedidosABM(HashMap pedidos, HashMap interno, HashMap clientes){
        //Metodo para realizar una alta, baja o modificacion en el ??? pedidos
        //Ejercicio n°5
        Scanner sc = new Scanner(System.in);
        Scanner st = new Scanner(System.in);
        System.out.print("Que accion desea realizar con pedidos" + 
        "\n 1) Alta \t 2) Baja \t 3) Modificacion\nOpcion elegida: ");
        int sw = sc.nextInt(), origen, destino, swInt;
        String fecha, tipoDni, numDni, calleSalida, calleDestino, cantBultos, volumen, pagado;
        Dni dni;
        Pedido pedido;
        Cliente cliente;
        switch(sw){
            case 1: System.out.print("Ingrese el origen del pedido nuevo: ");
                    origen = sc.nextInt();
                    System.out.print("Ingrese el destino del pedido nuevo: ");
                    destino = sc.nextInt();
                    System.out.print("Ingrese la fecha del pedido (DD/MM/YYYY): ");
                    fecha = st.nextLine();
                    System.out.print("Ingrese el tipo dni del cliente (PAS/DNI): ");
                    tipoDni = st.nextLine();
                    System.out.print("Ingrese el numero de dni del cliente: ");
                    numDni = st.nextLine();
                    System.out.print("Ingrese la cantidad de m^3 del pedido: ");
                    volumen = st.nextLine();
                    System.out.print("Ingrese la cantidad de bultos: ");
                    cantBultos = st.nextLine();
                    System.out.print("Ingrese la calle de salida: ");
                    calleSalida = st.nextLine();
                    System.out.print("Ingrese la calle de destino: ");
                    calleDestino = st.nextLine();
                    System.out.print("Ingrese si esta pagado o no (T/F): ");
                    pagado = st.nextLine();
                    
                    dni = new Dni(tipoDni, Integer.parseInt(numDni));
                    if(clientes.containsKey(dni)){
                        cliente = (Cliente) clientes.get(dni);
                        pedido = new Pedido(origen, destino, fecha, cliente, Float.parseFloat(volumen), 
                                Integer.parseInt(cantBultos), calleSalida, calleDestino, Boolean.parseBoolean(pagado));
                        if(pedidos.containsKey(origen)){
                            interno = (HashMap) pedidos.get(origen);
                            if(interno.containsKey(destino)){
                                /* Si ya se encuentra tanto el origen como el destino, y tenemos que insertar un nuevo viaje,
                                * insertamos todos los previos valores asociados al destino del hash interno para asi mantenerlos
                                * y poder tener multiples viajes con un mismo origen y destino
                                */
                                ((Lista) interno.get(destino)).insertar(pedido, 1);
                            }else{
                                Lista lTemp = new Lista();
                                lTemp.insertar(pedido, 1);
                                interno.put(destino, lTemp);
                            }
                        }else{
                            HashMap <Integer, Lista> internoNuevo = new HashMap<>();
                            Lista lTemp = new Lista();
                            lTemp.insertar(pedido, 1);
                            internoNuevo.put(destino, lTemp);
                            pedidos.put(origen, internoNuevo);
                        }
                    }
                    break;
            case 2: System.out.print("Ingrese el origen del pedido a dar de baja: ");
                    origen = sc.nextInt();
                    System.out.print("Ingrese el destino del pedido a dar de baja: ");
                    destino = sc.nextInt();
                    System.out.print("Ingrese la fecha del pedido (DD/MM/YYYY): ");
                    fecha = st.nextLine();
                    System.out.print("Ingrese el tipo dni del cliente (PAS/DNI): ");
                    tipoDni = st.nextLine();
                    System.out.print("Ingrese el numero de dni del cliente: ");
                    numDni = st.nextLine();
                    System.out.print("Ingrese la cantidad de m^3 del pedido: ");
                    volumen = st.nextLine();
                    System.out.print("Ingrese la cantidad de bultos: ");
                    cantBultos = st.nextLine();
                    System.out.print("Ingrese la calle de salida: ");
                    calleSalida = st.nextLine();
                    System.out.print("Ingrese la calle de destino: ");
                    calleDestino = st.nextLine();
                    System.out.print("Ingrese si esta pagado o no (true/false): ");
                    pagado = st.nextLine();
                    if(pedidos.containsKey(origen)){
                        interno = (HashMap) pedidos.get(origen);
                        if(interno.containsKey(destino)){
                            dni = new Dni(tipoDni, Integer.parseInt(numDni));
                            cliente = (Cliente) clientes.get(dni);
                            pedido = new Pedido(origen, destino, fecha, cliente, Float.parseFloat(volumen), 
                            Integer.parseInt(cantBultos), calleSalida, calleDestino, Boolean.parseBoolean(pagado));
                            if(interno.containsValue(pedido)){
                                interno.remove(destino, pedido);
                            }
                        }
                    }
                    break;
            case 3: System.out.print("Ingrese el origen del pedido a modificar: ");
                    origen = sc.nextInt();
                    System.out.print("Ingrese el destino del pedido a modificar: ");
                    destino = sc.nextInt();
                    System.out.print("Ingrese la fecha del pedido (DD/MM/YYYY): ");
                    fecha = st.nextLine();
                    System.out.print("Ingrese el tipo dni del cliente (PAS/DNI): ");
                    tipoDni = st.nextLine();
                    System.out.print("Ingrese el numero de dni del cliente: ");
                    numDni = st.nextLine();
                    System.out.print("Ingrese la cantidad de m^3 del pedido: ");
                    volumen = st.nextLine();
                    System.out.print("Ingrese la cantidad de bultos: ");
                    cantBultos = st.nextLine();
                    System.out.print("Ingrese la calle de salida: ");
                    calleSalida = st.nextLine();
                    System.out.print("Ingrese la calle de destino: ");
                    calleDestino = st.nextLine();
                    System.out.print("Ingrese si esta pagado o no (T/F): ");
                    pagado = st.nextLine();
                    if(pedidos.containsKey(origen)){
                        interno = (HashMap) pedidos.get(origen);
                        if(interno.containsKey(destino)){
                            dni = new Dni(tipoDni, Integer.parseInt(numDni));
                            cliente = (Cliente) clientes.get(dni);
                            //TODO VER
                            pedido = new Pedido(origen, destino, fecha, cliente, Float.parseFloat(volumen), 
                            Integer.parseInt(cantBultos), calleSalida, calleDestino, Boolean.parseBoolean(pagado));
                            System.out.print("Que dato desea modificar?\n\t1)Fecha\t2)Volumen\t3)Cant. de bultos"+
                            "\n\t4)Calle de salida\t5)Calle de destino\t6)Pagado\nOpcion:");
                            swInt = sc.nextInt();
                            switch(swInt){
                                case 1: System.out.print("Ingrese la fecha nueva: ");
                                        fecha = st.nextLine();
                                        pedido.setFecha(fecha);
                                        break;
                                case 2: System.out.print("Ingrese el volumen nuevo: ");
                                        volumen = st.nextLine();
                                        pedido.setVolumen(Float.parseFloat(volumen));
                                        break;
                                case 3: System.out.print("Ingrese la nueva cant. de bultos: ");
                                        cantBultos = st.nextLine();
                                        pedido.setBultos(Integer.parseInt(cantBultos));
                                        break;
                                case 4: System.out.print("Ingrese la nueva calle de salida: ");
                                        calleSalida = st.nextLine();
                                        pedido.setCalleSalida(calleSalida);
                                        break;
                                case 5: System.out.print("Ingrese la nueva calle de destino:");
                                        calleDestino = st.nextLine();
                                        pedido.setCalleDestino(calleDestino);
                                        break;
                                case 6: System.out.print("Ingrese si esta pagado o no el pedido: ");
                                        pagado = st.nextLine();
                                        pedido.setPagado(Boolean.parseBoolean(pagado));
                                        break;
                                default:System.out.println("No ingreso un numero valido");
                                        break;
                            }
                            if(interno.containsValue(pedido)){
                                interno.remove(destino, pedido);
                                System.out.print("Ingrese la nueva fecha del pedido (DD/MM/YYYY): ");
                                fecha = st.nextLine();
                                System.out.print("Ingrese la cantidad de m^3 del pedido: ");
                                volumen = st.nextLine();
                                System.out.print("Ingrese la cantidad de bultos: ");
                                cantBultos = st.nextLine();
                                System.out.print("Ingrese la calle de salida: ");
                                calleSalida = st.nextLine();
                                System.out.print("Ingrese la calle de destino: ");
                                calleDestino = st.nextLine();
                                System.out.print("Ingrese si esta pagado o no (T/F): ");
                                pagado = st.nextLine();
                                
                            }
                        }
                    }
                    break;
            default:System.out.println("No ingreso un valor valido. Operacion finalizada.");
                    break;
        }
    }

    public static Cliente buscarCliente(HashMap clientes, Dni clave){
        //Ejercicio n°6
        Cliente datosCliente = null;
        if(clientes.containsKey(clave)){
            datosCliente = (Cliente) clientes.get(clave);
        }
        return datosCliente;
    }

    public static Ciudad buscarCiudad(Diccionario ciudades, int cp){
        //Ejercicio n°7 a
        //Modulo que devuelve la ciudad que coincida con el cp recibido por parametro
        Ciudad dato = (Ciudad) ciudades.recuperarDato(cp);
        return dato;
    }

    public static Lista buscarPrefijo(Diccionario ciudad, int prefijo){
        //Ejercicio n°7 b
        //Modulo que devuelve una lista de ciudades que tengan como prefijo el recibido por parametro
        Lista listaCiudades = new Lista();
        ciudad.datosSegunPrefijo(prefijo, listaCiudades);
        if(listaCiudades.esVacia()) listaCiudades.insertar("Ninguna ciudad tiene ese prefijo", 1);
        return listaCiudades;
    }

    public static Lista caminoMenosCiudades(int cp1, int cp2, Grafo rutas){
        //Ejercicio n°8 a
        //Modulo que devuelve el camino entre cp1 y cp2 que tiene menor cantidad de ciudades entre ellas
        Lista camino = new Lista();
        camino = rutas.caminoMasCortoNodos(cp1, cp2);
        if(camino.esVacia()) camino.insertar("No hay camino valido", 1);
        return camino;
    }

    public static Lista caminoMenosKm(int cp1, int cp2, Grafo rutas){
        //Ejercicio n°8 b
        //Modulo que devuelve el camino entre cp1 y cp2 que tiene menor cantidad de kilometros entre ellas
        Lista camino = new Lista();
        camino = rutas.caminoMasCortoEtiq(cp1, cp2);
        if(camino.esVacia()) camino.insertar("No hay camino valido", 1);
        return camino;
    }

    public static Lista pedidosEntre(int cp1, int cp2, HashMap pedidos){
        //Ejercicio 9° a
        //Modulo que retorna una lista con todos los pedidos entre la ciudad cp1 y cp2
        Lista listaPedidos = new Lista();
        if(pedidos.containsKey(cp1)){
            HashMap temp = (HashMap)pedidos.get(cp1);
            listaPedidos = (Lista) temp.get(cp2);
        }
        return listaPedidos;
    }

    public static float espacio(Lista ls){
        //Ejercicio 9° a para calcular cuanto espacio necesita una lista de pedidos
        float espacio = 0;
        Lista clon = ls.clone();
        while(!clon.esVacia()){
            Pedido ped = (Pedido) clon.recuperar(1);
            espacio += ped.getVolumen();
            clon.eliminar(1);
        }
        return espacio;
    }

    public static boolean caminoPerfecto(Lista camino, Grafo rutas, Diccionario ciudades, HashMap pedidos, float capacidad) {
        //Modulo que verifica si un camino es perfecto
        boolean perfecto = true;
        
        if(!camino.esVacia()) {
            //System.out.println(camino.toString() + " es camino: " + rutas.esCamino(camino));
            if(rutas.esCamino(camino)) {
                if(capacidad > 0) {
                    //Si el camino ingresado se encuentra dentro del grafo, y la capacidad del camion es un valor valido
                    //perfecto = esCaminoPerfecto(camino, capacidad, (float)0., pedidos, rutas, new Lista());
                    perfecto = caminoPerfecto(camino, new Lista(), pedidos, capacidad, 0., ciudades);
                }
            } else {
                //El camino ingresado no es valido en el grafo
                perfecto = false;
            }
        }
        return perfecto;
    }

    public static Lista ingresarCamino(Diccionario ciudades){
        Scanner sc = new Scanner(System.in);
        Lista ls = new Lista();
        
        System.out.print("Ingrese el CP de la ciudad origen: ");
        int cp = sc.nextInt();

        while(cp != 0 || ciudades.existeClave(cp)){
            ls.insertar(cp, ls.longitud()+1);
            System.out.print("Ingrese el proximo destino, o 0 si desea finalizar: ");
            cp = sc.nextInt();
        }
        return ls;
    }

    public static boolean caminoPerfecto(Lista camino, Lista visitados, HashMap pedidos, 
            double capacidadMax, double capacidadAct, Diccionario ciudades) {
        /*
         * Modulo que verifica si un camino es perfecto. Se debe cumplir:
         * 1) Por cada elemento de camino, hay un pedido que comienza en esa ciudad y que termina en alguna siguiente
         * 2) Puede haber varios pedidos que entren a una misma ciudad, pero no 2 que salgan de la misma
         * 3) La suma de los volumenes de los pedidos que estan siendo transportados en un mismo momento no 
         *    puede ser mayor a capacidadMax.
         */
        boolean esPerfecto = false;
        int pos, posPedido;
        Object clave;

        Ciudad ciudad = (Ciudad)ciudades.recuperarDato((Comparable)camino.recuperar(1)); //guardo el origen
        Pedido pedido;
        HashMap hashAux;
        Lista listaPedidos;
        Iterator iterador;
        
        camino.eliminar(1);
        
        if(!camino.esVacia()) {
            //buscamos el hashMap que tiene guardado todas las solis que salen de ciu
            hashAux = (HashMap)pedidos.get(ciudad.getCP());
            
            if(hashAux != null) {
                //Ubicamos a pos en el final de la lista; si visitados tiene algun elemento, entramos al while
                pos = visitados.longitud();
                while(pos > 0) {
                    //eliminamos todas las solicitudes de visitados que terminan 
                    //en la ciudad actual, para reducir espacio
                    pedido = (Pedido)visitados.recuperar(pos);
                    if(pedido.getDestino()==ciudad.getCP()) {
                        visitados.eliminar(pos);
                        capacidadAct -= pedido.getVolumen();
                    }
                    pos--;
                }
                iterador = hashAux.keySet().iterator();
                while(!esPerfecto && iterador.hasNext()) {
                    //Recorremos el hashAux con las solicitudes
                    clave = iterador.next();
                    listaPedidos = (Lista)hashAux.get(clave);
                    posPedido = 1;
                    pedido = (Pedido)listaPedidos.recuperar(1);
                    while(!esPerfecto && pedido != null) {
                        if(camino.localizar(pedido.getDestino()) > 0 && 
                                capacidadAct+pedido.getVolumen() <= capacidadMax) {
                            //Si se cumple el if, significa que la ciudad esta en el camino, y que 
                            //queda suficiente volumen disponible para hacer ese pedido
                            visitados.insertar(pedido, 1);
                            //llamada recursiva, actualizando el volumen de capacidadAct
                            esPerfecto = caminoPerfecto(camino, visitados, pedidos, 
                                    capacidadMax, capacidadAct+pedido.getVolumen(), ciudades);
                            
                            if(!esPerfecto) {
                                //si volvemos de la recursion y esPerfecto es false, significa
                                //que mas profundo, no se encontro un camino perfecto para esta solicitud
                                visitados.eliminar(1);
                            }
                        }
                        pedido = (Pedido)listaPedidos.recuperar(++posPedido);
                    }
                }
                if(!esPerfecto) {
                    //no se encontro en ninguna solicitud un camino perfecto, agregamos de vuelta la ciudad al camino
                    camino.insertar(ciudad, 1);
                }
            }
        }
        return esPerfecto;
    }
}