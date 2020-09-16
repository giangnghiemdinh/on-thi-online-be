package vn.actvn.onthionline.repository.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import vn.actvn.onthionline.domain.ExamAnswer;

import javax.persistence.AttributeConverter;
import java.io.IOException;
import java.util.List;

public class ExamAnswerConverter implements AttributeConverter<List<ExamAnswer>, String> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<ExamAnswer> attribute) {
        String examAnswerJson = null;
        try {
            examAnswerJson = objectMapper.writeValueAsString(attribute);
        } catch (final JsonProcessingException e) {
            e.printStackTrace();
        }

        return examAnswerJson;
    }

    @Override
    public List<ExamAnswer> convertToEntityAttribute(String dbData) {
        List<ExamAnswer> examAnswers = null;
        try {
            examAnswers = objectMapper.readValue(
                    dbData,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, ExamAnswer.class));
        } catch (final IOException e) {
            e.printStackTrace();
        }

        return examAnswers;
    }
}
