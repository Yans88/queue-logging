package com.yansen.controllers;

import com.yansen.components.QueueProducer;
import com.yansen.dtos.requests.RegisterRequest;
import com.yansen.dtos.responses.ImportRequest;
import com.yansen.dtos.responses.RegisterResponse;
import com.yansen.dtos.responses.ResponseDTO;
import com.yansen.dtos.responses.ResponseSaveData;
import com.yansen.services.RegisterService;
import com.yansen.services.ScheduleCoinService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class ScheduleCoinController {

    @Autowired
    QueueProducer producer;
    @Autowired
    private ScheduleCoinService scheduleCoinService;
    @Autowired
    private RegisterService registerService;

    @PostMapping("/import")
    public ResponseEntity<ResponseDTO<ImportRequest>> importExcelFile(@RequestParam("file") MultipartFile files) throws IOException {
        String contenType = files.getContentType();
        ResponseDTO response = new ResponseDTO();
        if (!contenType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
            response.setErr_msg("document format is not supported");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        XSSFWorkbook workbook = new XSSFWorkbook(files.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);
        List<ImportRequest> dataList = new ArrayList<>();

        for (int index = 0; index < worksheet.getPhysicalNumberOfRows(); index++) {
            if (index > 0) {
                ImportRequest importData = new ImportRequest();
                XSSFRow row = worksheet.getRow(index);
                Integer id = (int) row.getCell(0).getNumericCellValue();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                importData.setId(Long.valueOf(id));
                importData.setName(row.getCell(1).getStringCellValue());
                importData.setTicker(row.getCell(2).getStringCellValue());
                importData.setCoinId((int) row.getCell(3).getNumericCellValue());
                importData.setCode(row.getCell(4).getStringCellValue());
                importData.setExchange(row.getCell(5).getStringCellValue());
                importData.setInvalid((int) row.getCell(6).getNumericCellValue());
                importData.setRecordTime((int) row.getCell(7).getNumericCellValue());
                importData.setUsd(new BigDecimal(row.getCell(8).getNumericCellValue()));
                importData.setIdr(new BigDecimal(row.getCell(9).getNumericCellValue()));
                importData.setHnst(new BigDecimal(row.getCell(10).getNumericCellValue()));
                importData.setEth(new BigDecimal(row.getCell(11).getNumericCellValue()));
                importData.setBtc(new BigDecimal(row.getCell(12).getNumericCellValue()));
                importData.setCreatedAt(sdf.format(row.getCell(13).getDateCellValue()));
                importData.setUpdatedAt(sdf.format(row.getCell(14).getDateCellValue()));
                dataList.add(importData);
                scheduleCoinService.save(importData);
            }
        }

        response.setData(dataList);
        response.setTotal_data(dataList.size());
        response.setErr_msg("ok");
        //scheduleCoinService.saveData(dataList);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/registrasi")
    public ResponseEntity<ResponseSaveData<RegisterResponse>> registerUser(@Valid @RequestBody RegisterRequest request, Errors errors) {
        ResponseSaveData<RegisterResponse> responseData = new ResponseSaveData<>();
        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        RegisterResponse response = registerService.saveData(request);
        responseData.setPayload(response);
        log.info("Email : " + request.getEmail() + ", Username : " + request.getUsername() + " Success registered");
        producer.send(request.getUsername() + "(" + request.getEmail() + ")  terdaftar sebagai user baru");
        return new ResponseEntity<>(responseData, HttpStatus.CREATED);
    }

}
