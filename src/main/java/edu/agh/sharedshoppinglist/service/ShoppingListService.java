package edu.agh.sharedshoppinglist.service;

import edu.agh.sharedshoppinglist.config.AppConfig;
import edu.agh.sharedshoppinglist.exception.ApplicationException;
import edu.agh.sharedshoppinglist.exception.ErrorCode;
import edu.agh.sharedshoppinglist.model.Session;
import edu.agh.sharedshoppinglist.model.ShoppingList;
import edu.agh.sharedshoppinglist.model.User;
import edu.agh.sharedshoppinglist.repository.ShoppingListRepository;
import edu.agh.sharedshoppinglist.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ShoppingListService {

    AppConfig appConfig;

    ShoppingListRepository shoppingListRepository;
    UserRepository userRepository;

    SessionService sessionService;

    // <editor-fold desc="Create and join list methods">
    public void createProductListForUser(String sessionId, String name) throws ApplicationException {
        Session session = sessionService.getActiveSessionById(sessionId);
        ShoppingList list = createProductList(name);
        createListUser(list, session.getUser());
    }

    private ShoppingList createProductList(String name) {
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setName(name);
        shoppingList.setCode(generateUniqueListCode());
        return shoppingListRepository.save(shoppingList);
    }

    private String generateUniqueListCode() {
        String code;
        do {
            code = RandomString.make(appConfig.getListCodeLength());
        } while (shoppingListRepository.getByCode(code) != null);
        return code;
    }

    public void joinProductList(String sessionId, String code) throws ApplicationException {
        Session session = sessionService.getActiveSessionById(sessionId);
        if (session.getUser().getLists().stream().anyMatch(list -> list.getCode().equals(code))) {
            return;
        }
        ShoppingList list = shoppingListRepository.getByCode(code);
        if (list == null) {
            throw new ApplicationException(ErrorCode.INVALID_LIST_CODE);
        }
        createListUser(list, session.getUser());
    }

    private void createListUser(ShoppingList list, User user) {
        user.getLists().add(list);
        userRepository.save(user);
    }
    // </editor-fold>

    // <editor-fold desc="Find list methods">
    public List<ShoppingList> getAllUserLists(String sessionId) throws ApplicationException {
        Session session = sessionService.getActiveSessionById(sessionId);
        return session.getUser().getLists();
    }

    public ShoppingList getProductList(String sessionId, String listCode) throws ApplicationException {
        Session session = sessionService.getActiveSessionById(sessionId);
        return session.getUser().getLists().stream()
                .filter(list -> list.getCode().equals(listCode))
                .findFirst().orElseThrow(() -> new ApplicationException(ErrorCode.INVALID_LIST_CODE));
    }
    // </editor-fold
}
