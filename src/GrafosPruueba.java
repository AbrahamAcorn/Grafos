import java.util.Scanner;

class Nodo {
	//Atributos
	Object elemento;
	Nodo siguiente;
	int dato;
	
	//Constructores
	public Nodo(Object x){
		elemento = x;
		siguiente = null;
	}
	public Nodo(int x){
		dato = x;
	    siguiente = null;
	}
	public Nodo(int x, Nodo n){
	    dato = x;
	    siguiente = n;
	}
	
	//Getters and Setters
	public int getDato(){
	    return dato;
	}
	
	public Nodo getEnlace(){
	    return siguiente;
	}
	public void setEnlace(Nodo enlace){
	    this.siguiente = enlace;
	}
}
class Arco{
	//Atributos
	int destino;
    double peso;
    
    //Constructores
    public Arco(int d){
    	destino = d;
    }
    public Arco(int d, double p){
    	this(d);
    	peso = p;
    }
    
    //Metodos
    public int getDestino(){
        return destino;
    }
   
    public boolean equals(Object n){
    	Arco a = (Arco)n;
    	return destino == a.destino;
    }
}
class ColaLista {
	//Atributos
	protected Nodo frente;
	protected Nodo fin;
	
	//Constructor
	// crea cola vacia
	public ColaLista(){
		frente = fin = null;
	}
	
	//Metodos
	// insertar: pone elemento por el final
    public void insertar(Object elemento){
    	Nodo a;
        a = new Nodo(elemento);
        if (colaVacia()){
        	frente = a;
        }
        else{
        	fin.siguiente = a;
        }
        fin = a;
    }
    
    // quitar: sale el elemento frente
    public Object quitar() throws Exception{
    	Object aux;
    	if (!colaVacia()){
    		aux = frente.elemento;
    		frente = frente.siguiente;
    	}
    	else
    		throw new Exception("Eliminar de una cola vacia");
    	return aux;
    }
    
    // libera todos los nodos de la cola
    public void borrarCola(){
    	for (; frente != null;){
    		frente = frente.siguiente;
        }
    	System.gc();
    }
    
    // acceso al primero de la cola
    public Object frenteCola() throws Exception{
    	if (colaVacia()){
    		throw new Exception("Error: cola vacia");
        }
    	return (frente.elemento);
    }
 
    // verificacion del estado de la cola
    public boolean colaVacia(){
    	return (frente == null);
    }
}
class GrafoAdcia {
	//Atributos
	int numVerts;
    static int maxVerts = 20;
    Vertice [] tablAdc;
    
    //Constructor
    public GrafoAdcia(int mx){
    	tablAdc = new Vertice[mx];
        numVerts = 0;
        maxVerts = mx;
    }
    
    //Metodos
    public int numVertice(String vs) {
        Vertice v = new Vertice(vs);
        boolean encontrado = false;
        int i = 0;
        for (; (i < numVerts) && !encontrado;){
        	encontrado = tablAdc[i].equals(v);
       	 	if (!encontrado) 
       	 		i++ ;
       	}
        return (i < numVerts) ? i : -1 ;
   }
}
class GrafoMatriz{
	//Atributos
	int numVerts;
	static int maxVerts = 20;
	Vertice [] verts;
	int [][] matAd;

	//Constructores
	public GrafoMatriz(){
		this(maxVerts);
	}
	public GrafoMatriz(int mx){
		maxVerts=mx;
		matAd = new int [mx][mx];
		verts = new Vertice[mx];
		for (int i = 0; i < mx; i++)
		for (int j = 0; i < mx; i++)
		matAd[i][j] = 0;
		numVerts = 0;
	}
	
	//Metodos
	public void nuevoVertice (String nom){
		boolean esta = numVertice(nom) >= 0;
		if (!esta){
			Vertice v = new Vertice(nom);
			v.asigVert(numVerts);
			verts[numVerts++] = v;
		}
	}
	
	public int numVertice(String vs){
		Vertice v = new Vertice(vs);
		boolean encontrado = false;
		int i = 0;
		for (; (i < numVerts) && !encontrado;){
			encontrado = verts[i].equals(v);
				if (!encontrado)
					i++;
		}
		return (i < numVerts) ? i : -1 ;
	}
	
	public void nuevoArco(String a, String b)throws Exception{
		int va, vb;
		va = numVertice(a);
		vb = numVertice(b);
		if (va < 0 || vb < 0) throw new Exception ("Vértice no existe");
		matAd[va][vb] = 1;
	}
	
	public void nuevoArco(int va, int vb)throws Exception{
		if (va < 0 || vb < 0) throw new Exception ("Vértice no existe");
		matAd[va][vb] = 1;
	}
	
	public boolean adyacente(String a, String b)throws Exception{
		int va, vb;
		va = numVertice(a);
		vb = numVertice(b);
		if (va < 0 || vb < 0) throw new Exception ("Vértice no existe");
		return matAd[va][vb] == 1;
	}
	
