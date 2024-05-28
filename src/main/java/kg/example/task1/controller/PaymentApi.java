package kg.example.task1.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import kg.example.task1.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Payment", description = "Payment Processing API")
@RequestMapping("/api/payment")
public class PaymentApi {
    private final PaymentService paymentService;
    @PutMapping
    @PreAuthorize("hasAnyAuthority('USER')")
    public String pay() {
        return paymentService.pay();
    }
}
