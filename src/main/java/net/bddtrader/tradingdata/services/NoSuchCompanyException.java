package net.bddtrader.tradingdata.services;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Company Not Found")
public class NoSuchCompanyException extends RuntimeException {
    public NoSuchCompanyException(String message) { super(message); }
}
