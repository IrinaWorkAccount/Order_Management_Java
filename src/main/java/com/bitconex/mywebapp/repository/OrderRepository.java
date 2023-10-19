package com.bitconex.mywebapp.repository;

import com.bitconex.mywebapp.model.Order;
import com.bitconex.mywebapp.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The `OrderRepository` is a repository interface for the `Order` entity. It provides access to basic CRUD (Create, Read, Update, Delete) operations for `Order` entities and additional custom query methods.
 */
@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    /**
     * Custom method to find an order by its ID.
     *
     * @param id The ID of the order to find.
     * @return An optional containing the order if found, otherwise an empty optional.
     */
    Optional<Order> findById(Long id);

    /**
     * Custom method to find all orders for a specific user.
     *
     * @param user The user for whom to retrieve orders.
     * @return A list of orders for the specified user.
     */
    List<Order> findAllByUser(User user);

    /**
     * Custom method to find all orders with a specific status for a user.
     *
     * @param user   The user for whom to retrieve orders.
     * @param status The status of the orders to find (e.g., "In Progress").
     * @return A list of orders with the specified status for the user.
     */
    List<Order> findAllByUserAndStatus(User user, String status);
}