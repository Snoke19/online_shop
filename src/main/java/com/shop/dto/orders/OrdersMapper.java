package com.shop.dto.orders;

import com.shop.entity.Orders;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrdersMapper {

    OrdersMapper mapper = Mappers.getMapper(OrdersMapper.class);

    List<OrdersDTO> ordersToOrdersDTO(List<Orders> ordersList);

    @IterableMapping(qualifiedByName="ordersToDTO")
    List<OrdersDTO> listOrdersDTO(List<Orders> ordersList);

    @Named("ordersToDTO")
    @Mappings({
            @Mapping(target = "phone", ignore = true),
            @Mapping(target = "delivery", ignore = true),
            @Mapping(target = "user.address", ignore = true),
            @Mapping(target = "user.birthday", ignore = true),
            @Mapping(target = "user.phone", ignore = true),
            @Mapping(target = "user.enabled", ignore = true)
    })
    OrdersDTO orderToDTO(Orders orders);


    OrdersDTO ordersToOrdersDTO(Orders orders);

    Orders ordersDTOToOrders(OrdersDTO ordersDTO);
}