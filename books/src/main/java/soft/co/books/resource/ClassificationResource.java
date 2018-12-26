package soft.co.books.resource;


import io.swagger.annotations.Api;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soft.co.books.configuration.security.other.AuthoritiesConstants;
import soft.co.books.domain.service.ClassificationService;
import soft.co.books.domain.service.dto.ClassificationDTO;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@Api(description = "Classification operations")
public class ClassificationResource {

    private final ClassificationService classificationService;

    public ClassificationResource(ClassificationService classificationService) {
        this.classificationService = classificationService;
    }

    @PostMapping("/allClassification")
    public List<ClassificationDTO> allClassification() {
        return classificationService.findAll().stream().map(ClassificationDTO::new).collect(Collectors.toList());
    }
}
