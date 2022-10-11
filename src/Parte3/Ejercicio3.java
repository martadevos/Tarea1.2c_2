package Parte3;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Ejercicio3 {
    static int LONGITUDPALABRA = 6;    // Se define la longitud del texto (5+\n=6)

    public static void main(String[] args) {
        try {
            File fichLeer = new File("src\\Parte3\\Leer.txt");
            File fichEscribir = new File("src\\Parte3\\Escribir.txt");
            fichEscribir.delete();
            RandomAccessFile ficheroLeer = new RandomAccessFile(fichLeer, "r");
            RandomAccessFile ficheroEscribir = new RandomAccessFile(fichEscribir, "rw");
            byte palabra[] = new byte[LONGITUDPALABRA];        // Esta variable recogerá cada palabra del archivo Lector

            /*
             * Si el lector no está vacío (posible error), y mientras no se llegue al final...
             */

            if (ficheroLeer.length() > 0) {
                while (!finalEncontrado(ficheroLeer)) {
                    palabra = leePalabra(ficheroLeer);                // Lee nueva palabra
                    if (escritorVacio(ficheroEscribir)) {            // Comprueba si el escritor está vacío...
                        escribePalabra(palabra, ficheroEscribir);    // ... para escribir la palabra
                    } else {
                        ordena(palabra, ficheroEscribir);            // ... u ordenar lo que exista.
                    }
                }
            }
            ficheroLeer.close();        //cerrar fichero
            ficheroEscribir.close();    //cerrar fichero
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void ordena(byte[] palabra, RandomAccessFile ficheroEscribir) throws IOException {
        long posicion = buscaPosicion(palabra, ficheroEscribir);    // pos guarda el lugar donde debe escribirse
        if (posicion < ficheroEscribir.length())    // si no es al final...
            mueve(posicion, ficheroEscribir);    // ... se debe reorganizar el fichero Escritor
        ficheroEscribir.seek(posicion);            // Se posiciona en el lugar deseado...
        escribePalabra(palabra, ficheroEscribir);    // y se escribe.
    }

    private static void mueve(long posicion, RandomAccessFile ficheroEscribir) throws IOException {
        byte[] palabraAux;                        // Palabra que se moverá
        long posicionLeer = ficheroEscribir.length() - 6;    // Posición de la última palabra (NO CONFUNDIR CON EL PUNTERO)
        boolean salir = false;                    // Para salir del bucle (se puede usar la condición que está antes del while)
        do {
            ficheroEscribir.seek(posicionLeer);            // Se posiciona el puntero en la palabra que se va a mover
            palabraAux = leePalabra(ficheroEscribir);    // Se almacena
            escribePalabra(palabraAux, ficheroEscribir);// Se escribe a continuación (aparece duplicada)
            posicionLeer -= LONGITUDPALABRA;            // La posición se cambia a la palabra anterior.
            // Si la posición deseada es mayor que la posición actual (porque nos hemos pasado)
            // O la posición actual de lectura ha llegado al principio... se sale del bucle.
            if (posicion > posicionLeer || posicionLeer < 0) salir = true;
        } while (!salir);

    }

    private static long buscaPosicion(byte[] palabra, RandomAccessFile fichero) throws IOException {
        long posicionActual = 0;                                                // Se comienza en la posición 0
        fichero.seek(posicionActual);
        byte[] auxPalabra = new byte[LONGITUDPALABRA];                        // Palabra que se lee para comparar
        do {                                                                // Se repite...
            posicionActual = fichero.getFilePointer();                            // ... guardar la posición actual del puntero
            auxPalabra = leePalabra(fichero);                                // ... y leer la palabra en esa posición
            // ... mientras que la palabra deseada es mayor que la leída y no se ha llegado al final del fichero
        } while (palabra[0] > auxPalabra[0] && !finalEncontrado(fichero));

        if (palabra[0] < auxPalabra[0]) {    // Si la palabra deseada es menor que la leída ...
            return posicionActual;            // ... se devuelve la posición
        } else {
            return fichero.length();    // ... si no, es que ha llegado al final.
        }
    }

    private static byte[] leePalabra(RandomAccessFile ficheroLeer) throws IOException {
        byte palabra[] = new byte[LONGITUDPALABRA];
        for (int i = 0; i < LONGITUDPALABRA; i++)
            palabra[i] = ficheroLeer.readByte();
        return palabra;

    }

    private static void escribePalabra(byte[] palabra, RandomAccessFile ficheroEscribir) throws IOException {
        ficheroEscribir.writeBytes(new String(palabra));
    }

    private static boolean finalEncontrado(RandomAccessFile fichero) throws IOException {
        return fichero.getFilePointer() == fichero.length();
    }

    private static boolean escritorVacio(RandomAccessFile ficheroEscribir) throws IOException {
        return ficheroEscribir.length() == 0;
    }
}
