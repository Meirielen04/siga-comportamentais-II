package siga;

public class PreMatriculada implements EstadoMatricula {
    
    @Override
    public void confirmar(Matricula matricula) {
        matricula.mudarPara(new Confirmada());
    }

    @Override
    public void trancar(Matricula matricula) {
        throw new IllegalStateException("Nao e possivel trancar uma matricula PRE_MATRICULADA");
    }

    @Override
    public void cancelar(Matricula matricula) {
        matricula.mudarPara(new Cancelada());
    }

    @Override
    public String nome() {
        return "PRE_MATRICULADA";
    }
}
