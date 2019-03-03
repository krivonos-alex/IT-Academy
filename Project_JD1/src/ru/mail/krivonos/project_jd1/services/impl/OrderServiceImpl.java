package ru.mail.krivonos.project_jd1.services.impl;

import ru.mail.krivonos.project_jd1.repository.OrderRepository;
import ru.mail.krivonos.project_jd1.repository.connection.ConnectionService;
import ru.mail.krivonos.project_jd1.repository.connection.ConnectionServiceImpl;
import ru.mail.krivonos.project_jd1.repository.exceptions.OrderRepositoryException;
import ru.mail.krivonos.project_jd1.repository.impl.OrderRepositoryImpl;
import ru.mail.krivonos.project_jd1.repository.model.Order;
import ru.mail.krivonos.project_jd1.repository.model.OrderState;
import ru.mail.krivonos.project_jd1.services.OrderService;
import ru.mail.krivonos.project_jd1.services.converter.order.CreatedOrderConverterImpl;
import ru.mail.krivonos.project_jd1.services.converter.order.OrderForCustomerConverterImpl;
import ru.mail.krivonos.project_jd1.services.converter.order.OrderForSaleConverterImpl;
import ru.mail.krivonos.project_jd1.services.model.order.CreatedOrderDTO;
import ru.mail.krivonos.project_jd1.services.model.order.OrderForCustomerDTO;
import ru.mail.krivonos.project_jd1.services.model.order.OrderForSaleDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderServiceImpl implements OrderService {

    private static OrderService instance;

    private ConnectionService connectionService = ConnectionServiceImpl.getInstance();

    private OrderRepository orderRepository = OrderRepositoryImpl.getInstance();

    private OrderServiceImpl() {
    }

    public static OrderService getInstance() {
        if (instance == null) {
            instance = new OrderServiceImpl();
        }
        return instance;
    }

    @Override
    public void add(CreatedOrderDTO orderDTO) {
        try (Connection connection = connectionService.getConnection()) {
            try {
                connection.setAutoCommit(false);
                Order order = CreatedOrderConverterImpl.getInstance().fromDTO(orderDTO);
                orderRepository.add(connection, order);
                connection.commit();
            } catch (SQLException | OrderRepositoryException e) {
                System.out.println(e.getMessage());
                connection.rollback();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(OrderForSaleDTO orderDTO) {
        try (Connection connection = connectionService.getConnection()) {
            try {
                connection.setAutoCommit(false);
                Order order = OrderForSaleConverterImpl.getInstance().fromDTO(orderDTO);
                orderRepository.updateState(connection, order);
                connection.commit();
            } catch (SQLException | OrderRepositoryException e) {
                System.out.println(e.getMessage());
                connection.rollback();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(OrderForSaleDTO orderDTO) {
        try (Connection connection = connectionService.getConnection()) {
            try {
                connection.setAutoCommit(false);
                Order order = OrderForSaleConverterImpl.getInstance().fromDTO(orderDTO);
                orderRepository.delete(connection, order);
                connection.commit();
            } catch (SQLException | OrderRepositoryException e) {
                System.out.println(e.getMessage());
                connection.rollback();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<OrderForSaleDTO> getAll(Integer pageNumber) {
        try (Connection connection = connectionService.getConnection()) {
            try {
                connection.setAutoCommit(false);
                List<Order> orders = orderRepository.findAll(connection, pageNumber);
                if (!orders.isEmpty()) {
                    List<OrderForSaleDTO> orderForSaleDTOList = getOrderForSaleDTOList(orders);
                    connection.commit();
                    System.out.println("-------- " + orderForSaleDTOList.size() + " Orders Selected --------");
                    return orderForSaleDTOList;
                }
                connection.commit();
            } catch (SQLException | OrderRepositoryException e) {
                System.out.println(e.getMessage());
                connection.rollback();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        System.out.println("-------- No Orders Was Found --------");
        return Collections.emptyList();
    }

    @Override
    public List<OrderForSaleDTO> getAllByOrderState(Integer pageNumber, String orderState) {
        try (Connection connection = connectionService.getConnection()) {
            try {
                connection.setAutoCommit(false);
                OrderState state = OrderState.valueOf(orderState);
                List<Order> orders = orderRepository.findAllByOrderState(connection, pageNumber, state);
                if (!orders.isEmpty()) {
                    List<OrderForSaleDTO> orderForSaleDTOList = getOrderForSaleDTOList(orders);
                    connection.commit();
                    System.out.println("-------- " + orderForSaleDTOList.size() + " Orders Selected --------");
                    return orderForSaleDTOList;
                }
                connection.commit();
            } catch (SQLException | OrderRepositoryException e) {
                System.out.println(e.getMessage());
                connection.rollback();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        System.out.println("-------- No Orders Was Found --------");
        return Collections.emptyList();
    }

    @Override
    public List<OrderForCustomerDTO> getAllForUser(Long userID, Integer pageNumber) {
        try (Connection connection = connectionService.getConnection()) {
            try {
                connection.setAutoCommit(false);
                List<Order> orders = orderRepository.findAllByUserID(connection, userID, pageNumber);
                if (!orders.isEmpty()) {
                    List<OrderForCustomerDTO> orderDTOList = getOrderForCustomerDTOList(orders);
                    connection.commit();
                    System.out.println("-------- " + orderDTOList.size() + " Orders Selected --------");
                    return orderDTOList;
                }
                connection.commit();
            } catch (SQLException | OrderRepositoryException e) {
                System.out.println(e.getMessage());
                connection.rollback();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        System.out.println("-------- No Orders Was Found --------");
        return Collections.emptyList();
    }

    private List<OrderForSaleDTO> getOrderForSaleDTOList(List<Order> orders) {
        List<OrderForSaleDTO> orderDTOList = new ArrayList<>();
        for (Order order : orders) {
            OrderForSaleDTO orderDTO = OrderForSaleConverterImpl.getInstance().toDTO(order);
            orderDTOList.add(orderDTO);
        }
        return orderDTOList;
    }

    private List<OrderForCustomerDTO> getOrderForCustomerDTOList(List<Order> orders) {
        List<OrderForCustomerDTO> orderDTOList = new ArrayList<>();
        for (Order order : orders) {
            OrderForCustomerDTO orderDTO = OrderForCustomerConverterImpl.getInstance().toDTO(order);
            orderDTOList.add(orderDTO);
        }
        return orderDTOList;
    }
}