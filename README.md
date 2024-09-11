# Resposta a [Entregável Parcial 3](https://ava.ufca.edu.br/mod/assign/view.php?id=9237): Classes implementadas em Java<BR/>

<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://drive.google.com/drive/folders/16Zi184nW16NXDW3KQkFen8y63UBEVv2b">
    <img src="https://github.com/lucns-ufca/app-biblioteca/blob/master/app/src/main/res/drawable/app_icon.png" alt="Logo" width="120" height="120">
  </a>

  <h3 align="center">Minha Biblioteca</h3>
  <a href="https://drive.google.com/file/d/11xx9n1JoI3Vo5VVEpwJac5RjYvsVtgHn/view?usp=sharing" target="_blank">APK</a>
</div>
<BR/>

## Contribuintes:<BR/>
Otavio Leopoldino Machado Junior - 2023011466 <BR/>RAKEL TORRES CARVALHO - 2023011493<BR/>CLECIANO FREITAS FERREIRA - 2023011288<BR/>Carlos Bruno Sousa de Araújo - 2023011250<BR/>Lívia Nunes de Melo - 2023011386 <BR/>Lucas do Nascimento Souza - 2023011395<BR/><BR/>

## Descrição básica<br/>
* Foi desenvolvido um app de Biblioteca chamado Minha Biblioteca, onde é possivel criar varias contas de usuários, realizar login e assim ter acesso ao conteúdo do app.<br/>
* A primeira tela é um Splash Screen onde podemos ver algumas animações no icone do app, no texto da equipe atuante e na logo da UFCA.
* A próxima tela é a de Login, onde o suário poderá realizar o aceso ao conteúdo do app por meio de suas credenciais, e também é possível acessar a opção de criar uma nova conta.
* Já na tela principal é mostrado dados gerais sobre os estados da conta do usuário, como débitos por atraso em devoluções de livros, ultimos livros acessados via app, livros favoritos e as abas de conteúdos que fica no inferior da tela.
* Nas abas foram disponibilizadas varias opções de conteúdos, onde é possivel alternar entre telas por meio de um Slider/PagerView.
* Wireframe do [Entregável Parcial 2](https://www.figma.com/design/e8aBcmDhMgyiUdDOY5y9di/Entregavel-Parcial-2---Wireframe).
* [Video da apresentação do app.](https://youtu.be/Hi_pD8vylTg)
<br/><br/>

## Descrição técnica<br/>
* Foi usado técnicas especificas que podem ser consideradas até avançadas, para criação dos fragmentos nas telas iniciais Login e Nova Conta e também no Slider das telas principais.<br/> 
* O Slider é uma classe com extenção para a classe FrameLayout, que possibilita criar itens na tela com dimensoes maiores que a própria tela, facilitando criar um Scroll automatico que escorrega o conteúdo da tela ao clicar em um item da TabBar (Botões costumizados que ficam na parte inferior da tela).<br/>
* Não foi necessário uso de biblioteca.<br/>
* Todas as animações e itens especiais, não nativos do Android, foram criados apenas fazendo extenções de classes, como o Slider que é extendido ao FrameLayout para que fosse possivel criar telas paralelas horizontalmente.<br/>
* Na parte superior da tela principal(primeira aba), há uns cards onde o primeiro mostra o dado referente ao débito. Nesses cards são usados um HorizontalScrollView como Widget pai, para possibilitar o escorregamento dos mesmos, na tela. E os cards são apenas Widgets do tipo RelativeLayout com um background setado do tipo gradient, via tags do XML, para dar o efeito de coloração e parecer um card.<br/>
* Na lista da tela inicial foi usada a classe básica de listas que é a ListView tendo a classe costumizada CardListAdapter exxtendida a classe ArrayAdapter, como provedor de conteúdos para a lista.<br/>
* Existe uma classe chamada LibraryProvider que serve exclusivamente para suprir as variaveis da classe Library. Ela faz isso fazendo a varredura dos dados presentes na pasta assets, que é onde fica salvo o acervo de livros.
* O acervo de livros são organizados por meio de um arquivo chamado Books.json, que está dentro da pasta /assets/books/Books.json.
* O arquivo Books.json guarda dados dos livros, como titulo, descrição, data de lançamento, e o nome do arquivo da capa dos livros.
* As capas sao imagens que ficam na pasta /assets/books/covers.

## Screenshoots<br/>
Telas iniciais:<br/>
![Screenshot_20240902_201540_My Library](https://github.com/user-attachments/assets/92015c69-f75e-4bed-82a4-9bce26852830)
![Screenshot_20240902_160257_My Library](https://github.com/user-attachments/assets/fff43315-545d-4ea7-87eb-712b54c0567a)
![Screenshot_20240902_201603_My Library](https://github.com/user-attachments/assets/fdaac927-7ecd-4973-8423-e307be2edced)
![Screenshot_20240902_160702_My Library](https://github.com/user-attachments/assets/0f4be400-6fe9-4b1b-9a60-96170fe271c2)
