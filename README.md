# Attend Your Classes

Este proyecto se llama **Attend Your Classes** y es un sistema sencillo hecho en Java para controlar la asistencia de estudiantes en una clase.

La idea del programa es registrar una clase, mostrar el docente encargado, la materia, la hora de inicio y luego ir mostrando la llegada de los estudiantes. También se puede ver si un estudiante llegó a tiempo, llegó tarde, presentó una excusa o no pertenece a la clase.

## Objetivo del proyecto

El objetivo es practicar los conceptos de Programación Orientada a Objetos vistos en clase, usando clases, objetos, atributos, métodos, constructores, herencia, polimorfismo y relaciones entre clases.

También se busca que el sistema ayude a organizar mejor la asistencia de una clase, evitando hacerlo todo de forma manual.

## Funciones principales

- Registrar información de una clase.
- Mostrar el docente encargado.
- Mostrar la materia de la clase.
- Mostrar la hora de inicio.
- Registrar estudiantes con su código.
- Mostrar si el estudiante llegó a tiempo o tarde.
- Mostrar una excusa cuando el estudiante llega tarde.
- Mostrar estudiantes que no pertenecen a la clase.
- Permitir que el docente decida si un estudiante externo puede quedarse o no.
- Generar un reporte en PDF con los datos de asistencia.
- Abrir automáticamente el PDF generado.

## Conceptos de POO usados

En el proyecto se usan varios conceptos de Programación Orientada a Objetos:

- Clases y objetos.
- Atributos y métodos.
- Constructores.
- Métodos get y set.
- Modificadores de acceso.
- Herencia.
- Clases abstractas.
- Polimorfismo.
- Sobrescritura de métodos.
- Sobrecarga de métodos.
- Relaciones entre clases.

## Clases principales del proyecto

Algunas clases usadas en el sistema son:

- Usuario
- Estudiante
- Docente
- Curso
- Clase
- Asistencia
- Justificacion
- Reporte

Estas clases representan las partes principales del sistema de asistencia.

## Cómo ejecutar el proyecto

1. Descargar o clonar el proyecto.
2. Abrirlo en NetBeans.
3. Verificar que el proyecto compile correctamente.
4. Ejecutar la clase principal.
5. Esperar a que el sistema muestre la simulación de asistencia.
6. Al finalizar, se genera un reporte en PDF dentro de la carpeta `reportes`.

## Reporte PDF

El sistema genera un archivo PDF donde se muestran los datos principales de la clase y la asistencia de los estudiantes.

El reporte incluye:

- Docente.
- Materia.
- Hora de inicio.
- Código del estudiante.
- Nombre del estudiante.
- Hora de llegada.
- Estado de asistencia.
- Excusa si llegó tarde.
- Observación si el estudiante no pertenece a la clase.

- # Cambios y mejoras agregadas al proyecto

Además de lo que estaba planteado en la primera entrega del proyecto, se agregaron algunas funciones para que el sistema se vea más completo y más cercano a un programa real.

## 1. Simulación de llegada de estudiantes

Se agregó una simulación usando pausas con `Thread.sleep()`, para que los estudiantes no aparezcan todos al mismo tiempo, sino uno por uno.

Esto hace que el sistema se vea más dinámico, como si la clase estuviera ocurriendo en tiempo real.

## 2. Estudiantes con código

Cada estudiante tiene un código estudiantil. Esto ayuda a identificar mejor a cada estudiante y hace que el registro de asistencia sea más ordenado.

## 3. Diez estudiantes registrados

Se agregaron diez estudiantes para que el sistema tenga más datos y no se vea tan vacío o básico.

## 4. Excusas de los estudiantes para cuando lleguen tarde

Cuando un estudiante llega tarde, el sistema muestra una excusa.Son situaciones normales que podrían pasar en la vida real, como tráfico, demora del bus, problemas de transporte o inconvenientes personales.

## 5. Pantalla de clase más completa

Se mejoró la pantalla principal para que muestre mejor la información de la clase, como:

- Docente.
- Materia.
- Hora de inicio.
- Aula.
- Lista de estudiantes.
- Estado de asistencia.

## 6. Estudiantes que no pertenecen a la clase

Se agregó la posibilidad de mostrar estudiantes que no hacen parte del curso. Esto permite identificar si una persona está intentando quedarse en una clase que no le corresponde.

## 7. Decisión del docente

Cuando aparece un estudiante que no pertenece a la clase, el docente puede decidir si lo deja quedarse o no, según su criterio.

Esto hace que el sistema sea más flexible y parecido a una situación real.

## 8. Generación de reporte PDF

Se agregó una opción para generar un reporte en PDF con la información de la asistencia.

El PDF muestra los datos de la clase, del docente, de la materia y de los estudiantes.

## 9. Apertura automática del PDF

Después de generar el reporte, el sistema intenta abrir automáticamente el archivo PDF para que el usuario pueda verlo sin tener que buscarlo manualmente.

## Integrantes

- Josue Jose Vasquez Severiche
- Oscar David Tapia Suarez
- Juan Pacheco