	public boolean adyacente(int va, int vb)throws Exception{
		if (va < 0 || vb < 0) throw new Exception ("Vértice no existe");
		return matAd[va][vb] == 1;
	}
	
	public static int[]recorrerAnchura(GrafoMatriz g, String org) throws Exception{
    	int w, v;
        int [] m;
        
        v = g.numVertice(org);
        int CLAVE =-1;
        if (v < 0) throw new Exception("Vertice origen no existe");
        
        ColaLista cola = new ColaLista();
        m = new int[g.numVerts];
        // inicializa los vertices como no marcados
        for (int i = 0; i < g.numVerts; i++)
        	m[i] = CLAVE;
        m[v] = 0; // vertice origen queda marcado
        cola.insertar(new Integer(v));
        while (! cola.colaVacia()){
        	Integer cw;
        	cw = (Integer) cola.quitar();
        	w = cw.intValue();
        	System.out.println("Vertice " + g.verts[w] + " Visitado");
        	// inserta en la cola los adyacentes de w no marcados
        	for (int u = 0; u < g.numVerts; u++){
        		if ((g.matAd[w][u] == 1) && (m[u] == CLAVE)){
		        	// se marca vertice u con numero de arcos hasta el
		        	m[u] = m[w] + 1;
		        	cola.insertar(new Integer(u));
        		}
        	}
        }
        return m;
	}
	
	/*
	static public int[] recorrerProf(GrafoAdcia g, String org) throws Exception {
		int CLAVE =-1;
		int v, w;
	    PilaLista pila = new PilaLista();
	    int [] m;
	    m = new int[g.numVerts];
	    // inicializa los vertices como no marcados
	    v = g.numVertice(org);
	    if (v < 0) throw new Exception("Vertice origen no existe");
	    for (int i = 0; i < g.numVerts; i++)
	    	m[i] = CLAVE;
	    m[v] = 0; // vertice origen queda marcado
	    
	    pila.insertar(new Integer(v));
	    while (!pila.pilaVacia()){
	    	Integer cw;
	    	cw = (Integer) pila.quitar();
	    	w = cw.intValue();
	    	System.out.println("Vertice" + g.tablAdc[w] + "visitado");
	    	// inserta en la pila los adyacentes de w no marcados
	    	// recorre la lista con un iterador
	    	ListaIterador list = new ListaIterador(g.tablAdc[w].lad);
	    	Arco ck;
	    	do{
	    		int k;
	    		ck = (Arco) list.siguiente();
	    		if (ck != null){
	    			k = ck.getDestino(); // vertice adyacente
	    			if (m[k] == CLAVE){
	    				pila.insertar(new Integer(k));
	    				m[k] = 1; // vertice queda marcado
	    			}
	    	    }
	    	} while (ck != null);
	    }
	    return m;
	}
	*/
	
	public boolean verificarinIcializacion(){
		if(verts.length>0)
			return true;
		else
			return false;
	}
	
	public boolean verificarGrafoLleno(){
		if(numVerts<=maxVerts-1)
			return false;
		else
			return true;
	}	
}
class Lista {
	/*
	//Atributos
	private Nodo primero;
	
	//Constructor
	public Lista(){
		primero = null;
	}
	
	//Metodos
	public Lista crearLista() {
		int x;
	    primero = null;
	    do {
	    	x = leerEntero();
	    	if (x != -1){
	    		primero = new Nodo(x,primero);
	        }
	    }
	    while (x != -1);
	    return this;
    }
    */
}
class ListaIterador {
	/*
	//Atributos
	private Nodo prm, actual;
	
	//Constructor
	public ListaIterador(Lista list){
		prm = actual = list.leerPrimero();
    }
	
	//Metodos
	public Object siguiente(){
		Object elemento = null;
		if (actual != null){
			elemento = actual.leerDato();
			actual = actual.siguiente();
		}
		return elemento;
	}
	
	public void inicIter(){
		actual = prm;
	}
	*/
}
class NodoPila {
	//Atributos
	Object elemento;
    NodoPila siguiente;
    
    //Constructor
    NodoPila(Object x){
    	elemento = x;
        siguiente = null;
    }
}
class PilaLista {
	//Atributos
	private NodoPila cima;
	
	//Constructor
	public PilaLista(){
		cima = null;
    }
	
	//Metodos
	public boolean pilaVacia(){
		return cima == null;
	}
	
	public void insertar(Object elemento){
		NodoPila nuevo;
	    nuevo = new NodoPila(elemento);
	    nuevo.siguiente = cima;
	    cima = nuevo;
	}
	
	public Object quitar() throws Exception{
		if (pilaVacia())
			throw new Exception ("Pila vacia, no se puede extraer.");
		Object aux = cima.elemento;
		cima = cima.siguiente;
		return aux;
	}
	
