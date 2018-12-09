package soft.co.books.configuration.database;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data MongoDB custom class for the all services.
 */
public abstract class CustomBaseService<T, ID extends Serializable> {

    private CustomBaseRepository customBaseRepository;

    public CustomBaseService(CustomBaseRepository customBaseRepository) {
        this.customBaseRepository = customBaseRepository;
    }

    public long count() {
        return this.customBaseRepository.count();
    }

    public List<T> findAll() {
        return this.customBaseRepository.findAll();
    }

    public void delete(T entity) {
        this.customBaseRepository.delete(entity);
    }

    public boolean existsById(ID id) {
        return this.customBaseRepository.existsById(id);
    }

    public List<T> findAll(Sort var1) {
        return this.customBaseRepository.findAll(var1);
    }

    public Page<T> findAll(Pageable var1) {
        return this.customBaseRepository.findAll(var1);
    }

    public List<T> saveAll(Iterable<T> var1) {
        return this.customBaseRepository.saveAll(var1);
    }

    public Optional<T> findOne(ID id) {
        return (Optional<T>) this.customBaseRepository.findById(id);
    }

    public T save(T value) {
        return (T) this.customBaseRepository.save(value);
    }

//    public Optional<T> findOne(Predicate var1) {
//        return this.customBaseRepository.findOne(var1);
//    }
//
//    public Iterable<T> findAll(Predicate var1) {
//        return this.customBaseRepository.findAll(var1);
//    }
//
//    public Iterable<T> findAll(Predicate var1, Sort var2) {
//        return this.customBaseRepository.findAll(var1);
//    }

//    public Iterable<T> findAll(Predicate var1, OrderSpecifier... var2) {
//        return this.customBaseRepository.findAll(var1, var2);
//    }
//
//    public Iterable<T> findAll(OrderSpecifier... var1) {
//        return this.customBaseRepository.findAll(var1);
//    }
//
//    public Page<T> findAll(Predicate var1, Pageable var2) {
//        return this.customBaseRepository.findAll(var1, var2);
//    }
//
//    public long count(Predicate var1) {
//        return this.customBaseRepository.count(var1);
//    }
//
//    public boolean exists(Predicate var1) {
//        return this.customBaseRepository.exists(var1);
//    }
}
