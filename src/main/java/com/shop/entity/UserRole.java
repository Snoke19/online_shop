package com.shop.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_role")
public class UserRole implements Serializable {

    private static final long serialVersionUID = -4318617034139784761L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user_role")
    private Long idUserRole;

    @Column(name = "role")
    private String role;

    @ManyToOne(optional = false, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;
}