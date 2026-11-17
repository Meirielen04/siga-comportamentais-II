package siga;

public class Concluida implements EstadoMatricula {
   
    @Override
    public void confirmar(Matricula matricula) {
        throw new IllegalStateException("Nao e possivel confirmar uma matricula CONCLUIDA");
    }

    @Override
    public void cancelar(Matricula matricula) {
        throw new IllegalStateException("Nao e possivel cancelar uma matricula CONCLUIDA");
    }

    @Override
    public void trancar(Matricula matricula) {
        throw new IllegalStateException("Nao e possivel trancar uma matricula CONCLUIDA");
    }

    @Override
    public String nome() {
        return "CONCLUIDA";
    } 
}
