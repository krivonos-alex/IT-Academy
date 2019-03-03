package ru.mail.krivonos.project_jd1.services.converter.user;

import ru.mail.krivonos.project_jd1.repository.model.User;
import ru.mail.krivonos.project_jd1.services.model.user.UserForOrderDTO;

public class UserForOrderConverterImpl implements UserForOrderConverter {

    private static UserForOrderConverter instance;

    private UserForOrderConverterImpl() {
    }

    public static UserForOrderConverter getInstance() {
        if (instance == null) {
            instance = new UserForOrderConverterImpl();
        }
        return instance;
    }

    @Override
    public User fromDTO(UserForOrderDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setSurname(userDTO.getSurname());
        user.setName(userDTO.getName());
        return user;
    }

    @Override
    public UserForOrderDTO toDTO(User user) {
        UserForOrderDTO userDTO = new UserForOrderDTO();
        userDTO.setId(user.getId());
        userDTO.setSurname(user.getSurname());
        userDTO.setName(user.getName());
        return userDTO;
    }
}