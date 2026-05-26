/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package main;

import modelo.academico.Clase;
import modelo.academico.Curso;
import modelo.academico.Institucion;
import modelo.control.RegistroLlegada;
import modelo.usuario.Docente;
import modelo.usuario.Estudiante;
import view.PantallaClase;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AttendYourClasses {

    public static void main(String[] args) {
        Institucion institucion = new Institucion(1, "Universidad de Cartagena",
                "Cl. 30 #48-152", "6757000", "info@unicartagena.edu.co");

        Docente docente = new Docente(20, "Taidy", "Marrugo",
                "1001234567", "tmarrugo@unicartagena.edu.co", "docente123",
                "Ingeniería de Software", "Sistemas", "Tiempo Completo");

        Curso curso = new Curso(100, "Programación Orientada a Objetos",
                "Universitario", "Nocturno", 35);

        curso.asignarDocente(docente);
        institucion.agregarCurso(curso);
        institucion.agregarUsuario(docente);

        List<Estudiante> estudiantes = new ArrayList<>();
        estudiantes.add(new Estudiante(1, "Josue", "Vasquez", "1123456789", "jvasquez@est.edu.co", "pass1", "POO-2026-001", "Ingeniería de Sistemas", 4));
        estudiantes.add(new Estudiante(2, "Oscar", "Tapia", "1123456780", "otapia@est.edu.co", "pass2", "POO-2026-002", "Ingeniería de Sistemas", 4));
        estudiantes.add(new Estudiante(3, "Juan", "Pacheco", "1123456781", "jpacheco@est.edu.co", "pass3", "POO-2026-003", "Ingeniería de Sistemas", 4));
        estudiantes.add(new Estudiante(4, "Laura", "Mejía", "1123456782", "lmejia@est.edu.co", "pass4", "POO-2026-004", "Ingeniería de Sistemas", 4));
        estudiantes.add(new Estudiante(5, "Camila", "Torres", "1123456783", "ctorres@est.edu.co", "pass5", "POO-2026-005", "Ingeniería de Sistemas", 4));
        estudiantes.add(new Estudiante(6, "Andrés", "Ramos", "1123456784", "aramos@est.edu.co", "pass6", "POO-2026-006", "Ingeniería de Sistemas", 4));
        estudiantes.add(new Estudiante(7, "Valentina", "Gómez", "1123456785", "vgomez@est.edu.co", "pass7", "POO-2026-007", "Ingeniería de Sistemas", 4));
        estudiantes.add(new Estudiante(8, "Mateo", "Herrera", "1123456786", "mherrera@est.edu.co", "pass8", "POO-2026-008", "Ingeniería de Sistemas", 4));
        estudiantes.add(new Estudiante(9, "Daniela", "Ruiz", "1123456787", "druiz@est.edu.co", "pass9", "POO-2026-009", "Ingeniería de Sistemas", 4));
        estudiantes.add(new Estudiante(10, "Santiago", "Moreno", "1123456788", "smoreno@est.edu.co", "pass10", "POO-2026-010", "Ingeniería de Sistemas", 4));

        for (Estudiante estudiante : estudiantes) {
            curso.agregarEstudiante(estudiante);
            institucion.agregarUsuario(estudiante);
        }

        Estudiante invitadaAutorizada = new Estudiante(11, "Mariana", "Castillo", "1123456790", "mcastillo@est.edu.co", "pass11", "BD-2026-021", "Ingeniería de Sistemas", 5);
        Estudiante invitadoNoAutorizado = new Estudiante(12, "Felipe", "Navarro", "1123456791", "fnavarro@est.edu.co", "pass12", "RED-2026-014", "Ingeniería de Sistemas", 6);

        Clase clase = new Clase(200, "Control de asistencia en tiempo real",
                new Date(), "18:00", 90, 10, "Sala A-3");

        docente.asignarClase(clase);
        clase.iniciarClase();

        List<RegistroLlegada> registros = new ArrayList<>();
        registros.add(registrarLlegada(clase, estudiantes.get(0), "17:58", ""));
        registros.add(registrarLlegada(clase, estudiantes.get(1), "18:03", ""));
        registros.add(registrarLlegada(clase, estudiantes.get(2), "18:08", ""));
        registros.add(registrarLlegada(clase, estudiantes.get(3), "18:12", "Tráfico por cierre de vía principal"));
        registros.add(registrarLlegada(clase, estudiantes.get(4), "18:15", "El bus demoró más de lo habitual"));
        registros.add(registrarLlegada(clase, estudiantes.get(5), "18:18", "Cita médica previa con constancia"));
        registros.add(registrarLlegada(clase, estudiantes.get(6), "18:21", "Falla de energía en casa antes de salir"));
        registros.add(registrarLlegada(clase, estudiantes.get(7), "18:24", "Problema familiar de último momento"));
        registros.add(registrarLlegada(clase, estudiantes.get(8), "18:28", "Retraso por lluvia y congestión vehicular"));
        registros.add(registrarLlegada(clase, estudiantes.get(9), "18:33", "Confusión con el cambio de aula"));
        registros.add(registrarLlegada(clase, invitadaAutorizada, "18:06", "", false, true, "Puede quedarse como oyente porque solicitó permiso antes de iniciar la clase"));
        registros.add(registrarLlegada(clase, invitadoNoAutorizado, "18:19", "", false, false, "No puede quedarse porque no pertenece al grupo y no presentó autorización académica"));

        new PantallaClase(curso, clase, docente, registros).mostrar();
    }

    private static RegistroLlegada registrarLlegada(Clase clase, Estudiante estudiante, String horaIngreso, String excusa) {
        RegistroLlegada registro = new RegistroLlegada((int) (Math.random() * 1000), clase, estudiante, horaIngreso, excusa);
        clase.agregarAsistencia(registro);
        estudiante.agregarAsistencia(registro);
        return registro;
    }

    private static RegistroLlegada registrarLlegada(Clase clase, Estudiante estudiante, String horaIngreso, String excusa,
            boolean perteneceClase, boolean autorizado, String decisionDocente) {
        RegistroLlegada registro = new RegistroLlegada((int) (Math.random() * 1000), clase, estudiante, horaIngreso, excusa,
                perteneceClase, autorizado, decisionDocente);
        clase.agregarAsistencia(registro);
        estudiante.agregarAsistencia(registro);
        return registro;
    }
}
