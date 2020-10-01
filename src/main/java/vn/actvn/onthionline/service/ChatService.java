package vn.actvn.onthionline.service;

import org.springframework.stereotype.Service;
import vn.actvn.onthionline.client.dto.ChatMessageRequest;
import vn.actvn.onthionline.client.dto.ChatMessageResponse;
import vn.actvn.onthionline.common.ValidationError;
import vn.actvn.onthionline.common.exception.ServiceException;
import vn.actvn.onthionline.common.exception.ServiceExceptionBuilder;
import vn.actvn.onthionline.common.exception.ValidationErrorResponse;

@Service
public class ChatService {
    public ChatMessageResponse chat(ChatMessageRequest request, String username) throws ServiceException {
        try {
            if (null == request.getMessage()) {
                throw ServiceExceptionBuilder.newBuilder()
                        .addError(new ValidationErrorResponse("Message", ValidationError.NotNull))
                        .build();
            }
            return new ChatMessageResponse("Chào bạn");
        } catch (ServiceException e) {
            throw e;
        }
    }

}
