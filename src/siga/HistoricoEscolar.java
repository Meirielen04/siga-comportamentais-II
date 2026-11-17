package siga;
public class HistoricoEscolar extends DocumentoAcademico {
    
    @Override
    protected String corpo(Aluno aluno) {
        StringBuilder meioTexto = new StringBuilder();
        meioTexto.append("HISTORICO ESCOLAR\n")
                 .append(aluno.getNome())
                 .append("\nCarga horaria cumprida: ")
                 .append(aluno.getCargaCumprida())
                 .append("h");
        return meioTexto.toString();
    }

    @Override 
    protected String tipo() {
        return "HISTORICO";
    }    
}
