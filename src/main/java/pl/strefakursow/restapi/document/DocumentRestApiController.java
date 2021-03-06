package pl.strefakursow.restapi.document;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api/documents")
public class DocumentRestApiController {

	private Collection<Document> documents = new ArrayList<>();

	@GetMapping
	public Iterable<Document> getDocuments() {
		return documents;
	}

	@GetMapping("/{number}/title")
	public Optional<String> getTitleOfDocument(@PathVariable long number) {
		return findDocumentByNumber(number).map(Document::getTitle);
	}

	@GetMapping("/{number}")
	public Optional<Document> getDocument(@PathVariable long number) {
		return findDocumentByNumber(number);
	}

	@PostMapping
	public void addDocument(@RequestBody Document document) {
		documents.add(document);
	}

	@PostMapping(value = "/{documentNumber}/tags", consumes =
		MediaType.TEXT_PLAIN_VALUE)
	public void addTag(@PathVariable long documentNumber,
			   @RequestBody String tag) {
		findDocumentByNumber(documentNumber)
			.ifPresent(document -> document.getTags().add(tag));
	}

	private Optional<Document> findDocumentByNumber(long number) {
		return documents.stream()
			.filter(document -> document.getNumber() == number)
			.findAny();
	}
}
