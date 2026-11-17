package siga;

public interface EstadoMatricula {
    void confirmar(Matricula matricula);
    void cancelar(Matricula matricula);
    void trancar(Matricula matricula);
    String nome();

    default void concluir(Matricula matricula) {
        throw new IllegalStateException("Nao e possivel concluir uma matricula " + nome());
    }
}
