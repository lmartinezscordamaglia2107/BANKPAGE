# BankPage 💳

O **BankPage** é um aplicativo mobile desenvolvido em **Kotlin** e **Jetpack Compose** no **Android Studio**. O projeto simula a interface principal (dashboard) de um aplicativo bancário moderno, apresentando informações de saldo, limite de crédito, atalhos para operações financeiras rápidas (como Pix, transferências e pagamentos) e um histórico detalhado de transações recentes.

Este projeto foi construído seguindo as melhores práticas do desenvolvimento moderno para Android, com uma arquitetura modular, limpa e focada em componentização.

---

## 🚀 Tecnologias Utilizadas

* **[Kotlin](https://kotlinlang.org/)** - Linguagem oficial e moderna para desenvolvimento Android.
* **[Jetpack Compose](https://developer.android.com/jetpack/compose)** - Toolkit moderno do Android para construção de telas de forma totalmente declarativa.
* **[Material Design 3](https://m3.material.io/)** - Sistema de design do Google para a criação de interfaces modernas e adaptáveis.
* **Arquitetura Limpa (Clean Architecture principles)** - Separação clara de responsabilidades entre as camadas de apresentação (UI) e de dados (Data/Model).

---

## 📦 Estrutura do Projeto

A organização de pastas do projeto foi pensada para facilitar a manutenção e escalabilidade:

Saída de código
README.md gerado com sucesso!

```text
com.seuusuario.bankpage/
│
├── data/           # Modelos de dados e lógica de dados (ex: transações, dados da conta)
│   └── Transaction.kt
│
├── ui/             # Componentes de interface e telas (Jetpack Compose)
│   ├── components/ # Widgets reutilizáveis do app
│   │   ├── BalanceCard.kt       # Card principal de saldo e limite
│   │   ├── ActionButtons.kt     # Carrossel horizontal de ações (Pix, Pagar, etc.)
│   │   └── TransactionList.kt   # Histórico vertical de transações recentes
│   ├── theme/      # Configurações globais de cores, tipografia e temas
│   └── HomeScreen.kt # Tela principal que organiza e exibe os componentes
│
└── MainActivity.kt # Ponto de entrada do aplicativo
