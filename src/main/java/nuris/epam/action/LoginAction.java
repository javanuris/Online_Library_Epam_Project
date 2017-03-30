package nuris.epam.action;

import nuris.epam.action.manage.Action;
import nuris.epam.action.manage.ActionResult;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by User on 29.03.2017.
 */
public class LoginAction implements Action {
    @Override
    public ActionResult execute(HttpServletRequest request) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        request.setAttribute("login" , login);
        request.setAttribute("pass" , password);
        return new ActionResult("login");
    }
}
