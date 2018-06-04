package net.bddtrader.portfolios;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PortfolioNotFoundException extends RuntimeException {
    PortfolioNotFoundException(String message) {
        super(message);
    }
}
