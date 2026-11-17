package siga;

public class Trancada implements EstadoMatricula {
   
    @Override
    public void confirmar(Matricula matricula) {
        matricula.mudarPara(new Confirmada());
    }

    @Override
    public void cancelar(Matricula matricula) {
        matricula.mudarPara(new Cancelada());
    }

    @Override
    public void trancar(Matricula matricula) {
        throw new IllegalStateException("Nao e possivel trancar uma matricula TRANCADA");
    }
    
    @Override
    public String nome() {
        return "TRANCADA";
    }
}
