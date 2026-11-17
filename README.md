# SIGA — Atividade de padrões comportamentais II: Template Method e State

**Técnicas de Programação II (TP2) · Aula 13** — CST em Desenvolvimento de Software Multiplataforma · Fatec de Porto Ferreira

Este é o **código final** da atividade prática da Aula 13. O código inicial compilava e executava, mas a saída mostrava um aluno irregular deixando rastro no log de um documento que nunca foi emitido. Eram **três deslizes**: o **Template Method** resolveu os dois primeiros, reunindo o rito de emissão dos documentos em um único método, e o **State** resolveu o terceiro, tirando o ciclo de vida da matrícula das condicionais.

## Estrutura do projeto

```
siga-comportamentais-ii/
└── src/
    └── siga/
        ├── Aluno.java                (entidade de domínio; não foi alterada)
        ├── LogAcademico.java         (registro de emissões; não foi alterado)
        │
        │   Template Method — emissão de documentos
        ├── DocumentoAcademico.java   (classe abstrata: método-template final emitir e passos fixos)
        ├── HistoricoEscolar.java     (implementa corpo e tipo)
        ├── DeclaracaoVinculo.java    (implementa corpo e tipo)
        ├── AtestadoFrequencia.java   (corpo, tipo e o gancho exigeAssinatura = false)
        │
        │   State — ciclo de vida da matrícula
        ├── Matricula.java            (repassa cada ação ao estado atual, sem if)
        ├── EstadoMatricula.java      (interface dos estados; concluir é um método default)
        ├── PreMatriculada.java       (estado inicial)
        ├── Confirmada.java           (leva a Trancada, Cancelada ou Concluida)
        ├── Trancada.java             (volta a Confirmada ou vai a Cancelada)
        ├── Cancelada.java            (estado final)
        ├── Concluida.java            (estado final; criado na etapa 5)
        │
        └── Main.java                 (demonstra os dois padrões funcionando)
```

## Como compilar e executar

Pré-requisito: JDK 17 ou superior (`java -version` para verificar).

```bash
# 1. Compilar (a saída vai para a pasta "bin")
javac -encoding UTF-8 -d bin src/siga/*.java

# 2. Executar
java -cp bin siga.Main
```

Saída obtida com o código final:

```
=== SIGA - Aula 13: Template Method e State ===

1) Tres documentos, um unico rito (Template Method)
   [log] HISTORICO emitido para 2024001
     FATEC DE PORTO FERREIRA
     Sistema de Gestao Academica
     ---
     HISTORICO ESCOLAR
     Maria Silva
     Carga horaria cumprida: 560h
     ---
     Emitido em 2026-11-17
     Assinado digitalmente pela secretaria academica.
   [log] ATESTADO emitido para 2024001
     FATEC DE PORTO FERREIRA
     Sistema de Gestao Academica
     ---
     ATESTADO DE FREQUENCIA
     Maria Silva
     ---
     Emitido em 2026-11-17
   >> Os quatro passos comuns ficam na DocumentoAcademico;
      cada classe so define o corpo

2) A declaracao de vinculo para um aluno irregular
   Emitindo para um aluno IRREGULAR:
   [erro] Aluno irregular: 2024002
   >> O documento nao foi emitido e nao consta no log.
      Registros no log: [HISTORICO - 2024001, ATESTADO - 2024001]

3) O ciclo de vida da matricula com o padrao State
   situacao inicial: PRE_MATRICULADA
   apos confirmar:   CONFIRMADA
   apos trancar:     TRANCADA
   apos confirmar:   CONFIRMADA
   apos cancelar:    CANCELADA
   [erro] Nao e possivel confirmar uma matricula CANCELADA
   >> Cada estado conhece suas transicoes;
      a matricula so repassa a chamada

4) O estado novo: Concluida
   situacao inicial: PRE_MATRICULADA
   apos confirmar:   CONFIRMADA
   apos concluir:    CONCLUIDA
   [erro] Nao e possivel cancelar uma matricula CONCLUIDA
```

## Os três deslizes que existiam no código inicial

