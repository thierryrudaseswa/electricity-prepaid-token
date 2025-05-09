package com.example.electricity_management_system.controller;
import com.example.electricity_management_system.dto.MeterRegistrationAndUpdateDto;
import com.example.electricity_management_system.dto.SuccessResponse;
import com.example.electricity_management_system.model.MeterModel;
import com.example.electricity_management_system.service.MeterServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Meter")
@RequestMapping("/api/v1/meters")
public class MeterController {
    private final MeterServices meterServices;
    public MeterController(MeterServices meterServices) {
        this.meterServices=meterServices;
    }
    @Operation(summary = "Create Meter",description = "Create a new meter",tags = {"Meter"},responses = {
            @ApiResponse(responseCode = "201",description = "Meter Created Successfully"),
            @ApiResponse(responseCode = "400",description = "Invalid Request"),
            @ApiResponse(responseCode = "500",description = "Something went wrong")
    })
    @PostMapping("/new")
    public ResponseEntity<SuccessResponse<MeterModel>> createMeter(@Valid MeterRegistrationAndUpdateDto meter){
        MeterModel meterModel=meterServices.createMeter(meter);
        return ResponseEntity.status(201).body(new SuccessResponse<MeterModel>("Meter Created Successfully",meterModel));

    }
    @Operation(summary = "Get All Meters",description = "Get all meters",responses = {
            @ApiResponse(responseCode = "200",description = "Meters Found"),
            @ApiResponse(responseCode = "500",description = "Something went wrong")
    })
    @GetMapping("/all")
    public ResponseEntity<SuccessResponse<List<MeterModel>>> getAllMeters(){
        List<MeterModel> meters=meterServices.getAllMeters();
        return ResponseEntity.ok(new SuccessResponse<>("All Meters Found",meters));
    }
    @Operation(summary = "Get Meter By Id",description = "Get Meter By Id",responses = {
            @ApiResponse(responseCode = "200",description = "Meter Found"),
            @ApiResponse(responseCode = "400",description = "Invalid Request"),
            @ApiResponse(responseCode = "500",description = "Something went wrong")
    })
    @GetMapping("{id}")
    public ResponseEntity<SuccessResponse<MeterModel>> getMeterById(@Valid Long id){
        MeterModel meter=meterServices.getMeterById(id);
        return ResponseEntity.ok(new SuccessResponse<>("Meter Found",meter));
    }
    @Operation(summary = "Delete Meter by Meter Number",description = "Delete Meter By Meter Number",responses = {
            @ApiResponse(responseCode = "200",description = "Meter Deleted Successfully"),
            @ApiResponse(responseCode = "400",description = "Invalid Request"),
            @ApiResponse(responseCode = "500",description = "Something went wrong")
    })

    @DeleteMapping("{meterNumber}")
    public ResponseEntity<SuccessResponse<String>> deleteMeterByMeterNUmber(@Valid  @PathVariable Long meterNumber){
        String message=meterServices.deleteMeterByMeterNumber(meterNumber);
        return ResponseEntity.ok(new SuccessResponse<>(message));
    }
    @Operation(summary = "Delete Meter By Id",description = "Delete Meter By Id",responses = {
            @ApiResponse(responseCode = "200",description = "Meter Deleted Successfully"),
            @ApiResponse(responseCode = "400",description = "Invalid Request"),
            @ApiResponse(responseCode = "500",description = "Something went wrong")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<SuccessResponse<String>> deleteMeterById(@Valid @PathVariable Long id){
        String message=meterServices.deleteMeterByMeterId(id);
        return ResponseEntity.ok(new SuccessResponse<>(message));
    }
//    @Operation(summary = "Update Meter By Id",description = "Update Meter By Id",
//    responses = {
//            @ApiResponse(responseCode = "200",description = "Meter Updated Successfully"),
//            @ApiResponse(responseCode = "400",description = "Invalid Request"),
//            @ApiResponse(responseCode = "500",description = "Something went wrong")
//    })

//    @PutMapping("{id}")
//    public ResponseEntity<SuccessResponse<MeterModel>> updateMeterById(@Valid @PathVariable Long id,@Valid @RequestBody MeterRegistrationAndUpdateDto meter){
//        MeterModel meterModel=meterServices.updateMeter(meter);
//        return ResponseEntity.ok(new SuccessResponse<>("Meter Updated Successfully",meterModel));
//    }


}
