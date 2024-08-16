package com.example.bankingapp.controller

import com.example.bankingapp.dto.AppointmentRequest
import com.example.bankingapp.exception.SlotAlreadyBookedException
import com.example.bankingapp.model.*
import com.example.bankingapp.service.AppointmentService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/appointments")
class AppointmentController(private val appointmentService: AppointmentService) {

    @PostMapping("/schedule")
    fun scheduleAppointment(@Valid @RequestBody request: AppointmentRequest): ResponseEntity<Any> {
        return try {
            val appointments = appointmentService.scheduleAppointment(
                customer = Customer(id = request.customerId),
                staffId = request.staffId,
                appointmentType = request.appointmentType,
                startTime = request.startTime,
                endTime = request.endTime,
                recurrenceType = request.recurrenceType,
                recurrenceEndDate = request.recurrenceEndDate
            )
            ResponseEntity.ok(appointments)
        } catch (e: SlotAlreadyBookedException) {
            ResponseEntity.status(HttpStatus.CONFLICT).body(e.message)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
        }
    }

    @GetMapping("/staff")
    fun getAvailableStaff(@RequestParam role: String): ResponseEntity<List<BankStaff>> {
        val staff = appointmentService.getAvailableStaff(role)
        return ResponseEntity.ok(staff)
    }
}