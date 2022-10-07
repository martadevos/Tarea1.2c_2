package Parte3;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Ejercicio3 {
    public static void main(String[] args) {
        try {
            //Declara ficheros aleatorios de lectura y escritura
            File fichLeer = new File("src\\Parte3\\Leer.txt");
            File fichEscribir = new File("src\\Parte3\\Escribir.txt");
            RandomAccessFile ficheroLeer = new RandomAccessFile(fichLeer, "r");
            RandomAccessFile ficheroEscribir = new RandomAccessFile(fichEscribir, "rw");

            //Declara variables
            String letraConNum, letraConNum2;

            ficheroLeer.seek(0);
            //Lee 1 letra
            letraConNum = ficheroLeer.readLine();
            while (letraConNum != null) {
                //Comprueba que el fichero esté vacío
                ficheroEscribir.seek(0);
                if (ficheroEscribir.readLine() != null) {
                    //Coge la posición del final del fichero de escritura
                    for (int pos = (int) ficheroEscribir.length(); pos >= 2; pos -= 2) {
                        ficheroEscribir.seek(pos - 2);
                        letraConNum2 = ficheroEscribir.readLine();
                        ficheroEscribir.seek(pos);
                        ficheroEscribir.writeChars(letraConNum2);
                        ficheroEscribir.write("\n".getBytes());
                    }
                    ficheroEscribir.seek(0);
                }
                //Escribe el carácter en el fichero de escritura
                ficheroEscribir.writeChars(letraConNum);
                ficheroEscribir.write("\n".getBytes());

                letraConNum = ficheroLeer.readLine();
            }

            //Cerramos los ficheros
            ficheroLeer.close();
            ficheroEscribir.close();
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }
}
