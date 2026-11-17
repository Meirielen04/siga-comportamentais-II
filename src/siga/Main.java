package siga;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== SIGA - Aula 13: Template Method e State ===");

        Aluno regular = new Aluno("2024001", "Maria Silva");
        regular.setCargaCumprida(560);

        Aluno irregular = new Aluno("2024002", "Joao Souza");
        irregular.setRegular(false);

        System.out.println();
        System.out.println("1) Tres documentos, um unico rito (Template Method)");
        System.out.println(recuar(new HistoricoEscolar().emitir(regular)));
        System.out.println(recuar(new AtestadoFrequencia().emitir(regular)));
        System.out.println("   >> Os quatro passos comuns ficam na DocumentoAcademico;");
        System.out.println("      cada classe so define o corpo");

        System.out.println();
        System.out.println("2) A declaracao de vinculo para um aluno irregular");
        System.out.println("   Emitindo para um aluno IRREGULAR:");
        try {
            new DeclaracaoVinculo().emitir(irregular);
        } catch (IllegalStateException e) {
            System.out.println("   [erro] " + e.getMessage());
        }
        System.out.println("   >> O documento nao foi emitido e nao consta no log.");
        System.out.println("      Registros no log: " + LogAcademico.getRegistros());

        System.out.println();
        System.out.println("3) O ciclo de vida da matricula com o padrao State");
        Matricula m = new Matricula(regular, "DSM-2A");
        System.out.println("   situacao inicial: " + m.getSituacao());
        m.confirmar();
        System.out.println("   apos confirmar:   " + m.getSituacao());
        m.trancar();
        System.out.println("   apos trancar:     " + m.getSituacao());
        m.confirmar();
        System.out.println("   apos confirmar:   " + m.getSituacao());
        m.cancelar();
        System.out.println("   apos cancelar:    " + m.getSituacao());
        try {
            m.confirmar();
        } catch (IllegalStateException e) {
            System.out.println("   [erro] " + e.getMessage());
        }
        System.out.println("   >> Cada estado conhece suas transicoes;");
        System.out.println("      a matricula so repassa a chamada");

        System.out.println();
        System.out.println("4) O estado novo: Concluida");
        Matricula m2 = new Matricula(regular, "DSM-2A");
        System.out.println("   situacao inicial: " + m2.getSituacao());
        m2.confirmar();
        System.out.println("   apos confirmar:   " + m2.getSituacao());
        m2.concluir();
        System.out.println("   apos concluir:    " + m2.getSituacao());
        try {
            m2.cancelar();
        } catch (IllegalStateException e) {
            System.out.println("   [erro] " + e.getMessage());
        }
    }

    private static String recuar(String texto) {
        StringBuilder saida = new StringBuilder();
        for (String linha : texto.split("\n")) {
            saida.append("     ").append(linha).append("\n");
        }
        return saida.toString().stripTrailing();
    }
}
