package ru.mail.krivonos.project_jd1.services.converter.impl;

import ru.mail.krivonos.project_jd1.repository.model.User;
import ru.mail.krivonos.project_jd1.services.converter.AuthorizedUserConverter;
import ru.mail.krivonos.project_jd1.services.model.user.AuthorizedUserDTO;

public class AuthorizedUserConverterImpl implements AuthorizedUserConverter {

    private static AuthorizedUserConverter instance;

    private AuthorizedUserConverterImpl() {
    }

    public static AuthorizedUserConverter getInstance() {
        if (instance == null) {
            synchronized (AuthorizedUserConverterImpl.class) {
                if (instance == null) {
                    instance = new AuthorizedUserConverterImpl();
                }
            }
        }
        return instance;
    }

    @Override

    public User fromDTO(AuthorizedUserDTO userDTO) {
        throw new UnsupportedOperationException();
    }

    @Override
    public AuthorizedUserDTO toDTO(User user) {
        AuthorizedUserDTO userDTO = new AuthorizedUserDTO();
        userDTO.setId(user.getId());
        userDTO.setPermission(user.getRole().getPermissions().get(0));
        return userDTO;
    }
}
