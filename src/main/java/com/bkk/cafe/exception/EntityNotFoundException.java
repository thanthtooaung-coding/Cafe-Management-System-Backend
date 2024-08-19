/*
 * @Author : Thant Htoo Aung
 * @Date : 19/08/2024
 * @Time : 12:27 PM
 * @Project_Name : cafe
 */
package com.bkk.cafe.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}