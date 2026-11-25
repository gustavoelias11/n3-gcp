# FestaGestor

Sistema ERP para gestão de aluguel de artigos para festas (brinquedos e decorações), com controle de estoque, clientes, contratos de aluguel e dashboard gerencial.

## Arquitetura do Monorepo

- `festagestor-api/`: Backend REST em Java 21 + Spring Boot 4, MySQL e migrações versionadas com Flyway (`src/main/resources/db/migration`).
- `festagestor-web/`: Frontend SPA em React 19 + Vite + Tailwind CSS.
- `.github/`: Pipelines de CI (`ci.yml`), CD com versionamento semântico e aprovação em produção (`cd.yml`), Rollback (`rollback.yml`), templates de Issue/PR e `CODEOWNERS`.
- `docs/`: Plano de Gerenciamento de Configuração (`Plano_Ger_Conf_DevOps.pdf`).

## Fluxo de GCS (DevOps)

1. Abertura de Issue descrevendo a demanda ou incidente.
2. Criação de branch a partir da `main` (`feature/<id>-<descricao>` ou `fix/<id>-<descricao>`).
3. Abertura de Pull Request para `main` com título em Conventional Commits e referência `Closes #<id>`.
4. Execução automática do CI (testes JUnit 5 no backend, ESLint e build no frontend, validação de Conventional Commits) + revisão por par.
5. Após merge na `main`, o pipeline de CD gera automaticamente a tag/Release SemVer e aguarda aprovação manual no ambiente `production`.
6. Em incidentes críticos, o workflow `Rollback` promove uma Release anterior para `Latest` após aprovação no ambiente `production`, sem reescrever o histórico da `main`.
