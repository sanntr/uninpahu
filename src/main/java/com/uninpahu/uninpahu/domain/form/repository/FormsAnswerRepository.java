package com.uninpahu.uninpahu.domain.form.repository;

import com.uninpahu.uninpahu.domain.form.model.FormsAnswer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormsAnswerRepository extends MongoRepository<FormsAnswer, String>
{

}
