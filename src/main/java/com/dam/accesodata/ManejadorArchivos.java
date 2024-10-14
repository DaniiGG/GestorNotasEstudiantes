package com.dam.accesodata;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ManejadorArchivos {

    private static final String ARCHIVO = "resources/notas_estudiantes.txt";

    // Método para añadir un estudiante al archivo
    public void añadirEstudiante(Estudiante estudiante) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO, true))) {
            writer.write(estudiante.toString());
            writer.newLine(); // Añadir una nueva línea después del estudiante
            System.out.println("Estudiante añadido con éxito.");
        } catch (IOException e) {
            System.out.println("Error al añadir el estudiante: " + e.getMessage());
        }
    }

    // Método para mostrar todos los estudiantes del archivo
    public void mostrarEstudiantes() {
        List<Estudiante> estudiantes = leerEstudiantes();
        if (estudiantes.isEmpty()) {
            System.out.println("No hay estudiantes registrados.");
        } else {
            System.out.println("Lista de estudiantes:");
            for (Estudiante estudiante : estudiantes) {
                System.out.println("Nombre: " + estudiante.getNombre() + ", Nota: " + estudiante.getNota());
            }
        }
    }

    // Método para buscar un estudiante por nombre en el archivo
    public void buscarEstudiante(String nombre) {
        List<Estudiante> estudiantes = leerEstudiantes();
        boolean encontrado = false;
        for (Estudiante estudiante : estudiantes) {
            if (estudiante.getNombre().equalsIgnoreCase(nombre)) {
                System.out.println("Estudiante encontrado: Nombre: " + estudiante.getNombre() + ", Nota: " + estudiante.getNota());
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            System.out.println("Estudiante con nombre '" + nombre + "' no encontrado.");
        }
    }

    // Método para calcular la nota media de todos los estudiantes
    public void calcularMedia() {
        List<Estudiante> estudiantes = leerEstudiantes();
        if (estudiantes.isEmpty()) {
            System.out.println("No hay estudiantes para calcular la media.");
            return;
        }

        double suma = 0;
        for (Estudiante estudiante : estudiantes) {
            suma += estudiante.getNota();
        }
        double media = suma / estudiantes.size();
        System.out.println("La nota media de los estudiantes es: " + media);
    }

    // Método privado para leer todos los estudiantes desde el archivo
    private List<Estudiante> leerEstudiantes() {
        List<Estudiante> estudiantes = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(","); // Se espera que el formato sea "nombre,nota"
                if (partes.length == 2) {
                    String nombre = partes[0].trim();
                    double nota = Double.parseDouble(partes[1].trim());
                    estudiantes.add(new Estudiante(nombre, nota));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("El archivo no existe. No hay estudiantes registrados.");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error en el formato de las notas en el archivo.");
        }

        return estudiantes;
    }

}
