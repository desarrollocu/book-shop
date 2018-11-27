package soft.co.books.domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import soft.co.books.configuration.database.CustomBaseService;
import soft.co.books.domain.repository.DescriptorRepository;

import javax.management.Descriptor;


/**
 * Service class for managing descriptors.
 */
@Service
public class DescriptorService extends CustomBaseService<Descriptor, String> {

    private final Logger log = LoggerFactory.getLogger(DescriptorService.class);

    private final DescriptorRepository descriptorRepository;

    public DescriptorService(DescriptorRepository descriptorRepository) {
        super(descriptorRepository);
        this.descriptorRepository = descriptorRepository;
    }
}
