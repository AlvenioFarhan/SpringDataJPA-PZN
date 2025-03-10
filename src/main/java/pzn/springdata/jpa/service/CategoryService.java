package pzn.springdata.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionOperations;
import pzn.springdata.jpa.entity.Category;
import pzn.springdata.jpa.repository.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TransactionOperations transactionOperations;

    @Autowired
    private PlatformTransactionManager transactionManager;

    public void manual() {
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setTimeout(10);
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        TransactionStatus transaction = transactionManager.getTransaction(definition);
        try {
            for (int index = 0; index < 5; index++) {
                Category category = new Category();
                category.setName("Category " + index);
                categoryRepository.save(category);
            }
            error();
            transactionManager.commit(transaction);
        } catch (Throwable throwable) {
            transactionManager.rollback(transaction);
            throw throwable;
        }
    }

    public void error() {
        throw new RuntimeException("Ups");
    }

    public void createCategories() {
        transactionOperations.executeWithoutResult(transactionStatus -> {
            for (int index = 0; index < 5; index++) {
                Category category = new Category();
                category.setName("Category " + index);
                categoryRepository.save(category);
            }
            error();
        });
    }

    //    @Transactional(propagation = Propagation.MANDATORY)
    @Transactional
    public void create() {
        for (int index = 0; index < 5; index++) {
            Category category = new Category();
            category.setName("Category " + index);
            categoryRepository.save(category);
        }
        throw new RuntimeException("Ups rollback please");
    }

    public void test() {
        create();
    }
}
