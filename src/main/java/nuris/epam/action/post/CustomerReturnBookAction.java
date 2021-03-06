package nuris.epam.action.post;

import nuris.epam.action.exception.ActionException;
import nuris.epam.action.manager.Action;
import nuris.epam.action.manager.ActionResult;
import nuris.epam.entity.Customer;
import nuris.epam.entity.Transaction;
import nuris.epam.services.TransactionService;
import nuris.epam.services.exceptions.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static nuris.epam.action.constants.Constants.*;

/**
 * Created by User on 14.04.2017.
 */
public class CustomerReturnBookAction implements Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {

        TransactionService transactionService = new TransactionService();
        Transaction transaction = new Transaction();
        Customer customer = new Customer();

        HttpSession session = req.getSession();
        int id = (int) session.getAttribute(ATT_CUSTOMER_ID);

        String transactionId = req.getParameter(RETURN_BOOK);

        transaction.setId(Integer.parseInt(transactionId));
        customer.setId(id);

        try {
            transactionService.returnBook(transaction, customer);
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        return new ActionResult(DEPT_CUSTOMER_BOOK, true);
    }
}
