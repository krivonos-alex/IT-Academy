package ru.mail.krivonos.project_jd1.servlets.command;

import ru.mail.krivonos.project_jd1.config.ConfigurationManagerImpl;
import ru.mail.krivonos.project_jd1.config.PropertiesVariables;
import ru.mail.krivonos.project_jd1.services.ItemService;
import ru.mail.krivonos.project_jd1.services.UserService;
import ru.mail.krivonos.project_jd1.services.impl.ItemServiceImpl;
import ru.mail.krivonos.project_jd1.services.impl.UserServiceImpl;
import ru.mail.krivonos.project_jd1.services.model.item.ItemDTO;
import ru.mail.krivonos.project_jd1.services.model.user.AuthorizedUserDTO;
import ru.mail.krivonos.project_jd1.services.model.user.UserLoginDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class LoginCommand implements Command {

    private UserService userService = UserServiceImpl.getInstance();
    private ItemService itemService = ItemServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        if (req.getParameter("state") != null) {
            return ConfigurationManagerImpl.getInstance().getProperty(PropertiesVariables.LOGIN_PAGE_PATH);
        }
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setEmail(email);
        userLoginDTO.setPassword(password);
        AuthorizedUserDTO authorizedUserDTO = userService.loginUser(userLoginDTO);
        if (authorizedUserDTO != null) {
            req.setAttribute("user", authorizedUserDTO);
            List<ItemDTO> items = itemService.getAll(1);
            req.setAttribute("items", items);
            return ConfigurationManagerImpl.getInstance().getProperty(PropertiesVariables.ITEMS_PAGE_PATH);
        }
        req.setAttribute("error", "Invalid email or password!");
        return ConfigurationManagerImpl.getInstance().getProperty(PropertiesVariables.LOGIN_PAGE_PATH);
    }
}