	public Object cimaPila() throws Exception{
		if (pilaVacia())
			throw new Exception ("Pila vacia, no se puede leer cima.");
		return cima.elemento;
	}
	public void limpiarPila(){
		NodoPila t;
		while(!pilaVacia()){
			t = cima;
			cima = cima.siguiente;
			t.siguiente = null;
		}
	}
}
class Vertice{
	//Atributos
	private String nombre;
	private int numVertice;
	
	//constructores
	public Vertice() {
	}
	public Vertice(String x){
		nombre = x;
		numVertice = -1;
}
	
	//Getters and Setters
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public int getNumVertice() {
		return numVertice;
	}
	public void setNumVertice(int numVertice) {
		this.numVertice = numVertice;
	}
	
	//Metodos
	public String nomVertice(){ // devuelve identificador del vértice
		return nombre;
	}
	public boolean equals(Vertice n){ // true, si dos vértices son iguales
		return nombre.equals(n.nombre);
	}
	public void asigVert(int n){ // establece el número de vértices
		numVertice = n;
	}
	public String toString(){ // características del vértice
		return nombre + " (" + numVertice + ")";
	}
}

/*
 * Luis Joyanes Aguilar, Ignacio Zahonero Martinez.(2008).
 * Grafos, representación y operaciones.
 * En Estructura de datos en Java(431 - 456).
 * España: Mc Graw Hill.
 */

public class GrafosPruueba {

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner entrada = new Scanner (System.in);
		
		System.out.println("Ingrese la cantidad maxima de vertices a ingresar...");
		int maximoVertices=entrada.nextInt();
		System.out.println();
		System.out.println();
		
		GrafoMatriz grafo = new GrafoMatriz(maximoVertices);
	    int opcion=0;
	    boolean repetir=true;
	    
	    do{
			System.out.println("1 = Agregar un vertice.");
			System.out.println("2 = Agregar un arco.");
			System.out.println("3 = Verificar adyacencia.");
			System.out.println("4 = Recorrer grafo en anchura.");
			System.out.println("5 = Salir.");
			System.out.println("------------------------------");
			System.out.println("Elija una opcion...");
		    opcion=entrada.nextByte();
		    System.out.println();
		    System.out.println();
		    
		    if(opcion>=1 && opcion<=5){
		    	entrada.nextLine();
		    	switch(opcion) {
			    case 1: 
			    	if(grafo.verificarinIcializacion()){
			    		if(!grafo.verificarGrafoLleno()){
			    			System.out.println("Ingrese el nombre del vertice...");
							String nombre=entrada.nextLine();
							grafo.nuevoVertice(nombre);
							System.out.println();
							System.out.println();
							System.out.println("   Vertice agregado correctamente.");
			    		}
			    		else
			    			System.out.println("   *Se ha alcanzado el maximo de vertices a ingresar");
			    	}
			    	else
			    		System.out.println("   *El grafo no ha sido inicializado");
					System.out.println();
					System.out.println();
					break;
			    case 2:
			    	System.out.println("Ingrese el nombre del vertice origen...");
					String verticeOrigen=entrada.nextLine();
					System.out.println("Ingrese el nombre del vertice destino...");
					String verticeDestino=entrada.nextLine();
					System.out.println();
					System.out.println();
					try {
						grafo.nuevoArco(verticeOrigen, verticeDestino);
						System.out.println("   Arco agregado correctamente.");
					}catch (Exception e) {
						System.out.println("   *Error al agregar el arco, ambos vertices deben ser validos.");
					}
					System.out.println();
					System.out.println();
					break;
			    case 3: 
			    	System.out.println("Ingrese el nombre del primer vertice...");
					String vertice1=entrada.nextLine();
					System.out.println("Ingrese el nombre del segundo vertice...");
					String vertice2=entrada.nextLine();
					System.out.println();
					System.out.println();
					try {
						if(grafo.adyacente(vertice1, vertice2))
							System.out.println("   Los vertices si son adyacentes.");
						else
							System.out.println("   Los vertices no son adyacentes.");
					} catch (Exception e) {
						System.out.println("   *Error al verificar adyacencia, ambos vertices deben ser validos.");
					}
					System.out.println();
					System.out.println();
					break;
			    case 4: 
			    	System.out.println("Ingrese el nombre del vertice a partir del cual se hara el recorrido...");
					String verticeInicio=entrada.nextLine();
					System.out.println();
					System.out.println();
					try {
						System.out.print("   ");
						grafo.recorrerAnchura(grafo, verticeInicio);
					} catch (Exception e) {
						System.out.println("   *Error al recorrer el grafo, ingrese un vertice valido.");
					}
					System.out.println();
					System.out.println();
					break;
			    case 5: 
			    	repetir=false;
			    	break;
		    	}
		    }
		    else{
		    	System.out.println("   *"+opcion+" no es una opcion valida, intenta otra vez.");
				System.out.println();
				System.out.println();
		    }
		    System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println();
			System.out.println();
	    }
	    while(repetir);
	    System.out.println("Usted ha salido.");
	}
}