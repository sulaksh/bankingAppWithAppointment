package com.example.bankingapp.service

import com.example.bankingapp.model.Customer
import com.example.bankingapp.repository.CustomerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class CustomerService(
    @Autowired private val customerRepository: CustomerRepository
) {
    fun createCustomer(name: String): Customer {
        val customerId = generateCustomerId()
        val customer = Customer(customerId = customerId, name = name)
        return customerRepository.save(customer)
    }

    fun getAllCustomers(): List<Customer> {
        return customerRepository.findAll()
    }

    fun findCustomerById(customerId: String): Customer? {
        return customerRepository.findByCustomerId(customerId)
    }

    private fun generateCustomerId(): String {
        return (100000..999999).random().toString()
    }
}
