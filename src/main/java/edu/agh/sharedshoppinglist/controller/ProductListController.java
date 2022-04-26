package edu.agh.sharedshoppinglist.controller;

import edu.agh.sharedshoppinglist.dto.request.CreateListForm;
import edu.agh.sharedshoppinglist.dto.request.JoinListForm;
import edu.agh.sharedshoppinglist.dto.response.ShoppingListDto;
import edu.agh.sharedshoppinglist.dto.response.ShoppingListResponse;
import edu.agh.sharedshoppinglist.exception.ApplicationException;
import edu.agh.sharedshoppinglist.model.Session;
import edu.agh.sharedshoppinglist.service.SessionService;
import edu.agh.sharedshoppinglist.service.ShoppingListService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/list")
public class ProductListController extends AbstractExceptionHandler {

    ShoppingListService shoppingListService;
    SessionService sessionService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createProductList(@RequestHeader("session-id") String sessionId, @RequestBody CreateListForm form) throws ApplicationException {
        shoppingListService.createProductListForUser(sessionId, form.getName());
    }

    @PostMapping("/join")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void joinProductList(@RequestHeader("session-id") String sessionId, @RequestBody JoinListForm form) throws ApplicationException {
        shoppingListService.joinProductList(sessionId, form.getCode());
    }

    @GetMapping("/all")
    public ShoppingListResponse getAllUserLists(@RequestHeader("session-id") String sessionId) throws ApplicationException {
        return new ShoppingListResponse(shoppingListService.getAllUserLists(sessionId).stream()
                .map(list -> new ShoppingListDto(list.getName(), list.getCode(), null))
                .collect(Collectors.toList()));
    }

    @GetMapping("/{listCode}")
    public ShoppingListDto getProductList(@RequestHeader("session-id") String sessionId, @PathVariable String listCode) {
        Session session = sessionService.getActiveSessionById(sessionId);
        return ShoppingListDto.prepare(session, shoppingListService.getProductList(sessionId, listCode));
    }
}
