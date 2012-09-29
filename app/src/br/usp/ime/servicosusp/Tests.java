package br.usp.ime.servicosusp;

import android.database.sqlite.SQLiteDatabase;

public class Tests {
	static void insertData(){
		
		SQLiteDatabase db = servicosUsp.dbHelper.getWritableDatabase();
		//Create Tables
		
		db.execSQL("create table categoria(codctg integer primary key, nomctg varchar(20))");
		db.execSQL("create table local(codloc integer primary key, sglloc varchar(30), nomloc  varchar(40), endloc varchar(100), cep varchar(10), urlphoto1 varchar(100), urlphoto2 varchar(100), urlphoto3 varchar(100), posLat INTEGER, posLong INTEGER  )");
		db.execSQL("create table servico(codsrv integer primary key, codctg integer, codloc integer, nomsrv varchar(80), descsrv varchar(150), keywords varchar(30), cplloc varchar(30), tel1 varchar(15), tel2 varchar(15), email varchar(60), link varchar(60) )");		
		
		//Table Categorias

		db.execSQL("insert into categoria(codctg,nomctg) values(1,'Ensino')");
		db.execSQL("insert into categoria(codctg,nomctg) values(2,'Restaurantes')");
		db.execSQL("insert into categoria(codctg,nomctg) values(3,'Transportes')");
		db.execSQL("insert into categoria(codctg,nomctg) values(4,'Esporte&Lazer')");
		db.execSQL("insert into categoria(codctg,nomctg) values(5,'Bibliotecas')");
		db.execSQL("insert into categoria(codctg,nomctg) values(6,'Artes')");
		db.execSQL("insert into categoria(codctg,nomctg) values(7,'Auditórios')");

		//Table Local
		// Ghost building - hosting the services not assigned to any building; must be included in the DB
		// It must have CODLOC = 0 !!!
		db.execSQL("insert into local(codloc,sglloc,nomloc,endloc,cep, urlphoto1,urlphoto2,urlphoto3,posLat,posLong) values(0,'-','<Sem predio>', '','','','','','','')");		
		
		// other locals
		db.execSQL("insert into local(codloc,sglloc,nomloc,endloc,cep, urlphoto1,urlphoto2,urlphoto3,posLat,posLong) values(1,'USP/P1','USP Portaria 1', 'Cruzamento da Av. Afranio Peixoto com Rua Alvarenga.Cidade Universitaria-SP-Brasil','05508-090','http://www.usp.br/cocesp/imagens/noticias/P1_2.JPG','','','-23565141','-46712480')");
		db.execSQL("insert into local(codloc,sglloc,nomloc,endloc,cep, urlphoto1,urlphoto2,urlphoto3,posLat,posLong) " +
				"values(2,'USP/P2','USP Portaria 2', 'Cidade Universitaria-SP-Brasil'," +
				"'05508-090','http://cbk3.google.com/cbk?output=thumbnail&cb_client=maps_sv&thumb=2&thumbfov=120&ll=-23.550702,-46.732247&cbll=-23.550696,-46.732293&thumbpegman=1&w=300&h=118','','','-23550664','-46732221' )");
		db.execSQL("insert into local(codloc,sglloc,nomloc,endloc,cep, urlphoto1,urlphoto2,urlphoto3,posLat,posLong) " +
				"values(3,'USP/P3','USP Portaria 3', 'Cidade Universitaria-SP-Brasil'," +
				"'05508-090','http://www.usp.br/cocesp/imagens/noticias/P%203.JPG','','','-23569114','-46741276')");
	
		db.execSQL("insert into local(codloc,sglloc,nomloc,endloc,cep, urlphoto1,urlphoto2,urlphoto3,posLat,posLong) " +
				"values(4,'IME','Instituto de Matematica e Estatistica', 'Rua do Matao, 1010 - Cidade Universitaria-SP-Brasil'," +
				"'05508-090','http://upload.wikimedia.org/wikipedia/commons/9/90/Ime_usp.JPG','http://www.ime.usp.br/~act/ime.jpg',"+
				"'http://lh4.ggpht.com/andre.java/SPK_-ZKsLgI/AAAAAAAAAjY/ujraBFr8t24/DSC01291.JPG','-23559391','-46732067' )");
	
		db.execSQL("insert into local(codloc,sglloc,nomloc,endloc,cep, urlphoto1,urlphoto2,urlphoto3,posLat,posLong) " +
				"values(5,'IOUSP','Instituto Oceanografico', 'PraĂ§a do Oceanografico, 191 Cidade Universitaria-SP-Brasil'," +
				"'05508-120','http://www.io.usp.br/show_image.php?id=99','','','-23560881','-46732019')");		
		db.execSQL("insert into local(codloc,sglloc,nomloc,endloc,cep, urlphoto1,urlphoto2,urlphoto3,posLat,posLong) " +
				"values(7,'COSEAS','Coordenadoria de Assistencia Social', 'Rua do Anfiteatro, 295 Cidade Universitaria-SP-Brasil'," +
				"' 05508-040','','','','-23558945','-46721213' )");
		db.execSQL("insert into local(codloc,sglloc,nomloc,endloc,cep, urlphoto1,urlphoto2,urlphoto3,posLat,posLong) " +
				"values(8,'IQUSP','Instituto Quimica', 'Av. Lineu Prestes, 748 - Instituto de Quimicas - Cidade Universitaria-SP-Brasil'," +
				"'','','','','-23565115','-46725554')");		
		db.execSQL("insert into local(codloc,sglloc,nomloc,endloc,cep, urlphoto1,urlphoto2,urlphoto3,posLat,posLong) " +
				"values(9,'IFUSP','Instituto Fisica', 'Rua do Matao, 187, Travessa R, Cidade Universitaria-SP-Brasil'," +
				"'05508-090','','','','-23560434','-46734760')");		
		db.execSQL("insert into local(codloc,sglloc,nomloc,endloc,cep, urlphoto1,urlphoto2,urlphoto3,posLat,posLong) " +
				"values(10,'FSP','Saude Publica', 'Av. Dr. Arnaldo, 715 - Faculdade de Saude Publica, Cerqueira Cesar-SP-Brasil'," +
				"'','','','','-23553138','-46673357')");		
		db.execSQL("insert into local(codloc,sglloc,nomloc,endloc,cep, urlphoto1,urlphoto2,urlphoto3,posLat,posLong) " +
				"values(11,'COCESP','Coordenadoria do Campus da Capital ESP', 'Av. Prof. Almeida Prado, 1280 Butanta - SP Brasil'," +
				"'','','','','-23558916','-46738336')");	
		db.execSQL("insert into local(codloc,sglloc,nomloc,endloc,cep, urlphoto1,urlphoto2,urlphoto3,posLat,posLong) " +
				"values(12,'FD','Faculdade de Direito', 'Rua Riachuelo, 194 - Faculdade de Direito - Largo Sao Francisco - SP Brasil'," +
				"'','','','','-23540803','-46637192')");	
		db.execSQL("insert into local(codloc,sglloc,nomloc,endloc,cep, urlphoto1,urlphoto2,urlphoto3,posLat,posLong) " +
				"values(13,'EE','Escola de Enfermagem','Avenida Doutor EnÄ‚Â©as de Carvalho Aguiar, 419 - Cerqueira Cesar - SP Brasil'," +
				"'','','','','-23554730','-46671009')");	
		db.execSQL("insert into local(codloc,sglloc,nomloc,endloc,cep, urlphoto1,urlphoto2,urlphoto3,posLat,posLong) " +
				"values(14,'Clube Prof.','Clube Professores','Rua do Matao, 801 - Clube dos Professores - Cidade Universitaria - SP Brasil'," +
				"'','','','','-23559124','-46736972')");	
		db.execSQL("insert into local(codloc,sglloc,nomloc,endloc,cep, urlphoto1,urlphoto2,urlphoto3,posLat,posLong) " +
				"values(15,'MAC','Museu de Arte Contemporanea da USP','Rua da PraĂ§a do RelÄ‚ogio, 160 - Cidade Universitaria - SP Brasil'," +
				"'','http://mw2.google.com/mw-panoramio/photos/medium/3727718.jpg','','','-23559948','-46722178')");
		db.execSQL("insert into local(codloc,sglloc,nomloc,endloc,cep, urlphoto1,urlphoto2,urlphoto3,posLat,posLong) " +
				"values(16,'FFLCH','Faculdade de Filosofia Letras E Ciencias Humanas - USP','Av. Prof. Lineu Prestes - travessa 12, nĂ‚Ĺź 350 - Cidade UniversitÄ‚Ë‡ria - SP Brasil'," +
				"'','','','','-23562681','-46725930')");
		db.execSQL("insert into local(codloc,sglloc,nomloc,endloc,cep, urlphoto1,urlphoto2,urlphoto3,posLat,posLong) " +
				"values(17,'Casa C.Japonesa','Casa da Cultura Japonesa','Av. Prof. Lineu Prestes, n. 159 (Predio da Casa de Cultura Japonesa)  - Cidade Universitaria - SP Brasil'," +
				"'','','','','-23563010','-46719761' )");
		db.execSQL("insert into local(codloc,sglloc,nomloc,endloc,cep, urlphoto1,urlphoto2,urlphoto3,posLat,posLong) " +
				"values(18,'CEPEUSP','Centro de Praticas Esportivas da USP','PraĂ§a 02, Prof. Rubiao Meira, 61 - Cidade Universitaria - SP Brasil'," +
				"'','http://www.cepe.usp.br/cepe//site/files/banners/cepeusp.jpg','','','-23561279','-46718028')");
		db.execSQL("insert into local(codloc,sglloc,nomloc,endloc,cep, urlphoto1,urlphoto2,urlphoto3,posLat,posLong) " +
				"values(19,'Raia','CEPEUSP - Raia Olimpica ','Cidade Universitaria - SP Brasil'," +
				"'','http://www.cepe.usp.br/cepe//site/files/banners/cepeusp.jpg','','','-23556244','-46721537')");
		db.execSQL("insert into local(codloc,sglloc,nomloc,endloc,cep, urlphoto1,urlphoto2,urlphoto3,posLat,posLong) " +
				"values(20,'C.Guarnieri','Anfiteatro Camargo Guarnieri','Rua do Anfiteatro, 109 - Cidade Universitaria - SP Brasil'," +
				"'','','','http://www.usp.br/coseas/COSEASHP/COSEAS2010_RSP.html','-23557941','-46720995')");
		db.execSQL("insert into local(codloc,sglloc,nomloc,endloc,cep, urlphoto1,urlphoto2,urlphoto3,posLat,posLong) " +
				"values(21,'Colmeia','Colmeia - Favo 17 - AuditĂłrio e Cinusp - Pro-Reitoria de Cultura','Rua do Anfiteatro, 300 - Cidade Universitaria - SP Brasil'," +
				"'','','','','-23558526','-46721488')");
		db.execSQL("insert into local(codloc,sglloc,nomloc,endloc,cep, urlphoto1,urlphoto2,urlphoto3,posLat,posLong) " +
				"values(22,'EACH','Escola de Artes e CiĂŞncia Humanas - USPLeste','USP Leste - SP Brasil'," +
				"'','','','','-23483873','-46496598')");
		db.execSQL("insert into local(codloc,sglloc,nomloc,endloc,cep, urlphoto1,urlphoto2,urlphoto3,posLat,posLong) " +
				"values(23,'ECA','Escola de ComunicaĂ§ĂŁo e Artes','Cidade Universitaria - SP Brasil'," +
				"'','','','','-23557914','-46726951')");
		db.execSQL("insert into local(codloc,sglloc,nomloc,endloc,cep, urlphoto1,urlphoto2,urlphoto3,posLat,posLong) " +
				"values(24,'EEFE','Escola de EducaĂ§ĂŁo Fisica e Esportes','Cidade Universitaria - SP Brasil'," +
				"'','','','','-23563446','-46713148')");
		db.execSQL("insert into local(codloc,sglloc,nomloc,endloc,cep, urlphoto1,urlphoto2,urlphoto3,posLat,posLong) " +
				"values(25,'EP Poli','Escola Politecnica USP','Cidade Universitaria - SP Brasil'," +
				"'','','','','-23554875','-46729896')");
		db.execSQL("insert into local(codloc,sglloc,nomloc,endloc,cep, urlphoto1,urlphoto2,urlphoto3,posLat,posLong) " +
				"values(26,'FAU','Faculdade de Arquitetura e Urbanismo USP','Cidade Universitaria - SP Brasil'," +
				"'','','','','-23560238','-46730331')");
		db.execSQL("insert into local(codloc,sglloc,nomloc,endloc,cep, urlphoto1,urlphoto2,urlphoto3,posLat,posLong) " +
				"values(27,'FCF','Faculdade de CiĂŞncias Farmaceuticas USP','SP Brasil'," +
				"'','','','','-23564676','-46725132')");
		db.execSQL("insert into local(codloc,sglloc,nomloc,endloc,cep, urlphoto1,urlphoto2,urlphoto3,posLat,posLong) " +
				"values(28,'FEA','Faculdade de Economia e AdministraĂ§ĂŁo USP','Cidade Universitaria - SP Brasil'," +
				"'','','','','-23559168','-46729274')");
		db.execSQL("insert into local(codloc,sglloc,nomloc,endloc,cep, urlphoto1,urlphoto2,urlphoto3,posLat,posLong) " +
				"values(29,'FM','Faculdade de Medicina USP','SP Brasil'," +
				"'','','','','-23556953','-46669377')");
		db.execSQL("insert into local(codloc,sglloc,nomloc,endloc,cep, urlphoto1,urlphoto2,urlphoto3,posLat,posLong) " +
				"values(30,'FMVZ','Faculdade de Medicina Veterinaria e Zootecnia USP','Cidade Universitaria - SP Brasil'," +
				"'','','','','-23569524','-46740622')");
		db.execSQL("insert into local(codloc,sglloc,nomloc,endloc,cep, urlphoto1,urlphoto2,urlphoto3,posLat,posLong) " +
				"values(31,'FO','Faculdade de Odontologia USP','Rua do Anfiteatro, 300 - Cidade Universitaria - SP Brasil'," +
				"'','','','','-23564958','-46739303')");
		db.execSQL("insert into local(codloc,sglloc,nomloc,endloc,cep, urlphoto1,urlphoto2,urlphoto3,posLat,posLong) " +
				"values(32,'IAG','Instituto de Astronomia, Geofisica e Ciencias Atmosfericas','Cidade Universitaria - SP Brasil'," +
				"'','','','','-23559576','-46734437')");
		db.execSQL("insert into local(codloc,sglloc,nomloc,endloc,cep, urlphoto1,urlphoto2,urlphoto3,posLat,posLong) " +
				"values(33,'IB','Instituto de Biociencias','Cidade Universitaria - SP Brasil'," +
				"'','','','','-23566205','-46730403')");
		db.execSQL("insert into local(codloc,sglloc,nomloc,endloc,cep, urlphoto1,urlphoto2,urlphoto3,posLat,posLong) " +
				"values(34,'ICB','Instituto de Ciencias Biomedicas','Cidade Universitaria - SP Brasil'," +
				"'','','','','-23567724','-46732063')");
		db.execSQL("insert into local(codloc,sglloc,nomloc,endloc,cep, urlphoto1,urlphoto2,urlphoto3,posLat,posLong) " +
				"values(35,'IEE','Instituto de Eletrotecnica e Energia','Cidade Universitaria - SP Brasil'," +
				"'','','','','-23557282','-46733917')");
		db.execSQL("insert into local(codloc,sglloc,nomloc,endloc,cep, urlphoto1,urlphoto2,urlphoto3,posLat,posLong) " +
				"values(36,'IEA','Instituto de Estudos AvanĂ§ados','Cidade Universitaria - SP Brasil'," +
				"'','','','','-23567648','-46733074')");
		db.execSQL("insert into local(codloc,sglloc,nomloc,endloc,cep, urlphoto1,urlphoto2,urlphoto3,posLat,posLong) " +
				"values(37,'IEB','Instituto de Estudos Brasileiros','Cidade Universitaria - SP Brasil'," +
				"'','','','','-23559099','-46719773')");
		db.execSQL("insert into local(codloc,sglloc,nomloc,endloc,cep, urlphoto1,urlphoto2,urlphoto3,posLat,posLong) " +
				"values(38,'Colmeia','Colmeia - Favo 17 - AuditĂłrio e Cinusp - Pro-Reitoria de Cultura','Rua do Anfiteatro, 300 - Cidade Universitaria - SP Brasil'," +
				"'','','','','-23558526','-46721488')");
		
			
		//Table Servicos
		
		//Ensino
		db.execSQL("insert into servico(codsrv, codctg, codloc, nomsrv, descsrv, keywords, cplloc, tel1,tel2, email,link) values(1,1,4,'Matricula PosGraduacao IME','Serviço de Pos Graduação - Matricula de alunos.','Matricula IME PosGraduação','Bloco B, sala X, andar terreo','3091-6135','','ade@ime.usp.br','http://www.ime.usp.br/')");
						
		db.execSQL("insert into servico(codsrv, codctg, codloc, nomsrv, descsrv, keywords, cplloc, tel1,tel2, email,link) " +
				"values(2,1,5,'Matricula PosGraduacao IO','ServiÄ‚Â§o de Pos GraduaÄ‚Â§ao - Matricula de alunos.', "+
				"'Matricula IO PosGraduaÄ‚Â§ao','andar terreo','(11) 3091-6528','','cpg-io@usp.br','http://www.io.usp.br/')");
				
		db.execSQL("insert into servico(codsrv, codctg, codloc, nomsrv, descsrv, keywords, cplloc, tel1,tel2, email,link) " +
				"values(3,1,5,'Matricula Graduacao IO','ServiÄ‚Â§o de GraduaÄ‚Â§ao - Matricula de Alunos de GraduaÄ‚Â§ao'," +
				"'Matricula IO GraduaÄ‚Â§ao','andar terreo','','','','http://www.io.usp.br/')");
		
		db.execSQL("insert into servico(codsrv, codctg, codloc, nomsrv, descsrv, keywords, cplloc, tel1,tel2, email,link) " +
				"values(4,1,17,'Centro de LÄ‚Â­nguas FFLCH','Cursos de Lingua Estrangeira e Portugues.',"+
				"'CEPE raia olimpica','','(11)3091-2416','','clprofic@usp.br','http://clinguas.fflch.usp.br/')");
	
		//Restaurantes
		//Central, Quimica, Fisica
	/*
		db.execSQL("insert into servico(codsrv, codctg, codloc, nomsrv, descsrv, keywords, cplloc, tel1,tel2, email,link) " +
					"values(5,2,7,'Restaurante Central','Restaurante Central para Comunidade USP.'," +
					"'Restaurante bandejao COSEAS','http://www.usp.br/coseas/COSEASHP/ALM/Rest%20Central/DSC04044.JPG','','','','http://www.usp.br/coseas/cardapio.html')");
		db.execSQL("insert into servico(codsrv, codctg, codloc, nomsrv, descsrv, keywords, cplloc, tel1,tel2, email,link) " +
					"values(6,2,8,'Restaurante Quimica','Restaurante da Quimica para  Comunidade USP.'," +
					"'Restaurante bandejao Quimica','','','','','http://www.usp.br/coseas/cardapioquimica.html')");
		db.execSQL("insert into servico(codsrv, codctg, codloc, nomsrv, descsrv, keywords, cplloc, tel1,tel2, email,link) " +
					"values(7,2,9,'Restaurante Fisica','Restaurante da Fisica para  Comunidade USP.'," +
					"'Restaurante bandejao Fisica','','','','','http://www.usp.br/coseas/cardapiofisica.html')");
		db.execSQL("insert into servico(codsrv, codctg, codloc, nomsrv, descsrv, keywords, cplloc, tel1,tel2, email,link) " +
					"values(8,2,10,'Restaurante FSP','Restaurante da Faculdade de Saude Publica  Comunidade USP.'," +
					"'Restaurante bandejao Saude','','','','','')");
		db.execSQL("insert into servico(codsrv, codctg, codloc, nomsrv, descsrv, keywords, cplloc, tel1,tel2, email,link) " +
					"values(9,2,11,'Restaurante COCESP','Restaurante da COCESP para  Comunidade USP.'," +
					"'Restaurante bandejao Prefeitura','','','','','http://www.usp.br/coseas/cardcocesp.html')");
		db.execSQL("insert into servico(codsrv, codctg, codloc, nomsrv, descsrv, keywords, cplloc, tel1,tel2, email,link) " +
					"values(10,2,14,'Restaurante Clube Professores','Restaurante Clube dos Professores para  Comunidade USP.'," +
					"'Restaurante professores docente','','','','','http://www.usp.br/coseas/carddoc.html')");
	*/
		
		// Transporte - 3
		
		db.execSQL("insert into servico(codsrv, codctg, codloc, nomsrv, descsrv, keywords, cplloc, tel1,tel2, email,link) " +
				"values(10,3,0,'Bilhete USP','Este bilhete permiti para usar os Circulars em grata. Para receber Bilhete USP" + 
				" perguntar na secretaria da seu programacao.','Bilhete USP Onibus Circular','','','','','')"); 		
		db.execSQL("insert into servico(codsrv, codctg, codloc, nomsrv, descsrv, keywords, cplloc, tel1,tel2, email,link) " +
				"values(11,3,7,'Bilhete Unico','Sobre o Bilhete Unico.','Bilhete transporte COSEAS','','','','','')"); 
		
				
		// Esporte & Lazer - 4
				
		db.execSQL("insert into servico(codsrv, codctg, codloc, nomsrv, descsrv, keywords, cplloc, tel1,tel2, email,link) " +
				"values(12,4,18,'Esporte&lazer CEPE','Praticas esportivas, cursos e lazer. Ä‚ďż˝rea com quadras de esportes e piscina para funcionÄ‚Ë‡rios, alunos, docentes e comunidade externa.'," +
				"'CEPE esportes piscina','','','','','http://www.cepe.usp.br/site/')");
		db.execSQL("insert into servico(codsrv, codctg, codloc, nomsrv, descsrv, keywords, cplloc, tel1,tel2, email,link) " +
				"values(13,4,19,'Esporte&lazer Raia Olimpica','A Raia OlÄ‚Â­mpica, paralela Ä‚Â  marginal do Rio Pinheiros, Ä‚Â© um conjunto esportivo destinado Ä‚Â  prÄ‚Ë‡tica do remo e da canoagem. "+"" +
				"Conta com vestiÄ‚Ë‡rios, sala de musculaÄ‚Â§Ä‚Ĺ�o, pista rÄ‚Ĺźstica, barcos e garagem.',"+
				"'CEPE raia olimpica','','','','','http://www.cepe.usp.br/site/')");
		
		// Biblioteca-5
		
		db.execSQL("insert into servico(codsrv, codctg, codloc, nomsrv, descsrv, keywords, cplloc, tel1,tel2, email,link) " +
				"values(14,5,16,'Biblioteca FLORESTAN FERNANDES FFLCH','ServiÄ‚Â§o de Biblioteca.',"+
				"'CEPE raia olimpica','','(11)3091-4377','','saufflch@usp.br','http://biblioteca.fflch.usp.br/')");
		db.execSQL("insert into servico(codsrv, codctg, codloc, nomsrv, descsrv, keywords, cplloc, tel1,tel2, email,link) " +
				"values(15,5,4,'Biblioteca Prof.Carlos Benjamin de Lyra IME','ServiÄ‚Â§o de Biblioteca.',"+
				"'Ime biblioteca matematica','','(11)3091-6109','','bib@ime.usp.br','http://www.ime.usp.br/bib')"); 
		
		//Artes - 6
		
		db.execSQL("insert into servico(codsrv, codctg, codloc, nomsrv, descsrv, keywords, cplloc, tel1,tel2, email,link) " +
				"values(16,6,15,'Artes - MAC-Museu de Arte Contemporanea','Museu de Arte Contemporanea. EspaÄ‚Â§o para exposiÄ‚Â§Ä‚Âµes de obras de arte.',"+
				"'MAC museu arte','','','','','http://www.mac.usp.br/mac/')");
		db.execSQL("insert into servico(codsrv, codctg, codloc, nomsrv, descsrv, keywords, cplloc, tel1,tel2, email,link) " +
				"values(17,6,21,'Artes - CoralUsp/OSUSP','Coral e Orquestra da USP. A participaÄ‚Â§ao no Coral e cursos de extensÄ‚Ĺ�o Ä‚Â© gratuita para a comunidade USP e publico externo.',"+
					"'PRCEU coral orquestra','','(11)3091-5071','','coralusp@usp.br','http://www.usp.br/coralusp')"); 
		
		// AuditÄ‚Ĺ‚rios
		
		db.execSQL("insert into servico(codsrv, codctg, codloc, nomsrv, descsrv, keywords, cplloc, tel1,tel2, email,link) " +
				"values(18,7,20,'Auditorio - Anfiteatro Camargo Guarnieri','Anfiteatro para concertos, congressos e eventos.',"+
				"'musica auditorio arte','','','','','www.usp.br')");
		db.execSQL("insert into servico(codsrv, codctg, codloc, nomsrv, descsrv, keywords, cplloc, tel1,tel2, email,link) " +
				"values(19,7,21,'Auditorio - Favo 17','AuditÄ‚Ĺ‚rio para seminarios e palestras.',"+
				"'PRCEU coral orquestra','','(11)3091-5071','','coralusp@usp.br','http://www.usp.br/coralusp')"); 

	}
}
