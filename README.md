# Resposta a [Entregável Parcial 3](https://ava.ufca.edu.br/mod/assign/view.php?id=9237): Classes implementadas em Java<BR/>

<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://drive.google.com/drive/folders/16Zi184nW16NXDW3KQkFen8y63UBEVv2b">
    <img src="https://github.com/lucns-ufca/app-biblioteca/blob/master/app/src/main/res/drawable/app_icon.png" alt="Logo" width="120" height="120">
  </a>

  <h3 align="center">Minha Biblioteca</h3>
</div>
<BR/>

## Contribuintes:<BR/>
Otavio Leopoldino Machado Junior - 2023011466 <BR/>RAKEL TORRES CARVALHO - 2023011493<BR/>CLECIANO FREITAS FERREIRA - 2023011288<BR/>Carlos Bruno Sousa de Araújo - 2023011250<BR/>Lívia Nunes de Melo - 2023011386 <BR/>Lucas do Nascimento Souza - 2023011395<BR/><BR/>

## Descrição básica<br/>
Foi desenvolvido um app de Biblioteca chamado Minha Biblioteca, onde é possivel criar varias contas de usuários, realizar login e assim ter acesso ao conteúdo do app.<br/>
* A primeira tela é um Splash Screen onde podemos ver algumas animações no icone do app, no texto da equipe atuante e na logo da UFCA.
* A próxima tela é a de Login, onde o suário poderá realizar o aceso ao conteúdo do app por meio de suas credenciais, onde nela também é possível acessar a opção de criar uma nova conta.
* Já na tela principal é mostrado dados gerais sobre os estados da conta do usuário, como débitos por atraso em devoluções de livros, ultimos livros acessados via app, livros favoritos e as abas de conteúdos que fica no inferior da tela.
* Nas abas foram disponibilizadas varias opções de conteúdos, onde é possivel alternar entre telas por meio de um Slider/PagerView.
<br/><br/>

## Descrição técnica<br/>
Foi usado técnicas especificas que podem ser consideradas até avançadas, para criação dos fragmentos nas telas iniciais Login e Nova Conta e também no Slider das telas principais.<br/> 
O Slider é uma classe com extenção para a classe FrameLayout, que possibilita criar itens na tela com dimensoes maiores que a própria tela, possibilitando criar assim, um Scroll automatico que escorrega o conteúdo da tela ao clicar em um item da TabBar (Botões costumizados que ficam na parte inferior da tela).<br/>
Não foi necessário uso de biblioteca.<br/>
Todas as animções e itens especiais, não nativos, foram criados usando as classes nativas do Android, apenas fazendo extenções de classes nativas como o Slider que é extendido ao FrameLayout.<br/>
Na parte superior da tela principal(primeira aba), há uns cards onde o primeiro mostra o dado referente ao débito. Nos cards são usados um HorizontalScrollView para possibilitar o escorregamento dos mesmos, na tela. E os cards são apenas RelativeLayout com um background setado do tipo gradient, para dar o efeito de coloração e parecer um card.<br/>
Na lista da tela inicial foi usada a classe básica de listas que é a ListView.<br/>

## Screenshoots<br/>
Telas iniciais:<br/>

<div align="center">
  <img src="https://github.com/user-attachments/assets/0a729623-32c4-477c-814c-e90c583afee5">
  <img src="https://github.com/user-attachments/assets/d4ee1336-1a5f-458e-b88a-fd64fa4cd432">
  <br/>
  <img src="https://github.com/user-attachments/assets/5c804b93-22b2-453d-9e88-3ff7877a1b9d">
  <img src="https://github.com/user-attachments/assets/b1314f8b-b39a-4e3a-80b1-50c14feddb55">
</div>
