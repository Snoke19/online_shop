package com.shop.entity;

import com.shop.utils.RatingJsonConverter;
import com.shop.utils.products.Description;
import com.shop.utils.products.DescriptionCategory;
import com.shop.utils.products.Rating;
import com.shop.utils.JsonConverter;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonNodeBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class),
        @TypeDef(name = "jsonb-node", typeClass = JsonNodeBinaryType.class)
})
@Table(name = "products")
public class Product implements Serializable {

    private static final long serialVersionUID = -7973981687713701860L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_products")
    private Long idProduct;

    @Column(name = "name")
    private String name;

    @Column(name = "producer")
    private String producer;

    @Column(name = "description")
    @Convert(converter = JsonConverter.class)
    private List<DescriptionCategory> description;

    @Column(name = "price")
    private BigDecimal price;

    @Type(type = "json")
    @Column(name = "image", columnDefinition = "json")
    private List<byte[]> listImages;

    @Column(name = "isActive")
    private Boolean isActive;

    @Column(name = "code")
    private String code;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "discount")
    private Integer discount;
    
    @Column(name = "rating")
    @Convert(converter = RatingJsonConverter.class)
    private List<Rating> rating;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_category", nullable = false)
    private Category category;
}