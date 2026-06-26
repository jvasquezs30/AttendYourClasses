# Attend Your Classes

Este proyecto se llama **Attend Your Classes** y es un sistema desarrollado en Java para controlar la asistencia de estudiantes durante una clase.

El sistema permite registrar la información de una clase, mostrar el docente encargado, la materia, la hora de inicio y realizar el seguimiento de la llegada de los estudiantes. Además, identifica si un estudiante llegó a tiempo, llegó tarde, presentó una excusa o no pertenece al curso, permitiendo al docente decidir si autoriza o no su permanencia en el salón.

---

# Objetivo del proyecto

El objetivo principal es aplicar los conceptos de Programación Orientada a Objetos vistos durante el curso mediante el desarrollo de un sistema funcional para el control de asistencia académica.

Además, se busca simular una situación real dentro de un salón de clases, permitiendo registrar la asistencia, administrar la información de los estudiantes y generar reportes automáticos de la sesión.

---

# Funciones principales

* Registrar la información de una clase.
* Mostrar el docente encargado.
* Mostrar la materia de la clase.
* Mostrar la hora de inicio y el aula.
* Registrar estudiantes mediante su código estudiantil.
* Determinar si un estudiante llegó a tiempo o con tardanza.
* Registrar excusas para estudiantes con tardanza.
* Identificar estudiantes que no pertenecen al curso.
* Permitir que el docente autorice o rechace la permanencia de un estudiante externo.
* Generar reportes de asistencia en formato PDF.
* Generar reportes de asistencia en formato JSON.
* Abrir automáticamente los reportes generados.
* Simular el paso del tiempo durante la clase mediante un hilo.
* Validar información utilizando excepciones personalizadas.

---

# Conceptos Utilizados

En el proyecto se aplican los principales conceptos :

* Clases y objetos.
* Encapsulamiento.
* Constructores.
* Métodos get y set.
* Modificadores de acceso.
* Herencia.
* Polimorfismo.
* Sobrescritura de métodos.
* Sobrecarga de métodos.
* Clases abstractas.
* Interfaces.
* Relaciones entre clases (asociación y herencia).
* Manejo de excepciones.
* Concurrencia mediante Thread y Runnable.
* Sincronización utilizando synchronized.

---

# Clases principales del proyecto

Las clases principales del sistema son:

### Modelo de usuarios

* Usuario
* Estudiante
* Docente
* DocenteCoordinador
* Administrador

### Modelo académico

* Institucion
* Curso
* Clase

### Modelo de control

* Asistencia
* RegistroLlegada
* Justificacion
* ReportePdf
* ReporteJson
* RelojClaseThread
* GeneradorJsonRunnable
* Exportable
* OperacionInvalidaException

Estas clases permiten organizar el proyecto siguiendo una arquitectura por paquetes, facilitando su mantenimiento y comprensión.

---

# Cómo ejecutar el proyecto

1. Descargar o clonar el proyecto.
2. Abrirlo en NetBeans.
3. Verificar que todas las dependencias del proyecto estén correctamente configuradas.
4. Compilar el proyecto.
5. Ejecutar la clase principal **AttendYourClasses.java**.
6. Esperar a que se cargue la interfaz principal del sistema.
7. Utilizar las opciones disponibles para registrar la asistencia.
8. Generar los reportes PDF o JSON cuando finalice la simulación.

---

# Reportes generados

## Reporte PDF

El sistema genera un archivo PDF con la información completa de la sesión.

El reporte incluye:

* Docente.
* Curso.
* Tema de la clase.
* Aula.
* Hora de inicio.
* Hora de llegada de cada estudiante.
* Estado de asistencia.
* Minutos de retraso.
* Excusa registrada.
* Pertenencia al curso.
* Decisión tomada por el docente.

---

## Reporte JSON

También se genera un archivo JSON que almacena la misma información de forma estructurada.

Este archivo contiene:

* Información del curso.
* Información de la clase.
* Información del docente.
* Datos completos de cada estudiante.
* Estado de asistencia.
* Excusas registradas.
* Decisión del docente.

Ambos reportes son almacenados automáticamente dentro de la carpeta **reportes** del proyecto y se abren automáticamente al finalizar su generación.

---

# Integrantes

* Josue Jose Vasquez Severiche
* Oscar David Tapia Suarez
* Juan Pacheco

