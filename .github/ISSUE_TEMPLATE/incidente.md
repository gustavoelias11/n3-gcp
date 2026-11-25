---
name: Incidente em Produção
about: Registrar um incidente crítico em produção, decisão de rollback e plano de correção
title: '[INCIDENTE] '
labels: ['incident', 'bug']
---

## 🚨 Descrição do Defeito / Incidente
Descreva o problema observado em produção e o impacto nos usuários/operações do FestaGestor.

## 🏷️ Versão Afetada
- **Release afetada:** `vX.Y.Z`
- **Ambiente:** `production`

## ⏪ Decisão Tomada (Rollback)
- **Rollback executado para a tag:** `vX.Y.Z`
- **Execução do workflow Rollback:** (link)
- **Aprovador da mudança em produção:** @usuario (data/hora)

## 🛠️ Plano para Corrigir a `main`
1. Reproduzir o defeito em ambiente local e criar teste automatizado de regressão.
2. Abrir branch `fix/<numero-da-issue>-<descricao>` a partir da `main`.
3. Implementar correção, abrir PR com revisão do par e CI verde.
4. Realizar merge e publicar nova release patch (`vX.Y.(Z+1)`) via CD.
