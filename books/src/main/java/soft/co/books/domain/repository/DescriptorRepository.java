package soft.co.books.domain.repository;


import soft.co.books.configuration.database.CustomBaseRepository;

import javax.management.Descriptor;

/**
 * Spring Data MongoDB repository for the Author entity.
 */
public interface DescriptorRepository extends CustomBaseRepository<Descriptor, String> {
}
