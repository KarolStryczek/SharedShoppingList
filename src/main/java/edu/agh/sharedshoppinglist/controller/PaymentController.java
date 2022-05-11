package edu.agh.sharedshoppinglist.controller;

import edu.agh.sharedshoppinglist.dto.request.PaymentForm;
import edu.agh.sharedshoppinglist.dto.response.PaymentDto;
import edu.agh.sharedshoppinglist.service.PaymentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/list")
public class PaymentController {

    PaymentService paymentService;

    @PostMapping("/{listCode}/payment")
    public void createPayment(@RequestHeader("session-id") String sessionId, @PathVariable String listCode, @RequestBody PaymentForm form) {
        paymentService.createPayment(sessionId, listCode, form.getTargetUser(), form.getAmount());
    }

    @ResponseBody
    @GetMapping("/{listCode}/payment/list")
    public List<PaymentDto> getListPayments(@RequestHeader("session-id") String sessionId, @PathVariable String listCode) {
        return paymentService.getAllListPayments(sessionId, listCode).stream()
                .map(payment -> new PaymentDto(payment.getSourceUser().getLogin(), payment.getTargetUser().getLogin(), payment.getAmount()))
                .collect(Collectors.toList());
    }

}