| # | Deslize | Onde estava | Por que era um problema | Como foi resolvido |
|---|---|---|---|---|
| 1 | **O rito de emissão duplicado** | `HistoricoEscolar`, `DeclaracaoVinculo` e `AtestadoFrequencia` | Cinco passos — validar, cabeçalho, corpo, rodapé, registrar — e **quatro deles eram idênticos** nas três classes. Só o corpo variava, e um quarto documento seria uma quarta cópia. | **Template Method**: os passos fixos foram para `DocumentoAcademico`, e cada documento implementa apenas `corpo` e `tipo`. A falta de assinatura do atestado virou o gancho `exigeAssinatura()`. |
| 2 | **Os passos fora de ordem** | `DeclaracaoVinculo.emitir` | O **registro no log acontecia antes da validação**: um aluno irregular deixava no log um documento que nunca foi emitido. | **Template Method**: a ordem dos passos fica no método `emitir`, que é `final`, então nenhuma subclasse consegue invertê-la. |
| 3 | **O ciclo de vida em condicionais sobre uma String** | `Matricula.confirmar`, `trancar` e `cancelar` | As regras de transição estavam espalhadas por três métodos, e um erro de digitação na situação só apareceria com o programa rodando. | **State**: cada situação virou uma classe que implementa `EstadoMatricula` e decide as próprias transições; a `Matricula` só repassa a ação ao estado atual. |

### Etapa 1 — Localização da inversão

#### Rito duplicado
- Os 5 passos são validar, cabeçalho, corpo, rodapé e registrar.
- Quatro dos cinco passos são iguais nas três classes; só o `corpo` varia de um documento para outro.
- Há duas exceções escondidas no código copiado: no `rodape`, o `AtestadoFrequencia.java` não tem a linha de assinatura; e o `registrar`, apesar de ser igual, está no lugar errado na `DeclaracaoVinculo.java`.

#### O ponto exato da inversão
- Arquivo: `src/siga/DeclaracaoVinculo.java`, linha 13.
- Código onde ocorre:
```java
public String emitir(Aluno aluno) {
    LogAcademico.registrar("DECLARACAO", aluno);   // fora de ordem
```
- O registro no log é a primeira instrução do método e vem antes da validação (linhas 15–18). No `HistoricoEscolar` e no `AtestadoFrequencia`, o registro é o último passo, logo antes do `return`.

#### A consequência observável
- Saída do programa:
```
2) A declaracao de vinculo, com os passos fora de ordem
   Emitindo para um aluno IRREGULAR:
   [log] DECLARACAO emitido para 2024002
   [erro] Aluno irregular: 2024002
   >> DESLIZE 2: repare na linha de log acima do erro.
      O documento nao foi emitido, mas ja consta no log.
      Registros no log: [HISTORICO - 2024001, ATESTADO - 2024001, DECLARACAO - 2024002]
```
- A linha `[log] DECLARACAO...` aparece antes da linha `[erro] Aluno`. 
- Na lista final do log aparece `DECLARACAO - 2024002`, um documento que nunca foi emitido.

#### Por que isso aconteceu
Cada classe tem sua própria cópia do rito, e falta um lugar único que diga qual é a ordem dos passos. Como são cópias, elas podem ficar diferentes: nada impede que alguém troque a ordem em uma delas, e foi o que aconteceu na `DeclaracaoVinculo`. O ideal é usar o Template Method, para que a ordem dos passos fique em um único método da classe-mãe. Como esse método é `final`, nenhuma subclasse pode alterá-lo, e cada documento só preenche o corpo.

### Etapa 5 - Medição do ganho

O estado `Concluida`, alcançável somente a partir de `Confirmada` pela nova ação `concluir()`, custou **4 arquivos**, além da `Main`, que só ganhou a demonstração:

| Arquivo | O que mudou |
|---|---|
| `Concluida.java` | arquivo novo, com as regras do estado |
| `Confirmada.java` | passa a oferecer a transição para `Concluida` |
| `EstadoMatricula.java` | declara `concluir` como método `default`, que lança erro |
| `Matricula.java` | ganha `concluir()`, com uma linha e sem `if` |

## Critério de sucesso

Ao final, verificou-se que:

- (a) o texto do cabeçalho e o do rodapé existem em **um único lugar**, a `DocumentoAcademico`;
- (b) o método-template `emitir` é **`final`**, então nenhuma subclasse consegue inverter a ordem dos passos;
- (c) emitir um documento para um aluno irregular **não deixa rastro no log**, como mostra a seção 2 da saída acima;
- (d) a palavra `if` não aparece em nenhum dos métodos públicos de `Matricula`;
- (e) as regras do estado `Concluida` ficaram em **dois arquivos**, a classe nova e a `Confirmada`; os outros dois arquivos alterados só declaram a nova ação `concluir`.
