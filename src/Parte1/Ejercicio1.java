package Parte1;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Ejercicio1 {
    public static void main(String[] args) {
        try {
            //Declara ficheros aleatorios de lectura y escritura
            File fichLeer = new File("src\\Parte1\\Leer.txt");
            File fichEscribir = new File("src\\Parte1\\Escribir.txt");
            RandomAccessFile ficheroLeer = new RandomAccessFile(fichLeer, "r");
            RandomAccessFile ficheroEscribir = new RandomAccessFile(fichEscribir, "rw");

            //Declara variables
            char letra;
            int pos = 0;

            //Posiciona en principio del fichero de lectura
            ficheroLeer.seek(pos);
            //Lee 1 letra
            letra = ficheroLeer.readChar();

            //Repite la siguiente acción 5 veces
            for (int i = 0; i < 5; i++) {
                //Escribe el carácter en el fichero de escritura
                ficheroEscribir.writeChar(letra);
            }

            //Cerramos los ficheros
            ficheroLeer.close();
            ficheroEscribir.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}