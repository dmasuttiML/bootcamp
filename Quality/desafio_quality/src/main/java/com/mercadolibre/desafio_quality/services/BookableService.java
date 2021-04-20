package com.mercadolibre.desafio_quality.services;

import com.mercadolibre.desafio_quality.dtos.PaymentMethodDTO;
import com.mercadolibre.desafio_quality.dtos.PeopleDTO;
import com.mercadolibre.desafio_quality.dtos.StatusCodeDTO;
import com.mercadolibre.desafio_quality.exceptions.InvalidArgumentException;
import com.mercadolibre.desafio_quality.utils.ValidatorUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public abstract class BookableService {

    protected static LocalDate getAndValidateDate(String strDate){
        try {
            return LocalDate.parse(strDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (DateTimeParseException e){
            throw new InvalidArgumentException("Date format must be dd/mm/yyyy");
        }
    }

    protected void validateDateRange(LocalDate dateFrom, LocalDate dateTo) {
        if(dateFrom.isAfter(dateTo))
            throw new InvalidArgumentException("The check-in date must be less than the check-out date");
    }

    protected void validateEmail(String email){
        if(!ValidatorUtil.isEmail(email))
            throw new InvalidArgumentException("Please enter a valid email");
    }

    protected void validatePeople(PeopleDTO peopleDTO){
        if(!ValidatorUtil.isDNI(peopleDTO.getDni()))
            throw new InvalidArgumentException("Invalid DNI");
        validateEmail(peopleDTO.getMail());
        getAndValidateDate(peopleDTO.getBirthDate());
    }

    protected void validatePaymentMethod(PaymentMethodDTO paymentMethodDTO) {
        if(paymentMethodDTO.getType().equals("CREDIT")){
            if(paymentMethodDTO.getDues() < 0 || paymentMethodDTO.getDues() > 12)
                throw new InvalidArgumentException("Invalid amount of dues for credit");
        } else {
            if(paymentMethodDTO.getDues() != 1)
                throw new InvalidArgumentException("Invalid amount of dues for debit");
        }
        String number = paymentMethodDTO.getNumber();
    }

    protected StatusCodeDTO getStatusCode() {
        return new StatusCodeDTO(200, "El proceso termino satisfactoriamente");
    }
}
