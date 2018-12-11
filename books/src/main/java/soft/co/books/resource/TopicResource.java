package soft.co.books.resource;

import io.swagger.annotations.Api;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import soft.co.books.configuration.security.other.AuthoritiesConstants;
import soft.co.books.domain.service.TopicService;
import soft.co.books.domain.service.dto.PageResultDTO;
import soft.co.books.domain.service.dto.TopicDTO;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@Api(description = "Topic operations")
public class TopicResource {

    private final TopicService topicService;

    public TopicResource(TopicService topicService) {
        this.topicService = topicService;
    }

    @PostMapping("/topics")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.TOPIC_LIST + "\")")
    public PageResultDTO<TopicDTO> getAllTopics(@RequestBody TopicDTO topicDTO, Pageable pageable) {
        return topicService.findAll(topicDTO, pageable);
    }

    @PostMapping("/topic")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.TOPIC_MANAGEMENT + "\")")
    public TopicDTO getTopic(@RequestBody TopicDTO topicDTO) {
        if (topicDTO.getId() != null)
            return topicService.findOne(topicDTO.getId())
                    .map(TopicDTO::new).get();
        else
            return null;
    }

    @PostMapping("/saveTopic")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.TOPIC_MANAGEMENT + "\")")
    public TopicDTO saveTopic(@Valid @RequestBody TopicDTO topicDTO) {
        if (topicDTO.getId() == null || topicDTO.getId().isEmpty())
            return topicService.createTopic(topicDTO).get();
        else
            return topicService.updateTopic(topicDTO).get();
    }

    @DeleteMapping("/deleteTopic/{topicId}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.TOPIC_MANAGEMENT + "\")")
    public ResponseEntity<Void> deleteTopic(@PathVariable String topicId) {
        topicService.delete(topicId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
