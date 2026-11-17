package siga;

public class AtestadoFrequencia extends DocumentoAcademico {

    @Override
    protected String corpo(Aluno aluno) {
        StringBuilder atestadoTexto = new StringBuilder();
        atestadoTexto.append("ATESTADO DE FREQUENCIA\n")
                .append(aluno.getNome());
        return atestadoTexto.toString();
    }

    @Override 
    protected boolean exigeAssinatura() {
        return false;
    }

    @Override
    protected String tipo() {
        return "ATESTADO";
    }
}
