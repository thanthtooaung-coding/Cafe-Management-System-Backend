/*
 * @Author : Thant Htoo Aung
 * @Date : 20/08/2024
 * @Time : 12:28 PM
 * @Project_Name : cafe
 */
package com.bkk.cafe.exception;

public class EntityAlreadyExistsException extends RuntimeException {
	public EntityAlreadyExistsException(String message) {
        super(message);
    }
}
