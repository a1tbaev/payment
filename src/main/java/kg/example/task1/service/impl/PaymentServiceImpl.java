package kg.example.task1.service.impl;

import kg.example.task1.config.jwt.JwtService;
import kg.example.task1.entity.Payment;
import kg.example.task1.entity.User;
import kg.example.task1.repo.PaymentRepository;
import kg.example.task1.service.PaymentService;
import kg.example.task1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final JwtService jwtService;
    private final PaymentRepository paymentRepository;
    private final UserService userservice;

    @Override
    @Transactional
    public String pay() {
        User user = jwtService.getAuthenticate();
        user.setBalance(paymentProcess(user));
        userservice.save(user);
        return "Payment is successful";
    }

    private BigDecimal paymentProcess(User user) {
        BigDecimal payment = new BigDecimal("1.1");

        if (payment.compareTo(user.getBalance()) > 0) {
            throw new RuntimeException("Not enough money");
        } else {
            Payment newPayment = new Payment();
            newPayment.setUser(user);
            newPayment.setPaymentFee(payment);
            paymentRepository.save(newPayment);
            return user.getBalance().subtract(payment);
        }
    }
}
