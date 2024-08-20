/*
 * @Author : Thant Htoo Aung
 * @Date : 19/08/2024
 * @Time : 12:25 PM
 * @Project_Name : cafe
 */
package com.bkk.cafe.util;

import java.lang.reflect.Field;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bkk.cafe.exception.EntityCreationException;

/**
 * Utility class for common entity operations.
 */
public class EntityUtil {

	private static final Logger logger = LoggerFactory.getLogger(EntityUtil.class);

	/**
	 * Saves an entity using the provided repository and checks if the entity was
	 * successfully created.
	 *
	 * Example Usage
	 * 
	 * <pre>
	 * {@code
	 * User user = new User();
	 * user.setName("John Doe");
	 * EntityUtil.saveEntity(userRepository, user, "User");
	 * }
	 * </pre>
	 *
	 * @param repository the repository for the entity
	 * @param entity     the entity to be saved
	 * @param entityName the name of the entity
	 * @param <T>        the type of the entity
	 * @return the saved entity
	 */
	public static <T> T saveEntity(JpaRepository<T, Long> repository, T entity, String entityName) {
		T savedEntity = repository.save(entity);
		if (savedEntity instanceof Identifiable && ((Identifiable) savedEntity).getId() == null) {
			throw new EntityCreationException("Failed to create the " + entityName);
		}		
		return savedEntity;
	}

	/**
	 * Retrieves all entities from the repository.
	 *
	 * Example Usage:
	 * 
	 * <pre>
	 * {@code
	 * List<User> users = EntityUtil.getAllEntities(userRepository);
	 * }
	 * </pre>
	 *
	 * @param repository the repository for the entities
	 * @param <T>        the type of the entities
	 * @return a list of all entities with their IDs set to null
	 */
	public static <T> List<T> getAllEntities(JpaRepository<T, Long> repository) {
		List<T> entities = repository.findAll();
        for (T entity : entities) {
            try {
                Field idField = entity.getClass().getDeclaredField("id");
                idField.setAccessible(true);
                idField.set(entity, null);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                logger.error("Failed to set ID field to null for entity: {}", entity.getClass().getName(), e);
            }
        }
        return entities;
	}

	/**
	 * Interface for entities that are identifiable by an ID.
	 */
	public interface Identifiable {
		Long getId();
	}
}