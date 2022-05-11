package edu.agh.sharedshoppinglist.controller;

import edu.agh.sharedshoppinglist.dto.request.CreateReceiptForm;
import edu.agh.sharedshoppinglist.dto.response.ReceiptDto;
import edu.agh.sharedshoppinglist.service.ReceiptService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/list/{listCode}/receipt")
public class ReceiptController {

    ReceiptService receiptService;

    @PostMapping("")
    public void createReceipt(@RequestHeader("session-id") String sessionId, @PathVariable String listCode, @RequestBody CreateReceiptForm form) {
        receiptService.createReceipt(sessionId, listCode, form.getPrice());
    }

    @ResponseBody
    @GetMapping("/list")
    public List<ReceiptDto> getUserListReceipts(@RequestHeader("session-id") String sessionId, @PathVariable String listCode) {
        return receiptService.getReceipts(sessionId, listCode).stream()
                .map(receipt -> new ReceiptDto(receipt.getUser().getLogin(), receipt.getPrice()))
                .collect(Collectors.toList());
    }

}
