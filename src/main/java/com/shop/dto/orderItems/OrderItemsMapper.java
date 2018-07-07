package com.shop.dto.orderItems;

import com.shop.dto.product.ProductDTO;
import com.shop.entity.OrderItems;
import com.shop.entity.Product;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderItemsMapper {

    OrderItemsMapper mapper = Mappers.getMapper(OrderItemsMapper.class);

    @IterableMapping(qualifiedByName="mapWithoutData")
    List<OrderItemsDTO> OrdersHistoryUserToDTO(List<OrderItems> orderItemsList);

    @Named("mapWithoutData")
    @Mappings({
            @Mapping(target = "orders.user", ignore = true),
            @Mapping(target = "product.isActive", ignore = true),
            @Mapping(target = "product.description", ignore = true),
            @Mapping(target = "product.producer", ignore = true),
            @Mapping(target = "product.code", ignore = true),
            @Mapping(target = "product.listImages", ignore = true)
    })
    OrderItemsDTO OrdersHistoryUserToDTO(OrderItems orderItems);


    @IterableMapping(qualifiedByName="orderItemsToOrdersItemsDTO")
    List<OrderItemsDTO> orderItemsToOrdersItemsDTO(List<OrderItems> orderItemsList);

    @Named("orderItemsToOrdersItemsDTO")
    @Mappings({
            @Mapping(target = "product.description", ignore = true)
    })
    OrderItemsDTO orderItemsToOrdersItemsDTO(OrderItems orderItems);
}