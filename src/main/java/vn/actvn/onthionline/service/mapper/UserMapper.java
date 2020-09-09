package vn.actvn.onthionline.service.mapper;

import org.springframework.stereotype.Service;
import vn.actvn.onthionline.domain.User;
import vn.actvn.onthionline.service.dto.UserDto;
import org.modelmapper.ModelMapper;

@Service
public class UserMapper {
    public User toEntity(UserDto userDto){
        if (userDto == null)
            return null;
        ModelMapper modelMapper = new ModelMapper();
        User user = modelMapper.map(userDto,User.class);
        return user;
    }
}
