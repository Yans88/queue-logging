package com.yansen.services;

import com.yansen.dtos.requests.RegisterRequest;
import com.yansen.dtos.responses.RegisterResponse;
import com.yansen.exceptions.ValidationUsernameEmailException;
import com.yansen.models.entities.Register;
import com.yansen.models.repositories.RegisterRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@Transactional
public class RegisterService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private RegisterRepo registerRepo;

    public RegisterResponse saveData(RegisterRequest request) {
        cekExist(request);
        Register register = convertToEntity(request);
        return convertToDto(registerRepo.save(register));
    }

    private RegisterResponse convertToDto(Register register) {
        return modelMapper.map(register, RegisterResponse.class);
    }

    private Register convertToEntity(RegisterRequest request) {
        return modelMapper.map(request, Register.class);
    }

    private void cekExist(RegisterRequest request) {
        if (registerRepo.existsByEmail(request.getEmail())) {
            throw new ValidationUsernameEmailException(request.getEmail() + " : Email already exist");
        }

        if (registerRepo.existsByUsername(request.getUsername())) {
            throw new ValidationUsernameEmailException(request.getUsername() + " : Username already exist");
        }


    }
}
