package nuris.epam.action.post;

import nuris.epam.action.exception.ActionException;
import nuris.epam.action.manager.Action;
import nuris.epam.action.manager.ActionResult;
import nuris.epam.entity.BookInfo;
import nuris.epam.entity.Customer;
import nuris.epam.entity.Transaction;
import nuris.epam.services.TransactionService;
import nuris.epam.services.exception.ServiceException;
import nuris.epam.utils.TextParse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static nuris.epam.action.constants.Constants.BOOKS;
import static nuris.epam.action.constants.Constants.BOOK_ID;
import static nuris.epam.action.constants.Constants.CUSTOMER_ID;

/**
 * Created by User on 13.04.2017.
 */
public class CustomerTakeBookAction implements Action{
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {

        HttpSession session = req.getSession();
        int id = (int) session.getAttribute(CUSTOMER_ID);

        String bookId = req.getParameter(BOOK_ID);
        Customer customer = new Customer();
        customer.setId(id);
        TransactionService transactionService = new TransactionService();
        BookInfo bookInfo = new BookInfo();
        Transaction transaction = new Transaction();
        bookInfo.setId(TextParse.toInt(bookId));
        transaction.setCustomer(customer);
        transaction.setBookInfo(bookInfo);

        try {
            transactionService.takeBook(transaction);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return new ActionResult(BOOKS , true);
    }
}