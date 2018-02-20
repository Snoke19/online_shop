package com.shop.dto.orders;

import com.shop.entity.Orders;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrdersMapper {

    OrdersMapper mapper = Mappers.getMapper(OrdersMapper.class);

    List<OrdersDTO> ordersToOrdersDTO(List<Orders> ordersList);

    @IterableMapping(qualifiedByName="newOrdersToDTO")
    List<OrdersDTO> newOrdersToDTO(List<Orders> ordersList);

    @Named("newOrdersToDTO")
    @Mappings({
            @Mapping(target = "phone", ignore = true),
            @Mapping(target = "status", ignore = true),
            @Mapping(target = "delivery", ignore = true),
            @Mapping(target = "user.address", ignore = true),
            @Mapping(target = "user.birthday", ignore = true),
            @Mapping(target = "user.phone", ignore = true),
            @Mapping(target = "user.enabled", ignore = true),
            @Mapping(target = "user.avatar", ignore = true)
    })
    OrdersDTO newOrderToDTO(Orders orders);


    @IterableMapping(qualifiedByName="inProcessOrdersToDTO")
    List<OrdersDTO> inProcessOrdersToDTO(List<Orders> ordersList);

    @Named("inProcessOrdersToDTO")
    @Mappings({
            @Mapping(target = "phone", ignore = true),
            @Mapping(target = "status", ignore = true),
            @Mapping(target = "delivery", ignore = true),
            @Mapping(target = "user.address", ignore = true),
            @Mapping(target = "user.birthday", ignore = true),
            @Mapping(target = "user.phone", ignore = true),
            @Mapping(target = "user.enabled", ignore = true),
            @Mapping(target = "user.avatar", ignore = true)
    })
    OrdersDTO inProcessOrdersToDTO(Orders orders);

    OrdersDTO ordersToOrdersDTO(Orders orders);

    Orders ordersDTOToOrders(OrdersDTO ordersDTO);
}