package siga;

public interface EstadoMatricula {
    void confirmar(Matricula matricula);
    void cancelar(Matricula matricula);
    void trancar(Matricula matricula);
    String nome();
}
