# LiterAlura
É um software de terminal que realiza buscas de livros na internet e salva as informações dos mesmos numa base de dados.

## 🔨 Funcionalidades do projeto
1. Consultar livros através da internet: Através da API Gutendex, o usuário pode fazer uma busca de livros sem nenhum filtro ou com base no título, nas opções de idioma a seguir:
  - Todos os idiomas disponíveis
  - Inglês
  - Português
  - Espanhol
2. Listagem dos livros buscados: Após realizar uma busca será retornada uma lista com todos os livros referentes a pesquisa em questão.
3. Listagem de Autores da busca: Com base nos resultados de uma pesquisa (mencionado na funcionalidade anterior), uma lista de autores será retornada se o usuário optar por:
  - Listar todos os autores
  - Listar autores vivos em determinado ano escolhido pelo usuário
4. Salvar livros no banco de dados: Com base nos resultados de uma pesquisa (mencionado na funcionalidade 2), o usuário pode optar por salvar:
  - Apenas um livro por vez
  - Vários livros por vez
  - Todos os livros
5. Consultar dados salvos no banco de dados: Através do banco de dados PostgreSQL, o usuário pode fazer uma filtragem de livros pelo título ou uma filtragem de autores desses livros:
  - Listar todos os livros salvos (com base nas opções de idioma da funcionalidade 1) e mostrar a quantidade dos mesmos
  - Listar todos os autores salvos se estiverem vivos (com base em um determinado ano escolhido pelo usuário) e mostrar a quantidade dos mesmos

✔️ Técnicas e tecnologias utilizadas
- Linguagem de programação Java
- Spring Framework
- [Gutendex API](https://gutendex.com/)
- Banco de dados PostgreSQL

📁 Acesso ao projeto
Você pode acessar o código fonte ou baixar o arquivo zip, ambas as formas são possíveis através da [página do github](https://github.com/PedroHenriqueMQ/Chellenge_LiterAlura-OracleNextEducation) do projeto.
