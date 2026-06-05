package modelo.control;

import javax.swing.SwingUtilities;

public class RelojClaseThread extends Thread {

    private static final int MILISEGUNDOS_POR_MINUTO_DE_CLASE = 5000;
    private final Runnable accionActualizarReloj;
    private volatile boolean activo;

    public RelojClaseThread(Runnable accionActualizarReloj) {
        this.accionActualizarReloj = accionActualizarReloj;
        this.activo = true;
        setName("Hilo-Reloj-Clase");
        setDaemon(true);
    }

    @Override
    public void run() {
        while (activo) {
            try {
                Thread.sleep(MILISEGUNDOS_POR_MINUTO_DE_CLASE);
                if (activo) {
                    SwingUtilities.invokeLater(accionActualizarReloj);
                }
            } catch (InterruptedException ex) {
                activo = false;
                Thread.currentThread().interrupt();
            }
        }
    }

    public void detenerReloj() {
        activo = false;
        interrupt();
    }
}
