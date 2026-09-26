package pti.sb_sigmod_mvc.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.springframework.stereotype.Component;

import pti.sb_sigmod_mvc.model.Author;

@Component
public class XmlReader {

	public Map<String, Integer> getAuthors() {

		Map<String, Integer> authorMap = new HashMap<>();

		try {
			SAXBuilder sb = new SAXBuilder();
			Document doc = sb.build(new File(
					"E:\\Java Fullstack tanfolyam 2026 tananyagok\\21.  XML\\Java_XML_Ora3\\sigmodRecords.xml"));
			Element rootElement = doc.getRootElement();
			Namespace ns = rootElement.getNamespace();

			List<Element> issueList = rootElement.getChildren("issue", ns);
			for (Element issue : issueList) {
				Element articles = issue.getChild("articles", ns);

				List<Element> articleList = articles.getChildren("article", ns);
				for (Element article : articleList) {
					Element authors = article.getChild("authors", ns);

					List<Element> authorList = authors.getChildren("author", ns);
					boolean authorPos01Found = false;
					for (Element author : authorList) {

						if (author.getAttributeValue("position").equals("01")) {

							authorPos01Found = true;
							break;
						}
					}

					if (authorPos01Found == true) {
						for (Element author : authorList) {

							String authorName = author.getValue();
							if (authorMap.containsKey(authorName)) {

								Integer counter = authorMap.get(authorName);
								counter++;
								authorMap.put(authorName, counter);
							} else {

								authorMap.put(authorName, 1);
							}
						}
					}
				}
			}
		} catch (Exception e) {

		}

		return authorMap;
	}

	public void createXml(Map<String, Integer> authorMap) {
		
		try {
			
			/** XML */
			FileWriter writer = new FileWriter("E:\\Java Fullstack tanfolyam 2026 tananyagok\\21.  XML\\Java_XML_Ora3\\sigmodAuthors.xml");
			XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
			Document doc = new Document();

			/** ELEMENTS */
			Element rootElement = new Element("authors");

			for (Map.Entry<String, Integer> xmlAuthor : authorMap.entrySet()) {
				Element authorElement = new Element("author");

				authorElement.setText(xmlAuthor.getKey());
				authorElement.setAttribute("count", xmlAuthor.getValue() + "");
				rootElement.addContent(authorElement);
			}

			doc.setRootElement(rootElement);

			/** SAVE */
			outputter.output(doc, writer);
			writer.close();

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public List<Author> getSearchAuthors(String sText) {
		List<Author> authorList = new ArrayList<>();

		try {
			SAXBuilder sb = new SAXBuilder();
			Document doc = sb.build(new File(
					"E:\\Java Fullstack tanfolyam 2026 tananyagok\\21. XML\\Java_XML_Ora3\\sigmodRecords.xml"));
			Element rootElement = doc.getRootElement();
			Namespace ns = rootElement.getNamespace();

			List<Element> issueList = rootElement.getChildren("issue", ns);
			for (Element issue : issueList) {
				Element articles = issue.getChild("articles", ns);

				List<Element> articleList = articles.getChildren("article", ns);
				for (Element article : articleList) {
					Element authors = article.getChild("authors", ns);

					List<Element> authorElementList = authors.getChildren("author", ns);
					for (Element author : authorElementList) {

						String authorName = author.getValue();

						if (authorName.contains(sText)) {

							boolean authorFound = false;

							for (Author authorObject : authorList) {

								if (authorObject.getName().equals(authorName)) {

									Integer counter = authorObject.getCount();

									counter++;

									authorObject.setCount(counter);

									authorFound = true;

									break;
								}
							}

							if (authorFound == false) {
								Author authorObject = new Author(null, authorName, 1);

								authorList.add(authorObject);

							}

						}
					}
				}

			}

		} catch (Exception e) {

		}

		return authorList;
	}

	public Map<String, Integer> readXML(String path) throws JDOMException, IOException {
		Map<String, Integer> authorsMap = new HashMap<>();
		
		
			SAXBuilder sb = new SAXBuilder();
			Document doc = sb.build(new File(path));
			
			Element rootElement = doc.getRootElement();
			
			List<Element> authorList = rootElement.getChildren("author");
			
			for(Element authorElement : authorList) {
				Integer count = Integer.parseInt(authorElement.getAttributeValue("count"));
				
				authorsMap.put(authorElement.getValue(), count);
			}
			
		
		return authorsMap;
	}

}
