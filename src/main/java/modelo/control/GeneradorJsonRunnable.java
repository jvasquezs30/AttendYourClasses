package modelo.control;

import modelo.academico.Clase;
import modelo.academico.Curso;
import modelo.usuario.Docente;

import java.util.List;

public class GeneradorJsonRunnable implements Runnable, Exportable {

    public interface ResultadoGeneracion {
        void alTerminar(String resultado);
    }

    private final Curso curso;
    private final Clase clase;
    private final Docente docente;
    private final List<RegistroLlegada> registros;
    private final ResultadoGeneracion resultadoGeneracion;

    public GeneradorJsonRunnable(Curso curso, Clase clase, Docente docente,
                                 List<RegistroLlegada> registros,
                                 ResultadoGeneracion resultadoGeneracion) {
        this.curso = curso;
        this.clase = clase;
        this.docente = docente;
        this.registros = registros;
        this.resultadoGeneracion = resultadoGeneracion;
    }

    @Override
    public void exportar() {
        run();
    }

    @Override
    public void run() {
        String resultado = ReporteJson.generar(curso, clase, docente, registros);
        if (resultadoGeneracion != null) {
            resultadoGeneracion.alTerminar(resultado);
        }
    }
}
