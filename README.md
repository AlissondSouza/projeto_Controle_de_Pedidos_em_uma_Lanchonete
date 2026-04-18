<h1 align="center">🍔 Sistema de Controle de Pedidos</h1>

<p align="center">
  Sistema completo para gerenciamento de pedidos em uma lanchonete para trabalho acadêmico.<br>
  Controle de produtos, pedidos e faturamento de forma simples e eficiente.
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white"/>
  <img src="https://img.shields.io/badge/Arquitetura-MVC-blue?style=for-the-badge"/>
  <img src="https://img.shields.io/badge/status-em%20desenvolvimento-yellow?style=for-the-badge"/>
  <img src="https://img.shields.io/badge/license-MIT-green?style=for-the-badge"/>
</p>

---

## ✨ Visão Geral

Este sistema foi desenvolvido para simular o funcionamento de uma **lanchonete**, permitindo:

- Gerenciamento de produtos do cardápio 🍟  
- Criação e controle de pedidos 🧾  
- Cálculo automático de valores 💰  
- Análise de faturamento diário 📊  

---

## 🚀 Funcionalidades

- 🍔 Cadastro de produtos  
- 🧾 Criação de pedidos  
- ➕ Adição de itens ao pedido  
- 🔢 Cálculo automático de subtotal e total  
- ✅ Finalização de pedidos  
- 📅 Consulta de pedidos por data  
- 💰 Cálculo de faturamento diário  

---

## 🧱 Modelagem do Sistema

### 🍔 Produto

| Campo | Tipo | Descrição |
|------|------|----------|
| id | int | Identificador único |
| nome | String | Nome do produto |
| descricao | String | Detalhes do produto |
| preco | double | Valor unitário |

---

### 🧾 Item do Pedido

| Campo | Tipo | Descrição |
|------|------|----------|
| produto | Produto | Referência ao produto |
| quantidade | int | Quantidade no pedido |
| subtotal | double | preço × quantidade |

---

### 📦 Pedido

| Campo | Tipo | Descrição |
|------|------|----------|
| id | int | Identificador |
| data | String | Data (dd/MM/yyyy) |
| itens | List<ItemPedido> | Lista de itens |
| total | double | Soma dos subtotais |
| finalizado | boolean | Status do pedido |

---

## 📏 Regras de Negócio

- ✅ Produto deve ter nome  
- ❌ Preço não pode ser negativo  
- 🔢 Total calculado automaticamente  
- ⚠️ Pedido precisa de itens para finalizar  
- 📅 Consulta de pedidos por data  
- 💰 Faturamento diário automático  
- 🔢 Quantidade deve ser maior que zero  

---

## 🛠️ Tecnologias
- ☕ Java
- 🧩 POO (Programação Orientada a Objetos)
- 🏗️ Arquitetura MVC

---

## 🧠 Arquitetura
🔹 Projeto 1
- Estrutura simples
- Entrada via Scanner
- Ideal para aprendizado

🔹 Projeto 2 (MVC)
- Model → Dados
- Repository → Persistência
- Controller → Regras
- View → Interface

---

## 🗂️ Estrutura do Projeto

```bash
lanchonete/
├── README.md
├── projeto1/          # Versão simples (Scanner)
│   └── src/main/java/lanchonete/
│       ├── Produto.java
│       ├── ItemPedido.java
│       ├── Pedido.java
│       ├── SistemaLanchonete.java
│       └── Main.java
│
└── projeto2/          # Versão MVC
    └── src/main/java/lanchonete/
        ├── model/
        ├── repository/
        ├── controller/
        ├── view/
        └── Main.java
````
<p align="center"> <strong>Alisson Junior</strong><br> 💻 Desenvolvedor em evolução </p>
