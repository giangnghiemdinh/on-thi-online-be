package vn.actvn.onthionline.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.actvn.onthionline.client.dto.ChatMessageRequest;
import vn.actvn.onthionline.client.dto.ChatMessageResponse;
import vn.actvn.onthionline.common.ValidationError;
import vn.actvn.onthionline.common.exception.ServiceException;
import vn.actvn.onthionline.common.exception.ServiceExceptionBuilder;
import vn.actvn.onthionline.common.exception.ValidationErrorResponse;
import vn.actvn.onthionline.domain.User;
import vn.actvn.onthionline.repository.UserRepository;

@Service
public class ChatService {
    @Autowired
    private UserRepository userRepository;

    public ChatMessageResponse chat(ChatMessageRequest request, String username) throws ServiceException {
        try {
            if (null == request.getMessage()) {
                throw ServiceExceptionBuilder.newBuilder()
                        .addError(new ValidationErrorResponse("Message", ValidationError.NotNull))
                        .build();
            }

            User user = null;
            if (null != username) {
                user = userRepository.findByUsername(username);
            }
            return new ChatMessageResponse("Chào bạn " + (user != null ? user.getFullname() : ""));
        } catch (ServiceException e) {
            throw e;
        }
    }

}
