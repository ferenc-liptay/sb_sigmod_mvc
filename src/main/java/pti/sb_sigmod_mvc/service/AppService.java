package pti.sb_sigmod_mvc.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pti.sb_sigmod_mvc.dto.AuthorDTO;
import pti.sb_sigmod_mvc.dto.AuthorResponseDTO;
import pti.sb_sigmod_mvc.dto.SimpleResponseDTO;
import pti.sb_sigmod_mvc.model.Author;
import pti.sb_sigmod_mvc.repository.AuthorRepository;
import pti.sb_sigmod_mvc.xml.XmlReader;

@Service
public class AppService {

	private XmlReader reader;
	private AuthorRepository authorRepo;
	
	
	@Autowired
	public AppService(XmlReader reader, AuthorRepository authorRepo) {
		super();
		this.reader = reader;
		this.authorRepo = authorRepo;
	}
	
	
	public List<AuthorDTO> getAuthors() {

		List<AuthorDTO> authorDTOList = new ArrayList<>();
		
		Map<String, Integer> authorCounts = reader.getAuthors();
		
		List<String> authorNames = new ArrayList<>(authorCounts.keySet());
		
		Collections.sort(authorNames);
		
		for(String authorName : authorNames) {
			
			AuthorDTO authorDTO = new AuthorDTO(
						authorName,
						authorCounts.get(authorName)
					);
			
			authorDTOList.add(authorDTO);
		}
		
		
		return authorDTOList;
	}


	public List<AuthorDTO> getSearchAuthors(String sText) {
		List<AuthorDTO> authorDTOList = new ArrayList<>();
		
		List<Author> authorList = reader.getSearchAuthors(sText);
			
			for(Author author : authorList) {
				
				AuthorDTO authorDTO = new AuthorDTO(
						author.getName(),
						author.getCount()
						);
				
				authorDTOList.add(authorDTO);
			}
			
		return authorDTOList;
	}


	public SimpleResponseDTO getExportAuthors(String exportType) {
		SimpleResponseDTO simpleResponseDTO = null;
		
		Map<String, Integer> authorMap = reader.getAuthors();
		
		if(exportType.equals("xml")) {
			
			reader.createXml(authorMap);
			
			simpleResponseDTO = new SimpleResponseDTO("XML");
			
		}else if(exportType.equals("database")) {
			
			for(Map.Entry<String, Integer> dbAuthor : authorMap.entrySet()) {
				
				Author author = new Author(
						null,
						dbAuthor.getKey(),
						dbAuthor.getValue()
						);
				
				
				author = authorRepo.save(author);
			}
			
			simpleResponseDTO = new SimpleResponseDTO("DB");
			
		}
		
		return simpleResponseDTO;
	}


	public AuthorResponseDTO getSelectedType(String selectedType) {
		AuthorResponseDTO authorResponseDTO = null;
		
		if(selectedType.equals("xml")) {
			reader.getAuthors();
			
		}else if (selectedType.equals("database")) {
		}
		
		
		return authorResponseDTO;
	}

}