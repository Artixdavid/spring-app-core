package com.app.core.validations;

import java.lang.reflect.GenericDeclaration;

import javax.persistence.EntityManager;

import org.springframework.data.repository.CrudRepository;

import com.app.core.exceptions.ExceptionHelper;

public class Validate {
	
	/*
	  public static Object checkExist(CrudRepository<T, Long> dao, Object id, String message) throws ExceptionHelper{
        Object o = dao.findById(id);
        
        if (o == null) {
            //throw ExceptionHelper.createNotFound(message);
        }
        
        return o;
    }
    */
	
	public static boolean isNull(Object value) {
        return value == null;
    }
	
	public static boolean isNullOrEmpty(Object value) {
        if (isNull(value)) {
            return true;
        }
        
        if (value.toString().trim().isEmpty()) {
            return true;
        }
        
        return false;
    }

}
