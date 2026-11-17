package siga;

public class DeclaracaoVinculo extends DocumentoAcademico {

    @Override
    protected String corpo(Aluno aluno) {
        StringBuilder declaracaoTexto = new StringBuilder();
        declaracaoTexto.append("DECLARACAO DE VINCULO\n")
                 .append("Declaramos que ")
                 .append(aluno.getNome())
                 .append(" possui vinculo ativo com esta instituicao.");
        return declaracaoTexto.toString();
    }

    @Override
    protected String tipo() {
        return "DECLARACAO";
    }
}

