package nuris.epam.action.post;


import nuris.epam.action.Action;
import nuris.epam.action.ActionResult;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by User on 29.03.2017.
 */
public class LogoutAction implements Action {
    @Override
    public ActionResult execute(HttpServletRequest request) {
        return new ActionResult("welcome");
    }
}