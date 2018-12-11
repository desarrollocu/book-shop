package soft.co.books.resource;

import io.swagger.annotations.Api;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import soft.co.books.configuration.security.other.AuthoritiesConstants;
import soft.co.books.domain.service.EditorService;
import soft.co.books.domain.service.dto.EditorDTO;
import soft.co.books.domain.service.dto.PageResultDTO;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@Api(description = "Editor operations")
public class EditorResource {

    private final EditorService editorService;

    public EditorResource(EditorService editorService) {
        this.editorService = editorService;
    }

    @PostMapping("/editors")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.EDITOR_LIST + "\")")
    public PageResultDTO<EditorDTO> getAllEditors(@RequestBody EditorDTO editorDTO, Pageable pageable) {
        return editorService.findAll(editorDTO, pageable);
    }

    @PostMapping("/editor")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.EDITOR_MANAGEMENT + "\")")
    public EditorDTO getEditor(@RequestBody EditorDTO editorDTO) {
        if (editorDTO.getId() != null)
            return editorService.findOne(editorDTO.getId())
                    .map(EditorDTO::new).get();
        else
            return null;
    }

    @PostMapping("/saveEditor")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.EDITOR_MANAGEMENT + "\")")
    public EditorDTO saveEditor(@Valid @RequestBody EditorDTO editorDTO) {
        if (editorDTO.getId() == null || editorDTO.getId().isEmpty())
            return editorService.createEditor(editorDTO).get();
        else
            return editorService.updateEditor(editorDTO).get();
    }

    @DeleteMapping("/deleteEditor/{editorId}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.EDITOR_MANAGEMENT + "\")")
    public ResponseEntity<Void> deleteEditor(@PathVariable String editorId) {
        editorService.delete(editorId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
