/*
 * @Author : Thant Htoo Aung
 * @Date : 19/08/2024
 * @Time : 12:25 PM
 * @Project_Name : cafe
 */
package com.bkk.cafe.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bkk.cafe.exception.EntityCreationException;
import com.bkk.cafe.exception.EntityNotFoundException;

/**
 * Utility class for common entity operations.
 */
public class EntityUtil {

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
		if (entities != null) {
			for (T entity : entities) {
				try {
					Field idField = entity.getClass().getDeclaredField("id");
					idField.setAccessible(true);
					idField.set(entity, null);
				} catch (NoSuchFieldException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		} else {
			entities = new ArrayList<>();
		}
		return entities;
	}

	/**
	 * Retrieves an entity by its ID from the repository and throws an exception if
	 * not found.
	 *
	 * Example Usage:
	 * 
	 * <pre>
	 * {@code
	 * User user = EntityUtil.getEntityById(userRepository, userDto.getId(), "User");
	 * }
	 * </pre>
	 *
	 * @param repository the repository for the entity
	 * @param id         the ID of the entity
	 * @param <T>        the type of the entity
	 * @return the retrieved entity with its ID set to null
	 */
	public static <T> T getEntityById(JpaRepository<T, Long> repository, Long id, String entityName) {
		T entity = id > 0 ? repository.findById(id).orElse(null) : null;
		if (entity == null) {
			throw new EntityNotFoundException(entityName + " not found with ID: " + id);
		}
		try {
			Field idField = entity.getClass().getDeclaredField("id");
			idField.setAccessible(true);
			idField.set(entity, null);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new RuntimeException("Failed to set ID to null for entity: " + entityName, e);
		}
		return entity;
	}

	/**
	 * Deletes an entity by its ID from the repository and throws an exception if
	 * not found.
	 *
	 * Example Usage:
	 * 
	 * <pre>
	 * {@code
	 * EntityUtil.deleteEntity(userRepository, userDto.getId(), "User");
	 * }
	 * </pre>
	 *
	 * @param repository the repository for the entity
	 * @param id         the ID of the entity to delete
	 * @param entityName the name of the entity
	 * @param <T>        the type of the entity
	 */
	public static <T> void deleteEntity(JpaRepository<T, Long> repository, Long id, String entityName) {
		if (!repository.existsById(id)) {
			throw new EntityNotFoundException(entityName + " not found with ID: " + id);
		}
		repository.deleteById(id);
	}

	/**
	 * Interface for entities that are identifiable by an ID.
	 */
	public interface Identifiable {
		Long getId();
	}
}