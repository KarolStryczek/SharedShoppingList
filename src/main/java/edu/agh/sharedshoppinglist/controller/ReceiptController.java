package edu.agh.sharedshoppinglist.controller;

import edu.agh.sharedshoppinglist.service.ReceiptService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/list")
public class ReceiptController {

    ReceiptService receiptService;

    @GetMapping("/{listCode}/receipt")
    public void createReceipt(@RequestHeader("session-id") String sessionId, @PathVariable String listCode) {
        receiptService.createReceipt(sessionId, listCode);
    }
}
