package pti.sb_sigmod_mvc.dto;

import java.util.List;

public class AuthorResponseDTO {
	
	private List<AuthorDTO> authors;
	private String response;
	
	public AuthorResponseDTO(List<AuthorDTO> authors, String response) {
		super();
		this.authors = authors;
		this.response = response;
	}

	public List<AuthorDTO> getAuthors() {
		return authors;
	}

	public void setAuthors(List<AuthorDTO> authors) {
		this.authors = authors;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	
}
