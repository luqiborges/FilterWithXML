package ProvaUnidade1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class Principal {

	public static void main(String args[]) throws ParserConfigurationException, IOException, SAXException{
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		org.w3c.dom.Document document = builder.parse("C:\\Users\\lsq\\Downloads\\dados.xml");
		
		Scanner leitura = new Scanner(System.in);
		List<Curso> cursos = new ArrayList<Curso>();
		int opcaoMenu = 0;
		
		while(opcaoMenu != 4) {
			System.out.println("\n--- MENU DE OPÇÕES ---");
			System.out.println("1. Pesquisar por texto");
			System.out.println("2. Pesquisar por número e texto");
			System.out.println("3. Listar todos os cursos");
			System.out.println("4. Sair");
			
			System.out.println("Digite a opção desejada: ");
			opcaoMenu = leitura.nextInt();
			leitura.nextLine();
			
			XPath caminho = XPathFactory.newInstance().newXPath();
			String expression = "";
			String nomeCurso;
			int cargaHoraria;
			
			cursos.clear();
			
			switch(opcaoMenu) {
			
				case 1:
					System.out.println("Digite o nome do curso que deseja pesquisar:");
					nomeCurso = leitura.nextLine();
					expression = "/universidade/curso[nome='" + nomeCurso + "']";
					break;
					
				case 2:
					System.out.println("Digite o nome do curso que deseja pesquisar:");
					nomeCurso = leitura.nextLine();
					System.out.println("Digite a carga horária maxima que deseja pesquisar, para o curso de " + nomeCurso);
					cargaHoraria = leitura.nextInt();
					leitura.nextLine();
					expression = "/universidade/curso[nome='" + nomeCurso + "' and ch<=" + Integer.toString(cargaHoraria) +  "]";
					break;
					
				case 3:
					expression = "/universidade/*";
					break;
					
				case 4:
					System.exit(0);
					break;
			}
			
			try {
				XPathExpression xPathExpression = caminho.compile(expression);
				NodeList lista = (NodeList) xPathExpression.evaluate(document, XPathConstants.NODESET);
				
				for(int i = 0; i < lista.getLength();i++) {
					 Node el = (Node) lista.item(i);
					 Element newElement = (Element) el;
					 String nome =  newElement.getElementsByTagName("nome").item(0).getTextContent();
					 String disciplina = newElement.getElementsByTagName("disciplina").item(0).getTextContent();
					 int iden = Integer.parseInt( newElement.getElementsByTagName("iden").item(0).getTextContent());
					 int ano = Integer.parseInt( newElement.getElementsByTagName("ano").item(0).getTextContent());
					 int ch = Integer.parseInt( newElement.getElementsByTagName("ch").item(0).getTextContent());
					 
					 Curso c = new Curso();
					 c.setIden(iden);
					 c.setAno(ano);
					 c.setDisciplina(disciplina);
					 c.setNome(nome);
					 c.setCh(ch);
					 cursos.add(c);
					 
				}
				
				for(int i = 0; i < cursos.size(); i++) {
					System.out.println("---");
					System.out.println("Identificador: " + cursos.get(i).iden);
					System.out.println("Ano: " + cursos.get(i).ano);
					System.out.println("Nome: " + cursos.get(i).nome);
					System.out.println("Disciplina: " + cursos.get(i).disciplina);
					System.out.println("CH: " + cursos.get(i).ch);
				}

				
			} catch (XPathExpressionException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
}
