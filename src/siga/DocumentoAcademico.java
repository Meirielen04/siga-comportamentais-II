package siga;

import java.time.LocalDate;

public abstract class DocumentoAcademico {

    public final String emitir(Aluno aluno) {
        validar(aluno);

        StringBuilder documento = new StringBuilder();
        documento.append(cabecalho());
        documento.append(corpo(aluno));
        documento.append(rodape());

        registrarEmissao(aluno);
        return documento.toString();
    }

    private void validar(Aluno aluno) {
        if (!aluno.isRegular()) {
            throw new IllegalStateException(
                    "Aluno irregular: " + aluno.getMatricula());
        }
    }

    private String cabecalho() {
        return "FATEC DE PORTO FERREIRA\n"
                + "Sistema de Gestao Academica\n---\n";
    }

    private String rodape() {
        String texto = "\n---\nEmitido em " + LocalDate.now();
        if (exigeAssinatura()) {
            texto += "\nAssinado digitalmente pela secretaria academica.";
        }
        return texto;
    }

    private void registrarEmissao(Aluno aluno) {
        LogAcademico.registrar(tipo(), aluno);
    }

    protected abstract String corpo(Aluno aluno);

    protected abstract String tipo();

    protected boolean exigeAssinatura() {
        return true;
    }
}
