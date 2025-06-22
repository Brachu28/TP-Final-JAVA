import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        Escuadron escuadronAlpha = new Escuadron("Alpha", "Comisaría 3F");


        Arma pistola = new ArmaCorta(null, 15, 150, "Glock", 9.0, "EN USO", false);
        Arma fusil = new ArmaLarga(null, 30, 500, "Remington", 12.5, "EN USO",
                true, "Defensa territorial", 3);
        Arma revolver = new ArmaCorta(null, 6, 100, "Smith & Wesson", 7.5,
                "EN MANTENIMIENTO", false);
        Arma rifle = new ArmaLarga(null, 20, 800, "Barrett", 12.7, "EN USO",
                true, "Francotirador", 5);


        Policia juan = new Policia("Juan", "Perez", 123, "Alpha", pistola);
        Policia ana = new Policia("Ana", "Gonzalezz", 456, "Alpha", fusil);
        Policia joan = new Policia("Joan", "Rodriguez", 789, "Alpha", revolver);
        Policia natalia = new Policia("Natalia", "López", 101, "Alpha", rifle);


        pistola.policia = juan;
        fusil.policia = ana;
        revolver.policia = joan;
        rifle.policia = natalia;


        escuadronAlpha.agregarPolicia(juan);
        escuadronAlpha.agregarPolicia(ana);
        escuadronAlpha.agregarPolicia(joan);
        escuadronAlpha.agregarPolicia(natalia);


        escuadronAlpha.mostrarInforme();
    }
}

class Policia {
    String nombre;
    String apellido;
    int legajo;
    String escuadron;
    Arma arma;


    public Policia(String nombre, String apellido, int legajo, String escuadron, Arma arma) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.legajo = legajo;
        this.escuadron = escuadron;
        this.arma = arma;
    }
}

class Arma {
    Policia policia;
    int municiones;
    int alcance;
    String marca;
    double calibre;
    String estado;


    public Arma(Policia policia, int municiones, int alcance, String marca,
                double calibre, String estado) {
        this.policia = policia;
        this.municiones = municiones;
        this.alcance = alcance;
        this.marca = marca;
        this.calibre = calibre;
        this.estado = estado;
    }
}

class ArmaCorta extends Arma {
    boolean automatica;

    public ArmaCorta(Policia policia, int municiones, int alcance, String marca,
                     double calibre, String estado, boolean automatica) {
        super(policia, municiones, alcance, marca, calibre, estado);
        this.automatica = automatica;
    }
}

class ArmaLarga extends Arma {
    boolean selloRENAR;
    String descripcionUso;
    int nivel;

    public ArmaLarga(Policia policia, int municiones, int alcance, String marca,
                     double calibre, String estado, boolean selloRENAR,
                     String descripcionUso, int nivel) {
        super(policia, municiones, alcance, marca, calibre, estado);
        this.selloRENAR = selloRENAR;
        this.descripcionUso = descripcionUso;
        this.nivel = nivel;
    }
}

class Escuadron {
    String nombre;
    String comisaria;
    ArrayList<Policia> policias = new ArrayList<>();

    public Escuadron(String nombre, String comisaria) {
        this.nombre = nombre;
        this.comisaria = comisaria;
    }

    public void agregarPolicia(Policia policia) {
        policias.add(policia);
    }

    public void mostrarInforme() {
        System.out.println("=== INFORME DEL ESCUADRÓN ===");
        System.out.println("Nombre del escuadrón: " + nombre);
        System.out.println("Comisaría: " + comisaria);
        System.out.println("Cantidad de integrantes: " + policias.size());


        int armasBuenas = 0;
        for (Policia policia : policias) {
            if (policia.arma.estado.equals("EN USO") &&
                    policia.arma.calibre >= 9.0) {
                armasBuenas++;
            }
        }
        System.out.println("Armas en condiciones: " + armasBuenas + "/" + policias.size());

        System.out.println("\nDetalle de armas por policía:");
        for (Policia policia : policias) {
            System.out.println("- " + policia.nombre + " " + policia.apellido + ":");
            System.out.println("  Legajo:" + policia.legajo);
            System.out.println("  Arma: " + policia.arma.marca);
            System.out.println("  Calibre: " + policia.arma.calibre +
                    " - Municiones: " + policia.arma.municiones +
                    " - Alcance: " + policia.arma.alcance + "m");

            String apta = "NO";
            if (policia.arma.estado.equals("EN USO") &&
                    policia.arma.calibre >= 9.0) {
                apta = "SI";
            }
            System.out.println("  Estado: " + policia.arma.estado +
                    " | Apta enfrentamiento: " + apta);

            if (policia.arma instanceof ArmaCorta) {
                ArmaCorta armaCorta = (ArmaCorta) policia.arma;
                System.out.println("  Tipo: Corta");
                System.out.println("  Automática: " + (armaCorta.automatica ? "SÍ" : "NO"));

                String disparaLargo = "NO";
                if (armaCorta.alcance > 200) {
                    disparaLargo = "SI";
                }
                System.out.println("  Dispara a más de 200m: " + disparaLargo);
            }
            else if (policia.arma instanceof ArmaLarga) {
                ArmaLarga armaLarga = (ArmaLarga) policia.arma;
                System.out.println("  Tipo: Larga");
                System.out.println("  Nivel: " + armaLarga.nivel);
                System.out.println("  Sello RENAR: " + (armaLarga.selloRENAR ? "SI" : "NO"));
                System.out.println("  Descripción uso: " + armaLarga.descripcionUso);
            }
            System.out.println();
        }

        ArmaLarga armaMayor = null;
        for (Policia policia : policias) {
            if (policia.arma instanceof ArmaLarga) {
                ArmaLarga armaLarga = (ArmaLarga) policia.arma;
                if (armaMayor == null || armaLarga.nivel > armaMayor.nivel) {
                    armaMayor = armaLarga;
                }
            }
        }

        if (armaMayor != null) {
            System.out.println("Arma Larga Mayor (Nivel " + armaMayor.nivel + "):");
            System.out.println("  Marca: " + armaMayor.marca);
            System.out.println("  Municiones: " + armaMayor.municiones);
            System.out.println("  Calibre: " + armaMayor.calibre);
            System.out.println("  Asignada a: " + armaMayor.policia.nombre + " " + armaMayor.policia.apellido);
            System.out.println("  Descripción uso: " + armaMayor.descripcionUso);
        } else {
            System.out.println("No hay armas largas en el escuadrón");
        }

        System.out.println("=============================");
    }
}
