package com.codedecode.order.service;

import com.codedecode.order.entity.Sequence;
import com.codedecode.order.global_exception.SequenceGenerationException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;


import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
@RequiredArgsConstructor
public class SequenceGenerator {

    private final MongoOperations mongoOperations;

    private static final Logger logger = LoggerFactory.getLogger(SequenceGenerator.class);

    public long getSequenceNumber() {
        var counter = mongoOperations.findAndModify(
                query(where("_id").is("sequence")),
                new Update().inc("sequence", 1),
                options().returnNew(true).upsert(true),
                Sequence.class);

        if(counter == null){
            logger.error("Counter is null");
            throw new SequenceGenerationException("Unable to generate sequence id");
        }
        return counter.getSequence();
    }

}
