package siga;

public class Matricula {

    private final Aluno aluno;
    private final String codigoTurma;
    private EstadoMatricula estado = new PreMatriculada();

    public Matricula(Aluno aluno, String codigoTurma) {
        this.aluno = aluno;
        this.codigoTurma = codigoTurma;
    }
    
    void mudarPara(EstadoMatricula novo) {
        this.estado = novo;
    }

    public void confirmar() {
        estado.confirmar(this);
    }

    public void cancelar() {
        estado.cancelar(this);
    }

    public void trancar() {
        estado.trancar(this);
    }

    public void concluir() {
        estado.concluir(this);
    }

    public Aluno getAluno() {
        return aluno;
    }

    public String getCodigoTurma() {
        return codigoTurma;
    }

    public String getSituacao() {
        return estado.nome();
    }
}
