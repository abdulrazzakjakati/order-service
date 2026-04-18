package com.codedecode.order.service;

import com.codedecode.order.entity.Sequence;
import com.codedecode.order.global_exception.SequenceGenerationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class SequenceGeneratorTest {

    @Mock
    private MongoOperations mongoOperations;

    @InjectMocks
    private SequenceGenerator sequenceGenerator;

    @Test
    void shouldReturnSequenceNumber() {
        Sequence sequence = new Sequence("sequence", 5);

        doReturn(sequence)
                .when(mongoOperations)
                .findAndModify(
                        any(Query.class),
                        any(Update.class),
                        any(FindAndModifyOptions.class),
                        any(Class.class)
                );

        long result = sequenceGenerator.getSequenceNumber();

        assertEquals(5, result);
    }

    @Test
    void shouldThrowExceptionWhenCounterIsNull() {

        doReturn(null)
                .when(mongoOperations)
                .findAndModify(
                        any(Query.class),
                        any(Update.class),
                        any(FindAndModifyOptions.class),
                        any(Class.class)
                );

        assertThrows(SequenceGenerationException.class,
                () -> sequenceGenerator.getSequenceNumber());
    }
}